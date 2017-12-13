package fi.alekster.classical.controllers.utils;

import fi.alekster.classical.controllers.model.GigDetailed;
import fi.alekster.classical.db.tables.pojos.Gig;
import fi.alekster.classical.representations.GenreView;
import fi.alekster.classical.representations.PerformanceView;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by aleksandr on 13.12.2017.
 */
@Component
public class GigUtils {
    public List<Gig> filterSuggestions (Gig detailedGig, List<Gig> gigs) {
        // make sure the main gig itself is not among them
        return gigs.stream()
                .filter(p -> !Objects.equals(p.getId(), detailedGig.getId()))
                .collect(Collectors.toList());
    }

    /**
     * sort the gigs consifering 1) the venue 2) genre of performances 3) datatime
     * the onces that are more similar to the currently views gig, go first
     **/
    public List<GigDetailed> sortSuggestions (GigDetailed detailedGig, List<GigDetailed> gigs) {
        gigs.sort((a, b) -> {
            Gig aGig = a.getGig();
            Gig bGig = b.getGig();

            // if the venues are the same or they are both not what the reference venue is,
            // procede to comparison by genre
            if (
                    Objects.equals(aGig.getVenueId(), bGig.getVenueId()) ||
                            (
                                    !Objects.equals(aGig.getVenueId(), detailedGig.getGig().getVenueId()) &&
                                    !Objects.equals(bGig.getVenueId(), detailedGig.getGig().getVenueId())
                            )
                    ) {
                // get "score" of how similar the a and b to the reference in terms of genre of performances
                int aGenreCount = getNumberOfMatchingGenres(
                        a.getPerformances(),
                        detailedGig.getPerformances()
                );
                int bGenreCount = getNumberOfMatchingGenres(
                        b.getPerformances(),
                        detailedGig.getPerformances()
                );

                // if the genre scores are the same, compare by closeness of the datetime
                if (aGenreCount == bGenreCount) {

                    // if the reference does not have a timestamp, or a and b both do not have it;
                    // a and b are equal
                    Timestamp detTimestamp = detailedGig.getGig().getTimestamp();
                    Timestamp aTimestamp = aGig.getTimestamp();
                    Timestamp bTimestamp = bGig.getTimestamp();
                    if (
                            detTimestamp == null || detTimestamp.getTime() == 0L ||
                                    (
                                            (aTimestamp == null || aTimestamp.getTime() == 0L) &&
                                                    (bTimestamp == null || bTimestamp.getTime() == 0L)
                                    )
                            ) {
                        return 0;
                    }

                    // if only a does not have a timestamp, b goes first
                    if (aTimestamp == null || aTimestamp.getTime() == 0L) {
                        return 1;
                    }

                    // if only b does not have a timestamp, a goes first
                    if (bTimestamp == null || bTimestamp.getTime() == 0L) {
                        return -1;
                    }

                    // if all the 3 gigs have the timestamps, sort based on date distance
                    long aDateDistance = Math.abs(aGig.getTimestamp().getTime() - detailedGig.getGig().getTimestamp().getTime());
                    long bDateDistance = Math.abs(aGig.getTimestamp().getTime() - detailedGig.getGig().getTimestamp().getTime());

                    if (aDateDistance == bDateDistance) {
                        return 0;
                    }

                    if (aDateDistance > bDateDistance) {
                        return 1;
                    }

                    return -1;
                }

                if (aGenreCount > bGenreCount) {
                    return -1;
                }

                return 1;
            }

            // the gig wi the same venue as the reference goes first
            if (
                    Objects.equals(aGig.getVenueId(), detailedGig.getGig().getVenueId())  &&
                            !Objects.equals(bGig.getVenueId(), detailedGig.getGig().getVenueId())
            ) {
                return -1;
            }

            return 1;
        });

        return gigs;
    }

    private int getNumberOfMatchingGenres (List<PerformanceView> performances, List<PerformanceView> referencePerformances) {
        int counter = 0;
        List<Integer> matchedIndices = new ArrayList<>();

        for (int i = 0; i < referencePerformances.size(); i = i + 1) {
            PerformanceView p = referencePerformances.get(i);

            for (int idx = 0; idx < performances.size(); idx = idx + 1) {
                PerformanceView s = performances.get(idx);

                if (!matchedIndices.contains(idx)) {
                    boolean genreMatch = false;

                    for (int k = 0; k < p.getGenres().size(); k = k + 1) {
                        GenreView genre = p.getGenres().get(k);

                        if (s.getGenres().stream().anyMatch(z -> Objects.equals(z.getId(), genre.getId()))) {
                            genreMatch = true;
                            break;
                        }
                    }

                    if (genreMatch) {
                        matchedIndices.add(idx);
                        counter = counter + 1;
                    }
                }
            }
        }

        return counter;
    }
}
