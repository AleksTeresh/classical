package fi.alekster.classical.dao;

import fi.alekster.classical.db.Tables;
import fi.alekster.classical.db.tables.daos.WatchdogAuthorDao;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by aleksandr on 17.11.2017.
 */
@Repository
public class ExWatchdogAuthorDao extends WatchdogAuthorDao {
    private final DSLContext dsl;

    @Autowired
    public ExWatchdogAuthorDao(DSLContext dsl) {
        super(dsl.configuration());

        this.dsl = dsl;
    }

    public void deleteByWatchdogId (Long id) {
        dsl.deleteFrom(Tables.WATCHDOG_AUTHOR)
                .where(Tables.WATCHDOG_AUTHOR.WATCHDOG_ID.equal(id))
                .execute();
    }
}
