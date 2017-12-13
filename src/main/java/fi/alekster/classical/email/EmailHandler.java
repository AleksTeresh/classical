package fi.alekster.classical.email;

import fi.alekster.classical.config.MailgunConfigs;
import fi.alekster.classical.dao.*;
import fi.alekster.classical.db.tables.pojos.Gig;
import fi.alekster.classical.db.tables.pojos.Performance;
import net.sargue.mailgun.Mail;
import net.sargue.mailgun.content.Body;
import net.sargue.mailgun.content.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by aleksandr on 15.11.2017.
 */
@Component
public class EmailHandler {
    private final MailgunConfigs mailgunConfig;

    private final ExGigDao gigDao;
    private final ExWatchdogDao watchdogDao;
    private final ExWatchdogAuthorDao watchdogAuthorDao;
    private final ExWatchdogGenreDao watchdogGenreDao;
    private final ExWatchdogVenueDao watchdogVenueDao;
    private final ExVenueDao venueDao;
    private final ExAuthorDao authorDao;
    private final ExPerformanceDao performanceDao;

    @Autowired
    public EmailHandler(
            MailgunConfigs mailgunConfig,
            ExUserDao userDao,
            ExGigDao gigDao,
            ExWatchdogDao watchdogDao,
            ExWatchdogAuthorDao watchdogAuthorDao,
            ExWatchdogGenreDao watchdogGenreDao,
            ExWatchdogVenueDao watchdogVenueDao,
            ExVenueDao venueDao,
            ExAuthorDao authorDao,
            ExPerformanceDao performanceDao
    ) {
        this.mailgunConfig = mailgunConfig;

        this.gigDao = gigDao;
        this.watchdogDao = watchdogDao;
        this.watchdogAuthorDao = watchdogAuthorDao;
        this.watchdogGenreDao = watchdogGenreDao;
        this.watchdogVenueDao = watchdogVenueDao;
        this.venueDao = venueDao;
        this.authorDao = authorDao;
        this.performanceDao = performanceDao;
    }

    // run at 7:30 UTC time every Monday
    @Scheduled(cron = "30 7 * * * 1")
    public void sendNewGigNotification() {
        watchdogDao.findAll()
                .forEach(p -> {
                    System.out.println("Found a watchdog");
                    List<Long> authorIds = p.getAllAuthors()
                    ? null
                    : watchdogAuthorDao.fetchByWatchdogId(p.getId())
                            .stream()
                            .map(s -> s.getAuthorId())
                            .collect(Collectors.toList());
                    List<Long> venueIds = watchdogVenueDao.fetchByWatchdogId(p.getId())
                            .stream()
                            .map(s -> s.getVenueId())
                            .collect(Collectors.toList());
                    List<Long> genreIds = p.getAllGenres()
                    ? null
                    : watchdogGenreDao.fetchByWatchdogId(p.getId())
                            .stream()
                            .map(s -> s.getGenreId())
                            .collect(Collectors.toList());

                    List<Gig> newSuitableGigs = gigDao.fetch(
                            p.getKeyPhrase(),
                            100,
                            0,
                            authorIds,
                            genreIds,
                            venueIds,
                            p.getStartDate(),
                            p.getEndDate(),
                            new Timestamp(new Date().getTime() - TimeUnit.DAYS.toMillis(7))
                            );
                    System.out.println(newSuitableGigs.size());
                    if (!newSuitableGigs.isEmpty()) {
                        Body body = getMailBody(newSuitableGigs);
                        System.out.println("About to send the message");
                        Mail.using(mailgunConfig.getConfiguration())
                                .to(p.getEmail())
                                .subject("New gigs on Classical")
                                .content(body)
                                .build()
                                .send();

                        System.out.println("Sent");
                    }
                });
    }

    private Body getMailBody (List<Gig> gigs) {
        Builder builder = Body.builder()
                .p("There are new gigs that match your interest!");

        for (int i = 0; i < gigs.size(); i++) {
            String venueName = venueDao.fetchOneById(gigs.get(i).getVenueId()).getName();
            List<Performance> performances = performanceDao
                    .fetchByGigId(gigs.get(i).getId());

            builder = builder.h2(gigs.get(i).getName())
                    .p("Takes place on " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
                            .format(gigs.get(i).getTimestamp()))
                    .p("The venue is " + venueName);

            if (performances != null && !performances.isEmpty()) {
                builder = builder.h3("The program is");

                for (int j = 0; j < performances.size(); j++) {
                    builder = builder
                            .strong(
                                    authorDao.fetchOneById(performances.get(j).getAuthorId()).getName()
                            )
                            .text(
                                    " - " + performances.get(j).getName()
                            ).br();
                }
            }
            // builder = builder.end();
        }

        return builder.build();
    }
}
