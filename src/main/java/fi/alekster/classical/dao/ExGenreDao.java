package fi.alekster.classical.dao;

import fi.alekster.classical.db.tables.daos.GenreDao;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by aleksandr on 6.11.2017.
 */
@Repository
public class ExGenreDao extends GenreDao {
    @Autowired
    public ExGenreDao(DSLContext dsl) {
        super(dsl.configuration());
    }
}
