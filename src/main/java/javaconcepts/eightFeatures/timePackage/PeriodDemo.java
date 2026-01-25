package javaconcepts.eightFeatures.timePackage;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;

/*
 * Duration was difference between 2 Instant objects
 * Similarly, Period is the difference between 2 LocalDate
 *
 */
public class PeriodDemo {

    public static void main(String[] args) {
        LocalDate dateOfBirth = LocalDate.of(1900, Month.APRIL, 25);
        Period period = dateOfBirth.until(LocalDate.now());
        System.out.println(period);
        System.out.println(period.getYears());
        /*
         * Get you number of days from dateOfBirth till now
         */
        long periodDays = dateOfBirth.until(LocalDate.now(), ChronoUnit.DAYS);
        System.out.println(periodDays);


    }
}
