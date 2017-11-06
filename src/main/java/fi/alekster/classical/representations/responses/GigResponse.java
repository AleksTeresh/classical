package fi.alekster.classical.representations.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import fi.alekster.classical.representations.GigView;

import java.util.List;

/**
 * Created by aleksandr on 6.11.2017.
 */
public class GigResponse {

    @JsonProperty
    private Long count;

    @JsonProperty
    private List<GigView> gigs;

    public GigResponse() {}

    public GigResponse (
        List<GigView> gigs,
        Long count
    ) {
        this.gigs = gigs;
        this.count = count;
    }
}
