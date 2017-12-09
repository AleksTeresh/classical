package fi.alekster.classical.controllers.utils;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * Created by aleksandr on 9.12.2017.
 */
@Component
public class CommonUtils {

    public Timestamp stringToTimestamp(String stringTimestamp) {
        try {
            System.out.println(stringTimestamp);
            DateTime dateTime = ISODateTimeFormat.dateTimeParser().parseDateTime(stringTimestamp);
            return Timestamp.valueOf(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception ex) {
            return Timestamp.valueOf("1970-01-01 00:00:00");
        }
    }
}
