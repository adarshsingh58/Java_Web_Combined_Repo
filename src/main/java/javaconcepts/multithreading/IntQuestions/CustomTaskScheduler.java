package javaconcepts.multithreading.IntQuestions;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread Safe Deferred Callback
 * <p>
 * Problem Statement
 * <p>
 * Design and implement a thread-safe class that allows registration of callback methods that are executed after a user
 * specified time interval in seconds has elapsed. The goal is to allow multiple threads to say: "Hey, run this piece of
 * code (callback) exactly 5 seconds from now," and have a dedicated "Executor" thread that wakes up at the exact right
 * moments to run them.
 * <p>
 * Solution
 * <p>
 * Let us try to understand the problem without thinking about concurrency. Let's say our class exposes an API called
 * registerCallback() that'll take a parameter of type Callback, which we'll define later. Anyone calling this API
 * should be able to specify after how many seconds should our executor invoke the passed in callback. One naive way to
 * solve this problem is to have a busy thread that continuously loops over the list of callbacks and executes them as
 * they become due. However, the challenge here is to design a solution which doesn't involve a busy thread. If we
 * restrict ourselves to use only concurrency constructs offered by Java then one possible solution is to have an
 * execution thread that maintains a priority queue of callbacks ordered by the time remaining to execute each of the
 * callbacks. The execution thread can sleep for the duration equal to the time duration before the earliest callback in
 * the min-heap becomes due for execution. Consumer threads can come and add their desired callbacks in the min-heap
 * within a critical section. However, whenever a consumer thread requests a callback be registered, the caveat is to
 * wake up the execution thread and recalculate the minimum duration it needs to sleep for before the earliest callback
 * becomes due for execution. It is possible that a callback with an earlier due timestamp gets added by a consumer
 * thread while the executor thread is current;y asleep for a duration, calculated for a callback due later than the one
 * just added. Consider this example: initially, the execution thread is sleeping for 30 mins before any callback in the
 * min-heap is due. A consumer thread comes along and adds a callback to be executed after 5 minutes. The execution
 * thread would need to wake up and reset itself to sleep for only 5 minutes instead of 30 minutes. Once we find an
 * elegant way of capturing this logic our problem is pretty much solved. To build this efficiently, you need three main
 * things:
 * <p>
 * A Data Structure (PriorityQueue): Since callbacks have different delay times, you need to always know which one is
 * "next." A PriorityQueue sorted by executionTime (absolute time) is perfect for this.
 * <p>
 * A Lock (ReentrantLock): Multiple threads will be adding tasks to the queue at the same time, and one background
 * thread will be removing them. You must protect the queue.
 * <p>
 * A Signaling System (Condition variable): You want to avoid "busy waiting" (a while(true) loop that eats CPU). You
 * need a way for the background thread to sleep and be woken up only when it’s time to work.
 */
public class CustomTaskScheduler {

    public static void main(String[] args) {

    }

    /*
     * The Execution Logic (The "Magic")
     * The background thread (the one that actually runs the callbacks) follows a specific logic loop:
     * Check the Queue: Is it empty? If yes, await() (sleep) until someone registers a task.
     * Peek at the Top: Look at the task with the soonest execution time.
     * Check the Clock: * Is it time? If currentTime >= taskTime, pull it off and run it.
     * Is it too early? If it’s not time yet, the thread shouldn't just loop.
     * It calculates waitTime = taskTime - currentTime and calls condition.awaitNanos(waitTime).
     * Wait Interruption: If a new task is registered while the background thread is sleeping,
     * and that new task is even "sooner" than the current one, the register thread will call signal() to wake the
     * background thread up so it can re-adjust its sleep time.
     *
     * Why this logic is professional-grade:
     * Avoids Busy Waiting: The background thread isn't checking the time in a tight while(true) loop.
     * It uses awaitNanos(time), which tells the OS to put the thread to sleep and wake it up exactly when the timer expires or a new task is added.
     * The "Priority" Factor: By using a PriorityQueue, peek() always gives us the task that is due next.
     * We don't have to scan the whole list.
     * Handling "Sooner" Tasks: Imagine the background thread is sleeping for 10 seconds waiting for Task A.
     * Suddenly, a user registers Task B with a 1-second delay. The signalAll() in the register method wakes the background thread up, it sees Task B is now at the top, and it executes Task B first.
     * Lock Safety: The ReentrantLock ensures that the PriorityQueue (which is not thread-safe by itself) is
     * never accessed by two threads at once.
     * */
    private void m1() {
        DeferredCallbackExecutor executor = new DeferredCallbackExecutor();

        System.out.println("Registering tasks at: " + System.currentTimeMillis());

        executor.registerCallback(5000, () -> System.out.println("Task 1 (5s delay) executed at " + System.currentTimeMillis()));
        executor.registerCallback(1000, () -> System.out.println("Task 2 (1s delay) executed at " + System.currentTimeMillis()));
        executor.registerCallback(3000, () -> System.out.println("Task 3 (3s delay) executed at " + System.currentTimeMillis()));
    }
}

class DeferredCallbackExecutor {
    // 1. Task definition with absolute execution time
    private static class CallBackTask implements Comparable<CallBackTask> {
        long executeAt;
        Runnable action;

        public CallBackTask(long delay, Runnable action) {
            this.executeAt = System.currentTimeMillis() + delay;
            this.action = action;
        }

        @Override
        public int compareTo(CallBackTask other) {
            return Long.compare(this.executeAt, other.executeAt);
        }
    }

    private final PriorityQueue<CallBackTask> queue = new PriorityQueue<>();
    private final Lock lock = new ReentrantLock();
    private final Condition newTaskAdded = lock.newCondition();

    public DeferredCallbackExecutor() {
        // Start the background consumer thread immediately
        Thread backgroundThread = new Thread(this::startExecutionLoop);
        backgroundThread.setDaemon(true); // Shut down if main app closes
        backgroundThread.start();
    }

    // Producer Method: Adds a task and wakes the worker if necessary
    public void registerCallback(long delayMs, Runnable action) {
        lock.lock();
        try {
            CallBackTask newTask = new CallBackTask(delayMs, action);
            queue.add(newTask);
            // Always signal because the new task might be "sooner" than the current top
            newTaskAdded.signalAll();
        } finally {
            lock.unlock();
        }
    }

    // Consumer Method: The background loop
    private void startExecutionLoop() {
        while (true) {
            lock.lock();
            try {
                // Step A: If queue is empty, wait indefinitely for a task
                while (queue.isEmpty()) {
                    newTaskAdded.await();
                }

                while (!queue.isEmpty()) {
                    long now = System.currentTimeMillis();
                    CallBackTask top = queue.peek();

                    if (now >= top.executeAt) {
                        // Step B: It's time! Remove and execute
                        CallBackTask task = queue.poll();
                        task.action.run();
                    } else {
                        // Step C: Too early. Wait for the remaining time
                        long sleepTime = top.executeAt - now;
                        // wait for sleepTime OR until a NEW sooner task is added
                        newTaskAdded.awaitNanos(java.util.concurrent.TimeUnit.MILLISECONDS.toNanos(sleepTime));
                        break; // Break internal loop to re-check queue top after waking
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } finally {
                lock.unlock();
            }
        }
    }
}