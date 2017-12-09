package fi.alekster.classical.wikipedia.model.specialSearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by aleksandr on 9.12.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpecSearchQuery {
    private List<SearchEntry> search;

    public List<SearchEntry> getSearch() {
        return search;
    }

    public void setSearch(List<SearchEntry> search) {
        this.search = search;
    }
}
