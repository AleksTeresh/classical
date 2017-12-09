package fi.alekster.classical.controllers.utils;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.function.Function;

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

    public String getRelatedTextForPerformance(String name, String author, Function<String, String> relatedTextRetriever) {
        String[] authorTokens = author.split(" ");
        String authorLastName = authorTokens.length > 0
                ? authorTokens[authorTokens.length - 1]
                : "";

        String result1 = relatedTextRetriever.apply(name + " " + authorLastName);
        String result2 = relatedTextRetriever.apply(name);
        String finalResult = "";

        if (FuzzySearch.ratio(name + " (" + authorLastName + ")", result1) >=
                FuzzySearch.ratio(name + " (" + authorLastName + ")", result2)) {
            finalResult = result1;
        } else {
            finalResult = result2;
        }

        return finalResult;
    }
}
