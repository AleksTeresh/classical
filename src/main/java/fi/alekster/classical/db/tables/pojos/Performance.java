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
public class Performance implements Serializable {

    private static final long serialVersionUID = -1068039723;

    private Long   id;
    private Long   authorId;
    private Long   gigId;
    private String name;
    private String description;

    public Performance() {}

    public Performance(Performance value) {
        this.id = value.id;
        this.authorId = value.authorId;
        this.gigId = value.gigId;
        this.name = value.name;
        this.description = value.description;
    }

    public Performance(
        Long   id,
        Long   authorId,
        Long   gigId,
        String name,
        String description
    ) {
        this.id = id;
        this.authorId = authorId;
        this.gigId = gigId;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuthorId() {
        return this.authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getGigId() {
        return this.gigId;
    }

    public void setGigId(Long gigId) {
        this.gigId = gigId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Performance (");

        sb.append(id);
        sb.append(", ").append(authorId);
        sb.append(", ").append(gigId);
        sb.append(", ").append(name);
        sb.append(", ").append(description);

        sb.append(")");
        return sb.toString();
    }
}
