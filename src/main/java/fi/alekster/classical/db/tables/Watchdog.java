/*
 * This file is generated by jOOQ.
*/
package fi.alekster.classical.db.tables;


import fi.alekster.classical.db.Keys;
import fi.alekster.classical.db.Public;
import fi.alekster.classical.db.tables.records.WatchdogRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
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
public class Watchdog extends TableImpl<WatchdogRecord> {

    private static final long serialVersionUID = 1677793869;

    /**
     * The reference instance of <code>public.watchdog</code>
     */
    public static final Watchdog WATCHDOG = new Watchdog();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<WatchdogRecord> getRecordType() {
        return WatchdogRecord.class;
    }

    /**
     * The column <code>public.watchdog.id</code>.
     */
    public final TableField<WatchdogRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.watchdog.email</code>.
     */
    public final TableField<WatchdogRecord, String> EMAIL = createField("email", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * The column <code>public.watchdog.start_date</code>.
     */
    public final TableField<WatchdogRecord, Timestamp> START_DATE = createField("start_date", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>public.watchdog.end_date</code>.
     */
    public final TableField<WatchdogRecord, Timestamp> END_DATE = createField("end_date", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>public.watchdog.key_phrase</code>.
     */
    public final TableField<WatchdogRecord, String> KEY_PHRASE = createField("key_phrase", org.jooq.impl.SQLDataType.VARCHAR.length(511), this, "");

    /**
     * Create a <code>public.watchdog</code> table reference
     */
    public Watchdog() {
        this("watchdog", null);
    }

    /**
     * Create an aliased <code>public.watchdog</code> table reference
     */
    public Watchdog(String alias) {
        this(alias, WATCHDOG);
    }

    private Watchdog(String alias, Table<WatchdogRecord> aliased) {
        this(alias, aliased, null);
    }

    private Watchdog(String alias, Table<WatchdogRecord> aliased, Field<?>[] parameters) {
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
    public UniqueKey<WatchdogRecord> getPrimaryKey() {
        return Keys.PK_WATCHDOG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<WatchdogRecord>> getKeys() {
        return Arrays.<UniqueKey<WatchdogRecord>>asList(Keys.PK_WATCHDOG);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<WatchdogRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<WatchdogRecord, ?>>asList(Keys.WATCHDOG__FK_WATCHDOG_USER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Watchdog as(String alias) {
        return new Watchdog(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Watchdog rename(String name) {
        return new Watchdog(name, null);
    }
}
