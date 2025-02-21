package javaconcepts.supersubclass;

public class SubClass extends SuperClass {

    public static void main(String[] args) {
        SuperClass sc = new SubClass();
        sc.print("hello");//prints from subclass because the actual object that is created is of subclass
        sc.print(2);//prints from superclass because this method has no impl in sub class
        //sc.print(2L);//Gives compile time err brcause print(long) is not in superclass which the var sc is referencing to.
    }

    @Override
    public void print(String s) {
        System.out.println("In Sub: " + s);
    }

    public void print(long l) {
        System.out.println("In Sub: " + l);
    }
}
