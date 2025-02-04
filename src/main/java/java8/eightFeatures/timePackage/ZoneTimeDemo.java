package java8.eightFeatures.timePackage;

import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

public class ZoneTimeDemo {

    public static void main(String[] args) {
        Set<String> allZone = ZoneId.getAvailableZoneIds();
        ZoneId ukTimeZone = ZoneId.of("Europe/London");
        System.out.println(ukTimeZone);

        //converting time to a particular zone
        ZonedDateTime zonedDateTime=ZonedDateTime.of(1564, Month.APRIL.getValue(), 23, 10, 0, 0, 0, ZoneId.of("Europe/London"));
        System.out.println(zonedDateTime);

        //the same time we got after zoning can be changed to a diff zone as:
        ZonedDateTime zonedDateTimeUS= zonedDateTime.withZoneSameInstant(ZoneId.of("US/Central"));
        System.out.println(zonedDateTimeUS);

    }
}
