package javaconcepts.eightFeatures.timePackage;

import java.time.Instant;

/*
 * Instant(class) represent a point on a time line i.e. precise nanosecond
 * Instant 0 is 1st Jan 1970 midnight GMT
 * Instant.MIN is 1 billion years ago
 * Instant.MAX is 31st Dec 1 billion years ahead
 * Instant.now() represent current instant
 *
 * Instant object is immutable
 *
 */
public class InstantDemo {

    public static void main(String[] args) {
        Instant before=Instant.now();
        System.out.println("Some computation happened");
        Instant after=Instant.now();

        System.out.println("Computation started at "+before +" and ended at "+after);
    }
}
