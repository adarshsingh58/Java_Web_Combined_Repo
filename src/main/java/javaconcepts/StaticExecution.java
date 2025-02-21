package javaconcepts;

/**
 * Whenever we are executing a java class, the following sequence of steps will be executed as a part of static control
 * flow:
 * Identification of static member from top to bottom
 * Execution of static variable assignments and static blocks from top to bottom.
 * Execution of Main method.
 */
public class StaticExecution {

    public static String name = "Adarsh";

    static {
        System.out.println("static block 1 with Name:" + name);

    }

    public static void main(String[] args) {
        System.out.println("Inside main ");
        new StaticExecution().printNonStaticBlock();
    }

    public static String age = "22";

    static {
        System.out.println("static block 2 with Name:" + name + " and age " + age);
    }

    public void printNonStaticBlock() {
        System.out.println("Non Static Object Block");
    }

}
