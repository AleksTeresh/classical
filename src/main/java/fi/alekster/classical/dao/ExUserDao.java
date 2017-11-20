package fi.alekster.classical.dao;

import fi.alekster.classical.db.tables.daos.UserDao;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by aleksandr on 16.11.2017.
 */

@Repository
public class ExUserDao extends UserDao {

    @Autowired
    public ExUserDao(DSLContext dsl) {
        super(dsl.configuration());
    }
}
