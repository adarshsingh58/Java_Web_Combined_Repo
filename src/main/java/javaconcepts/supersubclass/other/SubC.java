package javaconcepts.supersubclass.other;

public class SubC extends SuperC{


    int i=1;

    public void showName_nonStatic() {
        System.out.println("I am inside Sub.showName_nonStatic");
    }


    public static void showName_Static() {
        System.out.println("I am inside Sub.showName_Static");
    }

    public void printHelloFromSub() {
        System.out.println("HELLO SUB");
    }


}
