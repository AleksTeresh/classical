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
public class WatchdogGenre implements Serializable {

    private static final long serialVersionUID = -720835825;

    private Long watchdogId;
    private Long genreId;

    public WatchdogGenre() {}

    public WatchdogGenre(WatchdogGenre value) {
        this.watchdogId = value.watchdogId;
        this.genreId = value.genreId;
    }

    public WatchdogGenre(
        Long watchdogId,
        Long genreId
    ) {
        this.watchdogId = watchdogId;
        this.genreId = genreId;
    }

    public Long getWatchdogId() {
        return this.watchdogId;
    }

    public void setWatchdogId(Long watchdogId) {
        this.watchdogId = watchdogId;
    }

    public Long getGenreId() {
        return this.genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("WatchdogGenre (");

        sb.append(watchdogId);
        sb.append(", ").append(genreId);

        sb.append(")");
        return sb.toString();
    }
}
