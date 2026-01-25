package javaconcepts.multithreading;

/**
 * https://www.youtube.com/watch?v=WH5UvQJizH0&list=PLhfHPmPYPPRk6yMrcbfafFGSbE2EPK_A6&index=1
 * Volatile are mostly used for flags where state of the flag is imp.
 */
public class Two_VolatileTest {

    public static void main(String[] args) {
        new Two_VolatileTest().m2();
    }

    public void m1() {
        new Thread(new MyRunnableFail()).start();
        new Thread(new MyRunnableFail()).start();
        new Thread(new MyRunnableFail()).start();
        new Thread(new MyRunnableFail()).start();
        new Thread(new MyRunnableFail()).start();
    }

    public void m2() {
        new Thread(new MyRunnableSafe()).start();
        new Thread(new MyRunnableSafe()).start();
        new Thread(new MyRunnableSafe()).start();
        new Thread(new MyRunnableSafe()).start();
        new Thread(new MyRunnableSafe()).start();
    }

}

class MySingletonClassConcurrentSafe {
    /*
     * Making this var INSTANCE as volatile, coz this is a state. And we are performing some action, creation of new object, based on this state.
     * Whenever we perform some action based on the status of some variable in a multithreaded env, we should make that var as volatile.
     * Generally when a var is updated by a thread it is updated first to local CACHE and then to RAM, there might be a case that other thread is still
     * reading from its own cache or may be from RAM directly, which may not be updated yet. So marking a var as volatile tells the JVM to always update
     * the value in RAM directly and read from RAM directly, avoiding any inconsistencies.
     * */
    private static volatile MySingletonClassConcurrentSafe INSTANCE = null;

    private MySingletonClassConcurrentSafe() {
        System.out.println("Singleton Object Created");
    }

    /*
     * static synchronization. Class level Lock.
     * */
    public static synchronized MySingletonClassConcurrentSafe getInstance() {
        if (INSTANCE == null)
            INSTANCE = new MySingletonClassConcurrentSafe();
        else
            System.out.println("Existing object served");
        return INSTANCE;

    }
}

class MySingletonClassConcurrentFail {
    private static MySingletonClassConcurrentFail INSTANCE = null;

    private MySingletonClassConcurrentFail() {
        System.out.println("Singleton Object Created");
    }

    public static MySingletonClassConcurrentFail getInstance() {
        if (INSTANCE == null)
            INSTANCE = new MySingletonClassConcurrentFail();
        else
            System.out.println("Existing object served");
        return INSTANCE;

    }
}

class MyRunnableFail implements Runnable {

    @Override
    public void run() {
        MySingletonClassConcurrentFail obj = MySingletonClassConcurrentFail.getInstance();
    }
}

class MyRunnableSafe implements Runnable {

    @Override
    public void run() {
        MySingletonClassConcurrentSafe obj = MySingletonClassConcurrentSafe.getInstance();
    }
}