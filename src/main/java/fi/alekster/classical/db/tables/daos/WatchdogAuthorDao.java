/*
 * This file is generated by jOOQ.
*/
package fi.alekster.classical.db.tables.daos;


import fi.alekster.classical.db.tables.WatchdogAuthor;
import fi.alekster.classical.db.tables.records.WatchdogAuthorRecord;

import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.Record2;
import org.jooq.impl.DAOImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class WatchdogAuthorDao extends DAOImpl<WatchdogAuthorRecord, fi.alekster.classical.db.tables.pojos.WatchdogAuthor, Record2<Long, Long>> {

    /**
     * Create a new WatchdogAuthorDao without any configuration
     */
    public WatchdogAuthorDao() {
        super(WatchdogAuthor.WATCHDOG_AUTHOR, fi.alekster.classical.db.tables.pojos.WatchdogAuthor.class);
    }

    /**
     * Create a new WatchdogAuthorDao with an attached configuration
     */
    public WatchdogAuthorDao(Configuration configuration) {
        super(WatchdogAuthor.WATCHDOG_AUTHOR, fi.alekster.classical.db.tables.pojos.WatchdogAuthor.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Record2<Long, Long> getId(fi.alekster.classical.db.tables.pojos.WatchdogAuthor object) {
        return compositeKeyRecord(object.getWatchdogId(), object.getAuthorId());
    }

    /**
     * Fetch records that have <code>watchdog_id IN (values)</code>
     */
    public List<fi.alekster.classical.db.tables.pojos.WatchdogAuthor> fetchByWatchdogId(Long... values) {
        return fetch(WatchdogAuthor.WATCHDOG_AUTHOR.WATCHDOG_ID, values);
    }

    /**
     * Fetch records that have <code>author_id IN (values)</code>
     */
    public List<fi.alekster.classical.db.tables.pojos.WatchdogAuthor> fetchByAuthorId(Long... values) {
        return fetch(WatchdogAuthor.WATCHDOG_AUTHOR.AUTHOR_ID, values);
    }
}