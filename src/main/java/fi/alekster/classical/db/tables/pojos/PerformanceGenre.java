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
public class PerformanceGenre implements Serializable {

    private static final long serialVersionUID = -1511431786;

    private Long performanceId;
    private Long genreId;

    public PerformanceGenre() {}

    public PerformanceGenre(PerformanceGenre value) {
        this.performanceId = value.performanceId;
        this.genreId = value.genreId;
    }

    public PerformanceGenre(
        Long performanceId,
        Long genreId
    ) {
        this.performanceId = performanceId;
        this.genreId = genreId;
    }

    public Long getPerformanceId() {
        return this.performanceId;
    }

    public void setPerformanceId(Long performanceId) {
        this.performanceId = performanceId;
    }

    public Long getGenreId() {
        return this.genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PerformanceGenre (");

        sb.append(performanceId);
        sb.append(", ").append(genreId);

        sb.append(")");
        return sb.toString();
    }
}