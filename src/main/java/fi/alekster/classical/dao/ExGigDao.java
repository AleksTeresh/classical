package fi.alekster.classical.dao;

import fi.alekster.classical.db.tables.daos.GigDao;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by aleksandr on 31.10.2017.
 */
@Repository
public class ExGigDao extends GigDao {
    @Autowired
    public ExGigDao(DSLContext dsl) {
        super(dsl.configuration());
    }
}
