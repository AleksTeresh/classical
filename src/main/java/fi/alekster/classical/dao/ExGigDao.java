package fi.alekster.classical.dao;

import fi.alekster.classical.db.Tables;
import fi.alekster.classical.db.tables.daos.GigDao;
import fi.alekster.classical.db.tables.pojos.Gig;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
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

    public long count (
            String keyPhrase,
            List<Long> authorIds,
            List<Long> genreIds,
            List<Long> venueIds,
            Date startDate,
            Date endDate
    ) {
        return this.count(keyPhrase, authorIds, genreIds, venueIds, startDate, endDate, null);
    }

    public long count (
            String keyPhrase,
            List<Long> authorIds,
            List<Long> genreIds,
            List<Long> venueIds,
            Date startDate,
            Date endDate,
            Date createDate
    ) {
        return getConditionStep(keyPhrase, authorIds, genreIds, venueIds, startDate, endDate, createDate)
                .fetch()
                .size();
    }

    public boolean exists (
            String name,
            Timestamp timestamp,
            Long venueId
    ) {
        return dsl.select()
                .from(Tables.GIG)
                .where(Tables.GIG.NAME.equal(name)
                        .and(Tables.GIG.TIMESTAMP.equal(timestamp))
                        .and(Tables.GIG.VENUE_ID.equal(venueId)))
                .limit(1)
                .fetch()
                .size() > 0;
    }

    public List<Gig> fetch (
            String keyPhrase,
            int limit,
            int offset,
            List<Long> authorIds,
            List<Long> genreIds,
            List<Long> venueIds,
            Date startDate,
            Date endDate
    ) {
        return fetch(keyPhrase, limit, offset, authorIds, genreIds, venueIds, startDate, endDate, null);
    }

    public List<Gig> fetch (
            String keyPhrase,
            int limit,
            int offset,
            List<Long> authorIds,
            List<Long> genreIds,
            List<Long> venueIds,
            Date startDate,
            Date endDate,
            Date createDate
    ) {
        return getConditionStep(keyPhrase, authorIds, genreIds, venueIds, startDate, endDate, createDate)
                .offset(offset)
                .limit(limit)
                .stream()
                .map(p -> new Gig(
                        p.getValue(Tables.GIG.ID),
                        p.get(Tables.GIG.VENUE_ID),
                        p.get(Tables.GIG.NAME),
                        p.get(Tables.GIG.DESCRIPTION),
                        p.get(Tables.GIG.TIMESTAMP),
                        p.get(Tables.GIG.DURATION),
                        p.get(Tables.GIG.IMAGE_URL),
                        p.get(Tables.GIG.URL),
                        p.get(Tables.GIG.CREATE_TIME)
                )).collect(Collectors.toList());
    }

    private SelectSeekStep1<Record, Timestamp> getConditionStep (
            String keyPhrase,
            List<Long> authorIds,
            List<Long> genreIds,
            List<Long> venueIds,
            Date startDate,
            Date endDate,
            Date createDate
    ) {
        Condition condition = Tables.GIG.NAME.contains(keyPhrase)
                .or(Tables.GIG.DESCRIPTION.contains(keyPhrase))
                .or(Tables.GIG.ID.in(
                        dsl.select(Tables.PERFORMANCE.GIG_ID).from(Tables.PERFORMANCE)
                            .where(Tables.PERFORMANCE.NAME.contains(keyPhrase).or(Tables.PERFORMANCE.AUTHOR_ID.in(
                                    dsl.select(Tables.AUTHOR.ID).from(Tables.AUTHOR).where(Tables.AUTHOR.NAME.contains(keyPhrase))
                            )))
                ));
        // TODO: make the search fuzzy instead of strict one
        if (authorIds != null) {
            condition = condition.and(Tables.GIG.ID.in(
                    dsl.select(Tables.PERFORMANCE.GIG_ID)
                            .from(Tables.PERFORMANCE)
                            .where(Tables.PERFORMANCE.AUTHOR_ID.in(authorIds))
            ));
        }
        if (venueIds != null) {
            condition = condition.and(Tables.GIG.VENUE_ID.in(venueIds));
        }
        if (genreIds != null) {
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
        if (startDate != null) {
            condition = condition.and(Tables.GIG.TIMESTAMP.greaterOrEqual(new Timestamp(startDate.getTime())));
        }
        if (endDate != null) {
            condition = condition.and(Tables.GIG.TIMESTAMP.lessOrEqual(new Timestamp(endDate.getTime())));
        }

        if (createDate != null) {
            condition = condition.and(Tables.GIG.CREATE_TIME.greaterOrEqual(new Timestamp(createDate.getTime())));
        }

        SelectSeekStep1<Record, Timestamp> conditionStep = dsl.select()
                .from(Tables.GIG)
                .where(condition)
                .orderBy(Tables.GIG.TIMESTAMP);

        return conditionStep;
    }
}
