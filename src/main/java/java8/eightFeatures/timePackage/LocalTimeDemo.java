package java8.eightFeatures.timePackage;

import java.time.LocalTime;

public class LocalTimeDemo {

    public static void main(String[] args) {
        LocalTime  now=LocalTime.now();
        LocalTime  bedTime=LocalTime.of(22,20);
        LocalTime  wakeTime=bedTime.plusHours(8);

        System.out.println(wakeTime);

    }
}
