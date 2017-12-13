package fi.alekster.classical.controllers.model;

import fi.alekster.classical.db.tables.pojos.Gig;
import fi.alekster.classical.representations.PerformanceView;

import java.util.List;

/**
 * Created by aleksandr on 13.12.2017.
 */
public class GigDetailed {

    private Gig gig;

    private List<PerformanceView> performances;

    public GigDetailed (Gig gig, List<PerformanceView> performances) {
        this.gig = gig;
        this.performances = performances;
    }

    public Gig getGig() {
        return gig;
    }

    public List<PerformanceView> getPerformances() {
        return performances;
    }
}
