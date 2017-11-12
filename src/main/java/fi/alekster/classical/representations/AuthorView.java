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
    private String description;

    @JsonProperty
    private String wikipediaLink;

    @JsonProperty
    private String imageUrl;

    public AuthorView () {}

    public AuthorView(
            Long id,
            String name,
            String description,
            String wikipediaLink,
            String imageUrl
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.wikipediaLink = wikipediaLink;
        this.imageUrl = imageUrl;
    }

    public static AuthorView fromEntity(Author author) {
        return new AuthorView(
                author.getId(),
                author.getName(),
                author.getDescription(),
                author.getWikipediaLink(),
                author.getImageUrl()
        );
    }
}
