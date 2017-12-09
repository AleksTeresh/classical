package fi.alekster.classical.controllers.utils;

import fi.alekster.classical.db.tables.pojos.Author;
import fi.alekster.classical.db.tables.pojos.Genre;
import fi.alekster.classical.db.tables.pojos.Performance;
import fi.alekster.classical.wikipedia.WikiFetcher;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Created by aleksandr on 9.12.2017.
 */
@Component
public class GenreUtils {

    private final WikiFetcher wikiFetcher;
    private final CommonUtils commonUtils;

    @Autowired
    public GenreUtils(WikiFetcher wikiFetcher, CommonUtils commonUtils) {
        this.wikiFetcher = wikiFetcher;
        this.commonUtils = commonUtils;
    }

    public Optional<Genre> getMatchingGenre (Performance performance, List<Genre> genres, List<Author> authors) {
        Optional<Genre> matchGenre = Optional.empty();

        // try to define a genre from the performance's name
        matchGenre = getMatchingGenreFromPerformanceName(performance, genres, authors);

        // if perf's name does not help, look at first several sentences of wikipedia article to define the genre
        if (!matchGenre.isPresent()) {
            matchGenre = getMatchingGenreFromWiki(performance, genres, authors);
        }

        return matchGenre;
    }

    public Optional<Genre> getMatchingGenreFromPerformanceName (
            Performance performance,
            List<Genre> genres,
            List<Author> authors
    ) {
        Optional<Genre> matchGenre = Optional.empty();
        int maxPartialRation = -1;

        for (Genre genre : genres) {
            try {
                int partialRatio = FuzzySearch.partialRatio(genre.getName(), performance.getName());
                // System.out.println(partialRatio + "  " + genre.getName());
                if (partialRatio >= 75 && (!matchGenre.isPresent() || maxPartialRation < partialRatio)) {
                    matchGenre = Optional.of(genre);
                    maxPartialRation = partialRatio;
                }
            } catch (Exception ex) {}
        }

        return matchGenre;
    }

    public Optional<Genre> getMatchingGenreFromWiki (Performance performance, List<Genre> genres, List<Author> authors) {
        Optional<Genre> matchGenre = Optional.empty();
        int maxPartialRation = -1;

        // TODO: add error handling here
        String authorName = authors.stream()
                .filter(a -> a.getId() == performance.getAuthorId())
                .findFirst().get().getName();

        String firstSentence = commonUtils.getRelatedTextForPerformance(
                performance.getName(),
                authorName,
                wikiFetcher::fetchFirstSentence
        );

        if (firstSentence != null && firstSentence != "") {
            for (Genre genre : genres) {
                try {
                    int partialRatio = FuzzySearch.partialRatio(genre.getName(), firstSentence);
                    // System.out.println(partialRatio + "  " + genre.getName());
                    if (partialRatio >= 75 && (!matchGenre.isPresent() || maxPartialRation < partialRatio)) {
                        matchGenre = Optional.of(genre);
                        maxPartialRation = partialRatio;
                    }
                } catch (Exception ex) {}
            }
        }

        return matchGenre;
    }
}
