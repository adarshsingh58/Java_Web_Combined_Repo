package javaconcepts.other;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class WhenGenericsIsNotfollowed {

    public static void main(String[] args) {
        Set s=new HashSet();
        s.add("1");
        s.add(2);
        s.add(3);
        s.add(new ArrayList<>());

        s.forEach(System.out::println);
    }
}
