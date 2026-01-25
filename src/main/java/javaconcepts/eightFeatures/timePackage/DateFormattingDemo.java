package javaconcepts.eightFeatures.timePackage;

import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/*
 * Date formatting is imp and in Java8 a little complex, since there are many date type instances we can pick from
 * like Instant, ZoneTime, LocalTime.
 *
 * New Date API has a new feature DateTimeFormatter. It proposes a set of predefined formats for you to use.
 *
 */
public class DateFormattingDemo {

    public static void main(String[] args) {
        ZonedDateTime zonedDateTime=ZonedDateTime.of(1564, Month.APRIL.getValue(), 23, 10, 0, 0, 0, ZoneId.of("Europe/London"));

        System.out.println(DateTimeFormatter.ISO_DATE_TIME.format(zonedDateTime));
        System.out.println(DateTimeFormatter.RFC_1123_DATE_TIME.format(zonedDateTime));

    }

}
