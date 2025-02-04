package java8.eightFeatures.streams;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Main {


    public static void main(String[] args) {
        List<Person> personList = Arrays.asList(new Person(
                        "Adarsh", "DSR", 25),
                new Person("Prakhar", "PG", 26));

        Stream<Person> stringStream = personList.stream();
        Stream<Person> filteredStream = stringStream.filter(person -> person.getAge() <= 25);

        Consumer<Person> personConsumer = person -> System.out.println(person);

        filteredStream.forEach(personConsumer);
        //we can combine above as: stringStream.filter(person -> person.getAge()<=25).forEach(personConsumer);

        //2nd way
        Stream s = Stream.of("1", "hello");

    }
}
