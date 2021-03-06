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
public class Like implements Serializable {

    private static final long serialVersionUID = 169584157;

    private Long   id;
    private String email;
    private Long   performanceId;

    public Like() {}

    public Like(Like value) {
        this.id = value.id;
        this.email = value.email;
        this.performanceId = value.performanceId;
    }

    public Like(
        Long   id,
        String email,
        Long   performanceId
    ) {
        this.id = id;
        this.email = email;
        this.performanceId = performanceId;
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

    public Long getPerformanceId() {
        return this.performanceId;
    }

    public void setPerformanceId(Long performanceId) {
        this.performanceId = performanceId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Like (");

        sb.append(id);
        sb.append(", ").append(email);
        sb.append(", ").append(performanceId);

        sb.append(")");
        return sb.toString();
    }
}
