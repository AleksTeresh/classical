package fi.alekster.classical.dao;

import fi.alekster.classical.db.Tables;
import fi.alekster.classical.db.tables.daos.WatchdogDao;
import fi.alekster.classical.db.tables.pojos.Watchdog;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public void insert(Watchdog watchdog, List<Long> authorIds, List<Long> genreIds, List<Long> venueIds) {
        super.insert(watchdog);

        try {
            for (int i = 0; i < authorIds.size(); i++) {
                dsl.insertInto(Tables.WATCHDOG_AUTHOR)
                        .columns(Tables.WATCHDOG_AUTHOR.WATCHDOG_ID, Tables.WATCHDOG_AUTHOR.AUTHOR_ID)
                        .values(watchdog.getId(), authorIds.get(i))
                        .execute();
            }

            for (int i = 0; i < genreIds.size(); i++) {
                dsl.insertInto(Tables.WATCHDOG_GENRE)
                        .columns(Tables.WATCHDOG_GENRE.WATCHDOG_ID, Tables.WATCHDOG_GENRE.GENRE_ID)
                        .values(watchdog.getId(), genreIds.get(i))
                        .execute();
            }

            for (int i = 0; i < venueIds.size(); i++) {
                dsl.insertInto(Tables.WATCHDOG_VENUE)
                        .columns(Tables.WATCHDOG_VENUE.WATCHDOG_ID, Tables.WATCHDOG_VENUE.VENUE_ID)
                        .values(watchdog.getId(), venueIds.get(i))
                        .execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
