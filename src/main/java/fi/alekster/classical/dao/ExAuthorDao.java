package fi.alekster.classical.dao;

import fi.alekster.classical.db.Tables;
import fi.alekster.classical.db.tables.daos.AuthorDao;
import fi.alekster.classical.db.tables.pojos.Author;
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
public class ExAuthorDao extends AuthorDao {
    private final DSLContext dsl;

    @Autowired
    public ExAuthorDao(DSLContext dsl) {
        super(dsl.configuration());

        this.dsl = dsl;
    }

    public long count(
            String keyPhrase
    ) {
        return getConditionStep(keyPhrase)
                .fetch()
                .size();
    }

    public List<Author> fetch (
            String keyPhrase,
            int limit,
            int offset
    ) {
        return getConditionStep(keyPhrase)
                .offset(offset)
                .limit(limit)
                .stream()
                .map(p -> new Author(
                        p.getValue(Tables.AUTHOR.ID),
                        p.get(Tables.AUTHOR.NAME),
                        p.get(Tables.AUTHOR.DESCRIPTION),
                        p.get(Tables.AUTHOR.WIKIPEDIA_LINK)
                )).collect(Collectors.toList());
    }

    private SelectConditionStep<Record> getConditionStep (
            String keyPhrase
    ) {
        Condition condition = Tables.AUTHOR.NAME.contains(keyPhrase)
                .or(Tables.AUTHOR.DESCRIPTION.contains(keyPhrase));

        SelectConditionStep<Record> conditionStep = dsl.select()
                .from(Tables.AUTHOR)
                .where(condition);

        return conditionStep;
    }
}
