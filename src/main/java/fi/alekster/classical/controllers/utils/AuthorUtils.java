package fi.alekster.classical.controllers.utils;

import fi.alekster.classical.db.tables.pojos.Author;
import fi.alekster.classical.wikipedia.WikiFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Created by aleksandr on 9.12.2017.
 */
@Component
public class AuthorUtils {

    private final WikiFetcher wikiFetcher;
    private final CommonUtils commonUtils;

    @Autowired
    public AuthorUtils (WikiFetcher wikiFetcher, CommonUtils commonUtils) {
        this.wikiFetcher = wikiFetcher;
        this.commonUtils = commonUtils;
    }

    public Author constructAuthor(String name, Long id) {
        return new Author(
                id,
                name,
                wikiFetcher.fetchDescription(name),
                wikiFetcher.fetchUrl(name),
                "" // TODO: use wikiFetcher to get an actual image of the author
        );
    }

    public boolean isAuthorValid (Author authors) {
        String name = authors.getName();
        String[] nameTokens = name.split(" ");

        // if the name consists of more than 5 words, the author is invalid
        if (nameTokens.length > 5) {
            return false;
        }

        // if the second word in the author name is "by", the author is not valid
        if (nameTokens.length > 1 && Objects.equals(nameTokens[1], "by")) {
            return false;
        }

        int capitalWordsCount = 0;
        int nonCapitalWordsCount = 0;
        for (int i = 0; i < nameTokens.length; i++) {
            String word = nameTokens[i];

            if (word != null && !Objects.equals(word, "") && Character.isUpperCase(word.codePointAt(0))) {
                capitalWordsCount = capitalWordsCount + 1;
            } else {
                nonCapitalWordsCount = nonCapitalWordsCount + 1;
            }
        }

        // if there are more words starting with non-capital letter in the author's name
        // the author is invalid
        if (nonCapitalWordsCount > capitalWordsCount) {
            return false;
        }

        // if the whole authon name is just an html tag without text within, the name is invalid
        String plainTextName = commonUtils.htmlToText(name);
        if (plainTextName == null || Objects.equals(plainTextName, "")) {
            return false;
        }

        return true;
    }

    public Author removeTranscriptorFromName (Author author) {
        if (author.getName().contains(" – ")) {
            author.setName(author.getName().split(" – ")[0]);
        }

        return author;
    }
}
