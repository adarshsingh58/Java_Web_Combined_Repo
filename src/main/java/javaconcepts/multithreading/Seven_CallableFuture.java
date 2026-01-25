package javaconcepts.multithreading;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Normal Runnable interface has run() which deos not return anything, so if your thread needs to return some data
 * Runnable do not work. For that we have Callable.
 *
 */
public class Seven_CallableFuture {

    public static void main(String[] args) {

    }
/*
* We have created task that extends callable. Now these tasks will be executed async,
* while the main thread continues, so where do we store the o/p of these tasks? Thats where Future comes into picture.
* Now after submitted the tasks to exs, some unrelated operation can be performed by the main thread in parallel unless 
* we try to do future.get().
* Remember the submission to exs of task is async. But when we try to get the values from the Future of callable, we dont
* know if the values are really yet calculated and returned or not. So when we do future.get(), main thread will wait
* until the thread executing this tasks actually returns a value.
* So Future is a placeholder where value from callable task will be returned in Future in some time, where we dont know
* in exactly what time will that value be returned. So the idea is to utilize that intermittent time in doing some 
* other operations.
* */
    private void m1() throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService exs = Executors.newFixedThreadPool(3);
        Future<Integer> op1=exs.submit(new TaskThatReturns());
        Future<Integer> op2=exs.submit(new TaskThatReturns());
        Future<Integer> op3=exs.submit(new TaskThatReturns());
        
        // Perform some operations unrelated to ops of the above tasks. 
        op1.get(); // Get value from Future of task1. Remember this is a BLOCKING call. Main Thread will not move from here until thread returns op1
        op2.get(100, TimeUnit.MILLISECONDS); // Same as get but can define a time out.
        op3.get();
        
    }

    /*
     * Here by extending our task with Callable, we can now call the call() and it would return the data. 
     * But thread is async, while the main thread continues, so where do we store the o/p of this?
     * Thats where Future comes into picture.
     * */
    private class TaskThatReturns implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            
            return new Random().nextInt();
        }
    }
}
