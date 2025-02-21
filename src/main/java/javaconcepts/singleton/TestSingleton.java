package javaconcepts.singleton;

public class TestSingleton {

    public static void main(String[] args) {
        SingletonClass s1=SingletonClass.getInstance();
        System.out.println(s1.hashCode());

        SingletonClass s2=SingletonClass.getInstance();
        System.out.println(s2.hashCode());

        System.out.println(s1==s2);
    }
}
