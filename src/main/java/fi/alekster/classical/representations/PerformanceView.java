package fi.alekster.classical.representations;

import com.fasterxml.jackson.annotation.JsonProperty;
import fi.alekster.classical.db.tables.pojos.Performance;

import java.util.List;

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

    @JsonProperty
    private List<GenreView> genres;

    @JsonProperty
    private String conductor;

    @JsonProperty
    private String players;

    @JsonProperty
    private String youtubeId;

    public PerformanceView () {}

    public PerformanceView(
            Long id,
            String name,
            String description,
            AuthorView author,
            List<GenreView> genres,
            String conductor,
            String players,
            String youtubeId
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author = author;
        this.genres = genres;
        this.conductor = conductor;
        this.players = players;
        this.youtubeId = youtubeId;
    }

    public static PerformanceView fromEntity(Performance performance, AuthorView author, List<GenreView> genres) {
        return new PerformanceView(
                performance.getId(),
                performance.getName(),
                performance.getDescription(),
                author,
                genres,
                performance.getConductor(),
                performance.getPlayers(),
                performance.getYoutubeId()
        );
    }
}
