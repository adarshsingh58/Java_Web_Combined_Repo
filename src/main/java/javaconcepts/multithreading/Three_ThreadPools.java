package javaconcepts.multithreading;

import java.util.concurrent.*;

/**
 * https://www.youtube.com/watch?v=Dma_NmOrp1c&list=PLhfHPmPYPPRk6yMrcbfafFGSbE2EPK_A6&index=7
 * <p>
 * Java has 4 types of threadPools: - FixedThreadPool - CachedThreadPool - ScheduledThreadPool - SingleThreadedExecutor
 * <p>
 * *FixedThreadPool:     corePoolSize(constructor-arg)  keepAliveTime(NA) maxPoolSize(same as corePoolSize)
 * QueueType(LinkedBlockingQueue because Threads are limited, thus unbounded queue to store all tasks.)
 * <p>
 * *CachedThreadPool:    corePoolSize(0)  keepAliveTime(60) maxPoolSize(Integer.MAX_VALUE) QueueType(SynchronousQueue
 * coz Threads are unbounded, thus no need to store the tasks. Synchronous queue is a queue with single)
 * <p>
 * *ScheduledThreadPool: corePoolSize(constructor-arg)  keepAliveTime(60sec) maxPoolSize(Integer.MAX_VALUE)
 * QueueType(DelayedWorkQueue Special queue that deals with schedules/time-delays)
 * <p>
 * *Single Threaded: corePoolSize(1)  keepAliveTime(NA) maxPoolSize(1) QueueType(LinkedBlockingQueue)
 *
 *
 */
public class Three_ThreadPools {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        Three_ThreadPools tp = new Three_ThreadPools();
//        tp.fixedThreadPool();
//        tp.cachedThreadPool();
//        tp.scheduledThreadPool();
        tp.singleThreadedExecutor();
    }

    /*
     * FIxed Thread pool use Blocking Queue and uses wait and notify to assign a task to the threads from the pool
     * */
    private void fixedThreadPool() {
        ExecutorService exs = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            exs.submit(() -> {
                System.out.println("Thread " + Thread.currentThread().getName() + " Running task ");
            });
        }
        exs.shutdown();
    }

    /*
     * CachedTP doesn't take size. It does not uses blocking Q and has only 1 available slot for task.
     * So it takes in 1 task at a time and looks for any available thread in sys and assigns if available.
     * If not it will create a new thread and add to the pool. This can also kill a thread if idle for >60 sec.
     * So the size can grow and shrink as per need.
     * */
    private void cachedThreadPool() {
        ExecutorService exs = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            exs.submit(() -> {
                System.out.println("Thread " + Thread.currentThread().getName() + " Running task ");
            });
        }
        if (!exs.isTerminated())
            exs.shutdown();
    }

    /*
     * This uses Delay queues to store the task and are used to perform a task periodically.
     * Here we define the pool size and the task that are to be run in intervals uses the threads from
     * pool to execute.
     * */
    private void scheduledThreadPool() {
        ScheduledExecutorService exs = Executors.newScheduledThreadPool(10);
        exs.schedule(() -> System.out.println("Running Task 1"), 5, TimeUnit.SECONDS); //Run this task after 5secs
        try {
            exs.awaitTermination(6, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Same as fixedTP but it has only 1 Thread. This is used in scenarios when task submitted to threads
     * needs to run in the same sequence in which they are submitted.
     * Having just 1 thread ensures that 1 thread will take only 1 task at a time and take up another only when
     * previous is finished.
     * */
    private void singleThreadedExecutor() {
    }


    public void m10() throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService ex = Executors.newFixedThreadPool(3);
        Runnable runnableTask1 = () -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("Hello from runnableTask1 -> " + i);
            }

        };
        Callable<String> callableTask = () -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("Hello from callable task -> " + i);
            }
            return "Task's execution from callable successful";
        };
        ex.execute(runnableTask1);
        Future<String> fut = ex.submit(callableTask);
        System.out.println(fut.get(200, TimeUnit.MILLISECONDS));
        ex.shutdown();
    }
}
