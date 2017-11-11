package fi.alekster.classical.dao;

import fi.alekster.classical.db.Tables;
import fi.alekster.classical.db.tables.daos.PerformanceDao;
import fi.alekster.classical.db.tables.pojos.Performance;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by aleksandr on 31.10.2017.
 */
@Repository
public class ExPerformanceDao extends PerformanceDao {
    private final DSLContext dsl;

    @Autowired
    public ExPerformanceDao(DSLContext dsl) {
        super(dsl.configuration());

        this.dsl = dsl;
    }

    public void insert(Performance performance, Long genreId) {
        super.insert(performance);

        try {
            dsl.insertInto(Tables.PERFORMANCE_GENRE)
                    .columns(Tables.PERFORMANCE_GENRE.PERFORMANCE_ID, Tables.PERFORMANCE_GENRE.GENRE_ID)
                    .values(performance.getId(), genreId)
                    .execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
