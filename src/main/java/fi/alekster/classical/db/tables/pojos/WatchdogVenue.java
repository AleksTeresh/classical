/*
 * This file is generated by jOOQ.
*/
package fi.alekster.classical.db.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


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
public class WatchdogVenue implements Serializable {

    private static final long serialVersionUID = 454811683;

    private Long watchdogId;
    private Long venueId;

    public WatchdogVenue() {}

    public WatchdogVenue(WatchdogVenue value) {
        this.watchdogId = value.watchdogId;
        this.venueId = value.venueId;
    }

    public WatchdogVenue(
        Long watchdogId,
        Long venueId
    ) {
        this.watchdogId = watchdogId;
        this.venueId = venueId;
    }

    public Long getWatchdogId() {
        return this.watchdogId;
    }

    public void setWatchdogId(Long watchdogId) {
        this.watchdogId = watchdogId;
    }

    public Long getVenueId() {
        return this.venueId;
    }

    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("WatchdogVenue (");

        sb.append(watchdogId);
        sb.append(", ").append(venueId);

        sb.append(")");
        return sb.toString();
    }
}
