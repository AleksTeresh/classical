package fi.alekster.classical.wikipedia;

import com.google.gson.Gson;
import fi.alekster.classical.wikipedia.model.QueryResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by aleksandr on 8.11.2017.
 */
@Component
public class WikiFetcher {
    private final RestTemplate restTemplate;

    public WikiFetcher() {
        restTemplate = new RestTemplate();
    }

    public String fetchUrl(String keyPhrase) {
        Object[] openSearchResult = getOpenSearchResult(keyPhrase);

        String url = "";

        if (openSearchResult.length >= 4) {
            try {
                url = ((List<String>) openSearchResult[3]).get(0);
            } catch (Exception ex) {
                System.out.println(ex);

                return "";
            }
        } else {
            return "";
        }

        return url;
    }

    public String fetchDescription(String keyPhrase) {
        Object[] openSearchResult = getOpenSearchResult(keyPhrase);

        String title = "";

        if (openSearchResult.length >= 2) {
            try {
                title = ((List<String>) openSearchResult[1]).get(0);
            } catch (Exception ex) {
                ex.printStackTrace();
                return "";
            }
        } else {
            return "";
        }
/*
        String encodedTitle;
        try {
            encodedTitle = URLEncoder.encode(title, "UTF-8");
            System.out.println("Encoded title " + encodedTitle);
        } catch (UnsupportedEncodingException ex) {
            return "";
        }
*/
        QueryResult queryResult = restTemplate.getForObject(
                "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&exintro&explaintext&titles=" +
                        title,
                QueryResult.class
        );

        Gson gson = new Gson();
        String json = gson.toJson(queryResult);
        System.out.printf(json);

        // TODO: add error handling here
        return queryResult.getQuery().getPages().values().iterator().next().getExtract();
    }

    private Object[] getOpenSearchResult(String keyPhrase) {
        /*
        String encodedKeyPhrase;
        try {
            encodedKeyPhrase = URLEncoder.encode(keyPhrase, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            return new Object[0];
        }
*/
        Object[] openSearchResult = restTemplate.getForObject(
                "https://en.wikipedia.org/w/api.php?action=opensearch&limit=1&namespace=0&format=json&search=" + keyPhrase,
                Object[].class
        );

        return openSearchResult;
    }
}
