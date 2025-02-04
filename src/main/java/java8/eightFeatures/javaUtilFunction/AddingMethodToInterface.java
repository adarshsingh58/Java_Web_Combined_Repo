package java8.eightFeatures.javaUtilFunction;

/*
 * Now we can add a method with implementation inside an interface.
 * The same has been done with the forEach() that is added in the iterable interface
 * which is implemented by almost all the collection classes.
 *
 * Having to add forEach() in a top level interface w/o refactoring all implementation was not possible before.
 * Hence, in java 8, implemented functions were allowed with 'default' keyword inside an interface.
 * Static methods are also allowed in java 8 interfaces
 *
 * */
public interface AddingMethodToInterface {

    //Method must be default to have an implementation inside an interface
    default void printName(String name) {
        System.out.println("Hello " + name);
    }

    // static methods are also allowed. a static method should not have a default keyword
    static void printHello() {
        System.out.println("Hello World!!!");
    }
}
