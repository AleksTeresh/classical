package fi.alekster.classical.wikipedia.model.specialSearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by aleksandr on 9.12.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpecialSearchResult {
    private String batchcomplete;
    private SpecSearchQuery query;

    public String getBatchcomplete() {
        return batchcomplete;
    }

    public void setBatchcomplete(String batchcomplete) {
        this.batchcomplete = batchcomplete;
    }

    public SpecSearchQuery getQuery() {
        return query;
    }

    public void setQuery(SpecSearchQuery query) {
        this.query = query;
    }
}
