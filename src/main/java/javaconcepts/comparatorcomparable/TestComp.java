package javaconcepts.comparatorcomparable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestComp {

    public static void main(String[] args) {
        Student s1 = new Student("Adarsh", 8);
        Student s2 = new Student("Adarsh", 2);
        Student s3 = new Student("Aditi", 1);
        Student s4 = new Student("Jain", 3);
        Student s5 = new Student("Rocky", 4);
        Student s7 = new Student("Rocky", 24);
        Student s6 = new Student("Gengiz", 6);

        List<Student> l = new ArrayList<>();
        l.add(s1);
        l.add(s2);
        l.add(s3);
        l.add(s4);
        l.add(s5);
        l.add(s6);
        l.add(s7);
        Collections.sort(l);

        l.forEach(System.out::println);

        System.out.println("\n CUSTOM ORDER. Sorting by Roll no using Comparator");
        Collections.sort(l, (o1, o2) -> o1.getRollNo().compareTo(o2.getRollNo()));

        l.forEach(System.out::println);

    }
}
