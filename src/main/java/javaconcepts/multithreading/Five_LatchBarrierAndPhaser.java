package javaconcepts.multithreading;

import java.util.concurrent.*;

public class Five_LatchBarrierAndPhaser {
    public static void main(String[] args) throws InterruptedException {
        new Five_LatchBarrierAndPhaser().m1();
        new Five_LatchBarrierAndPhaser().m2();
    }


    /*
     * A CountDownLatch is like a threadsafe integer which keep tracks of certain number of occurrences which is the initialized number in latch.
     * In this code a latch of size 2 is initialized and 3 Tasks are scheduled in thread sharing the latch. the tasks are then sent to exec service where one of 5
     * threads will be assigned to each runnable task(dependent service). now the idea is that main thread wants to proceed only when certain actions
     * are guaranteed taken/completed in all/some tasks. So in those tasks we pass the countdown latch and when those task completes the expected action,
     * they do countdown on the latch number i.e. sends signal to latch.await. This does not stop any operation on the runnable task itself, the
     * normal operation of the task in thread continues. In Main thread we have latch.await(). here the flow will pause until the latch.await received
     * number of signals equal to the init size of the latch.
     * So when the 3 dependent service do latch.countDown, eventually latch.await will receive 3 signals and then only main will proceed. It is imp to note
     * that latch may be passed to 100 tasks, but if latch init size is say 30 then upon receiving 30 signals as latch.countdown from any tasks, the await will
     * resume the processing, it doesnt have to wait for all the tasks to signal latch countdown. Also, this way latch is tied to events not tasks or threads.
     * i.e. if in a single tasks we do latch.countdown() 30 times, it will signal await 30 times and main processing will resume. It doesnt have to come from diff
     * tasks. latch.await() just cares about the signal from latch.countdown(), it do not care about who has sent this how many times.
     * You can think of this task as some connections. only when all the task have successfully connected to DB, one to Cloud service and other with say an encryption
     * vault, then only main thread can be assured that the next steps which needs all these things can be continued.
     * This is diff from join because in Join main thread will wait for all the threads to finish the task, there join is done on thread. In our case,
     * we have a threadpool, so we added a latch on the task irrespective of the thread it gets assigned to. Also Join will wait for the entire thread to finish the task,
     * but latch will do countdown and await will be notified and both runnable task and main task can continue parallel execution.
     *
     * Thread.join() waits for ->	A specific thread’s termination
     * CountDownLatch.await() waits for -> A condition to become true (counter → 0)
     *
     * Join:  Mental model “I started this thread. I want to wait until it finishes.”
     * Key characteristics
     * ✅ One thread waits for one specific thread ✅ Tight coupling between threads ✅ Implicit lifecycle dependency ❌ Cannot wait for multiple threads easily
     * ❌ Cannot reuse ❌ No timeout logic beyond join(timeout) ❌ Not scalable for dynamic systems
     *
     * CountDownLatch.await():  Wait for a CONDITION
     * Mental model "I don’t care WHO finishes — I care WHEN N things are done.”
     * Key characteristics
     * ✅ Waits for N events, not threads ✅ Decouples waiters from workers ✅ Multiple threads can await ✅ Works with thread pools ✅ Scales to dynamic systems
     * ❌ One-time use only (cannot reset)
     *
     * Why join() is almost never used in real systems.
     * thread.join(); This assumes:
     * You own the thread; You know when it started ; You know when it ends;
     * ❌ This breaks in: Executors ; Thread pools ; Async pipelines ; Microservices
     * Doesn't work with thread pools -> "executor.submit(task);" // What do you join?
     *
     * join() can always be replaced by CountDownLatch.
     * join() answers:“Is this thread still alive?”  -> CountDownLatch answers: “Has this system reached a stable state?”
     *
     * */
    private void m1() throws InterruptedException {
        ExecutorService exs = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(3);
        exs.submit(new DependendentService(latch));
        exs.submit(new DependendentService(latch));
        exs.submit(new DependendentService(latch));

        latch.await();
        System.out.println("All dependent services are initialized");
        //Program perform other operations

    }

    /*
     * With CyclicBarriers, its opposite. Here the thread awaits signal from each other.
     * We define CyclicBarrier with initial parties i.e. how many parties will be needed
     * to break the barrier of waiting. we pass the barrier to multiple tasks. The task perform
     * some action in diff time, but all the task will pause at barrier.await(). Now they wil only resume
     * when they receive signals equivalent to the initialized values. when that amount of
     * signal is received the barrier is broken and the all tasks resume their operations from
     * await() line.
     *
     * CountDownLatch waits for events to finish -> CyclicBarrier waits for threads to meet
     * CountDownLatch — “Wait until N things are done”. They are Event-based, One-time and Asymmetric (some wait, others count down).
     * Example thought: “I don’t care who finishes — I care when enough things are finished.”
     *
     * CyclicBarrier — “Everyone wait here until everyone arrives”. They are Thread-based, Reusable (cyclic) and Symmetric (all wait)
     * Example thought: “Nobody proceeds until all participants reach this point.”
     * Barrier also supports barrieraction, which basically is an action performed when barrier is reached. Below is printing "Barrier reached".
     * */
    private void m2() {
        CyclicBarrier barrier = new CyclicBarrier(2, () -> {
            System.out.println("Barrier reached");
        });
        ExecutorService exs = Executors.newFixedThreadPool(5);
        exs.submit(new InterconnectedTask1(barrier));
        exs.submit(new InterconnectedTask2(barrier));
    }

    /*
    * Phaser is a class that can act as a countdown latch as well as Cyclic Barrier.
    * */
    private void m3() {
        Phaser phaser=new Phaser(3);
        ExecutorService exs=Executors.newFixedThreadPool(5);
        exs.submit(new DependendentService_WithPhaserAsLatch(phaser));
        exs.submit(new DependendentService_WithPhaserAsLatch(phaser));
        exs.submit(new DependendentService_WithPhaserAsLatch(phaser));
        phaser.awaitAdvance(1);// Similar to latch.await()
        System.out.println("All dependent Programs initialized");
        // All dependent progs are initialized, other ops can advance.
      
    }
    
    /*
    * Phaser is a class that can act as a countdown latch as well as Cyclic Barrier.
    * */
    private void m4() {
        Phaser phaser=new Phaser(3);
        ExecutorService exs=Executors.newFixedThreadPool(5);
        exs.submit(new DependendentService_WithPhaserAsBarrier(phaser));
        exs.submit(new DependendentService_WithPhaserAsLatch(phaser));
        exs.submit(new DependendentService_WithPhaserAsLatch(phaser));
      
    }

    private class DependendentService implements Runnable {
        private CountDownLatch latch;

        public DependendentService(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            //start up tasks
            latch.countDown();
            //continue with other operations
        }
    }

    private class InterconnectedTask1 implements Runnable {
        CyclicBarrier barrier;

        public InterconnectedTask1(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            //perform some operation takes x time
            try {
                barrier.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            //continue some operations
        }
    }

    private class InterconnectedTask2 implements Runnable {
        CyclicBarrier barrier;

        public InterconnectedTask2(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            //perform some operation takes y time
            try {
                barrier.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            //continue some operations
        }
    }

    private class DependendentService_WithPhaserAsLatch implements Runnable {
        private Phaser phaser;
        public DependendentService_WithPhaserAsLatch(Phaser phaser) {
            this.phaser=phaser;
        }

        @Override
        public void run() {
            //some task
            phaser.arrive();//Similar to latch.countdown(). phaser.awaitAdvance is now signalled.
            //continue other ops
        }
    }

    private class DependendentService_WithPhaserAsBarrier implements Runnable {
        private Phaser phaser;
        public DependendentService_WithPhaserAsBarrier(Phaser phaser) {
            this.phaser=phaser;
        }
        @Override
        public void run() {
            //some task
            phaser.arriveAndAwaitAdvance();//Similar to barrier.await().
            //resume other ops only when signal from other parties to advance is arrived
        }
    }
}
