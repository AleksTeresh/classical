package fi.alekster.classical.dao;

import fi.alekster.classical.db.Tables;
import fi.alekster.classical.db.tables.daos.WatchdogDao;
import fi.alekster.classical.db.tables.pojos.Watchdog;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by aleksandr on 17.11.2017.
 */
@Repository
public class ExWatchdogDao extends WatchdogDao {
    private final DSLContext dsl;

    @Autowired
    public ExWatchdogDao(DSLContext dsl) {
        super(dsl.configuration());

        this.dsl = dsl;
    }

    public boolean exists (
            String email,
            String keyPhrase,
            List<Long> authorIds,
            List<Long> genreIds,
            List<Long> venueIds,
            boolean allGenres,
            boolean allAuthors,
            Date startDate,
            Date endDate
    ) {
        Condition condition = Tables.WATCHDOG.EMAIL.equal(email)
                .and(Tables.WATCHDOG.KEY_PHRASE.equal(keyPhrase));

        if (startDate != null) {
            condition = condition.and(Tables.WATCHDOG.START_DATE.equal(new Timestamp(startDate.getTime())));
        }
        if (endDate != null) {
            condition = condition.and(Tables.WATCHDOG.END_DATE.equal(new Timestamp(endDate.getTime())));
        }

        condition = condition.and(Tables.WATCHDOG.ALL_AUTHORS.equal(allAuthors));
        condition = condition.and(Tables.WATCHDOG.ALL_GENRES.equal(allGenres));

        List<Long> candidateIds = dsl.select(Tables.WATCHDOG.ID)
                .from(Tables.WATCHDOG)
                .where(condition)
                .fetch(Tables.WATCHDOG.ID);

        List<Long> matchedIds = candidateIds.stream()
                .filter(p -> {
                    boolean shouldInclude = true;

                    if (authorIds != null && !allAuthors) {
                        List<Long> savedAuthorIds = dsl.select(Tables.WATCHDOG_AUTHOR.AUTHOR_ID)
                                .from(Tables.WATCHDOG_AUTHOR)
                                .where(Tables.WATCHDOG_AUTHOR.WATCHDOG_ID.equal(p))
                                .fetch(Tables.WATCHDOG_AUTHOR.AUTHOR_ID);

                        shouldInclude = shouldInclude && new HashSet<>(savedAuthorIds).equals(new HashSet<>(authorIds));
                    }
                    if (shouldInclude && venueIds != null) {
                        List<Long> savedVanueIds = dsl.select(Tables.WATCHDOG_VENUE.VENUE_ID)
                                .from(Tables.WATCHDOG_VENUE)
                                .where(Tables.WATCHDOG_VENUE.WATCHDOG_ID.equal(p))
                                .fetch(Tables.WATCHDOG_VENUE.VENUE_ID);

                        shouldInclude = shouldInclude && new HashSet<>(savedVanueIds).equals(new HashSet<>(venueIds));
                    }
                    if (shouldInclude && genreIds != null && !allGenres) {
                        List<Long> savedGenreIds = dsl.select(Tables.WATCHDOG_GENRE.GENRE_ID)
                                .from(Tables.WATCHDOG_GENRE)
                                .where(Tables.WATCHDOG_GENRE.WATCHDOG_ID.equal(p))
                                .fetch(Tables.WATCHDOG_GENRE.GENRE_ID);

                        shouldInclude = shouldInclude && new HashSet<>(savedGenreIds).equals(new HashSet<>(genreIds));
                    }

                    return shouldInclude;
                })
                .collect(Collectors.toList());

        return !matchedIds.isEmpty();
    }

    public void insert(Watchdog watchdog, List<Long> authorIds, List<Long> genreIds, List<Long> venueIds) {
        super.insert(watchdog);

        if (authorIds != null && !watchdog.getAllAuthors()) {
            for (int i = 0; i < authorIds.size(); i++) {
                dsl.insertInto(Tables.WATCHDOG_AUTHOR)
                        .columns(Tables.WATCHDOG_AUTHOR.WATCHDOG_ID, Tables.WATCHDOG_AUTHOR.AUTHOR_ID)
                        .values(watchdog.getId(), authorIds.get(i))
                        .execute();
            }
        }


        if (genreIds != null && !watchdog.getAllGenres()) {
            for (int i = 0; i < genreIds.size(); i++) {
                dsl.insertInto(Tables.WATCHDOG_GENRE)
                        .columns(Tables.WATCHDOG_GENRE.WATCHDOG_ID, Tables.WATCHDOG_GENRE.GENRE_ID)
                        .values(watchdog.getId(), genreIds.get(i))
                        .execute();
            }
        }

        if (venueIds != null) {
            for (int i = 0; i < venueIds.size(); i++) {
                dsl.insertInto(Tables.WATCHDOG_VENUE)
                        .columns(Tables.WATCHDOG_VENUE.WATCHDOG_ID, Tables.WATCHDOG_VENUE.VENUE_ID)
                        .values(watchdog.getId(), venueIds.get(i))
                        .execute();
            }
        }
    }
}
