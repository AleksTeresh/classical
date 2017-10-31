/*
 * This file is generated by jOOQ.
*/
package fi.alekster.classical.db.tables.records;


import fi.alekster.classical.db.tables.Gig;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
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
public class GigRecord extends UpdatableRecordImpl<GigRecord> implements Record6<Long, Long, String, String, Timestamp, Integer> {

    private static final long serialVersionUID = 385366846;

    /**
     * Setter for <code>public.gig.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.gig.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.gig.venue_id</code>.
     */
    public void setVenueId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.gig.venue_id</code>.
     */
    public Long getVenueId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>public.gig.name</code>.
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.gig.name</code>.
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.gig.description</code>.
     */
    public void setDescription(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.gig.description</code>.
     */
    public String getDescription() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.gig.timestamp</code>.
     */
    public void setTimestamp(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.gig.timestamp</code>.
     */
    public Timestamp getTimestamp() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>public.gig.duration</code>.
     */
    public void setDuration(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.gig.duration</code>.
     */
    public Integer getDuration() {
        return (Integer) get(5);
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
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Long, Long, String, String, Timestamp, Integer> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Long, Long, String, String, Timestamp, Integer> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return Gig.GIG.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return Gig.GIG.VENUE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Gig.GIG.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Gig.GIG.DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return Gig.GIG.TIMESTAMP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return Gig.GIG.DURATION;
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
    public Long value2() {
        return getVenueId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getTimestamp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getDuration();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GigRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GigRecord value2(Long value) {
        setVenueId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GigRecord value3(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GigRecord value4(String value) {
        setDescription(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GigRecord value5(Timestamp value) {
        setTimestamp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GigRecord value6(Integer value) {
        setDuration(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GigRecord values(Long value1, Long value2, String value3, String value4, Timestamp value5, Integer value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GigRecord
     */
    public GigRecord() {
        super(Gig.GIG);
    }

    /**
     * Create a detached, initialised GigRecord
     */
    public GigRecord(Long id, Long venueId, String name, String description, Timestamp timestamp, Integer duration) {
        super(Gig.GIG);

        set(0, id);
        set(1, venueId);
        set(2, name);
        set(3, description);
        set(4, timestamp);
        set(5, duration);
    }
}