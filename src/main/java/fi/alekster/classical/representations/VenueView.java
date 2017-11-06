package fi.alekster.classical.representations;

import com.fasterxml.jackson.annotation.JsonProperty;
import fi.alekster.classical.db.tables.pojos.Venue;

/**
 * Created by aleksandr on 6.11.2017.
 */
public class VenueView {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String description;

    public VenueView () {}

    public VenueView (
        Long id,
        String name,
        String description
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static VenueView fromEntity (Venue venue) {
        return new VenueView(
                venue.getId(),
                venue.getName(),
                venue.getDescription()
        );
    }
}
