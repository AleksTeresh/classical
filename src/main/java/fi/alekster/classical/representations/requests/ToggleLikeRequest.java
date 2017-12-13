package fi.alekster.classical.representations.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aleksandr on 13.12.2017.
 */
public class ToggleLikeRequest {

    @JsonProperty
    private Long performanceId;

    public ToggleLikeRequest() {}

    public ToggleLikeRequest(
            Long performanceId
    ) {
        this.performanceId = performanceId;
    }

    public Long getPerformanceId() {
        return this.performanceId;
    }

    public void setPerformanceId(Long performanceId) {
        this.performanceId = performanceId;
    }
}
