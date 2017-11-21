package fi.alekster.classical.controllers;

import fi.alekster.classical.controllers.utils.UserUtils;
import fi.alekster.classical.dao.ExWatchdogAuthorDao;
import fi.alekster.classical.dao.ExWatchdogDao;
import fi.alekster.classical.dao.ExWatchdogGenreDao;
import fi.alekster.classical.dao.ExWatchdogVenueDao;
import fi.alekster.classical.db.tables.pojos.Watchdog;
import fi.alekster.classical.representations.requests.CreateWatchdogRequest;
import fi.alekster.classical.representations.requests.WatchdogView;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotAuthorizedException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by aleksandr on 17.11.2017.
 */

@RestController
@RequestMapping("/watchdog")
public class WatchdogController {
    private final ExWatchdogDao watchdogDao;
    private final ExWatchdogAuthorDao watchdogAuthorDao;
    private final ExWatchdogGenreDao watchdogGenreDao;
    private final ExWatchdogVenueDao watchdogVenueDao;

    private final UserUtils userUtils;

    @Autowired
    public WatchdogController (
            ExWatchdogDao watchdogDao,
            ExWatchdogAuthorDao watchdogAuthorDao,
            ExWatchdogGenreDao watchdogGenreDao,
            ExWatchdogVenueDao watchdogVenueDao,
            UserUtils userUtils
    ) {
        this.watchdogDao = watchdogDao;
        this.watchdogAuthorDao = watchdogAuthorDao;
        this.watchdogGenreDao = watchdogGenreDao;
        this.watchdogVenueDao = watchdogVenueDao;

        this.userUtils = userUtils;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createWatchdog(
            HttpServletRequest httpServletRequest,
            @RequestBody CreateWatchdogRequest request
    ) {
        Timestamp startTimestamp = null;
        Timestamp endTimestamp = null;

        if (request.getStartDate() != null) {
            try {
                DateTime dateTime = ISODateTimeFormat.dateTimeParser().parseDateTime(request.getStartDate());
                startTimestamp = Timestamp.valueOf(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
            } catch (Exception ex) {
                startTimestamp = Timestamp.valueOf("1970-01-01 02:00:00");
            }
        }


        if (request.getEndDate() != null) {
            try {
                DateTime dateTime = ISODateTimeFormat.dateTimeParser().parseDateTime(request.getEndDate());
                endTimestamp = Timestamp.valueOf(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
            } catch (Exception ex) {
                endTimestamp = Timestamp.valueOf("1970-01-01 02:00:00");
            }
        }

        watchdogDao.insert(
                new Watchdog(
                        watchdogDao.count(),
                        userUtils.getAuthenticatedCredential(httpServletRequest).getEmail(),
                        startTimestamp,
                        endTimestamp,
                        request.getKeyPhrase()
                ),
                request.getAuthorIds(),
                request.getGenreIds(),
                request.getVenueIds()
        );

        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> removeWatchdog(
            HttpServletRequest httpServletRequest,
            @PathVariable("id") Long id
    ) {
        if (Objects.equals(
                watchdogDao.fetchOneById(id).getEmail(),
                userUtils.getAuthenticatedCredential(httpServletRequest).getEmail()
        )) {
            watchdogAuthorDao.deleteByWatchdogId(id);
            watchdogVenueDao.deleteByWatchdogId(id);
            watchdogGenreDao.deleteByWatchdogId(id);
            watchdogDao.deleteById(id);

            return ResponseEntity.ok().build();
        }

        throw new NotAuthorizedException("Unauthorized: this watchdog does not belong to you");
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<WatchdogView> getWatchdogs(
            HttpServletRequest httpServletRequest
    ) {
        return watchdogDao.fetchByEmail(userUtils.getAuthenticatedCredential(httpServletRequest).getEmail())
                .stream()
                .map(p -> WatchdogView.fromEntity(
                        p,
                        watchdogAuthorDao.fetchByWatchdogId(p.getId()).stream().map(s -> s.getAuthorId()).collect(Collectors.toList()),
                        watchdogVenueDao.fetchByWatchdogId(p.getId()).stream().map(s -> s.getVenueId()).collect(Collectors.toList()),
                        watchdogGenreDao.fetchByWatchdogId(p.getId()).stream().map(s -> s.getGenreId()).collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public WatchdogView getWatchdog(
            HttpServletRequest httpServletRequest,
            @PathVariable("id") Long id
    ) {
        Watchdog fetchedWG = watchdogDao.fetchOneById(id);

        if (Objects.equals(fetchedWG.getEmail(), userUtils.getAuthenticatedCredential(httpServletRequest).getEmail())) {
            return WatchdogView.fromEntity(
                    fetchedWG,
                    watchdogAuthorDao.fetchByWatchdogId(fetchedWG.getId()).stream().map(s -> s.getAuthorId()).collect(Collectors.toList()),
                    watchdogVenueDao.fetchByWatchdogId(fetchedWG.getId()).stream().map(s -> s.getVenueId()).collect(Collectors.toList()),
                    watchdogGenreDao.fetchByWatchdogId(fetchedWG.getId()).stream().map(s -> s.getGenreId()).collect(Collectors.toList())
            );
        }

        throw new NotAuthorizedException("Unauthorized: this watchdog does not belong to you");
    }
}
