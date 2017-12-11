package fi.alekster.classical.wikipedia;

import fi.alekster.classical.wikipedia.model.query.Page;
import fi.alekster.classical.wikipedia.model.query.QueryResult;
import fi.alekster.classical.wikipedia.model.specialSearch.SearchEntry;
import fi.alekster.classical.wikipedia.model.specialSearch.SpecialSearchResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
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
        String fullTitle = getFullTitle(keyPhrase);
        Object[] openSearchResult = getOpenSearchResult(fullTitle);

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

    public String fetchFirstSentence (String keyPhrase) {
        String fullTitle = getFullTitle(keyPhrase);

        Object[] openSearchResult = getOpenSearchResult(fullTitle);
        if (openSearchResult.length >= 3) {
            try {
                return ((List<String>) openSearchResult[2]).get(0);
            } catch (Exception ex) {
                System.out.println(keyPhrase);
                System.out.println(openSearchResult.length);
                ex.printStackTrace();
                return "";
            }
        } else {
            return "";
        }
    }

    public String fetchDescription(String keyPhrase) {
        String title = getFullTitle(keyPhrase);
        if (title == "") {
            return "";
        }

        try {
            QueryResult queryResult = restTemplate.getForObject(
                    "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&exintro&explaintext&redirects&titles=" +
                            title,
                    QueryResult.class
            );

            Collection<Page> pages = queryResult.getQuery().getPages().values();

            if (!pages.isEmpty()) {
                return pages.iterator().next().getExtract();
            }
        } catch (Exception e) {
            return "";
        }

        return "";
    }

    private String getFullTitle (String keyPhrase) {
        if (keyPhrase == "" || keyPhrase == null) {
            return "";
        }

        try {
            SpecialSearchResult specialSearchResult = restTemplate.getForObject(
                    "https://en.wikipedia.org/w/api.php?action=query&list=search&format=json&srsearch=" +
                            keyPhrase,
                    SpecialSearchResult.class
            );

            List<SearchEntry> searchEntries = specialSearchResult.getQuery().getSearch();

            if (searchEntries.isEmpty()) {
                return "";
            } else {
                return searchEntries.get(0).getTitle();
            }
        } catch (Exception ex) {
            return "";
        }
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
        try {
            Object[] openSearchResult = restTemplate.getForObject(
                    "https://en.wikipedia.org/w/api.php?action=opensearch&limit=1&namespace=0&redirects=resolve&format=json&search=" + keyPhrase,
                    Object[].class
            );

            return openSearchResult;
        } catch (Exception ex) {
            return new Object[0];
        }
    }
}
