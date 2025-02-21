package javaconcepts.other;

import java.util.ArrayList;
import java.util.List;

public class ReferenceByType {

    public static void main(String[] args) {
        List<Integer> l=new ArrayList<>();
        l.add(2);

        List<Integer> m=l;
        List<Integer> n=new ArrayList<>(m);

        l.clear();

        m.add(9);
        n.add(10);

        System.out.println(l);
        System.out.println(m);
        System.out.println(n);
    }
}
