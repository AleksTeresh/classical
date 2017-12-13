/*
 * This file is generated by jOOQ.
*/
package fi.alekster.classical.db;


import fi.alekster.classical.db.tables.Author;
import fi.alekster.classical.db.tables.Credential;
import fi.alekster.classical.db.tables.Databasechangelog;
import fi.alekster.classical.db.tables.Databasechangeloglock;
import fi.alekster.classical.db.tables.Genre;
import fi.alekster.classical.db.tables.Gig;
import fi.alekster.classical.db.tables.Like;
import fi.alekster.classical.db.tables.Performance;
import fi.alekster.classical.db.tables.PerformanceGenre;
import fi.alekster.classical.db.tables.User;
import fi.alekster.classical.db.tables.Venue;
import fi.alekster.classical.db.tables.Watchdog;
import fi.alekster.classical.db.tables.WatchdogAuthor;
import fi.alekster.classical.db.tables.WatchdogGenre;
import fi.alekster.classical.db.tables.WatchdogVenue;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in public
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>public.author</code>.
     */
    public static final Author AUTHOR = fi.alekster.classical.db.tables.Author.AUTHOR;

    /**
     * The table <code>public.credential</code>.
     */
    public static final Credential CREDENTIAL = fi.alekster.classical.db.tables.Credential.CREDENTIAL;

    /**
     * The table <code>public.databasechangelog</code>.
     */
    public static final Databasechangelog DATABASECHANGELOG = fi.alekster.classical.db.tables.Databasechangelog.DATABASECHANGELOG;

    /**
     * The table <code>public.databasechangeloglock</code>.
     */
    public static final Databasechangeloglock DATABASECHANGELOGLOCK = fi.alekster.classical.db.tables.Databasechangeloglock.DATABASECHANGELOGLOCK;

    /**
     * The table <code>public.genre</code>.
     */
    public static final Genre GENRE = fi.alekster.classical.db.tables.Genre.GENRE;

    /**
     * The table <code>public.gig</code>.
     */
    public static final Gig GIG = fi.alekster.classical.db.tables.Gig.GIG;

    /**
     * The table <code>public.like</code>.
     */
    public static final Like LIKE = fi.alekster.classical.db.tables.Like.LIKE;

    /**
     * The table <code>public.performance</code>.
     */
    public static final Performance PERFORMANCE = fi.alekster.classical.db.tables.Performance.PERFORMANCE;

    /**
     * The table <code>public.performance_genre</code>.
     */
    public static final PerformanceGenre PERFORMANCE_GENRE = fi.alekster.classical.db.tables.PerformanceGenre.PERFORMANCE_GENRE;

    /**
     * The table <code>public.user</code>.
     */
    public static final User USER = fi.alekster.classical.db.tables.User.USER;

    /**
     * The table <code>public.venue</code>.
     */
    public static final Venue VENUE = fi.alekster.classical.db.tables.Venue.VENUE;

    /**
     * The table <code>public.watchdog</code>.
     */
    public static final Watchdog WATCHDOG = fi.alekster.classical.db.tables.Watchdog.WATCHDOG;

    /**
     * The table <code>public.watchdog_author</code>.
     */
    public static final WatchdogAuthor WATCHDOG_AUTHOR = fi.alekster.classical.db.tables.WatchdogAuthor.WATCHDOG_AUTHOR;

    /**
     * The table <code>public.watchdog_genre</code>.
     */
    public static final WatchdogGenre WATCHDOG_GENRE = fi.alekster.classical.db.tables.WatchdogGenre.WATCHDOG_GENRE;

    /**
     * The table <code>public.watchdog_venue</code>.
     */
    public static final WatchdogVenue WATCHDOG_VENUE = fi.alekster.classical.db.tables.WatchdogVenue.WATCHDOG_VENUE;
}
