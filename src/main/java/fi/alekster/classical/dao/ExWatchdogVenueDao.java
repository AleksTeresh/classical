package fi.alekster.classical.dao;

import fi.alekster.classical.db.Tables;
import fi.alekster.classical.db.tables.daos.WatchdogVenueDao;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by aleksandr on 17.11.2017.
 */
@Repository
public class ExWatchdogVenueDao extends WatchdogVenueDao {
    private final DSLContext dsl;

    @Autowired
    public ExWatchdogVenueDao(DSLContext dsl) {
        super(dsl.configuration());

        this.dsl = dsl;
    }

    public void deleteByWatchdogId (Long id) {
        dsl.deleteFrom(Tables.WATCHDOG_VENUE)
                .where(Tables.WATCHDOG_VENUE.WATCHDOG_ID.equal(id))
                .execute();
    }
}
