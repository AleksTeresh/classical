package fi.alekster.classical.youtube;

/**
 * Created by aleksandr on 8.11.2017.
 */
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class YoutubeSearcher {
    @Value("${youtube.api-key}")
    private String apiKey;

    private YouTube youtube;

    public YoutubeSearcher() {
        youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            public void initialize(HttpRequest request) throws IOException {
            }
        }).setApplicationName("classical").build();
    }

    public void setApiKey (String key) {
        this.apiKey = key;
    }

    public String getYoutubeId (String keyPhrase) {
        try {
            YouTube.Search.List search = youtube.search().list("id,snippet");

            search.setKey(apiKey);
            search.setQ(keyPhrase);
            search.setType("video");
            search.setFields("items(id/videoId,snippet/title)");
            search.setMaxResults(5L);

            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            if (searchResultList != null && !searchResultList.isEmpty()) {
                SearchResult firstVideo = searchResultList.iterator().next();
                ResourceId rId = firstVideo.getId();

                if (rId.size() > 0) {
                    String videoId = rId.getVideoId();
                    String title =  firstVideo.getSnippet().getTitle();

                    // check that the found video is indeed relevant
                    // otherwise, return an empty string
                    if (FuzzySearch.tokenSortPartialRatio(keyPhrase, title) >= 50) {
                        return videoId;
                    }

                    return "";
                }
            }
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return "";
    }
}
