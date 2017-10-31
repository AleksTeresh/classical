package fi.alekster.classical.controllers;

import fi.alekster.classical.dao.ExAuthorDao;
import fi.alekster.classical.dao.ExGigDao;
import fi.alekster.classical.dao.ExPerformanceDao;
import fi.alekster.classical.representations.AuthorView;
import fi.alekster.classical.representations.GigView;
import fi.alekster.classical.representations.PerformanceView;
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

    @Autowired
    public GigController(
            ExGigDao gigDao,
            ExPerformanceDao performanceDao,
            ExAuthorDao authorDao
    ) {
        this.gigDao = gigDao;
        this.performanceDao = performanceDao;
        this.authorDao = authorDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<GigView> getGigs(
            @RequestParam(value = "keyPhrase", defaultValue = "", required = false) String keyPhrase,
            @RequestParam(value = "limit", defaultValue = "10") String limit,
            @RequestParam(value = "offset", defaultValue = "0") String offset,
            @RequestParam(value = "author", defaultValue = "", required = false) String author
    ) {
        return gigDao.findAll().stream()
                .map(p -> GigView.fromEntity(
                        p,
                        performanceDao.fetchByGigId(p.getId())
                                .stream()
                                .map(s -> PerformanceView.fromEntity(
                                        s,
                                        AuthorView.fromEntity(authorDao.fetchOneById(s.getAuthorId()))
                                ))
                                .collect(Collectors.toList())
                )).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public GigView getGig(@PathVariable("id") Long id) {
        return GigView.fromEntity(
                gigDao.fetchOneById(id),
                performanceDao.fetchByGigId(id)
                    .stream()
                    .map(p -> PerformanceView.fromEntity(
                            p,
                            AuthorView.fromEntity(authorDao.fetchOneById(p.getAuthorId()))
                    ))
                    .collect(Collectors.toList())
        );
    }
}
