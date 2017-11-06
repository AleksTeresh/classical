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

    private static final long serialVersionUID = 396730802;

    private Long   id;
    private Long   authorId;
    private Long   gigId;
    private String name;
    private String description;
    private String conductor;
    private String players;
    private String youtubeId;

    public Performance() {}

    public Performance(Performance value) {
        this.id = value.id;
        this.authorId = value.authorId;
        this.gigId = value.gigId;
        this.name = value.name;
        this.description = value.description;
        this.conductor = value.conductor;
        this.players = value.players;
        this.youtubeId = value.youtubeId;
    }

    public Performance(
        Long   id,
        Long   authorId,
        Long   gigId,
        String name,
        String description,
        String conductor,
        String players,
        String youtubeId
    ) {
        this.id = id;
        this.authorId = authorId;
        this.gigId = gigId;
        this.name = name;
        this.description = description;
        this.conductor = conductor;
        this.players = players;
        this.youtubeId = youtubeId;
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

    public String getConductor() {
        return this.conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public String getPlayers() {
        return this.players;
    }

    public void setPlayers(String players) {
        this.players = players;
    }

    public String getYoutubeId() {
        return this.youtubeId;
    }

    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Performance (");

        sb.append(id);
        sb.append(", ").append(authorId);
        sb.append(", ").append(gigId);
        sb.append(", ").append(name);
        sb.append(", ").append(description);
        sb.append(", ").append(conductor);
        sb.append(", ").append(players);
        sb.append(", ").append(youtubeId);

        sb.append(")");
        return sb.toString();
    }
}
