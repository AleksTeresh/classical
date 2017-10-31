package fi.alekster.classical.representations;

import com.fasterxml.jackson.annotation.JsonProperty;
import fi.alekster.classical.db.tables.pojos.Author;

/**
 * Created by aleksandr on 31.10.2017.
 */
public class AuthorView {
    @JsonProperty
    private Long   id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String wikipediaLink;

    public AuthorView () {}

    public AuthorView(
            Long id,
            String name,
            String wikipediaLink
    ) {
        this.id = id;
        this.name = name;
        this.wikipediaLink = wikipediaLink;
    }

    public static AuthorView fromEntity(Author author) {
        return new AuthorView(
                author.getId(),
                author.getName(),
                author.getWikipediaLink()
        );
    }
}
