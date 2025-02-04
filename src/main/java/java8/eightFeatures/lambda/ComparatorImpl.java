package java8.eightFeatures.lambda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorImpl {

    public static void main(String[] args) {
        Comparator<String> comparator = comparatorAnonymousC();
        Comparator<String> comparatorLambda = comparatorLambda();

        List<String> listOfNames = new ArrayList<>();
        listOfNames.add("****");
        listOfNames.add("***");
        listOfNames.add("**");
        listOfNames.add("*");
        Collections.sort(listOfNames, comparatorLambda);
        System.out.println(listOfNames);
    }

    private static Comparator<String> comparatorLambda() {
        Comparator<String> stringComparator = (String o1, String o2) -> Integer.compare(o1.length(), o2.length());

//        better yet. Not providing type of argument as compiler will extrapolate that automatically
//        Comparator<String> stringComparator2 = (o1, o2) -> Integer.compare(o1.length(), o2.length());
        return stringComparator;
    }

    private static Comparator<String> comparatorAnonymousC() {
        return new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o1.length(), o2.length());
            }
        };
    }
}
