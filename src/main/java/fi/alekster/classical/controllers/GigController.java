package fi.alekster.classical.controllers;

import fi.alekster.classical.controllers.model.GigDetailed;
import fi.alekster.classical.controllers.utils.*;
import fi.alekster.classical.dao.*;
import fi.alekster.classical.db.tables.pojos.*;
import fi.alekster.classical.representations.*;
import fi.alekster.classical.representations.requests.DetailedGigResponse;
import fi.alekster.classical.representations.requests.GigRequest;
import fi.alekster.classical.representations.requests.PerformanceRequest;
import fi.alekster.classical.representations.responses.GigResponse;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by aleksandr on 31.10.2017.
 */
@RestController
@RequestMapping("/gig")
public class GigController {
    private final ExGigDao gigDao;
    private final ExPerformanceDao performanceDao;
    private final ExAuthorDao authorDao;
    private final ExGenreDao genreDao;
    // private final ExVenueDao venueDao;
    private final ExLikeDao likeDao;

    private final CommonUtils commonUtils;
    private final PerformanceUtils performanceUtils;
    private final AuthorUtils authorUtils;
    private final GenreUtils genreUtils;
    private final DurationUtils durationUtils;
    private final UserUtils userUtils;
    private final GigUtils gigUtils;

    @Autowired
    public GigController(
            ExGigDao gigDao,
            ExPerformanceDao performanceDao,
            ExAuthorDao authorDao,
            ExGenreDao genreDao,
            // ExVenueDao venueDao,
            ExLikeDao likeDao,
            CommonUtils commonUtils,
            PerformanceUtils performanceUtils,
            AuthorUtils authorUtils,
            GenreUtils genreUtils,
            DurationUtils durationUtils,
            UserUtils userUtils,
            GigUtils gigUtils
    ) {
        System.out.println("Constructor has been called");
        this.gigDao = gigDao;
        this.performanceDao = performanceDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        // this.venueDao = venueDao;
        this.likeDao = likeDao;
        this.gigUtils = gigUtils;

        this.commonUtils = commonUtils;
        this.performanceUtils = performanceUtils;
        this.authorUtils = authorUtils;
        this.genreUtils = genreUtils;
        this.durationUtils = durationUtils;
        this.userUtils = userUtils;
    }

    @RequestMapping(method = RequestMethod.GET)
    public GigResponse getGigs(
            @RequestParam(value = "keyPhrase", defaultValue = "", required = false) String keyPhrase,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "authors", required = false) List<Long> authorIds,
            @RequestParam(value = "genres", required = false) List<Long> genreIds,
            @RequestParam(value = "venues", required = false) List<Long> venueIds,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate
    ) {
        Long count = gigDao.count(keyPhrase, authorIds, genreIds, venueIds, startDate, endDate);
        List<GigView> gigs = gigDao.fetch(keyPhrase, limit, offset, authorIds, genreIds, venueIds, startDate, endDate)
                .stream()
                .map(GigView::fromEntity)
                .collect(Collectors.toList());

        return new GigResponse(gigs, count);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public DetailedGigResponse getGig(HttpServletRequest httpServletRequest, @PathVariable("id") Long id) {
        String authHeader = httpServletRequest.getHeader("Authorization");
        String userEmail = (authHeader != null && !Objects.equals(authHeader, ""))
                ? userUtils.getAuthenticatedCredential(httpServletRequest).getEmail()
                : "";

        Gig fetchedGig = gigDao.fetchOneById(id);
        GigView gigView = GigView.fromEntity(
                fetchedGig
                // VenueView.fromEntity(venueDao.fetchOneById(fetchedGig.getVenueId()))
        );

        List<PerformanceView> performances = getPerformanceViewsById(id, userEmail);

        Date today = new Date();
        DateTime yearAhead = new DateTime(today).plusYears(1);
        List<Gig> suggestions = gigDao.fetch(
                "",
                40,
                0,
                performances.stream()
                    .filter(p -> !Objects.equals(p.getAuthor().getName(), "No author") && p.getAuthor().getId() != 1)
                    .map(p -> p.getAuthor().getId())
                    .collect(Collectors.toList()),
                null,
                null,
                new Date(),
                yearAhead.toDate()
                );

        suggestions = gigUtils.filterSuggestions(fetchedGig, suggestions);

        List<GigDetailed> detailedSuggestions = suggestions.stream()
                .map(s -> new GigDetailed(
                        s,
                        getPerformanceViewsById(s.getId(), userEmail)
                        )
                )
                .collect(Collectors.toList());

        List<Like> likes = Objects.equals(userEmail, "")
                ? new ArrayList<>()
                : likeDao.fetchByEmail(userEmail);

        List<Long> likedAuthorIds = getLikedAuthorIds(likes);
        detailedSuggestions = gigUtils.sortSuggestions(
                new GigDetailed(fetchedGig, performances),
                detailedSuggestions,
                likedAuthorIds
        );

        return new DetailedGigResponse(
                gigView,
                performances,
                detailedSuggestions.subList(0, Math.min(detailedSuggestions.size(), 3))
                        .stream()
                        .map(s -> GigView.fromEntity(s.getGig()))
                        .collect(Collectors.toList())
        );
    }

    @RequestMapping(method = RequestMethod.POST)
    public GigView createGig(HttpServletRequest httpServletRequest, @RequestBody GigRequest input) {
        Timestamp timestamp = commonUtils.stringToTimestamp(input.getTimestamp());

        // check if the gig already exists, and if so, return the existing gig
        if (gigDao.exists(input.getName(), timestamp, input.getVenue())) {
            Optional<Gig> existingGig = gigDao.fetchByTimestamp(timestamp)
                    .stream()
                    .filter(p -> p.getName().contains(input.getName()) && Objects.equals(p.getVenueId(), input.getVenue()))
                    .findFirst();
            if (existingGig.isPresent()) {
                return getGigView(existingGig.get().getId());
            }
        }

        // create authors that are featured in the gig, but do not exist in the DB yet
        createMissingAuthors(input.getPerformances());

        List<Author> authors = authorDao.findAll();
        List<String> authorNames = authors.stream()
                .map(Author::getName)
                .collect(Collectors.toList());
        List<Performance> performances = input.getPerformances()
                .stream()
                .map(p -> performanceUtils.requestToPerformance(p, authors))
                .collect(Collectors.toList());

        Gig newGig = new Gig(
                gigDao.count() + 1,
                input.getVenue(),
                input.getName(),
                // if the venue is Mariinsky, change all the links to direct to mariinky website
                input.getVenue() == 2
                ? input.getDescription().replaceAll("a href=\"", "a href=\"https://www.mariinsky.ru")
                : input.getDescription(),
                timestamp,
                durationUtils.stringToMilliseconds(input.getDuration()),
                input.getImageUrl(),
                input.getUrl(),
                Timestamp.valueOf(new DateTime().toString("yyyy-MM-dd HH:mm:ss"))
        );
        gigDao.insert(newGig);

        insertNewPerformances(performances, authors, newGig);

        return getGigView(newGig.getId());
    }

    private List<Long> getLikedAuthorIds (List<Like> likes) {
        return likes.stream()
                .map(p -> performanceDao.fetchOneById(p.getPerformanceId()).getAuthorId())
                .collect(Collectors.toList());
    }

    private void insertNewPerformances (List<Performance> performances, List<Author> authors, Gig newlyCreatedGig) {
        List<Genre> genres = genreDao.findAll();
        performances.forEach(p -> {
                    p.setGigId(newlyCreatedGig.getId());
                    p.setId(performanceDao.count() + 1);

                    Optional<Genre> matchGenre = genreUtils.getMatchingGenre(p, genres, authors);

                    if (matchGenre.isPresent()) {
                        performanceDao.insert(p, matchGenre.get().getId());
                    } else { // if no genre was defined, set the genre to be Others and persist the performance
                        Genre otherGenre = genres.stream()
                                .filter(s -> Objects.equals(s.getName(), "Other"))
                                .findFirst().get();
                        performanceDao.insert(p, otherGenre.getId());
                    }
                });
    }

    private List<PerformanceView> getPerformanceViewsById (Long id, String userEmail) {
        return performanceDao.fetchByGigId(id)
                .stream()
                .map(p -> PerformanceView.fromEntity(
                        p,
                        AuthorView.fromEntity(authorDao.fetchOneById(p.getAuthorId())),
                        genreDao.fetchByPerformanceId(p.getId())
                                .stream()
                                .map(GenreView::fromEntity)
                                .collect(Collectors.toList()),
                        !Objects.equals(userEmail, "") && !likeDao.fetchByEmailAndPerformanceId(userEmail, p.getId()).isEmpty()
                ))
                .collect(Collectors.toList());
    }

    private void createMissingAuthors (List<PerformanceRequest> performances) {
        performances
                .forEach(p -> {
                    if (p.getAuthor() == null || Objects.equals(p.getAuthor(), "")) {
                        return;
                    }

                    List<String> authorNames = authorDao.findAll().stream()
                            .map(Author::getName)
                            .collect(Collectors.toList());
                    ExtractedResult result = FuzzySearch.extractOne(p.getAuthor(), authorNames);
                    if (result.getScore() < 75) {
                        Author newAuthor = authorUtils.constructAuthor(p.getAuthor(), authorDao.count() + 1);
                        authorDao.insert(newAuthor);
                    }
                });
    }

    private GigView getGigView (Long id) {
        Gig fetchedGig = gigDao.fetchOneById(id);

        return GigView.fromEntity(
                fetchedGig
                // VenueView.fromEntity(venueDao.fetchOneById(fetchedGig.getVenueId()))
        );
    }
}
