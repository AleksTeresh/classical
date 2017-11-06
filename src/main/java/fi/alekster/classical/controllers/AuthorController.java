package fi.alekster.classical.controllers;

import fi.alekster.classical.dao.ExAuthorDao;
import fi.alekster.classical.representations.AuthorView;
import fi.alekster.classical.representations.responses.AuthorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by aleksandr on 6.11.2017.
 */
@RestController
@RequestMapping("/author")
public class AuthorController {
    private final ExAuthorDao authorDao;

    @Autowired
    public AuthorController(
            ExAuthorDao authorDao
    ) {
        this.authorDao = authorDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public AuthorResponse getAuthors(
            @RequestParam(value = "keyPhrase", defaultValue = "", required = false) String keyPhrase,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "offset", defaultValue = "0") int offset
    ) {
        long count = authorDao.count(keyPhrase);
        List<AuthorView> authors = authorDao.fetch(keyPhrase, limit, offset).stream()
                .map(AuthorView::fromEntity).collect(Collectors.toList());

        return new AuthorResponse(authors, count);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public AuthorView getAuthor(@PathVariable("id") Long id) {
        return AuthorView.fromEntity(authorDao.fetchOneById(id));
    }
}
