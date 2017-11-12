/*
 * This file is generated by jOOQ.
*/
package fi.alekster.classical.db.tables.records;


import fi.alekster.classical.db.tables.Author;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


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
public class AuthorRecord extends UpdatableRecordImpl<AuthorRecord> implements Record5<Long, String, String, String, String> {

    private static final long serialVersionUID = 641082100;

    /**
     * Setter for <code>public.author.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.author.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.author.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.author.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.author.description</code>.
     */
    public void setDescription(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.author.description</code>.
     */
    public String getDescription() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.author.wikipedia_link</code>.
     */
    public void setWikipediaLink(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.author.wikipedia_link</code>.
     */
    public String getWikipediaLink() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.author.image_url</code>.
     */
    public void setImageUrl(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.author.image_url</code>.
     */
    public String getImageUrl() {
        return (String) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Long, String, String, String, String> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Long, String, String, String, String> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return Author.AUTHOR.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Author.AUTHOR.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Author.AUTHOR.DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Author.AUTHOR.WIKIPEDIA_LINK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return Author.AUTHOR.IMAGE_URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getWikipediaLink();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getImageUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthorRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthorRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthorRecord value3(String value) {
        setDescription(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthorRecord value4(String value) {
        setWikipediaLink(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthorRecord value5(String value) {
        setImageUrl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthorRecord values(Long value1, String value2, String value3, String value4, String value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AuthorRecord
     */
    public AuthorRecord() {
        super(Author.AUTHOR);
    }

    /**
     * Create a detached, initialised AuthorRecord
     */
    public AuthorRecord(Long id, String name, String description, String wikipediaLink, String imageUrl) {
        super(Author.AUTHOR);

        set(0, id);
        set(1, name);
        set(2, description);
        set(3, wikipediaLink);
        set(4, imageUrl);
    }
}
