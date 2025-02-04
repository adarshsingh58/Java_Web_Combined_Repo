package java8.eightFeatures.StringandIOchanges;

import java8.eightFeatures.streams.Person;
import java8.eightFeatures.streams.Person;

import java.util.Comparator;

public class ComparatorDemo {

    public static void main(String[] args) {

        Person person1 =new Person("adarsh","zas",12);
        Person person2 =new Person("Jagga","klo",22);
        Person person3 =new Person("shawn","crw",23);


        //Old way
        Comparator<Person> comparator=new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
        System.out.println(comparator.compare(person1,person2));

        //New way
        /*
        * The comparing method uses lambda expression to take the object type to compare
        * and the getter method which will return the value on which comparing will happen
        * */
        Comparator<Person> comparator1=Comparator.comparing(Person::getName);
        System.out.println(comparator1.compare(person1,person2));

//Comparator also got naturalOrder(), reverseOrder() etc to sort the data in those order.
    }
}
