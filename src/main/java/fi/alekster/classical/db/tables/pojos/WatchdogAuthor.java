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
public class WatchdogAuthor implements Serializable {

    private static final long serialVersionUID = 1907007091;

    private Long watchdogId;
    private Long authorId;

    public WatchdogAuthor() {}

    public WatchdogAuthor(WatchdogAuthor value) {
        this.watchdogId = value.watchdogId;
        this.authorId = value.authorId;
    }

    public WatchdogAuthor(
        Long watchdogId,
        Long authorId
    ) {
        this.watchdogId = watchdogId;
        this.authorId = authorId;
    }

    public Long getWatchdogId() {
        return this.watchdogId;
    }

    public void setWatchdogId(Long watchdogId) {
        this.watchdogId = watchdogId;
    }

    public Long getAuthorId() {
        return this.authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("WatchdogAuthor (");

        sb.append(watchdogId);
        sb.append(", ").append(authorId);

        sb.append(")");
        return sb.toString();
    }
}
