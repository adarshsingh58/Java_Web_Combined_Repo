package java8.eightFeatures.javaUtilFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ConsumerDemo {

    public static void main(String[] args) {

        List<String> names = Arrays.asList("Adarsh", "Prakhar", "John");
        List<String> newList = new ArrayList<>();


    /*    Consumer<String> stringConsumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
*/
        /* Consumer<String> stringConsumer=s-> System.out.println(s);*/

        Consumer<String> stringConsumer = System.out::println;

        Consumer<String> consumer2 = s -> newList.add(s);
        System.out.println("newList size before " + newList.size());
        names.forEach(stringConsumer.andThen(consumer2));//andThen will attach another consumer at the end
        System.out.println("newList size after " + newList.size());
    }
}
