package fi.alekster.classical.controllers;

import fi.alekster.classical.dao.*;
import fi.alekster.classical.db.tables.pojos.Gig;
import fi.alekster.classical.representations.*;
import fi.alekster.classical.representations.responses.GigResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @Autowired
    public GigController(
            ExGigDao gigDao,
            ExPerformanceDao performanceDao,
            ExAuthorDao authorDao,
            ExGenreDao genreDao,
            ExVenueDao venueDao
    ) {
        this.gigDao = gigDao;
        this.performanceDao = performanceDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.venueDao = venueDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public GigResponse getGigs(
            @RequestParam(value = "keyPhrase", defaultValue = "", required = false) String keyPhrase,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "author", defaultValue = "", required = false) Long authorId,
            @RequestParam(value = "genre", required = false) List<Long> genreIds,
            @RequestParam(value = "venue", defaultValue = "", required = false) Long venueId
    ) {
        Long count = gigDao.count(keyPhrase, authorId, genreIds, venueId);
        List<GigView> gigs = gigDao.fetch(keyPhrase, limit, offset, authorId, genreIds, venueId)
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
}
