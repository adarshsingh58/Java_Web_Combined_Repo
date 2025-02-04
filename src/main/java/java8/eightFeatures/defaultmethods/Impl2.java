package java8.eightFeatures.defaultmethods;

public class Impl2 implements DefaultMethod,DefaultMethod2 {
    @Override
    public void printHello() {
        System.out.println("hello");
    }

    public String returnSomething(){
       return DefaultMethod.super.returnSomething();
    }

    public static void main(String[] args) {
        System.out.println(new Impl2().returnSomething());
    }
}
