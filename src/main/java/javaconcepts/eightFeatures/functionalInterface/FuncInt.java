package javaconcepts.eightFeatures.functionalInterface;

/*
* A functional Interface can only have one abstract method.
* More than 1 will give compilation error
* Lambda expressions are functional interface
*
* And we can have 1 implemented default method
*/
@FunctionalInterface
public interface FuncInt {

    public void printHello();

    default String returnSomething(){
        return "something";
    }

}