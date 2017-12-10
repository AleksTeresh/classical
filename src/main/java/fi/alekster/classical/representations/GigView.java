package fi.alekster.classical.representations;

import com.fasterxml.jackson.annotation.JsonProperty;
import fi.alekster.classical.db.tables.pojos.Gig;

import java.util.List;

/**
 * Created by aleksandr on 31.10.2017.
 */
public class GigView {
    @JsonProperty
    private Long id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String description;

    @JsonProperty
    private Long timestamp;

    @JsonProperty
    private Long duration;

    @JsonProperty
    private List<PerformanceView> performances;

    @JsonProperty
    private VenueView venue;

    @JsonProperty
    private String imageUrl;

    @JsonProperty
    private String url;

    public GigView () {}

    public GigView(
            Long id,
            String name,
            String description,
            Long timestamp,
            Long duration,
            List<PerformanceView> performances,
            VenueView venue,
            String imageUrl,
            String url
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.timestamp = timestamp;
        this.duration = duration;
        this.performances = performances;
        this.venue = venue;
        this.imageUrl = imageUrl;
        this.url = url;
    }

    public static GigView fromEntity(Gig gig, List<PerformanceView> performances, VenueView venue) {
        return new GigView(
                gig.getId(),
                gig.getName(),
                gig.getDescription(),
                gig.getTimestamp().getTime(),
                gig.getDuration(),
                performances,
                venue,
                gig.getImageUrl(),
                gig.getUrl()
        );
    }
}
