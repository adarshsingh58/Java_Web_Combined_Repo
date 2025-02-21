package javaconcepts.supersubclass.other;

public class SuperC {


    int i=0;

    public void showName_nonStatic() {
        System.out.println("I am inside SuperC.showName_nonStatic");
    }


    public static void showName_Static() {
        System.out.println("I am inside SuperC.showName_Static");
    }


    public void printHelloFromSuper() {
        System.out.println("HELLO SUPER");
    }
}
