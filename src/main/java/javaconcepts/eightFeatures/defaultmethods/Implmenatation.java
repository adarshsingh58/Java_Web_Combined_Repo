package javaconcepts.eightFeatures.defaultmethods;

public class Implmenatation implements DefaultMethod {

    @Override
    public void printHello() {
        System.out.println("Hello");
    }

    public static void main(String[] args) {
        DefaultMethod impl = new Implmenatation();
        impl.printHello();
        System.out.println(impl.returnSomething());

        DefaultMethod.sayHi();
    }
}
