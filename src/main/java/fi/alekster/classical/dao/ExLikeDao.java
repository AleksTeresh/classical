package fi.alekster.classical.dao;

import fi.alekster.classical.db.Tables;
import fi.alekster.classical.db.tables.daos.LikeDao;
import fi.alekster.classical.db.tables.pojos.Like;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by aleksandr on 13.12.2017.
 */
@Repository
public class ExLikeDao extends LikeDao {

    private final DSLContext dsl;

    @Autowired
    public ExLikeDao(DSLContext dsl) {
        super(dsl.configuration());

        this.dsl = dsl;
    }

    public List<Like> fetchByEmailAndPerformanceId (String email, Long performanceId) {
        return dsl.select()
                .from(Tables.LIKE)
                .where(Tables.LIKE.EMAIL.equal(email).and(Tables.LIKE.PERFORMANCE_ID.equal(performanceId)))
                .fetch(p -> new Like(p.get(Tables.LIKE.ID), email, performanceId));
    }
}
