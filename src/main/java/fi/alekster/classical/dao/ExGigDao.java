package fi.alekster.classical.dao;

import fi.alekster.classical.db.Tables;
import fi.alekster.classical.db.tables.daos.GigDao;
import fi.alekster.classical.db.tables.pojos.Gig;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by aleksandr on 31.10.2017.
 */
@Repository
public class ExGigDao extends GigDao {
    private final DSLContext dsl;

    @Autowired
    public ExGigDao(DSLContext dsl) {
        super(dsl.configuration());

        this.dsl = dsl;
    }

    public long count(
            String keyPhrase,
            Long authorId,
            List<Long> genreIds,
            Long venueId
    ) {
        return getConditionStep(keyPhrase, authorId, genreIds, venueId)
                .fetch()
                .size();
    }

    public List<Gig> fetch (
            String keyPhrase,
            int limit,
            int offset,
            Long authorId,
            List<Long> genreIds,
            Long venueId
    ) {
        return getConditionStep(keyPhrase, authorId, genreIds, venueId)
                .offset(offset)
                .limit(limit)
                .stream()
                .map(p -> new Gig(
                        p.getValue(Tables.GIG.ID),
                        p.get(Tables.GIG.VENUE_ID),
                        p.get(Tables.GIG.NAME),
                        p.get(Tables.GIG.DESCRIPTION),
                        p.get(Tables.GIG.TIMESTAMP),
                        p.get(Tables.GIG.DURATION)
                )).collect(Collectors.toList());
    }

    private SelectConditionStep<Record> getConditionStep (
            String keyPhrase,
            Long authorId,
            List<Long> genreIds,
            Long venueId
    ) {
        Condition condition = Tables.GIG.NAME.contains(keyPhrase)
                .or(Tables.GIG.DESCRIPTION.contains(keyPhrase));
        // TODO: add conditions for searching the keyPhrase in names and descriptions of the associated performances
        if (authorId != null) {
            condition = condition.and(Tables.GIG.ID.in(
                    dsl.select(Tables.PERFORMANCE.GIG_ID)
                            .from(Tables.PERFORMANCE)
                            .where(Tables.PERFORMANCE.AUTHOR_ID.equal(authorId))
            ));
        }
        if (venueId != null) {
            condition = condition.and(Tables.GIG.VENUE_ID.equal(venueId));
        }
        if (genreIds != null && genreIds.size() != 0) {
            condition = condition.and(Tables.GIG.ID.in(
                    dsl.select(Tables.PERFORMANCE.GIG_ID)
                            .from(Tables.PERFORMANCE)
                            .where(Tables.PERFORMANCE.ID.in(
                                    dsl.select(Tables.PERFORMANCE_GENRE.PERFORMANCE_ID)
                                            .from(Tables.PERFORMANCE_GENRE)
                                            .where(Tables.PERFORMANCE_GENRE.GENRE_ID.in(genreIds))
                            ))
            ));
        }

        SelectConditionStep<Record> conditionStep = dsl.select()
                .from(Tables.GIG)
                .where(condition);

        return conditionStep;
    }
}
