/*
 * This file is generated by jOOQ.
*/
package fi.alekster.classical.db.tables.daos;


import fi.alekster.classical.db.tables.Author;
import fi.alekster.classical.db.tables.records.AuthorRecord;

import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AuthorDao extends DAOImpl<AuthorRecord, fi.alekster.classical.db.tables.pojos.Author, Long> {

    /**
     * Create a new AuthorDao without any configuration
     */
    public AuthorDao() {
        super(Author.AUTHOR, fi.alekster.classical.db.tables.pojos.Author.class);
    }

    /**
     * Create a new AuthorDao with an attached configuration
     */
    public AuthorDao(Configuration configuration) {
        super(Author.AUTHOR, fi.alekster.classical.db.tables.pojos.Author.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Long getId(fi.alekster.classical.db.tables.pojos.Author object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<fi.alekster.classical.db.tables.pojos.Author> fetchById(Long... values) {
        return fetch(Author.AUTHOR.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public fi.alekster.classical.db.tables.pojos.Author fetchOneById(Long value) {
        return fetchOne(Author.AUTHOR.ID, value);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<fi.alekster.classical.db.tables.pojos.Author> fetchByName(String... values) {
        return fetch(Author.AUTHOR.NAME, values);
    }

    /**
     * Fetch records that have <code>description IN (values)</code>
     */
    public List<fi.alekster.classical.db.tables.pojos.Author> fetchByDescription(String... values) {
        return fetch(Author.AUTHOR.DESCRIPTION, values);
    }

    /**
     * Fetch records that have <code>wikipedia_link IN (values)</code>
     */
    public List<fi.alekster.classical.db.tables.pojos.Author> fetchByWikipediaLink(String... values) {
        return fetch(Author.AUTHOR.WIKIPEDIA_LINK, values);
    }

    /**
     * Fetch records that have <code>image_url IN (values)</code>
     */
    public List<fi.alekster.classical.db.tables.pojos.Author> fetchByImageUrl(String... values) {
        return fetch(Author.AUTHOR.IMAGE_URL, values);
    }
}
