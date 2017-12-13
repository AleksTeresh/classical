package fi.alekster.classical.representations;

import com.fasterxml.jackson.annotation.JsonProperty;
import fi.alekster.classical.db.tables.pojos.Genre;

/**
 * Created by aleksandr on 6.11.2017.
 */
public class GenreView {
    @JsonProperty
    private Long id;

    @JsonProperty
    private String name;

    public GenreView () {}

    public GenreView (
        Long id,
        String name
    ) {
        this.id = id;
        this.name = name;
    }

    public static GenreView fromEntity (Genre genre) {
        return new GenreView(
                genre.getId(),
                genre.getName()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
