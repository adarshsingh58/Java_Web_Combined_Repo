package java8.eightFeatures.defaultmethods;

@FunctionalInterface
public interface DefaultMethod {

    public void printHello();

    default String returnSomething() {
        return "something";
    }

    static void sayHi(){
        System.out.println("Hi");
    }

}