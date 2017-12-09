package fi.alekster.classical.controllers.utils;

import fi.alekster.classical.db.tables.pojos.Author;
import fi.alekster.classical.wikipedia.WikiFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by aleksandr on 9.12.2017.
 */
@Component
public class AuthorUtils {

    private final WikiFetcher wikiFetcher;

    @Autowired
    public AuthorUtils (WikiFetcher wikiFetcher) {
        this.wikiFetcher = wikiFetcher;
    }

    public Author constructAuthor(String name, Long id) {
        return new Author(
                id,
                name,
                wikiFetcher.fetchDescription(name),
                wikiFetcher.fetchUrl(name),
                "" // TODO: use wikiFetcher to get an actual image
        );
    }
}
