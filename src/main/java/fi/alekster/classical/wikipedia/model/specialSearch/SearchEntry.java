package fi.alekster.classical.wikipedia.model.specialSearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by aleksandr on 9.12.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchEntry {
    private String title;
    private Long pageid;
    private Long size;
    private Long wordcount;
    private String snippet;
    private String timestamp;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPageid() {
        return pageid;
    }

    public void setPageid(Long pageid) {
        this.pageid = pageid;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getWordcount() {
        return wordcount;
    }

    public void setWordcount(Long wordcount) {
        this.wordcount = wordcount;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
