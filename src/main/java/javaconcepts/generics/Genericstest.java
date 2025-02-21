package javaconcepts.generics;

public class Genericstest {

    public static void main(String[] args) {
        Bucket<Integer, String> b = new Bucket<>();
        System.out.println(b.getSomeValue(2));
//        System.out.println(b.getSomeValue("2")); this gives err because "2" is now a string which is not allowed for this method
        b.printTheInput("hello");
        b.setTypeOfDataInBucket(3);
        b.setSomeRandomData("Jesus");

        System.out.println(b.getTypeOfDataInBucket());
        System.out.println(b.getSomeRandomData());
    }
}
