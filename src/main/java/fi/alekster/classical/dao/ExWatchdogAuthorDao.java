package fi.alekster.classical.dao;

import fi.alekster.classical.db.tables.daos.WatchdogAuthorDao;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by aleksandr on 17.11.2017.
 */
@Repository
public class ExWatchdogAuthorDao extends WatchdogAuthorDao {

    @Autowired
    public ExWatchdogAuthorDao(DSLContext dsl) {
        super(dsl.configuration());
    }
}
