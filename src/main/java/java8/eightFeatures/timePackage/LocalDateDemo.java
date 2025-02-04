package java8.eightFeatures.timePackage;

import java.time.LocalDate;
import java.time.Month;

/*
* Instant does not cover all concepts of date. Hence we get a LocalDate concept which represent time in terms of date
* and not in nano seconds.
*
* nano seconds date concept is used in Instant. But nanosec are calculated from 1st Jan 1970 midnight GMT
*
* So any time before that cant be represented in termsof nanosecond. Hence LocalDate concept.
*
* We can give day month year here.
*
* */
public class LocalDateDemo {

    public static void main(String[] args) {
        LocalDate now=LocalDate.now();
        //OR
        LocalDate dateOfBirth=LocalDate.of(1900,Month.APRIL,25);
        System.out.println(dateOfBirth);
    }
}
