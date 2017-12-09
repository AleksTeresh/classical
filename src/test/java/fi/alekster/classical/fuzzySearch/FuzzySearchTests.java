package fi.alekster.classical.fuzzySearch;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by aleksandr on 8.12.2017.
 */
public class FuzzySearchTests {
    private List<String> genres;



    public FuzzySearchTests () {
        genres = new ArrayList<>();

        genres.add("Waltz");
        genres.add("Suite");
        genres.add("Symphony");
        genres.add("Sonata");
        genres.add("Scherzo");
        genres.add("Rhapsody");
        genres.add("Requiem");
        genres.add("Quintet");
        genres.add("Quartet");
        genres.add("Prelude");
        genres.add("Polonaise");
        genres.add("Serenade");
        genres.add("Opera");
        genres.add("Nocturne");
        genres.add("March");
        genres.add("Mazurka");
        genres.add("Impromptu");
        genres.add("Etude");
        genres.add("Dance");
        genres.add("Concerto");
        genres.add("Caprice");
        genres.add("Ballet");
        genres.add("Ballade");
        genres.add("Overture");
        genres.add("Other");
;    }

    @Test
    public void TestCorrectGenreUnderstandingOnCompositeNames () {
        String name = "Prelude and Fugue";
        Optional<String> matchGenre = Optional.empty();
        int maxPartialRation = -1;

        for (String genre : genres) {
            try {
                int partialRatio = FuzzySearch.partialRatio(genre, name);
                System.out.println(partialRatio + "  " + genre);
                if (partialRatio >= 75 && (!matchGenre.isPresent() || maxPartialRation < partialRatio)) {
                    matchGenre = Optional.of(genre);
                    maxPartialRation = partialRatio;
                }
            } catch (Exception ex) {}
        }

        Assert.assertEquals("Prelude", matchGenre.get());


    }
}
