/*
 * This file is generated by jOOQ.
*/
package fi.alekster.classical.db.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

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
public class Watchdog implements Serializable {

    private static final long serialVersionUID = -549210317;

    private Long      id;
    private String    email;
    private Timestamp startDate;
    private Timestamp endDate;
    private String    keyPhrase;

    public Watchdog() {}

    public Watchdog(Watchdog value) {
        this.id = value.id;
        this.email = value.email;
        this.startDate = value.startDate;
        this.endDate = value.endDate;
        this.keyPhrase = value.keyPhrase;
    }

    public Watchdog(
        Long      id,
        String    email,
        Timestamp startDate,
        Timestamp endDate,
        String    keyPhrase
    ) {
        this.id = id;
        this.email = email;
        this.startDate = startDate;
        this.endDate = endDate;
        this.keyPhrase = keyPhrase;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String getKeyPhrase() {
        return this.keyPhrase;
    }

    public void setKeyPhrase(String keyPhrase) {
        this.keyPhrase = keyPhrase;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Watchdog (");

        sb.append(id);
        sb.append(", ").append(email);
        sb.append(", ").append(startDate);
        sb.append(", ").append(endDate);
        sb.append(", ").append(keyPhrase);

        sb.append(")");
        return sb.toString();
    }
}