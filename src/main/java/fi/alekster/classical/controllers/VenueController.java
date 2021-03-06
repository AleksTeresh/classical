package fi.alekster.classical.controllers;

import fi.alekster.classical.dao.ExVenueDao;
import fi.alekster.classical.representations.VenueView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by aleksandr on 6.11.2017.
 */
@RestController
@RequestMapping("/venue")
public class VenueController {
    private final ExVenueDao venueDao;

    @Autowired
    public VenueController(
            ExVenueDao venueDao
    ) {
        this.venueDao = venueDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<VenueView> getGenres() {
        return venueDao.findAll()
                .stream()
                .map(VenueView::fromEntity)
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public VenueView getGenre(@PathVariable("id") Long id) {
        return VenueView.fromEntity(venueDao.fetchOneById(id));
    }
}
