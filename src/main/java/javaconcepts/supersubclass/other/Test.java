package javaconcepts.supersubclass.other;

/*
 * https://docs.google.com/document/d/1jl116RbZ3UbQz7CUVQoUaNjzgy6iv-Vm/edit
 * */
public class Test {
    public static void main(String[] args) {

//		Case 1
        System.out.println("\n \n CASE 1");
        SuperC superc = new SuperC();
        superc.showName_Static();
        superc.showName_nonStatic();
        System.out.println(superc.i);


//		Case 2
        System.out.println("\n \n CASE 2");
        SuperC superc_Sub = new SubC();
        superc_Sub.showName_Static();
        superc_Sub.showName_nonStatic();
        System.out.println(superc_Sub.i);
        superc_Sub.printHelloFromSuper();
//		superc_Sub.printHelloFromSub(); //compile time error

//		Case 3
        System.out.println("\n \n CASE 3");
        SubC sub = new SubC();
        sub.showName_Static();
        sub.showName_nonStatic();
        System.out.println(sub.i);
        sub.printHelloFromSub();
        sub.printHelloFromSuper();

    }
}

