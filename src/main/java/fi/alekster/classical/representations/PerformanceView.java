package fi.alekster.classical.representations;

import com.fasterxml.jackson.annotation.JsonProperty;
import fi.alekster.classical.db.tables.pojos.Performance;

/**
 * Created by aleksandr on 31.10.2017.
 */
public class PerformanceView {
    @JsonProperty
    private Long id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String description;

    @JsonProperty
    private AuthorView author;

    public PerformanceView () {}

    public PerformanceView(
            Long id,
            String name,
            String description,
            AuthorView author
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author = author;
    }

    public static PerformanceView fromEntity(Performance performance, AuthorView author) {
        return new PerformanceView(
                performance.getId(),
                performance.getName(),
                performance.getDescription(),
                author
        );
    }
}
