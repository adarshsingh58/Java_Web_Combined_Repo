package java8.eightFeatures.javaUtilFunction;

import java.util.function.Predicate;

/*
 * and, or and negate are "default" methods inside predicate Interface with implementation.
 * A predicate is an interface with method test(String s). It takes a string input and you can specify your
 * own condition in this method and return a boolean result accordingly.
 *
 * and, or and negate works on 1 predicate with other predicate as parameter and do a &&, || and ! operation accordingly.
 *
 *
 */
public class PredicateDemo {

    public static void main(String[] args) {
        Predicate<String> stringPredicate1 = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return !s.isEmpty() ? true : false;
            }
        };

        Predicate<String> stringPredicate2 = s -> !s.isEmpty() ? true : false;

        Predicate<String> stringPredicate3 = stringPredicate1.and(stringPredicate2);
    }
}
