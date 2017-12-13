package fi.alekster.classical.representations.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import fi.alekster.classical.representations.GigView;
import fi.alekster.classical.representations.PerformanceView;

import java.util.List;

/**
 * Created by aleksandr on 13.12.2017.
 */
public class DetailedGigResponse {
    @JsonProperty
    private GigView gig;

    @JsonProperty
    private List<GigView> suggestions;

    @JsonProperty
    private List<PerformanceView> performances;

    public DetailedGigResponse () {}

    public DetailedGigResponse (
            GigView gig,
            List<PerformanceView> performances,
            List<GigView> suggestions
    ) {
        this.gig = gig;
        this.performances = performances;
        this.suggestions = suggestions;
    }

    public GigView getGig() {
        return gig;
    }

    public List<GigView> getSuggestions() {
        return suggestions;
    }

    public List<PerformanceView> getPerformances() {
        return performances;
    }
}
