package java8.eightFeatures.javaUtilFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Main {
    /*
     * Functions, consumer, producer, predicate examples
     * */
    public static void main(String[] args) {
        Consumer<String> consumer=(String name) -> System.out.println("Hi my name is "+name);
        List<String> listOfPeople=new ArrayList<>();
        listOfPeople.add("Adarsh");
        listOfPeople.add("Prakhar");
        listOfPeople.add("Jain");

        listOfPeople.forEach(consumer);

    }
}
