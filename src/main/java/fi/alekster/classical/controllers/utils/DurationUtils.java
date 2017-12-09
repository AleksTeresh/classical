package fi.alekster.classical.controllers.utils;

import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Created by aleksandr on 9.12.2017.
 */
@Component
public class DurationUtils {

    public Long stringToMilliseconds (String duration) {
        if (Objects.equals(duration, "") || duration == null) {
            return 0L;
        }

        String[] tokens = duration.split(" ");

        // valid string representatino of duration should have even number of tokens
        if (tokens.length % 2 != 0) {
            return 0L;
        }

        Long totalDuration = 0L;

        for (int i = 0; i < tokens.length / 2; i++) {
            String numberString = tokens[i * 2];
            String timePeriodString = tokens[i * 2 + 1];

            int number;
            try {
                number = Integer.parseInt(numberString);
            } catch (Exception ex) {
                ex.printStackTrace();

                // if the duration is not parsable, return 0 value
                return 0L;
            }

            switch (timePeriodString) {
                case "minute":
                case "minutes":
                    totalDuration = totalDuration + number * 60 * 1000;
                    break;

                case "hour":
                case "hours":
                    totalDuration = totalDuration + number * 60 * 60 * 1000;
                    break;

                case "day":
                case "days":
                    totalDuration = totalDuration + number * 24 * 60 * 60 * 1000;
                    break;

                // if the value is not minutes, days or hours, be safe and return 0 value for the whole duration
                default:
                    return 0L;
            }
        }

        return totalDuration;
    }
}
