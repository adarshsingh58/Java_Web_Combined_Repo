package javaconcepts.multithreading;

import java.util.concurrent.*;

/**
 * The TheadLocal construct allows us to store data that will be accessible only by a specific thread.
 * The purpose of ThreadLocal is that the fields do not have to be atomic -- there is no need to synchronize their values with centralized memory.
 * They are thread-local and only exist in the threads local memory storage.
 * This is a useful pattern because then we don't have to synchronize on it or worry about any volatile or other atomic operations that have memory barriers.
 * <p>
 * Atomic also mean that you don't have to worry about synchronization, but they are specifically meant to be shared across threads.
 *
 * https://www.baeldung.com/java-threadlocal
 */
public class ThreadLocal {

    static java.lang.ThreadLocal<Integer> threadLocal = new java.lang.ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    //ThreadLocal is same as passing a local var to the runnable method
    //In below example multiThreadedMethod uses ThreadLocal while multiThreadedMethod1 uses a
    //var sent in parameter which wold be local to that thread. Both will give sme value.
    //So ThreadLocal is used when we have common value or object to be shared across multiple threads,
    //we dont have pass then individually just create a threadlocal.
    //However if you pass an object to both thread its value can be altered by both threads and could cause inconsistency.
    //
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Future<java.lang.ThreadLocal<Integer>> threadLocalFuture1 = executorService.submit(() -> multiThreadedMethod());
        Future<java.lang.ThreadLocal<Integer>> threadLocalFuture2 = executorService.submit(() -> multiThreadedMethod());

        Future<Integer> Future1 = executorService.submit(() -> multiThreadedMethod1(0));
        Future<Integer> Future2 = executorService.submit(() -> multiThreadedMethod1(0));


        //when using executor service remember to do awaitTermination to wait for thread's processing to finish
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println(threadLocalFuture1.get().get());
        System.out.println(threadLocalFuture2.get().get());

        System.out.println(Future1.get());
        System.out.println(Future2.get());

    }

    private static java.lang.ThreadLocal<Integer> multiThreadedMethod() {
        for (int i = 0; i < 1000; i++) {
            threadLocal.set(threadLocal.get() + 1);
        }
        return threadLocal;
    }

    private static Integer multiThreadedMethod1(int j) {
        for (int i = 0; i < 1000; i++) {
            j++;
        }
        return j;
    }
}
