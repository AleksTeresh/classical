/*
 * This file is generated by jOOQ.
*/
package fi.alekster.classical.db.tables.daos;


import fi.alekster.classical.db.tables.Performance;
import fi.alekster.classical.db.tables.records.PerformanceRecord;

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
public class PerformanceDao extends DAOImpl<PerformanceRecord, fi.alekster.classical.db.tables.pojos.Performance, Long> {

    /**
     * Create a new PerformanceDao without any configuration
     */
    public PerformanceDao() {
        super(Performance.PERFORMANCE, fi.alekster.classical.db.tables.pojos.Performance.class);
    }

    /**
     * Create a new PerformanceDao with an attached configuration
     */
    public PerformanceDao(Configuration configuration) {
        super(Performance.PERFORMANCE, fi.alekster.classical.db.tables.pojos.Performance.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Long getId(fi.alekster.classical.db.tables.pojos.Performance object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<fi.alekster.classical.db.tables.pojos.Performance> fetchById(Long... values) {
        return fetch(Performance.PERFORMANCE.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public fi.alekster.classical.db.tables.pojos.Performance fetchOneById(Long value) {
        return fetchOne(Performance.PERFORMANCE.ID, value);
    }

    /**
     * Fetch records that have <code>author_id IN (values)</code>
     */
    public List<fi.alekster.classical.db.tables.pojos.Performance> fetchByAuthorId(Long... values) {
        return fetch(Performance.PERFORMANCE.AUTHOR_ID, values);
    }

    /**
     * Fetch records that have <code>gig_id IN (values)</code>
     */
    public List<fi.alekster.classical.db.tables.pojos.Performance> fetchByGigId(Long... values) {
        return fetch(Performance.PERFORMANCE.GIG_ID, values);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<fi.alekster.classical.db.tables.pojos.Performance> fetchByName(String... values) {
        return fetch(Performance.PERFORMANCE.NAME, values);
    }

    /**
     * Fetch records that have <code>description IN (values)</code>
     */
    public List<fi.alekster.classical.db.tables.pojos.Performance> fetchByDescription(String... values) {
        return fetch(Performance.PERFORMANCE.DESCRIPTION, values);
    }

    /**
     * Fetch records that have <code>conductor IN (values)</code>
     */
    public List<fi.alekster.classical.db.tables.pojos.Performance> fetchByConductor(String... values) {
        return fetch(Performance.PERFORMANCE.CONDUCTOR, values);
    }

    /**
     * Fetch records that have <code>players IN (values)</code>
     */
    public List<fi.alekster.classical.db.tables.pojos.Performance> fetchByPlayers(String... values) {
        return fetch(Performance.PERFORMANCE.PLAYERS, values);
    }

    /**
     * Fetch records that have <code>youtube_id IN (values)</code>
     */
    public List<fi.alekster.classical.db.tables.pojos.Performance> fetchByYoutubeId(String... values) {
        return fetch(Performance.PERFORMANCE.YOUTUBE_ID, values);
    }

    /**
     * Fetch records that have <code>wikipedia_link IN (values)</code>
     */
    public List<fi.alekster.classical.db.tables.pojos.Performance> fetchByWikipediaLink(String... values) {
        return fetch(Performance.PERFORMANCE.WIKIPEDIA_LINK, values);
    }
}
