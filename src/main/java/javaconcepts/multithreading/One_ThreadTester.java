package javaconcepts.multithreading;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * https://www.youtube.com/watch?v=WldMTtUWqTg
 *
 */
public class One_ThreadTester {

    //1:This would now start a main thread when this java program is run
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main Thread Started");
        new One_ThreadTester().m8();
    }

    public void m1() {
        Thread t1 = new Thread(new MyRunnable1(), "Thread 1");
        t1.start();
        System.out.println("Main Thread is now exiting");
    }

    public void m2() {
        Thread t1 = new Thread(new MyRunnable1(), "Thread 1");
        t1.setDaemon(true);
        t1.start();
        System.out.println("Main Thread is now exiting");
    }

    public void m3() {
        Thread t1 = new Thread(new MyRunnable1(), "Thread 1");
        t1.start();
        Thread t2 = new Thread(new MyRunnable1(), "Thread 2");
        t2.start();
        System.out.println("Main Thread is now exiting");
    }

    public void m4() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println(i + " by " + Thread.currentThread().getName());
            }
        }, "Thread 1");
        t1.start();

        System.out.println("Main Thread is now exiting");
    }

    // <<----Synchronization----->>
    /*
     * Threads are spawned from a main thread and a process. So the child threads by default have access to all the var and objects and resources
     * from the parent Process it is originated from. So multiple threads have shared access. There might be a case where multiple threads wants to
     * modify same resource/data/var/object but if they start data manipulation at the same time, the end data would be inconsistent. And all the
     * threads will be Racing to update the data. The last thread which makes the modification would sort of win because its final value is
     * written back to the var. This is called RACE CONDITION and to avoid this we use SYNCHRONIZED keyword to allow one thread access to a data at a time
     * Race Condition :
     * It occurs when two or more threads simultaneously update the same value(stackTopIndex) and, as a consequence, leave the value in an undefined or inconsistent state.
     * */
    public void m5() throws InterruptedException {
        StackNormal stackNormal = new StackNormal();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    stackNormal.push(i);
                    System.out.println(Thread.currentThread().getName() + " pushed the element " + i + " on index " + stackNormal.top);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        }, "Thread 1");
        t1.start();
        Thread t2 = new Thread(() -> {

            for (int i = 0; i < 100; i++) {
                try {
                    System.out.println(Thread.currentThread().getName() + " -> Popped element " + stackNormal.pop());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        }, "Thread 2");

        t2.start();
        System.out.println("Main Thread is now exiting");
    }

    public void m6() throws InterruptedException {
        StackConcurrentSafeWithSyncBlock stackConcSafe = new StackConcurrentSafeWithSyncBlock(new Object());
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    stackConcSafe.push(i);
                    System.out.println(Thread.currentThread().getName() + " pushed the element " + i + " on index " + stackConcSafe.top);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        }, "Thread 1");
        t1.start();
        Thread t2 = new Thread(() -> {

            for (int i = 0; i < 100; i++) {
                try {
                    System.out.println(Thread.currentThread().getName() + " -> Popped element " + stackConcSafe.pop());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        }, "Thread 2");

        t2.start();
        System.out.println("Main Thread is now exiting");
    }

    public void m7() throws InterruptedException {
        StackConcurrentSafeWithSyncMethods stackConcSafe = new StackConcurrentSafeWithSyncMethods();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    stackConcSafe.push(i);
                    System.out.println(Thread.currentThread().getName() + " pushed the element " + i + " on index " + stackConcSafe.top);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        }, "Thread 1");
        t1.start();
        Thread t2 = new Thread(() -> {

            for (int i = 0; i < 100; i++) {
                try {
                    System.out.println(Thread.currentThread().getName() + " -> Popped element " + stackConcSafe.pop());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        }, "Thread 2");

        t2.start();
        System.out.println("Main Thread is now exiting");
    }

    public void m8() throws InterruptedException {
        StackConcurrentSafeWithSync_waitNotify stackConcSafe = new StackConcurrentSafeWithSync_waitNotify(new Object());
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    stackConcSafe.push(i);
                    System.out.println(Thread.currentThread().getName() + " pushed the element " + i + " on index " + stackConcSafe.top);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        }, "Thread 1");
        t1.start();
        Thread t2 = new Thread(() -> {

            for (int i = 0; i < 100; i++) {
                try {
                    System.out.println(Thread.currentThread().getName() + " -> Popped element " + stackConcSafe.pop());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        }, "Thread 2");

        t2.start();
        System.out.println("Main Thread is now exiting");
    }

    public void m9() {

    }

    public void m10() throws InterruptedException {
        StackNormal stackNormal = new StackNormal();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    stackNormal.push(i);
                    System.out.println(Thread.currentThread().getName() + " pushed the element " + i + " on index " + stackNormal.top);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        }, "Thread 1");
        t1.start();
        t1.join();// This would prevent errors since we would be popping before pushing w/o this
        Thread t2 = new Thread(() -> {

            for (int i = 0; i < 100; i++) {
                try {
                    System.out.println(Thread.currentThread().getName() + " -> Popped element " + stackNormal.pop());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        }, "Thread 2");

        t2.start();
        System.out.println("Main Thread is now exiting");
    }

    public class MyRunnable1 implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                System.out.println(i + " by " + Thread.currentThread().getName());
            }
        }
    }

    public class StackNormal {
        int[] arr = new int[100];
        int top = -1;

        public void push(int val) throws Exception {
            if (top < 99) {
                ++top;
                Thread.sleep(100);
                arr[top] = val;
            } else throw new Exception("Overflow");
        }

        public int pop() throws Exception {
            if (top > -1) {
                int op = arr[top];
                top--;
                Thread.sleep(100);
                return op;
            } else throw new Exception("Underflow");
        }

    }

    /*
     * This Stack is now concurrency safe because if concurrent threads try to acccess the push or pop methods instead of having race condition,
     * only 1 will be allowed to execute the synchronize block at a time. It is imp to note that only that thread which has the lock would be granted
     * access through the synchronized door. once thread is done executing and is out of the sync block it is essentially out of the door and will
     * pass the lock to JVM and JVM will handover the lock to next waiting thread. This thread now having the access to lock can get inside the synchronized
     * door.
     * It is imp to understand that its the "lock" object that prevents the concurrent access to the sync block. We have sync blocks in both pop and push,
     * but those 2 sync are gaurded by same "lock" object, its like a key only one thread can have at a time. Even if Thread1 has the "lock" and operating
     * on push method, the pop method will also be block since the sync block inside pop is also gaurded by same "lock" object.
     * So the object through which lock is added is very imp. Once a given object is used a lock in the entire application, any sync blocks using that object
     * as lock will allow only 1 thread access across block at a time through entire application.
     *
     * If we want to avoid this we can have 2 locks below lock1push lock2pop. one for each push and pop. Then 2 threads cant concurrently push or cant concurrently pop
     * but 2 threads can concurrently push and pop, which is not desirable in this case coz we need that 2 threads should neither be able to push or pop or push&pop
     * concurrently. so we have just one lock object used both in push and pop.
     *
     * • While a thread is inside a synchronized method of an object, all other threads that wish to execute this synchronized method or any other synchronized method of the object will have to wait.
     * • This restriction does not apply to the thread that already has the lock and is executing a synchronized method of the object.
     * • Such a method can invoke other synchronized methods of the object without being blocked.
     * • The non-synchronized methods of the object can always be called at any time by any thread.
     * • A thread must acquire the object lock associated with a shared resource before it can enter the shared resource.
     * • The runtime system ensures that no other thread can enter a shared resource if another thread already holds the object lock associated with it.
     * • If a thread cannot immediately acquire the object lock, it is blocked, i.e., it must wait for the lock to become available.
     * • When a thread exits a shared resource, the runtime system ensures that the object lock is also relinquished. If another thread is waiting for this object lock, it can try to acquire the lock in order to gain access to the shared resource.
     * */
    public class StackConcurrentSafeWithSyncBlock {
        int[] arr = new int[100];
        int top = -1;
        Object lock;

        StackConcurrentSafeWithSyncBlock(Object lock) {
            this.lock = lock;
        }

        public void push(int val) throws Exception {
            synchronized (lock) {
                if (top < 99) {
                    ++top;
                    Thread.sleep(100);
                    arr[top] = val;
                } else System.out.println("Overflow");
            }
        }

        public int pop() throws Exception {
            synchronized (lock) {
                if (top > -1) {
                    int op = arr[top];
                    top--;
                    Thread.sleep(100);
                    return op;
                } else System.out.println("Underflow");
            }
            return Integer.MIN_VALUE;
        }
    }

    /*
     * Now Instead of using sync block we can put the sync keyword on the methods we want to lock from concurrent access. But what is the lock object in this case?
     * So now, since sync is at method level, by default compiler uses the "this" object instance as lock. which means if an object is created as:
     * "StackConcurrentSafeWithSyncMethods s=new StackConcurrentSafeWithSyncMethods()", and called "s.push() and s.pop()"
     * then object instance s itself is a lock for push and pop, where ever push and pop are used on object s, they all will be safe from concurrency.
     * Hence the lock is on object, them being non-static is very important.
     * What synchronized gives you
     * ✅ Mutual exclusion ✅ Visibility (happens-before) ✅ Simplicity ✅ Automatic unlock on exception
     * Limitations
     * ❌ No fairness control ❌ No timeout ❌ No try-lock ❌ No interruptible lock acquisition ❌ One condition queue only (wait/notify) ❌ Cannot check lock state ❌ No lock composition ❌ Hard to debug ❌ Monitor-level only (object-level lock)
     * Mental model: synchronized = “basic mechanical lock with no controls”
     * */
    public class StackConcurrentSafeWithSyncMethods {
        int[] arr = new int[100];
        int top = -1;

        public synchronized void push(int val) throws Exception {
            if (top < 99) {
                ++top;
                Thread.sleep(100);
                arr[top] = val;
            } else System.out.println("Overflow");
        }

        public synchronized int pop() throws Exception {
            if (top > -1) {
                int op = arr[top];
                top--;
                Thread.sleep(100);
                return op;
            } else System.out.println("Underflow");
            return Integer.MIN_VALUE;
        }
    }

    /*
     * Just having Synchronized methods with a lock is not enough for a good
     * concurrent object. Imagine this, you are pushing the data in stack but
     * it's size is already MAX, so the push operation was simple logging or erroring that
     * its overflow, similarly for pop its just logging or erroring underflow. What is
     * happening that precious CPU cycles were getting wasted, because if the thread that is
     * popping will run 100 times before push thread, it will just log Underflow but the processing
     * is still happening so CPU cycles are wasted. Instead of this, if push can say since size is
     * maxed out i will WAIT for it to come down. That way pop thread can take control and pop the
     * element and then pop can NOTIFY the waiting thread to continue(not restart) execution. Now
     * push thread, since was waiting, will continue execution. This strategy helps in reducing
     * unnecessary processing.
     * In your current implementation, if a thread calls pop() and the stack is empty, it immediately returns Integer.MIN_VALUE.
     * This forces the calling thread to either give up or loop repeatedly to check for data, a process known as Active Waiting or Busy Waiting.
     * To fix this, you can implement Signaling using Java's built-in wait() and notifyAll() methods.
     * This allows threads to "sleep" until the state of the stack changes.
     * The stack previously uses "check and fail" logic rather than "wait and notify."
     *  - The Issue: If the stack is empty, pop() simply returns Integer.MIN_VALUE. In many multithreaded scenarios (like Producer-Consumer), you want the thread to wait until data becomes available.
     *  - The Issue: Without wait() and notifyAll(), threads must "busy-wait" by calling pop() in a loop, which wastes CPU cycles.
     *
     * To implement this, the threads must communicate using the lock object.
     *  - When a pop() thread finds the stack empty, it calls lock.wait().
     *  - When a push() thread adds data, it calls lock.notifyAll() to wake up the waiting threads.
     * In this specific stack implementation, you should use notifyAll().
     * If you use notify(), Java picks only one random thread to wake up.
     * IMP: This wait and notify only works if there are 2 threads: 1 for producing/pushing and other for consuming/popping.
     * Because if there are multiple producers and consumers, a notify from producer may notify another producer, since
     * every thread is waiting with the common lock and notify doesn't know which thread to exactly notify.
     *
     * We have used 1 prod consumer wait and notify below.
     * */
    public class StackConcurrentSafeWithSync_waitNotify {
        int[] arr = new int[100];
        int top = -1;
        private final Object lock;

        public StackConcurrentSafeWithSync_waitNotify(Object lock) {
            this.lock = lock;
        }

        public void push(int val) throws Exception {
            synchronized (lock) {
                while (top >= 99) { // Threads can occasionally wake up from a wait() state even if no one called notify(). Use 'while' to handle spurious wakeups
                    System.out.println("Stack Full. Push waiting...");
                    lock.wait(); // Thread releases lock and sleeps
                }
                top++;
                arr[top] = val;
                lock.notifyAll(); // Signal all threads waiting in pop() that data is now available
            }
        }

        public int pop() throws Exception {
            synchronized (lock) {
                while (top <= -1) {
                    System.out.println("Stack Empty. Pop waiting...");
                    lock.wait(); // Thread releases lock and sleeps until a push occurs
                }
                int op = arr[top];
                top--;
                lock.notifyAll(); // Signal any threads waiting in push() that space is now available
                return op;
            }
        }
    }

    /*
     * ReentrantLock (same lock, professional controls). Now we replace synchronized with ReentrantLock.
     * What changed? Functionally: nothing -> Semantically: everything
     * What ReentrantLock adds:
     * ✅ tryLock() ✅ tryLock(timeout) ✅ lockInterruptibly() ✅ fairness policy ✅ condition variables ✅ lock state inspection ✅ non-blocking acquisition ✅ better deadlock control ✅ better debugging
     * Mental model: ReentrantLock = synchronized + control panel
     * */
    public class StackConcurrentSafeWithSync_ReentractLock {

        private final int[] arr = new int[100];
        private int top = -1;
        private final ReentrantLock lock = new ReentrantLock();

        public void push(int item) {
            lock.lock();
            try {
                arr[++top] = item;
            } finally {
                lock.unlock();
            }
        }

        public int pop() {
            lock.lock();
            try {
                if (top <= -1) return Integer.MIN_VALUE;
                return arr[top--];
            } finally {
                lock.unlock();
            }
        }
    }

    /*
     * Condition (proper waiting, not busy waiting)
     * Now we add blocking behavior: If stack empty → consumer waits If stack full → producer waits
     * So when we have multiple producers and consumers, simple wait and notify will also fail. We need to use Explicit Locks.
     * When multiple producers and consumers are all waiting on the same lock object, they are effectively in the same "waiting room".
     * Because the lock doesn't know the difference between a producer thread and a consumer thread, a call to notifyAll() or notify() can
     * wake up any random thread from that combined pool it can be producer or consumer. But we need to ensure that a consumer notifies a producer
     * and a producer notifies a consumer.
     *
     * Solution is to Use ReentrantLock and Conditions. To fix this "mixed signaling" problem, you should move away from synchronized and
     * use java.util.concurrent.locks.Condition. This allows you to have two separate signaling channels on a single lock.
     *
     * Precision Signaling: notEmpty.signal() will only wake up a consumer thread. There is zero chance it accidentally wakes up another producer.
     * Efficiency: You can safely use signal() (which wakes one thread) instead of signalAll() because you are guaranteed to wake a thread
     * that can actually perform the task.
     * Mental model: Condition = intelligent waiting room instead of shouting wait()/notify()
     * */
    public class StackConcurrentSafeWithSync_ReentractConditionalLock {
        private final int[] arr = new int[100];
        private int top = -1;

        private final Lock lock = new ReentrantLock();
        // Separate "waiting rooms" for producers and consumers
        private final Condition notFull = lock.newCondition();
        private final Condition notEmpty = lock.newCondition();

        public void push(int val) throws InterruptedException {
            lock.lock();
            try {
                while (top >= 99) {
                    notFull.await(); // Producers only wait here
                }
                arr[++top] = val;
                // Signal ONLY waiting consumers that data is ready
                notEmpty.signal();
            } finally {
                lock.unlock();
            }
        }

        public int pop() throws InterruptedException {
            lock.lock();
            try {
                while (top <= -1) {
                    notEmpty.await(); // Consumers only wait here
                }
                int val = arr[top--];
                // Signal ONLY waiting producers that space is available
                notFull.signal();
                return val;
            } finally {
                lock.unlock();
            }
        }
    }

    /*
    * ReentrantReadWriteLock (read-heavy optimization)
    * Now assume:90% operations = peek() -> 10% operations = push/pop
    * Using normal locks = wasted parallelism
    * 
    * What this enables
    * ✅ Multiple readers simultaneously ❌ Writers still exclusive ✅ Massive throughput improvement in read-heavy systems 
    * Mental model: ReentrantReadWriteLock = concurrency optimization lock
    * */
    public class StackConcurrentSafeWithSync_ReentractReadWriteLock {
        private final int[] arr = new int[100];
        private int top = -1;
        private final ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
        private final Lock readLock = rw.readLock();
        private final Lock writeLock = rw.writeLock();

        public void push(int item) {
            writeLock.lock();
            try {
                arr[++top]=item;
            } finally {
                writeLock.unlock();
            }
        }

        public int pop() {
            writeLock.lock();
            try {
                return arr[top--];
            } finally {
                writeLock.unlock();
            }
        }

        public int peek() {
            readLock.lock();
            try {
                return arr[top];
            } finally {
                readLock.unlock();
            }
        }
    }
    /*
    * Semaphore (resource-based concurrency)
    * Now we model capacity control, not mutual exclusion. Example: Stack capacity = 10
    * Semaphore meaning: "Semaphore ≠ lock" instead "Semaphore = permits counter"
    * Mental model: Semaphore = "How many can enter?" Lock = "Who can enter?"
    * 
    * */
    public class StackConcurrentSafeWith_Semaphores {
        private final int[] arr = new int[100];
        private int top = -1;
        private final Semaphore items = new Semaphore(0);
        private final Semaphore spaces;
        private final ReentrantLock lock = new ReentrantLock();

        public StackConcurrentSafeWith_Semaphores(int capacity) {
            spaces = new Semaphore(capacity);
        }

        public void push(int item) throws InterruptedException {
            spaces.acquire();   // wait for space
            lock.lock();
            try {
                arr[++top]=item;
            } finally {
                lock.unlock();
                items.release(); // signal item available
            }
        }

        public int pop() throws InterruptedException {
            items.acquire();    // wait for item
            lock.lock();
            try {
                return arr[top--];
            } finally {
                lock.unlock();
                spaces.release(); // signal space available
            }
        }
    }

    /*
     * MasterStack: A high-performance, thread-safe stack demonstrating: 1. Semaphores (for capacity/permit management)
     * 2. ReentrantReadWriteLock (to allow multiple simultaneous readers) 3. Conditions (for specialized wait/notify
     * signaling) Synchronized: One thread at a time for everything. If one thread is doing a slow peek(), no one can
     * push().
     * <p>
     * ReentrantLock: Adds the ability to tryLock() (don't wait forever) and Fairness (first-come, first-served),
     * preventing thread starvation.
     * <p>
     * Conditions: Allows us to have a dedicated "waiting room" (notEmpty). We only wake up consumers when there is
     * actually something to pop.
     * <p>
     * ReentrantReadWriteLock: Significant throughput boost for peek(). Many threads can "peek" at once. They only pause
     * when a "writer" (push/pop) needs exclusive access.
     * <p>
     * Semaphores: Replaces manual counter checks with Permits. It elegantly manages the "Stack Full" state by blocking
     * producers before they even touch the lock.
     */
    public class MasterStackForConcurrentOps {
        private final int[] arr;
        private int top = -1;
        private final int capacity;

        // 1. REENTRANT READ-WRITE LOCK
        // We use this to allow multiple threads to 'peek' at the same time,
        // but only one thread can 'push' or 'pop' at a time.
        private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(true); // 'true' for fairness
        private final Lock readLock = rwLock.readLock();
        private final Lock writeLock = rwLock.writeLock();

        // 2. CONDITIONS
        // These must come from the WriteLock because Conditions are not supported on ReadLocks.
        // They allow us to wake up specific groups of threads.
        private final Condition notEmpty = writeLock.newCondition();

        // 3. SEMAPHORES
        // Instead of manually checking 'if (top >= capacity)', we use permits.
        // A thread must 'acquire' a permit to push. This handles 'Wait if Full' automatically.
        private final Semaphore capacitySemaphore;

        public MasterStackForConcurrentOps(int capacity) {
            this.capacity = capacity;
            this.arr = new int[capacity];
            // Initialize with 'capacity' permits. If stack has 100 slots, it has 100 permits.
            this.capacitySemaphore = new Semaphore(capacity);
        }

        /**
         * PUSH: Uses WriteLock and Semaphore. Improvement: Automatically waits if full due to Semaphore.
         */
        public void push(int val) throws InterruptedException {
            // Semaphore handles the "is it full?" logic. It blocks until a permit is available.
            capacitySemaphore.acquire();

            writeLock.lock(); // Exclusive access to modify 'top' and 'arr'
            try {
                arr[++top] = val;
                System.out.println("Pushed: " + val);

                // Signal one waiting consumer that data is now available
                notEmpty.signal();
            } finally {
                writeLock.unlock(); // Always release in finally block!
            }
        }

        /**
         * POP: Uses WriteLock and Condition. Improvement: Precision signaling; releases a permit back to the
         * Semaphore.
         */
        public int pop() throws InterruptedException {
            writeLock.lock();
            try {
                // If empty, wait for a signal from a 'push' operation
                while (top == -1) {
                    System.out.println("Stack empty, waiting for data...");
                    notEmpty.await();
                }

                int val = arr[top--];

                // Releasing a permit back to the capacity semaphore
                // This tells waiting producers that one slot is now free.
                capacitySemaphore.release();

                return val;
            } finally {
                writeLock.unlock();
            }
        }

        /**
         * PEEK: Uses ReadLock. MASSIVE IMPROVEMENT: Multiple threads can call peek() simultaneously without blocking
         * each other, as long as no one is pushing/popping.
         */
        public int peek() {
            readLock.lock(); // Shared access
            try {
                if (top == -1) return Integer.MIN_VALUE;
                return arr[top];
            } finally {
                readLock.unlock();
            }
        }

        /**
         * TRY_PUSH: Demonstrates non-blocking attempt. Improvement over 'synchronized': Gives up if the lock isn't
         * free.
         */
        public boolean tryPush(int val) {
            // tryLock() allows the thread to do something else if the stack is busy
            if (writeLock.tryLock()) {
                try {
                    if (capacitySemaphore.tryAcquire()) { // Also try to get a permit without waiting
                        arr[++top] = val;
                        notEmpty.signal();
                        return true;
                    }
                } finally {
                    writeLock.unlock();
                }
            }
            return false;
        }
    }


    /*
     * Static Synchronized Methods :
     * • A thread acquiring the lock of a class to execute a static synchronized method has no effect on any thread acquiring the lock on any object of the class to execute a synchronized instance method.
     * • In other words, synchronization of static methods in a class is independent from the synchronization of instance methods on objects of the class.
     * • A subclass decides whether the new definition of an inherited synchronized method will remain synchronized in the subclass.
     * */
}
