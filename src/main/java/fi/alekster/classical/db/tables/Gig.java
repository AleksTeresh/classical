/*
 * This file is generated by jOOQ.
*/
package fi.alekster.classical.db.tables;


import fi.alekster.classical.db.Keys;
import fi.alekster.classical.db.Public;
import fi.alekster.classical.db.tables.records.GigRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


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
public class Gig extends TableImpl<GigRecord> {

    private static final long serialVersionUID = 957877475;

    /**
     * The reference instance of <code>public.gig</code>
     */
    public static final Gig GIG = new Gig();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GigRecord> getRecordType() {
        return GigRecord.class;
    }

    /**
     * The column <code>public.gig.id</code>.
     */
    public final TableField<GigRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('gig_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>public.gig.venue_id</code>.
     */
    public final TableField<GigRecord, Long> VENUE_ID = createField("venue_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.gig.name</code>.
     */
    public final TableField<GigRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * The column <code>public.gig.description</code>.
     */
    public final TableField<GigRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.gig.timestamp</code>.
     */
    public final TableField<GigRecord, Timestamp> TIMESTAMP = createField("timestamp", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>public.gig.duration</code>.
     */
    public final TableField<GigRecord, Long> DURATION = createField("duration", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.gig.image_url</code>.
     */
    public final TableField<GigRecord, String> IMAGE_URL = createField("image_url", org.jooq.impl.SQLDataType.VARCHAR.length(511), this, "");

    /**
     * The column <code>public.gig.url</code>.
     */
    public final TableField<GigRecord, String> URL = createField("url", org.jooq.impl.SQLDataType.VARCHAR.length(511).nullable(false), this, "");

    /**
     * The column <code>public.gig.create_time</code>.
     */
    public final TableField<GigRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * Create a <code>public.gig</code> table reference
     */
    public Gig() {
        this("gig", null);
    }

    /**
     * Create an aliased <code>public.gig</code> table reference
     */
    public Gig(String alias) {
        this(alias, GIG);
    }

    private Gig(String alias, Table<GigRecord> aliased) {
        this(alias, aliased, null);
    }

    private Gig(String alias, Table<GigRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<GigRecord, Long> getIdentity() {
        return Keys.IDENTITY_GIG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<GigRecord> getPrimaryKey() {
        return Keys.PK_GIG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<GigRecord>> getKeys() {
        return Arrays.<UniqueKey<GigRecord>>asList(Keys.PK_GIG);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<GigRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<GigRecord, ?>>asList(Keys.GIG__FK_GIG_VENUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Gig as(String alias) {
        return new Gig(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Gig rename(String name) {
        return new Gig(name, null);
    }
}
