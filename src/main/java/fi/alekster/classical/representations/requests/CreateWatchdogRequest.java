package fi.alekster.classical.representations.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by aleksandr on 17.11.2017.
 */
public class CreateWatchdogRequest {

    @JsonProperty
    private String startDate;

    @JsonProperty
    private String endDate;

    @JsonProperty
    private String keyPhrase;

    @JsonProperty
    private List<Long> authorIds;

    @JsonProperty
    private List<Long> venueIds;

    @JsonProperty
    private List<Long> genreIds;

    @JsonProperty
    private boolean allGenres;

    @JsonProperty
    private boolean allAuthors;

    public CreateWatchdogRequest() {}

    public CreateWatchdogRequest(
            String startDate,
            String endDate,
            String keyPhrase,
            List<Long> authorIds,
            List<Long> venueIds,
            List<Long> genreIds,
            boolean allGenres,
            boolean allAuthors
    ) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.keyPhrase = keyPhrase;
        this.authorIds = authorIds;
        this.venueIds = venueIds;
        this.genreIds = genreIds;
        this.allGenres = allGenres;
        this.allAuthors = allAuthors;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public String getKeyPhrase() {
        return this.keyPhrase;
    }

    public List<Long> getAuthorIds() {
        return this.authorIds;
    }

    public List<Long> getGenreIds() {
        return this.genreIds;
    }

    public List<Long> getVenueIds() {
        return this.venueIds;
    }

    public boolean isAllGenres() { return allGenres; }

    public boolean isAllAuthors() { return allAuthors; }
}
