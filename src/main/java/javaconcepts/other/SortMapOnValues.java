package javaconcepts.other;

import java.util.*;

public class SortMapOnValues {

    public static void main(String[] args) {
        Map<Integer, String> student = new HashMap<>();
        student.put(1, "Handi");
        student.put(2, "Pulao");
        student.put(3, "Jijands");
        student.put(4, "Kirpos");


        List<Map.Entry<Integer, String>> l = new LinkedList<>();
        l.addAll(student.entrySet());
        Collections.sort(l, Comparator.comparing(Map.Entry::getValue));

        l.forEach(v -> System.out.println(v.getKey() + " " + v.getValue()));
    }
}
