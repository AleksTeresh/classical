package fi.alekster.classical.representations.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import fi.alekster.classical.db.tables.pojos.Watchdog;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by aleksandr on 17.11.2017.
 */
public class WatchdogView {

    @JsonProperty
    private Long id;

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

    public WatchdogView() {}

    public WatchdogView(
            Long id,
            String startDate,
            String endDate,
            String keyPhrase,
            List<Long> authorIds,
            List<Long> venueIds,
            List<Long> genreIds
    ) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.keyPhrase = keyPhrase;
        this.authorIds = authorIds;
        this.venueIds = venueIds;
        this.genreIds = genreIds;
    }

    public Long getId () {
        return this.id;
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

    public static WatchdogView fromEntity (Watchdog watchdog, List<Long> authorIds, List<Long> venueIds, List<Long> genreIds) {
        String startDateString = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(watchdog.getStartDate());
        String endDateString = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(watchdog.getEndDate());

        return new WatchdogView(
                watchdog.getId(),
                startDateString,
                endDateString,
                watchdog.getKeyPhrase(),
                authorIds,
                venueIds,
                genreIds
        );
    }
}
