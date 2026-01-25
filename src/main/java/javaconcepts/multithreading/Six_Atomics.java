package javaconcepts.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Using an atomic variable is another way to achieve thread-safety in java. When variables are shared by multiple
 * threads, the atomic variable ensures that threads don’t crash into each other.
 * <p>
 * In mutithreading, the shared entity mostly leads to a problem when concurrency is incorporated. A shared entity such
 * as, mutable object or variable, might be changed, which may result in the inconsistency of the program or database.
 * So, it becomes crucial to deal with the shared entity while accessed concurrently. An atomic variable can be one of
 * the alternatives in such a scenario.
 * <p>
 * Java provides atomic classes such as AtomicInteger, AtomicLong, AtomicBoolean and AtomicReference. Objects of these
 * classes represent the atomic variable of int, long, boolean, and object reference respectively. These classes contain
 * the following methods.
 * <p>
 * set(int value): Sets to the given value get(): Gets the current value lazySet(int value): Eventually sets to the
 * given value compareAndSet(int expect, int update): Atomically sets the value to the given updated value if the
 * current value == the expected value addAndGet(int delta): Atomically adds the given value to the current value
 * decrementAndGet(): Atomically decrements by one the current value
 */
public class Six_Atomics {

    //    This is the difference bet normal integer and atomic integer,
//    atomicInteger will be accessed by only 1 thread at a time
//    while normal integer will be accessed simultaneously causing values to be inconsistent
    //class level vars are global vars so not local to a thread and will be used by any thread concurrently. Thats why we use AtomicInteger at class level since it
    // provides synchronization out of the box for current class's object  
    static AtomicInteger atomicInteger = new AtomicInteger(0);
    static Integer normalnteger = 0;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(() -> multiThreadedMethod());
        executorService.submit(() -> multiThreadedMethod());
        //when using executor service remember to do awaitTermination to wait for thread's processing to finish
        executorService.awaitTermination(2, TimeUnit.SECONDS);
        System.out.println("atomic " + atomicInteger.get());
        System.out.println("normal " + normalnteger);
    }

    //the same can be acheived by making this synchrinized and not using atomic var at all
    //but that would be inefficient since entire method is not synchronized
    private static void multiThreadedMethod() {
        for (int i = 0; i < 1000; i++) {
            atomicInteger.addAndGet(1);
            normalnteger++;
        }
    }


    // add atomicLong and atomicReference
}
