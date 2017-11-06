package fi.alekster.classical.controllers;

import fi.alekster.classical.dao.ExGenreDao;
import fi.alekster.classical.representations.GenreView;
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
@RequestMapping("/genre")
public class GenreController {
    private final ExGenreDao genreDao;

    @Autowired
    public GenreController(
            ExGenreDao genreDao
    ) {
        this.genreDao = genreDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<GenreView> getGenres() {
        return genreDao.findAll()
                .stream()
                .map(GenreView::fromEntity)
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public GenreView getGenre(@PathVariable("id") Long id) {
        return GenreView.fromEntity(genreDao.fetchOneById(id));
    }
}
