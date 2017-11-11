package fi.alekster.classical.wikipedia.model;

/**
 * Created by aleksandr on 9.11.2017.
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Page {
    private Long pageid;
    private int ns;
    private String title;
    private String extract;

    public Page() {

    }

    public Long getPageid() {
        return pageid;
    }

    public void setPageid(Long pageid) {
        this.pageid = pageid;
    }

    public int getNs() {
        return ns;
    }

    public void setPNs(int ns) {
        this.ns = ns;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExtract() {
        return extract;
    }

    public void setExtract(String extract) {
        this.extract = extract;
    }
}
