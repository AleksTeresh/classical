package fi.alekster.classical.wikipedia.model.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

/**
 * Created by aleksandr on 9.11.2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Query {

    private Map<Long, Page> pages;

    public Map<Long, Page> getPages() {
        return this.pages;
    }

    public void setPages(Map<Long, Page> pages) {
        this.pages = pages;
    }
}
