package fi.alekster.classical.controllers.utils;

import fi.alekster.classical.db.tables.pojos.Author;
import fi.alekster.classical.db.tables.pojos.Performance;
import fi.alekster.classical.representations.requests.PerformanceRequest;
import fi.alekster.classical.wikipedia.WikiFetcher;
import fi.alekster.classical.youtube.YoutubeSearcher;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by aleksandr on 9.12.2017.
 */
@Component
public class PerformanceUtils {
    private final YoutubeSearcher youtubeSearcher;
    private final WikiFetcher wikiFetcher;
    private final CommonUtils commonUtils;

    @Autowired
    public PerformanceUtils (
            YoutubeSearcher youtubeSearcher,
            WikiFetcher wikiFetcher,
            CommonUtils commonUtils
    ) {
        this.youtubeSearcher = youtubeSearcher;
        this.wikiFetcher = wikiFetcher;
        this.commonUtils = commonUtils;
    }

    public Performance requestToPerformance (PerformanceRequest performanceRequest, List<Author> authors) {
        List<String> authorNames = authors
                .stream()
                .map(p -> p.getName())
                .collect(Collectors.toList());
        ExtractedResult result = FuzzySearch.extractOne(performanceRequest.getAuthor(), authorNames);
        Long authorId = authors.stream()
                .filter(p -> Objects.equals(p.getName(), result.getString()))
                .findFirst().get().getId();

        return constructPerformances(
                performanceRequest,
                0L,
                youtubeSearcher.getYoutubeId(
                        performanceRequest.getAuthor() + " " + performanceRequest.getName()
                ),
                getOpusWikipediaUrl(performanceRequest),
                authorId
        );
    }

    private String getOpusWikipediaUrl (PerformanceRequest performanceRequest) {
        String url = commonUtils.getRelatedTextForPerformance(
                performanceRequest.getName(),
                performanceRequest.getAuthor(),
                wikiFetcher::fetchUrl
        );

        return url;
    }

    private Performance constructPerformances(
            PerformanceRequest perfReqs,
            Long gigId,
            String youTubeId,
            String wikipediaLink,
            Long authorId
    ) {
        return new Performance(
                0L,
                authorId, // authorId
                gigId, // gigId
                perfReqs.getName(),
                "", // description
                "", // conductor
                "", // players
                youTubeId,
                wikipediaLink
        );
    }
}
