package fi.alekster.classical.dao;

import fi.alekster.classical.db.tables.daos.AuthorDao;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by aleksandr on 31.10.2017.
 */
@Repository
public class ExAuthorDao extends AuthorDao {
    @Autowired
    public ExAuthorDao(DSLContext dsl) {
        super(dsl.configuration());
    }
}
