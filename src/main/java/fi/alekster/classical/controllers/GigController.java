package fi.alekster.classical.controllers;

import fi.alekster.classical.dao.*;
import fi.alekster.classical.db.tables.pojos.Author;
import fi.alekster.classical.db.tables.pojos.Genre;
import fi.alekster.classical.db.tables.pojos.Gig;
import fi.alekster.classical.db.tables.pojos.Performance;
import fi.alekster.classical.email.EmailHandler;
import fi.alekster.classical.representations.*;
import fi.alekster.classical.representations.requests.GigRequest;
import fi.alekster.classical.representations.requests.PerformanceRequest;
import fi.alekster.classical.representations.responses.GigResponse;
import fi.alekster.classical.wikipedia.WikiFetcher;
import fi.alekster.classical.youtube.YoutubeSearcher;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
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

    private final YoutubeSearcher youtubeSearcher;
    private final WikiFetcher wikiFetcher;
    private final EmailHandler emailHandler;

    @Autowired
    public GigController(
            ExGigDao gigDao,
            ExPerformanceDao performanceDao,
            ExAuthorDao authorDao,
            ExGenreDao genreDao,
            ExVenueDao venueDao,
            YoutubeSearcher youtubeSearcher,
            WikiFetcher wikiFetcher,
            EmailHandler emailHandler
    ) {
        System.out.println("Constructor has been called");
        this.gigDao = gigDao;
        this.performanceDao = performanceDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.venueDao = venueDao;

        this.youtubeSearcher = youtubeSearcher;
        this.wikiFetcher = wikiFetcher;
        this.emailHandler = emailHandler;
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

        Timestamp timestamp;
        try {
            System.out.println(input.getTimestamp());
            DateTime dateTime = ISODateTimeFormat.dateTimeParser().parseDateTime(input.getTimestamp());
            timestamp = Timestamp.valueOf(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception ex) {
            timestamp = Timestamp.valueOf("1970-01-01 02:00:00");
        }

        if (gigDao.exists(input.getName(), timestamp, input.getVenue())) {
            Optional<Gig> existingGig = gigDao.fetchByTimestamp(timestamp)
                    .stream()
                    .filter(p -> p.getName().contains(input.getName()) && p.getVenueId() == input.getVenue())
                    .findFirst();
            if (existingGig.isPresent()) {
                return getGig(existingGig.get().getId());
            }
        }

        input.getPerformances()
                .stream()
                .forEach(p -> {
                    List<String> authorNames = authorDao.findAll().stream()
                            .map(s -> s.getName())
                            .collect(Collectors.toList());
                    ExtractedResult result = FuzzySearch.extractOne(p.getAuthor(), authorNames);
                    if (result.getScore() < 75) {
                        Author newAuthor = constructAuthor(p.getAuthor());
                        authorDao.insert(newAuthor);
                    }
                });

        List<Author> authors = authorDao.findAll();
        List<String> authorNames = authors.stream()
                .map(s -> s.getName())
                .collect(Collectors.toList());
        List<Performance> performances = input.getPerformances()
                .stream()
                .map(p -> {
                    ExtractedResult result = FuzzySearch.extractOne(p.getAuthor(), authorNames);
                    Long authorId = authorDao.fetchByName(result.getString()).stream().findFirst().get().getId();

                    return constructPerformances(
                            p,
                            0L,
                            youtubeSearcher.getYoutubeId(p.getAuthor() + " " +p.getName()),
                            authorId
                    );
                })
                .collect(Collectors.toList());

        Gig newGig = new Gig(
                gigDao.count() + 1,
                input.getVenue(),
                input.getName(),
                input.getDescription(),
                timestamp,
                0,
                input.getImageUrl(),
                input.getUrl(),
                Timestamp.valueOf(new DateTime().toString("yyyy-MM-dd HH:mm:ss"))
        );
        gigDao.insert(newGig);

        List<Genre> genres = genreDao.findAll();
        List<String> genreNames = genres
                .stream()
                .map(p -> p.getName())
                .collect(Collectors.toList());
        performances.stream()
            .forEach(p -> {
                p.setGigId(newGig.getId());
                p.setId(performanceDao.count() + 1);

                // List<Genre> matchGenres = new ArrayList<>();
                Optional<Genre> matchGenre = Optional.empty();
                int maxPartialRation = -1;

                for (Genre genre : genres) {
                    try {
                        int partialRatio = FuzzySearch.partialRatio(genre.getName(), p.getName());

                        if (partialRatio >= 75 && (!matchGenre.isPresent() || maxPartialRation < partialRatio)) {
                            matchGenre = Optional.of(genre);
                            maxPartialRation = partialRatio;
                        }
                    } catch (Exception ex) {}
                }

                if (!matchGenre.isPresent()) {
                    // TODO: add error handling here
                    String authorName = authors.stream()
                            .filter(a -> a.getId() == p.getAuthorId())
                            .findFirst().get().getName();

                    String firstSentence = wikiFetcher.fetchFirstSentence(p.getName() + " " + authorName);
                    if (firstSentence == null || firstSentence == "") {
                        firstSentence = wikiFetcher.fetchFirstSentence(authorName + " " + p.getName());
                    }
                    if (firstSentence == null || firstSentence == "") {
                        firstSentence = wikiFetcher.fetchFirstSentence(p.getName());
                    }

                    if (firstSentence != null && firstSentence != "") {
                        for (Genre genre : genres) {
                            try {
                                int partialRatio = FuzzySearch.partialRatio(genre.getName(), firstSentence);

                                if (partialRatio >= 75 && (!matchGenre.isPresent() || maxPartialRation < partialRatio)) {
                                    matchGenre = Optional.of(genre);
                                    maxPartialRation = partialRatio;
                                }
                            } catch (Exception ex) {}
                        }
                    }
                }

                if (matchGenre.isPresent()) {
                    performanceDao.insert(p, matchGenre.get().getId());
                } else {
                    Genre otherGenre = genres.stream()
                            .filter(s -> Objects.equals(s.getName(), "Other"))
                            .findFirst().get();
                    performanceDao.insert(p, otherGenre.getId());
                }
            });

        return getGig(newGig.getId());
    }

    private Author constructAuthor(String name) {
        return new Author(
                authorDao.count() + 1,
                name,
                wikiFetcher.fetchDescription(name),
                wikiFetcher.fetchUrl(name),
                "" // TODO: use wikiFetcher to get an actual image
        );
    }

    private Performance constructPerformances(PerformanceRequest perfReqs, Long gigId, String youTubeId, Long authorId) {
        return new Performance(
                0L,
                authorId, // authorId
                gigId, // gigId
                perfReqs.getName(),
                "", // description
                "", // conductor
                "", // players
                youTubeId
        );
    }
}
