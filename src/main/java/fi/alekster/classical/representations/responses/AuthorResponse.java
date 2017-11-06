package fi.alekster.classical.representations.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import fi.alekster.classical.representations.AuthorView;

import java.util.List;

/**
 * Created by aleksandr on 6.11.2017.
 */
public class AuthorResponse {
    @JsonProperty
    private Long count;

    @JsonProperty
    private List<AuthorView> authors;

    public AuthorResponse() {}

    public AuthorResponse (
            List<AuthorView> authors,
            Long count
    ) {
        this.authors = authors;
        this.count = count;
    }
}
