package fi.alekster.classical.dao;

import fi.alekster.classical.db.Tables;
import fi.alekster.classical.db.tables.daos.GenreDao;
import fi.alekster.classical.db.tables.pojos.Genre;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by aleksandr on 6.11.2017.
 */
@Repository
public class ExGenreDao extends GenreDao {
    private final DSLContext dsl;

    @Autowired
    public ExGenreDao(DSLContext dsl) {
        super(dsl.configuration());

        this.dsl = dsl;
    }

    public List<Genre> fetchByPerformanceId (
            Long performanceId
    ) {
        List<Long> idList = dsl.select(Tables.PERFORMANCE_GENRE.GENRE_ID)
                .from(Tables.PERFORMANCE_GENRE)
                .where(Tables.PERFORMANCE_GENRE.PERFORMANCE_ID.equal(performanceId))
                .stream()
                .map(p -> Tables.PERFORMANCE_GENRE.GENRE_ID.get(p))
                .collect(Collectors.toList());

        Long[] ids = new Long[idList.size()];
        return this.fetchById(
                idList.toArray(ids)
        );
    }
}
