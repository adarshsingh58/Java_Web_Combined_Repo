package javaconcepts.other;

import java.util.ArrayList;
import java.util.List;

public class ReduceLambda {
    /**
     * In Java, the Stream.reduce() method is used to perform a reduction on the elements of a stream using an
     * associative accumulation function and returns an Optional. It is commonly used to aggregate or combine elements
     * into a single result, such as computing the maximum, minimum, sum, or product.
     *
     * here we are checking for length of words. we get america coz it is last of the same sized words
     */
    public static void main(String[] args) {
        List<String> l = new ArrayList<>();
        l.add("germany");
        l.add("denmark");
        l.add("india");
        l.add("america");
        l.add("gp");

        String s = l.stream().reduce((s1, s2) -> s1.length() > s2.length() ? s1 : s2).orElse("");
        System.out.println(s);
    }
}
