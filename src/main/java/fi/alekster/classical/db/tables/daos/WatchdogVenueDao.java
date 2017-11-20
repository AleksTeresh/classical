/*
 * This file is generated by jOOQ.
*/
package fi.alekster.classical.db.tables.daos;


import fi.alekster.classical.db.tables.WatchdogVenue;
import fi.alekster.classical.db.tables.records.WatchdogVenueRecord;

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
public class WatchdogVenueDao extends DAOImpl<WatchdogVenueRecord, fi.alekster.classical.db.tables.pojos.WatchdogVenue, Record2<Long, Long>> {

    /**
     * Create a new WatchdogVenueDao without any configuration
     */
    public WatchdogVenueDao() {
        super(WatchdogVenue.WATCHDOG_VENUE, fi.alekster.classical.db.tables.pojos.WatchdogVenue.class);
    }

    /**
     * Create a new WatchdogVenueDao with an attached configuration
     */
    public WatchdogVenueDao(Configuration configuration) {
        super(WatchdogVenue.WATCHDOG_VENUE, fi.alekster.classical.db.tables.pojos.WatchdogVenue.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Record2<Long, Long> getId(fi.alekster.classical.db.tables.pojos.WatchdogVenue object) {
        return compositeKeyRecord(object.getWatchdogId(), object.getVenueId());
    }

    /**
     * Fetch records that have <code>watchdog_id IN (values)</code>
     */
    public List<fi.alekster.classical.db.tables.pojos.WatchdogVenue> fetchByWatchdogId(Long... values) {
        return fetch(WatchdogVenue.WATCHDOG_VENUE.WATCHDOG_ID, values);
    }

    /**
     * Fetch records that have <code>venue_id IN (values)</code>
     */
    public List<fi.alekster.classical.db.tables.pojos.WatchdogVenue> fetchByVenueId(Long... values) {
        return fetch(WatchdogVenue.WATCHDOG_VENUE.VENUE_ID, values);
    }
}