package fi.alekster.classical.representations.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by aleksandr on 8.11.2017.
 */
public class GigRequest {
    @JsonProperty
    private String name;

    @JsonProperty
    private String description;

    @JsonProperty
    private String timestamp;

    @JsonProperty
    private String duration;

    @JsonProperty
    private List<PerformanceRequest> performances;

    @JsonProperty
    private Long venue;

    @JsonProperty
    private String imageUrl;

    @JsonProperty
    private String url;

    public GigRequest() {}

    public GigRequest(
            String name,
            String description,
            String timestamp,
            String duration,
            List<PerformanceRequest> performances,
            Long venue,
            String imageUrl,
            String url
    ) {
        this.name = name;
        this.description = description;
        this.timestamp = timestamp;
        this.duration = duration;
        this.performances = performances;
        this.venue = venue;
        this.imageUrl = imageUrl;
        this.url = url;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDuration() {
        return this.duration;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public Long getVenue() {
        return this.venue;
    }

    public List<PerformanceRequest> getPerformances() {
        return this.performances;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public String getUrl() {
        return this.url;
    }
}
