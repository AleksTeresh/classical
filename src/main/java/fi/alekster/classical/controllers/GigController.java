package fi.alekster.classical.controllers;

import fi.alekster.classical.controllers.utils.*;
import fi.alekster.classical.dao.*;
import fi.alekster.classical.db.tables.pojos.Author;
import fi.alekster.classical.db.tables.pojos.Genre;
import fi.alekster.classical.db.tables.pojos.Gig;
import fi.alekster.classical.db.tables.pojos.Performance;
import fi.alekster.classical.representations.*;
import fi.alekster.classical.representations.requests.GigRequest;
import fi.alekster.classical.representations.requests.PerformanceRequest;
import fi.alekster.classical.representations.responses.GigResponse;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

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
    private final ExVenueDao venueDao;

    private final CommonUtils commonUtils;
    private final PerformanceUtils performanceUtils;
    private final AuthorUtils authorUtils;
    private final GenreUtils genreUtils;
    private final DurationUtils durationUtils;

    @Autowired
    public GigController(
            ExGigDao gigDao,
            ExPerformanceDao performanceDao,
            ExAuthorDao authorDao,
            ExGenreDao genreDao,
            ExVenueDao venueDao,
            CommonUtils commonUtils,
            PerformanceUtils performanceUtils,
            AuthorUtils authorUtils,
            GenreUtils genreUtils,
            DurationUtils durationUtils
    ) {
        System.out.println("Constructor has been called");
        this.gigDao = gigDao;
        this.performanceDao = performanceDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.venueDao = venueDao;

        this.commonUtils = commonUtils;
        this.performanceUtils = performanceUtils;
        this.authorUtils = authorUtils;
        this.genreUtils = genreUtils;
        this.durationUtils = durationUtils;
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
        if (startDate == null) {
            startDate = new Date();
        }
        Long count = gigDao.count(keyPhrase, authorIds, genreIds, venueIds, startDate, endDate);
        List<GigView> gigs = gigDao.fetch(keyPhrase, limit, offset, authorIds, genreIds, venueIds, startDate, endDate)
                .stream()
                .map(p -> GigView.fromEntity(
                        p,
                        performanceDao.fetchByGigId(p.getId())
                                .stream()
                                .map(s -> PerformanceView.fromEntity(
                                        s,
                                        AuthorView.fromEntity(authorDao.fetchOneById(s.getAuthorId())),
                                        genreDao.fetchByPerformanceId(s.getId())
                                                .stream()
                                                .map(GenreView::fromEntity)
                                                .collect(Collectors.toList())
                                ))
                                .collect(Collectors.toList()),
                        VenueView.fromEntity(venueDao.fetchOneById(p.getVenueId()))
                )).collect(Collectors.toList());

        return new GigResponse(gigs, count);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public GigView getGig(@PathVariable("id") Long id) {
        Gig fetchedGig = gigDao.fetchOneById(id);

        return GigView.fromEntity(
                fetchedGig,
                performanceDao.fetchByGigId(id)
                    .stream()
                    .map(p -> PerformanceView.fromEntity(
                            p,
                            AuthorView.fromEntity(authorDao.fetchOneById(p.getAuthorId())),
                            genreDao.fetchByPerformanceId(p.getId())
                                    .stream()
                                    .map(GenreView::fromEntity)
                                    .collect(Collectors.toList())
                    ))
                    .collect(Collectors.toList()),
                VenueView.fromEntity(venueDao.fetchOneById(fetchedGig.getVenueId()))
        );
    }

    @RequestMapping(method = RequestMethod.POST)
    public GigView createGig(@RequestBody GigRequest input) {
        Timestamp timestamp = commonUtils.stringToTimestamp(input.getTimestamp());

        // check if the gig already exists, and if so, return the existing gig
        if (gigDao.exists(input.getName(), timestamp, input.getVenue())) {
            Optional<Gig> existingGig = gigDao.fetchByTimestamp(timestamp)
                    .stream()
                    .filter(p -> p.getName().contains(input.getName()) && p.getVenueId() == input.getVenue())
                    .findFirst();
            if (existingGig.isPresent()) {
                return getGig(existingGig.get().getId());
            }
        }

        // create authors that are featured in the gig, but do not exist in the DB yet
        createMissingAuthors(input.getPerformances());

        List<Author> authors = authorDao.findAll();
        List<String> authorNames = authors.stream()
                .map(s -> s.getName())
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

        return getGig(newGig.getId());
    }

    private void insertNewPerformances (List<Performance> performances, List<Author> authors, Gig newlyCreatedGig) {
        List<Genre> genres = genreDao.findAll();
        performances.stream()
                .forEach(p -> {
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

    private void createMissingAuthors (List<PerformanceRequest> performances) {
        performances
                .stream()
                .forEach(p -> {
                    List<String> authorNames = authorDao.findAll().stream()
                            .map(s -> s.getName())
                            .collect(Collectors.toList());
                    ExtractedResult result = FuzzySearch.extractOne(p.getAuthor(), authorNames);
                    if (result.getScore() < 75) {
                        Author newAuthor = authorUtils.constructAuthor(p.getAuthor(), authorDao.count() + 1);
                        authorDao.insert(newAuthor);
                    }
                });
    }

}
