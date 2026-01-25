package javaconcepts.eightFeatures.timePackage;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Date;

/*
 * To migrate from old Date API to new one there are additional methds added to old Date API
 *
 */
public class LegacyDateToNewAPI {

    public static void main(String[] args) {
        Date today=new Date();
        Instant instantToday=today.toInstant();

        Timestamp todayStamp=new Timestamp(today.getTime());
        Instant instantStamp=todayStamp.toInstant();

        Time time=new Time(today.getTime());
        LocalTime localTime=time.toLocalTime();
    }
}
