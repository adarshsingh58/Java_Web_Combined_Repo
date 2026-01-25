package javaconcepts.eightFeatures.lambda;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class lambdaShortcuts {

    public static void main(String[] args) {

        Comparator<String>  comparator1=(String s1,String s2) -> Integer.compare(s1.length(),s2.length());
        Comparator<String>  comparator2=(s1,s2) -> Integer.compare(s1.length(),s2.length());

        Comparator<Integer>  comparator_Int_1=(Integer s1,Integer s2) -> Integer.compare(s1,s2);
        Comparator<Integer>  comparator_Int_2=(s1,s2) -> Integer.compare(s1,s2);
        Comparator<Integer>  comparator_Int_3=Integer::compare;


        List<String> listCustomers=new ArrayList<>();
        listCustomers.add("Adarsh");
        listCustomers.add("Prakhar");
        listCustomers.add("Akanksha");

        //listCustomers.forEach is a consumer method for collections
       listCustomers.forEach(customer -> System.out.println(customer));
       listCustomers.forEach(System.out::println);

    }
}
