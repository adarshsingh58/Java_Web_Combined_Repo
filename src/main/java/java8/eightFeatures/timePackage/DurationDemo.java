package java8.eightFeatures.timePackage;

import java.time.Duration;
import java.time.Instant;

/*
 * Duration class takes 2 Instant object and can provide duration between them
 *
 * We can add mili, nano etc time and divide and multiply convert to nano, mili , sec, days an Instant object
 * with the help of Duration object
 */
public class DurationDemo {
    public static void main(String[] args) {
        Instant before = Instant.now();
        System.out.println("Some computation happened");
        Instant after = Instant.now();

        Duration timeElaspsed = Duration.between(before, after);
        System.out.println("Time elapsed: " + timeElaspsed);
        System.out.println("Time elapsed in millis: " + timeElaspsed.toMillis());
    }
}
