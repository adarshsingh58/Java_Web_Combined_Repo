package javaconcepts.multithreading.IntQuestions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Blocking Queue | Bounded Buffer | Consumer Producer
 * <p>
 * Explore the implementation of a blocking queue in Java, including enqueue and dequeue methods that safely block or
 * notify threads based on queue state. Understand synchronization techniques necessary to manage producer and consumer
 * threads, which prepares you to solve common concurrency problems asked in senior engineering interviews.
 * <p>
 * Problem Statement
 * <p>
 * A blocking queue is defined as a queue which blocks the caller of the enqueue method if there’s no more capacity to
 * add the new item being enqueued. Similarly, the queue blocks the dequeue caller if there are no items in the queue.
 * Also, the queue notifies a blocked enqueuing thread when space becomes available and a blocked dequeuing thread when
 * an item becomes available in the queue.
 * <p>
 * Producer Consumer uses concept of a BLOCKING QUEUE. Basically producer should produce the messages/data into a Queue
 * consumer should consume it. and all produce and consume ops should be thread safe. With that there is one more unique
 * quality of a blocking queue: when data is not present in the queue, consumer is blocked i.e. gives up the thread and
 * notify the producer to produce message first. similarly if queue is full, producer will wait and notify consumer that
 * it is blocked.
 * <p>
 * Havent used, new Thread() or synchronized keywords or join in this representation as they are seldom used in
 * production grade code. Instead we use ExecutorService, ReentractLocks Conditions and waitNotify., RW lock, volatle
 * and atomic,
 */
public class ProducerConsumer {

    public static void main(String[] args) {
        ProducerConsumer pcInstance = new ProducerConsumer();

        try {
            Date start = new Date();
//            m1(pcInstance);
//            m2(pcInstance);
//            m3(pcInstance);
//            m4(pcInstance);
            m5(pcInstance);
            Date end = new Date();
            System.out.println("Time Taken " + (end.getTime() - start.getTime()));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * This method uses NormalQueue. This is a normal circular queue which allows enqueue and dequeue.
     * The m1 method tests if adding data and removing works well or not. This will work as expected.
     * Unless we test the NormalQueue in Multithreaded env.
     *
     * */
    private static void m1(ProducerConsumer pcInstance) {
        // 1. Initialize a queue with a capacity of 3
        NormalQueue<String> queue = pcInstance.new NormalQueue<>(3);

        System.out.println("--- Starting Enqueue Operations ---");
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        // This should trigger the "Size is full" message
        queue.enqueue("D");

        System.out.println("\n--- Starting Dequeue Operations ---");
        // Should print "First"
        System.out.println("Dequeued: " + queue.dequeue());
        // Should print "Second"
        System.out.println("Dequeued: " + queue.dequeue());

        System.out.println("\n--- Testing Wrap-Around/Reuse ---");
        // Because we dequeued two items, we should be able to add two more
        queue.enqueue("D (Retry)");
        queue.enqueue("E");

        System.out.println("\n--- Final Drain ---");
        while (true) {
            String val = queue.dequeue();
            if (val == null) break; // Queue is empty
            System.out.println("Final Dequeue: " + val);
        }
    }

    /*
     * IN this scenario our task is enqueue and dequeue. To perform these 2 task multiple times we can create threads and give it these tasks
     * to run in parallel.
     * In real world multiple threads would be enqueuing the data and multiple threads would be dequeueing but one thread would either enqueue or dequque,
     * not like a thread is enqueuing and dequeue-ing.
     * When multiple threads execute enqueue at the exact same time, they will interfere with each other's operations on the shared variables (size and front).
     * 1. The Major Problems You Will See
     * Lost Updates (Data Corruption): Two threads might read the same value for front simultaneously. For example, if front is 0, both Thread 1 and Thread 2 might try to write their value to arr[0].
     * One value will simply overwrite the other and be lost forever.
     * Incorrect Size: The line size++ is not atomic. It actually involves three steps: reading the current size, adding one, and writing it back. If two threads do this at once,
     * they might both read "size is 2," both calculate "3," and both write back "3." You added two items, but the queue thinks you only added one.
     * Visibility Issues: Without synchronization or the volatile keyword, one thread might update front or size in its local CPU cache, but another thread might not "see"
     * that update immediately, leading it to work with stale data.
     *
     * POINT X is added in NormalQueue enqueue() method where thread is forcefully made to sleep and give up execution of task. With this you would see that even though
     * 10 tasks are submitted size might be <10 most times. Because size var is shared and one thread say had the value of size as 6 but before printing the size,
     * another thread came and got he size=6 and printed, when prev thread resumed it also printed 6.
     * O/P:
     * Enqueued C Size of queue 2
     * Enqueued E Size of queue 3
     * Enqueued H Size of queue 4
     * Enqueued A Size of queue 2
     * Enqueued F Size of queue 6
     * Enqueued I Size of queue 6
     * Enqueued B Size of queue 6
     * nqueued D Size of queue 6
     * Enqueued G Size of queue 7
     * Enqueued J Size of queue 8
     *
     * */
    private static void m2(ProducerConsumer pcInstance) throws InterruptedException {
        // 1. Initialize a queue with a capacity of 3
        NormalQueue<String> queue = pcInstance.new NormalQueue<>(10);
        ExecutorService ecs = Executors.newFixedThreadPool(4);
        ecs.submit(() -> {
            queue.enqueue("A");
            queue.enqueue("B");
        });
        ecs.submit(() -> {
            queue.enqueue("C");
            queue.enqueue("D");
        });
        ecs.submit(() -> {
            queue.enqueue("E");
            queue.enqueue("F");
            queue.enqueue("G");
        });
        ecs.submit(() -> {
            queue.enqueue("H");
            queue.enqueue("I");
            queue.enqueue("J");
        });
        /*ecs.submit(() -> { // thread will dequeue
            queue.dequeue();
            queue.dequeue();
        });*/
        //Now 4 tasks are submitted to a pool of 3 threads. They would execute in parallel.

        // 5. Shutdown gracefully
        ecs.shutdown();
        ecs.awaitTermination(5, TimeUnit.SECONDS);
    }

    /*
     * Now to fix above issues of inconsistencies in multithreaded env, we need to make some fixes to our Normal Queue. One way is
     *  to use "synchronized" keyword on enqueue and dequeue.
     * Since we have synchronized the enq and deq methods in QueueWithSyncMethods o/p is expected:
     * Enqueued A Size of queue 1
     * Enqueued B Size of queue 2
     * Enqueued H Size of queue 3
     * Enqueued I Size of queue 4
     * Enqueued J Size of queue 5
     * Enqueued E Size of queue 6
     * Enqueued F Size of queue 7
     * Enqueued C Size of queue 8
     * Enqueued D Size of queue 9
     * Enqueued G Size of queue 10
     *
     * All data is enqueued.
     *
     * Why this is now thread-safe:
     * Atomic Operations: Even though size++ and index updates take multiple steps, the synchronized keyword ensures that once a thread enters enqueue, no other thread can enter enqueue or dequeue until the first thread exits.
     * Holding the Lock during Sleep: When a thread hits Thread.sleep(100) inside a synchronized method, it does not release the lock. Other threads will be forced into a BLOCKED state, waiting for the sleeping thread to finish the entire method.
     * Memory Visibility: Synchronization creates a "happens-before" relationship. This ensures that the changes one thread makes to size, front, and rear are visible to the next thread that acquires the lock.
     *
     * Issues you will observe during Simulation:
     * While the data is now safe, you will notice a massive performance hit due to the synchronization and sleep combination:
     * Strictly Sequential Execution: If you submit 10 tasks to an ExecutorService with 10 threads, they will not run in parallel. Because of the lock, Task 2 cannot start until Task 1 finishes its 100ms sleep and exits the method.
     * Increased Latency: Total time to enqueue 10 items will be at least 1000ms (1 second), even if you have multiple CPU cores available.
     * Thread Bottleneck: Most of your threads in the pool will spend their time in the BLOCKED state, waiting for the monitor lock.
     * m3() actually takes 3times more than m2() because it looks parallelized but is sequential.
     *
     * Improvement: Synchronized Blocks (Finer-Grained Locking)
     * We can improve the time taken slightly by using a synchronized block instead of a synchronized method. This allows us to lock only the critical lines of code (updating the array and size) while
     * keeping non-critical code (like logging or preparation) outside the lock. But this would not help much.
     *
     * */
    private static void m3(ProducerConsumer pcInstance) throws InterruptedException {
        // 1. Initialize a queue with a capacity of 3
        QueueWith_LockUsingSynchronized<String> queue = pcInstance.new QueueWith_LockUsingSynchronized(10);
        ExecutorService ecs = Executors.newFixedThreadPool(4);
        ecs.submit(() -> {
            queue.enqueue("A");
            queue.enqueue("B");
        });
        ecs.submit(() -> {
            queue.enqueue("C");
            queue.enqueue("D");
        });
        ecs.submit(() -> {
            queue.enqueue("E");
            queue.enqueue("F");
            queue.enqueue("G");
        });
        ecs.submit(() -> {
            queue.enqueue("H");
            queue.enqueue("I");
            queue.enqueue("J");
        });
        /*ecs.submit(() -> { // thread will dequeue
            queue.dequeue();
            queue.dequeue();
        });*/
        //Now 4 tasks are submitted to a pool of 3 threads. They would execute in parallel.

        // 5. Shutdown gracefully
        ecs.shutdown();
        ecs.awaitTermination(5, TimeUnit.SECONDS);
    }

    /*
     * We will fix the performance issue. But before that lets fix one major problem. In real world,
     * the data will keep adding to the queue and keep dequeing as well. So if the queue is full,
     * we cant say queue is full and return because then that data which needs to be pricessed by the consumer
     * is lost by the producer.
     * What we want is that when the queue is full, the thread that has the task to enqueue should wait() in hope
     * of dequeuer removing some items. When a dequeuer removes some items, it can notify() all threads that
     * some item is removed if any thread is waiting on this lock it can proceed. This way producer will now
     * resume enqueuing and data would not be lost. This is the core Producer/Consumer problem.
     * To do this we will use wait() and notifyAll() now. This queue moves from "Check and Fail" to Signaling.
     * What to Look for in the Console Output
     * When you run this, you will see the orchestration of wait() and notifyAll() in action:
     *  Initial Burst: The producer will immediately enqueue "A" and "B".
     *  The Wait: You will see the message: "Size is full, enqueuer waiting..." The producer is now stuck inside the wait() call of the enqueue method.
     *  The Relief: After 2 seconds, the consumer wakes up, calls dequeue(), and removes "A".
     *  The Resumption: The consumer calls notifyAll(), which wakes the producer. The producer then finishes adding "C" and likely goes back to waiting again.
     * Mutual Exclusion: Even though the producer is "inside" the enqueue method, the consumer can still enter dequeue. This is because calling wait() releases the lock.
     * */
    private static void m4(ProducerConsumer pcInstance) throws InterruptedException {
        // 1. Create a small queue to force threads to wait quickly
        QueueWith_LockUsingSynchronized_SignallingUsingWaitNotify<String> queue = pcInstance.new QueueWith_LockUsingSynchronized_SignallingUsingWaitNotify(2);

        // 2. Create a thread pool
        ExecutorService ecs = Executors.newFixedThreadPool(5);

        // 3. TASK: Producers (Adding 5 items to a 2-slot queue)
        ecs.submit(() -> {
            try {
                String[] items = {"A", "B", "C", "D", "E"};
                for (String item : items) {
                    queue.enqueue(item);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 4. TASK: Consumer (Removing items with a delay)
        ecs.submit(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    // Consumer waits 2 seconds before each dequeue
                    // This forces the Producer to get stuck in wait()
                    Thread.sleep(2000);
                    queue.dequeue();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 5. Graceful Shutdown
        ecs.shutdown();
        ecs.awaitTermination(20, TimeUnit.SECONDS);
    }

    /*
     *
     * FIRST:
     * From m3() We want to move beyond synchronized methods, we must reduce Lock Contention.
     * In current code, the synchronized keyword creates a "Global Lock" on the entire object.
     * This means while one thread is doing the math to update front, another thread cannot even start the math to update rear.
     * They are essentially standing in a single line outside the main door waiting for the active thread to release the lock.
     *
     * One thing we can do is to make the shared resources Atomic. size and front rear if becoming atomic and volatile, only
     * 1 thread can update it at a time. If we remove synchronized and only make the shared variables (size, front, rear) Atomic,
     * you will solve the "Size Mismatch" issue, but you will still have a broken Queue due to a race condition between the atomic updates and the array access.
     * This is a classic "Check-then-Act" bug. Here is the breakdown of what happens:
     * 1. The Data Race (The Main Problem)
     *   Even if front is an AtomicInteger, the operation of "get the current index" and "put value in the array" is not one single atomic step.
     *   Thread A calls front.getAndIncrement(). It gets index 0.
     *   Thread B calls front.getAndIncrement(). It gets index 1.
     * The Issue: Thread B might be faster and put its value in arr[1] before Thread A even starts writing to arr[0].
     * While this sounds okay, if Thread A crashes or is delayed, a Consumer might try to dequeue from index 0 and find a null value, even though the size says there is 1 item in the queue.
     *
     * So making Atomic will help but we still need something to keep a lock on the common update code.
     *
     * SECOND:
     * The Problem with m4 code (Spurious Wakeups and Race Conditions):
     *   Imagine the queue is full. Two Producer threads are both waiting at this.wait().
     *   A Consumer dequeues one item and calls this.notifyAll().
     *   Both Producers wake up.
     *   Producer A grabs the lock, enqueues an item, and finishes. The queue is now full again.
     *   Producer B grabs the lock. Because you used if, it does not re-check the size. It proceeds to arr[front] = value, overwriting data or crashing because the queue is actually full.
     *
     * Because there is just one lock i.e. this object and all wait and notify is happening on this object. There is no way to make sure that enqueuer notifies dequeuer and vice versa.
     * Here when notifyAll is called, all waiting threads, be enqueuer or dequer is called. One fix here is to add a While Look instead of If like:
     * while (size == MAX_CAPACITY) {
     *         System.out.println("Size is full, waiting...");
     *         this.wait();
     *     }
     * This way after the thread resumes upon notified, it will check the when condition again instead of going ahead with enqueue or dequeue.
     *
     * SOLUTION:
     * But there is a better way. Why not we make the enqueuer and dequeuer sit in a different distinguished waiting room on the same lock. That way one task would know
     * which type of waiting threads to notify. This can be done by removing Synchronized lock and applying Reentrant Lock with Condition.
     * wait and notify can only be used with a synchronized block. Since we removed sync from methods wait and notify cant be used.Can you use wait()? With synchronized? Yes, you must. With ReentrantLock? No, you must use condition.await().
     * So, Instead we will use await and signal.
     *
     * Why This is "Better" (Performance Breakdown)
     * This implementation is superior to your previous synchronized version for three reasons:
     *  Targeted Signaling: When you call waitingConsumers.signal(), you wake up only a thread that wants to dequeue. In your synchronized version, notifyAll() woke up everyone, including other producers who couldn't do anything yet.
     *  Reduced Context Switching: Waking fewer threads means the CPU spends less time managing thread states and more time executing your code.
     *  Fairness Option: You can initialize your lock with new ReentrantLock(true). This ensures that threads are served in the order they arrived, preventing "starvation" where one thread waits forever while others jump the line.
     *
     * What this test proves
     *  Thread Parking: You will see "Alpha" and "Bravo" get enqueued immediately. Then, the logs will show "Charlie" attempting to enqueue but stopping. The producer thread is now "parked" and consuming zero CPU.
     *  Lock Release on Await: Notice that even though the Producer is "inside" the enqueue method, the Consumer is able to enter dequeue. This proves that await() successfully released the ReentrantLock so others could work.
     *  Targeted Signaling: When the Consumer finally removes an item and calls waitingProducers.signal(), the Producer wakes up immediately to finish its task.
     *
     * */
    private static void m5(ProducerConsumer pcInstance) throws InterruptedException {
        // 1. Initialize the queue with a small capacity (e.g., 2) to trigger 'await' logic
        var queue = pcInstance.new QueueWith_LockUsingReentractCondition_SignallingUsingCondition<String>(2);

        // 2. Create a thread pool with enough threads to act as producers and consumers
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // 3. PRODUCER TASK: Try to add 5 items to a 2-slot queue
        executor.submit(() -> {
            try {
                String[] items = {"Alpha", "Bravo", "Charlie", "Delta", "Echo"};
                for (String item : items) {
                    System.out.println("[Producer] Attempting to enqueue: " + item);
                    queue.enqueue(item);
                    // Small sleep so we can read the logs easily
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 4. CONSUMER TASK: Remove items with a long delay
        // This forces the Producers to get stuck in the 'waitingProducers.await()' state
        executor.submit(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(2000); // 2-second delay between pops
                    String val = queue.dequeue();
                    System.out.println("[Consumer] Successfully Dequeued: " + val);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 5. Shutdown and wait for completion
        executor.shutdown();
        if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
            executor.shutdownNow();
        }
        System.out.println("--- Test Completed ---");
    }

    /*
     * The code still contains one critical logic error:
     * The Critical Bug: if vs while
     *   Just like with synchronized and wait(), you must use a while loop when calling await() on a condition.
     *   The Risk: If two producers are waiting for space and one item is dequeued, both might wake up (due to spurious wakeups or multiple signals).
     *   The Result: The first producer to grab the lock fills the slot. The second producer, because it is using an if statement, will not check the size again and will overwrite the data or corrupt the front index.
     * The Fix: Change if (size == MAX_CAPACITY) to while (size == MAX_CAPACITY).
     * It seems logical that if you are signaling the "correct" room, the thread waking up should find exactly what it expects. However, even with Targeted Signaling (separate Condition objects), the while loop is mandatory for three specific reasons: Interleaving, Signals vs. Locks, and Spurious Wakeups.
     * 1. The Interleaving Problem (Signal vs. Acquisition)
     *   There is a tiny "gap" of time between when a thread is signaled and when it actually re-acquires the lock.
     *   Imagine this scenario in your 10-capacity queue:
     *   Queue is Full (Size 10): Producer A calls waitingProducers.await() and goes to sleep.
     *   Consumer Dequeues: A consumer calls dequeue(), making the size 9. It then calls waitingProducers.signal().
     *   Producer A wakes up: Producer A is moved from the "Waiting Set" to the "Entry Set" to compete for the lock.
     *   The "Sneak In": Before Producer A can grab the lock, Producer B (which was not waiting, just arrived) calls enqueue(). Since the lock is free and size is 9, Producer B grabs the lock, adds an item, and makes the size 10 again.
     *   The Bug: Producer A finally gets the lock. If it uses an if, it assumes the slot is still open and tries to add an item. Because the size is actually 10, it overfills the queue or corrupts the array.
     * 2. Signals do not Guarantee the Lock
     *   A common misconception is that signal() hands the lock directly to the waiting thread. It does not.
     *   signal() only tells the JVM: "This thread is now eligible to fight for the lock again".
     *   The signaled thread must still compete with every other thread currently trying to enter an enqueue or dequeue method.
     *   The while loop acts as a safety guard that forces the thread to double-check the reality of the situation once it finally wins the lock.
     * 3. Spurious Wakeups
     *   This is a low-level JVM/OS behavior where a thread can wake up from an await() or wait() call for no reason at all (even if no one called signal).
     *   While rare, if a thread wakes up spuriously and you used an if statement, it will proceed to execute the code even if the queue is still full.
     *   The Java documentation explicitly states that because of spurious wakeups, await() should always be called in a loop.
     *
     * The fix for this is to use a while loop instead of if after await or wait.
     * */
    private static void m6(ProducerConsumer pcInstance) throws InterruptedException {
        // 1. Initialize the queue with a small capacity (e.g., 2) to trigger 'await' logic
        var queue = pcInstance.new QueueWith_LockUsingReentractCondition_SignallingUsingCondition_BugFix<String>(2);

        // 2. Create a thread pool with enough threads to act as producers and consumers
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // 3. PRODUCER TASK: Try to add 5 items to a 2-slot queue
        executor.submit(() -> {
            try {
                String[] items = {"Alpha", "Bravo", "Charlie", "Delta", "Echo"};
                for (String item : items) {
                    System.out.println("[Producer] Attempting to enqueue: " + item);
                    queue.enqueue(item);
                    // Small sleep so we can read the logs easily
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 4. CONSUMER TASK: Remove items with a long delay
        // This forces the Producers to get stuck in the 'waitingProducers.await()' state
        executor.submit(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(2000); // 2-second delay between pops
                    String val = queue.dequeue();
                    System.out.println("[Consumer] Successfully Dequeued: " + val);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 5. Shutdown and wait for completion
        executor.shutdown();
        if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
            executor.shutdownNow();
        }
        System.out.println("--- Test Completed ---");
    }

    /*
     * ReentrantReadWriteLock (For peek()). ReadWriteLock is useful if you add a peek() method.
     * The Logic: In a standard queue, enqueue and dequeue both write to the state (modifying front, rear, and size).
     * A peek() method only reads the data.
     * The Benefit: Multiple threads can call peek() simultaneously without blocking each other,
     * but they will be blocked if a thread is currently enqueuing or dequeuing.
     * Caution: If you use this, you must ensure that your size update is still protected by the Write lock,
     * or the peek() might read a stale or null value.
     *
     * ReentrantReadWriteLock Performance
     * By using readLock for peek(), you've significantly improved performance for read-heavy workloads.
     * Concurrency: Multiple threads can execute peek() at the exact same time without blocking one another.
     * Safety: If a thread is currently in enqueue or dequeue (holding the writeLock), all peek() threads
     * will block until the write is complete, ensuring they never see a partially updated state.
     *
     * Why Callable/Future?
     * Returning Values: While Runnable simply executes code, Callable allows your test threads to return the specific object they retrieved from the queue.
     * Future.get(): This method acts as a synchronization point, blocking the main thread until the result from the worker thread is ready.
     *
     * PS: I’d only use RWLock if peek/read ratio is very high (e.g., leaderboard reads)” This code is just for demonstration
     *  */
    private static void m7(ProducerConsumer pcInstance) throws InterruptedException {
        // 1. Initialize the queue with a small capacity (e.g., 2) to trigger 'await' logic
        var queue = pcInstance.new QueueWith_LockUsingReentractConditionAndRWLOCK_SignallingUsingCondition<String>(3);
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // 1. PRODUCER: Enqueue 3 items
        executor.submit(() -> {
            try {
                queue.enqueue("Task1");
                queue.enqueue("Task2");
                queue.enqueue("Task3");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 2. READERS (PEEK): Multiple threads can peek simultaneously
        List<Future<String>> peekResults = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            peekResults.add(executor.submit(() -> queue.peek()));
        }

        // 3. CONSUMER (DEQUEUE): Use Callable to return the dequeued value
        Callable<String> dequeueTask = () -> {
            Thread.sleep(500); // Wait for producers/peeks to finish
            return queue.dequeue();
        };
        Future<String> dequeuedValue = executor.submit(dequeueTask);

        try {
            // Print Results
            for (Future<String> res : peekResults) {
                System.out.println("Peek Result: " + res.get()); // Should be Task1
            }
            System.out.println("Final Dequeued Value: " + dequeuedValue.get()); //
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }

    class NormalQueue<T> {
        private T[] arr;
        private int front, rear, size; // front is where data is added and rear is where its removed
        private final int MAX_CAPACITY;

        NormalQueue(int MAX) {
            this.MAX_CAPACITY = MAX;
            arr = (T[]) new Object[MAX];
            front = rear = 0;
            size = 0;
        }

        void enqueue(T value) {
            if (size == MAX_CAPACITY) {
                System.out.println("Cant Enqueue Size is full");
                return;
            } else {
                arr[front] = value;
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                } // Force other threads to catch up. ADDED FOR m2(). POINT X
                front = (front + 1) % MAX_CAPACITY;
                size++;
                System.out.println("Enqueued " + value + " Size of queue " + size);
            }
        }

        T dequeue() {
            if (size == 0) {
                System.out.println("Can Dequeue, No elements in the Queue");
                return null;
            } else {
                size--;
                T value = arr[rear];
                arr[rear] = null;//for GC
                rear = (rear + 1) % MAX_CAPACITY;
                System.out.println("Dequeued " + value + " Size of queue " + size);
                return value;
            }

        }

        T peek() {
            if (size == 0) {
                System.out.println("No elements in the Queue");
                return null;
            } else {
                T value = arr[rear];
                System.out.println("Value at rear " + value);
                return value;
            }
        }
    }

    class QueueWith_LockUsingSynchronized<T> {
        private T[] arr;
        private int front, rear, size; // front is where data is added and rear is where its removed
        private final int MAX_CAPACITY;

        QueueWith_LockUsingSynchronized(int MAX) {
            this.MAX_CAPACITY = MAX;
            arr = (T[]) new Object[MAX];
            front = rear = 0;
            size = 0;
        }

        synchronized void enqueue(T value) {
            if (size == MAX_CAPACITY) {
                System.out.println("Cant Enqueue Size is full");
                return;
            } else {
                arr[front] = value;
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                } // Force other threads to catch up. ADDED FOR m2(). POINT X
                front = (front + 1) % MAX_CAPACITY;
                size++;
                System.out.println("Enqueued " + value + " Size of queue " + size);
            }
        }

        synchronized T dequeue() {
            if (size == 0) {
                System.out.println("Can Dequeue, No elements in the Queue");
                return null;
            } else {
                size--;
                T value = arr[rear];
                arr[rear] = null;//for GC
                rear = (rear + 1) % MAX_CAPACITY;
                System.out.println("Dequeued " + value + " Size of queue " + size);
                return value;
            }
        }

        synchronized T peek() {
            if (size == 0) {
                System.out.println("No elements in the Queue");
                return null;
            } else {
                T value = arr[rear];
                System.out.println("Value at rear " + value);
                return value;
            }
        }
    }

    class QueueWith_LockUsingSynchronized_SignallingUsingWaitNotify<T> {
        private T[] arr;
        private int front, rear, size; // front is where data is added and rear is where its removed
        private final int MAX_CAPACITY;

        QueueWith_LockUsingSynchronized_SignallingUsingWaitNotify(int MAX) {
            this.MAX_CAPACITY = MAX;
            arr = (T[]) new Object[MAX];
            front = rear = 0;
            size = 0;
        }

        synchronized void enqueue(T value) throws InterruptedException {
            if (size == MAX_CAPACITY) {
                System.out.println("Size is full, enqueuer waiting for dequeuer to remove some item");
                this.wait(); // since the implicit lock/monitor is on the current object we did this.wait(). the thread releases the lock here
            }
            arr[front] = value;
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            } // Force other threads to catch up. ADDED FOR m2(). POINT X
            front = (front + 1) % MAX_CAPACITY;
            size++;
            this.notifyAll();// since data is added now, any thread waiting on "this" object will be notified to resume. Below execution will not be hampered in sync block
            System.out.println("Enqueued " + value + " Size of queue " + size);

        }

        synchronized T dequeue() throws InterruptedException {
            if (size == 0) {
                System.out.println("Can Dequeue, No elements in the Queue");
                this.wait(); // Dequeuer is waiting for enqueuer to add some data. the thread releases the lock here
            }
            size--;
            T value = arr[rear];
            arr[rear] = null;//for GC

            rear = (rear + 1) % MAX_CAPACITY;
            this.notifyAll(); // Since data is removed from the queue now, it will signal any thread waiting on "this" object to resume execution. Below execution will not be hampered in sync block
            System.out.println("Dequeued " + value + " Size of queue " + size);
            return value;
        }

        synchronized T peek() {
            if (size == 0) {
                System.out.println("No elements in the Queue");
                return null;
            } else {
                T value = arr[rear];
                System.out.println("Value at rear " + value);
                return value;
            }
        }

    }

    class QueueWith_LockUsingReentractCondition_SignallingUsingCondition<T> {
        private T[] arr;
        private int front, rear, size; // front is where data is added and rear is where its removed
        private final int MAX_CAPACITY;
        private Lock lock = new ReentrantLock();
        // Two separate "waiting rooms" for better performance
        private final Condition waitingProducers = lock.newCondition();
        private final Condition waitingConsumers = lock.newCondition();

        QueueWith_LockUsingReentractCondition_SignallingUsingCondition(int MAX) {
            this.MAX_CAPACITY = MAX;
            arr = (T[]) new Object[MAX];
            front = rear = 0;
            size = 0;
        }

        void enqueue(T value) throws InterruptedException {
            lock.lock();
            try {
                if (size == MAX_CAPACITY) {
                    System.out.println("Size is full, enqueuer waiting for dequeuer to remove some item");
                    waitingProducers.await();
                }
                arr[front] = value;
                front = (front + 1) % MAX_CAPACITY;
                size++;
                waitingConsumers.signal();// since data is added now, any thread waiting on "this" object will be notified to resume. Below execution will not be hampered in sync block
                System.out.println("Enqueued " + value + " Size of queue " + size);
            } catch (Exception e) {
            } finally {
                lock.unlock();
            }


        }

        T dequeue() throws InterruptedException {
            lock.lock();
            try {
                if (size == 0) {
                    System.out.println("Can Dequeue, No elements in the Queue");
                    waitingConsumers.await(); // Dequeuer is waiting for enqueuer to add some data. the thread releases the lock here
                }
                size--;
                T value = arr[rear];
                arr[rear] = null;//for GC

                rear = (rear + 1) % MAX_CAPACITY;
                waitingProducers.signal(); // Since data is removed from the queue now, it will signal any thread waiting on "this" object to resume execution. Below execution will not be hampered in sync block
                System.out.println("Dequeued " + value + " Size of queue " + size);
                return value;
            } finally {
                lock.unlock();
            }
        }

        T peek() {
            lock.lock();
            try {
                if (size == 0) {
                    System.out.println("No elements in the Queue");
                    return null;
                } else {
                    T value = arr[rear];
                    System.out.println("Value at rear " + value);
                    return value;
                }
            } finally {
                lock.unlock();
            }
        }
    }

    class QueueWith_LockUsingReentractCondition_SignallingUsingCondition_BugFix<T> {
        private T[] arr;
        private int front, rear, size; // front is where data is added and rear is where its removed
        private final int MAX_CAPACITY;
        private Lock lock = new ReentrantLock();
        // Two separate "waiting rooms" for better performance
        private final Condition waitingProducers = lock.newCondition();
        private final Condition waitingConsumers = lock.newCondition();

        QueueWith_LockUsingReentractCondition_SignallingUsingCondition_BugFix(int MAX) {
            this.MAX_CAPACITY = MAX;
            arr = (T[]) new Object[MAX];
            front = rear = 0;
            size = 0;
        }

        void enqueue(T value) throws InterruptedException {
            lock.lock();
            try {
                while (size == MAX_CAPACITY) { //Bug Fixed 
                    System.out.println("Size is full, enqueuer waiting for dequeuer to remove some item");
                    waitingProducers.await();
                }
                arr[front] = value;
                front = (front + 1) % MAX_CAPACITY;
                size++;
                waitingConsumers.signal();// since data is added now, any thread waiting on "this" object will be notified to resume. Below execution will not be hampered in sync block
                System.out.println("Enqueued " + value + " Size of queue " + size);
            } catch (Exception e) {
            } finally {
                lock.unlock();
            }


        }

        T dequeue() throws InterruptedException {
            lock.lock();
            try {
                while (size == 0) { // Bug Fixed
                    System.out.println("Can Dequeue, No elements in the Queue");
                    waitingConsumers.await(); // Dequeuer is waiting for enqueuer to add some data. the thread releases the lock here
                }
                size--;
                T value = arr[rear];
                arr[rear] = null;//for GC

                rear = (rear + 1) % MAX_CAPACITY;
                waitingProducers.signal(); // Since data is removed from the queue now, it will signal any thread waiting on "this" object to resume execution. Below execution will not be hampered in sync block
                System.out.println("Dequeued " + value + " Size of queue " + size);
                return value;
            } finally {
                lock.unlock();
            }
        }

        T peek() {
            lock.lock();
            try {
                if (size == 0) {
                    System.out.println("No elements in the Queue");
                    return null;
                } else {
                    T value = arr[rear];
                    System.out.println("Value at rear " + value);
                    return value;
                }
            } finally {
                lock.unlock();
            }
        }
    }

    class QueueWith_LockUsingReentractConditionAndRWLOCK_SignallingUsingCondition<T> {
        private T[] arr;
        private int front, rear, size; // front is where data is added and rear is where its removed
        private final int MAX_CAPACITY;
        private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        private Lock readLock = rwLock.readLock();
        private Lock writeLock = rwLock.writeLock();

        // Conditions must come from the Write Lock for coordination
        private final Condition waitingProducers = writeLock.newCondition();
        private final Condition waitingConsumers = writeLock.newCondition();

        QueueWith_LockUsingReentractConditionAndRWLOCK_SignallingUsingCondition(int MAX) {
            this.MAX_CAPACITY = MAX;
            arr = (T[]) new Object[MAX];
            front = rear = 0;
            size = 0;
        }

        void enqueue(T value) throws InterruptedException {
            writeLock.lock();
            try {
                while (size == MAX_CAPACITY) { //Bug Fixed 
                    System.out.println("Size is full, enqueuer waiting for dequeuer to remove some item");
                    waitingProducers.await();
                }
                arr[front] = value;
                front = (front + 1) % MAX_CAPACITY;
                size++;
                waitingConsumers.signal();// since data is added now, any thread waiting on "this" object will be notified to resume. Below execution will not be hampered in sync block
                System.out.println("Enqueued " + value + " Size of queue " + size);
            } catch (Exception e) {
            } finally {
                writeLock.unlock();
            }


        }

        T dequeue() throws InterruptedException {
            writeLock.lock();
            try {
                while (size == 0) { // Bug Fixed
                    System.out.println("Can Dequeue, No elements in the Queue");
                    waitingConsumers.await(); // Dequeuer is waiting for enqueuer to add some data. the thread releases the lock here
                }
                size--;
                T value = arr[rear];
                arr[rear] = null;//for GC

                rear = (rear + 1) % MAX_CAPACITY;
                waitingProducers.signal(); // Since data is removed from the queue now, it will signal any thread waiting on "this" object to resume execution. Below execution will not be hampered in sync block
                System.out.println("Dequeued " + value + " Size of queue " + size);
                return value;
            } finally {
                writeLock.unlock();
            }
        }

        T peek() {
            readLock.lock();
            try {
                if (size == 0) {
                    System.out.println("No elements in the Queue");
                    return null;
                } else {
                    T value = arr[rear];
                    System.out.println("Value at rear " + value);
                    return value;
                }
            } finally {
                readLock.unlock();
            }
        }
    }
}
