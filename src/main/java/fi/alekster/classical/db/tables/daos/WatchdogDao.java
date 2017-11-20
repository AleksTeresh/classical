/*
 * This file is generated by jOOQ.
*/
package fi.alekster.classical.db.tables.daos;


import fi.alekster.classical.db.tables.Watchdog;
import fi.alekster.classical.db.tables.records.WatchdogRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
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
public class WatchdogDao extends DAOImpl<WatchdogRecord, fi.alekster.classical.db.tables.pojos.Watchdog, Long> {

    /**
     * Create a new WatchdogDao without any configuration
     */
    public WatchdogDao() {
        super(Watchdog.WATCHDOG, fi.alekster.classical.db.tables.pojos.Watchdog.class);
    }

    /**
     * Create a new WatchdogDao with an attached configuration
     */
    public WatchdogDao(Configuration configuration) {
        super(Watchdog.WATCHDOG, fi.alekster.classical.db.tables.pojos.Watchdog.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Long getId(fi.alekster.classical.db.tables.pojos.Watchdog object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<fi.alekster.classical.db.tables.pojos.Watchdog> fetchById(Long... values) {
        return fetch(Watchdog.WATCHDOG.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public fi.alekster.classical.db.tables.pojos.Watchdog fetchOneById(Long value) {
        return fetchOne(Watchdog.WATCHDOG.ID, value);
    }

    /**
     * Fetch records that have <code>email IN (values)</code>
     */
    public List<fi.alekster.classical.db.tables.pojos.Watchdog> fetchByEmail(String... values) {
        return fetch(Watchdog.WATCHDOG.EMAIL, values);
    }

    /**
     * Fetch records that have <code>start_date IN (values)</code>
     */
    public List<fi.alekster.classical.db.tables.pojos.Watchdog> fetchByStartDate(Timestamp... values) {
        return fetch(Watchdog.WATCHDOG.START_DATE, values);
    }

    /**
     * Fetch records that have <code>end_date IN (values)</code>
     */
    public List<fi.alekster.classical.db.tables.pojos.Watchdog> fetchByEndDate(Timestamp... values) {
        return fetch(Watchdog.WATCHDOG.END_DATE, values);
    }

    /**
     * Fetch records that have <code>key_phrase IN (values)</code>
     */
    public List<fi.alekster.classical.db.tables.pojos.Watchdog> fetchByKeyPhrase(String... values) {
        return fetch(Watchdog.WATCHDOG.KEY_PHRASE, values);
    }
}