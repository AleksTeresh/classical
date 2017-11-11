package fi.alekster.classical.representations.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aleksandr on 8.11.2017.
 */
public class PerformanceRequest {
    @JsonProperty
    private String name;

    @JsonProperty
    private String author;

    public PerformanceRequest() {}

    public PerformanceRequest(
            String name,
            String author
    ) {
        this.name = name;
        this.author = author;
    }

    public String getName() {
        return this.name;
    }

    public String getAuthor() {
        return this.author;
    }
}
