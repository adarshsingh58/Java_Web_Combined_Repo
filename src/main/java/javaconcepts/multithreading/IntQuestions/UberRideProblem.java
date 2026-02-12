package javaconcepts.multithreading.IntQuestions;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Problem Statement
 * <p>
 * Imagine at the end of a political conference, republicans and democrats are trying to leave the venue and ordering
 * Uber rides at the same time. However, to make sure no fight breaks out in an Uber ride, the software developers at
 * Uber come up with an algorithm whereby either an Uber ride can have all democrats or republicans or two Democrats and
 * two Republicans. All other combinations can result in a fist-fight. puYour task as the Uber developer is to model the
 * ride requestors as threads. Once an acceptable combination of riders is possible, threads are allowed to proceed to
 * ride. Each thread invokes the method seated() when selected by the system for the next ride. When all the threads are
 * seated, any one of the four threads can invoke the method drive() to inform the driver to start the ride.
 * <p>
 * Here we have many thread of type either Republican or Democrats. We have Task as Seated and Drive. The Task can only
 * be performed once a certain condition is met. I see a use of barrier/phaser here. unless certain condition is met
 * i.e. all customer threads do not reach and met this condition of all or 2-2 rep/democrat, the process will not start.
 * We can also use a semaphore since total riders are 4. permits to sit can be given out till 4 and then all 4 threads
 * can reach barrier and check for req condition, if met ride starts else all release the permits and wait.
 * <p>
 * The Semaphore & Counting Logic Using a Semaphore is a great way to control the "flow" of riders, but the 2-2 or 4-0
 * logic is the real challenge. The "Permit" Strategy: You can't just give out 4 generic permits because you might end
 * up with 3 Democrats and 1 Republican (a "fist-fight" combination). The Better Way: Use two separate Semaphores (one
 * for democrats and one for republicans). When a Democrat arrives, they check: "Are there 3 other Democrats waiting? Or
 * are there 1 Democrat and 2 Republicans waiting?" If yes, that Democrat "releases" the other 3 threads and they all
 * head to the barrier.
 * <p>
 * But barrier only waits for #of threads to come to a point not about any condition. So we need allow threads only when
 * conditions are met,
 * <p>
 * If There are yet no thread or if just 1 Demo or Rep -> Allow any thread
 * <p>
 * If 3 Demo threads present or only 2 Rep threads -> Allow Demo thread
 * <p>
 * If 1 or 3 Rep threads present or only 2 Demo threads -> Allow Rep thread
 * <p>
 * ELSE wait
 *
 */


public class UberRideProblem {

    record Customer(String name, String demoOrRep) {
    }

    private final String REPUBLICAN = "R";
    private final String DEMOCRAT = "D";

    public static void main(String[] args) throws InterruptedException {
        new UberRideProblem().m2();
    }

    private void m1() throws InterruptedException {
        UberRideProblem uberRideProblem = new UberRideProblem();
        UberCar car = new UberCar();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 5; i++) {
            executorService.submit(() -> {
                try {
                    car.seated(uberRideProblem.REPUBLICAN);
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        for (int i = 0; i < 5; i++) {
            executorService.submit(() -> {
                try {
                    car.seated(uberRideProblem.DEMOCRAT);
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executorService.awaitTermination(5, TimeUnit.SECONDS);
    }

    private void m2() throws InterruptedException {
        UberCarSemaphoresAndBarriers car = new UberCarSemaphoresAndBarriers();
        ExecutorService executorService = Executors.newFixedThreadPool(20);

        System.out.println("Starting Uber Ride Service...");

        // Simulate a random mix of 20 riders arriving at different times
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                try {
                    car.seated("R");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            executorService.submit(() -> {
                try {
                    car.seated("D");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            // Slight delay to simulate realistic arrival times
            Thread.sleep(200);
        }

        executorService.shutdown();
        executorService.awaitTermination(20, TimeUnit.SECONDS);
        System.out.println("All rides completed.");
    }
}

/*
 * So I am keeping count of # of republicans and democrats. when seated is called, based on #rep or #demo
 * the current republican or democrats thread is allowed to sit, if condition is not met the thread will wait
 * and give chance to another rider.
 *
 * Post adding all the riders as per condition, the barrier will wait for 4 parties and once 4 thread reaches the
 * barrier, drive method will be invoked and existing demo/rep will be reset to 0 and all other threads are notified
 * to proceed seating again.
 *
 * 1. Major Logic Flaws
 * - This Code is a deadlock, coz the thread calls barrier.await(). Unlike Object.wait(), which releases the synchronized lock, CyclicBarrier.await() does NOT release the lock.
 * - The "3-1" Fist-Fight Problem: Your if (democrats.get() <= 2) logic allows a Republican to sit if there are 0, 1, or 2 Democrats.
 *  Scenario: 3 Democrats are already seated. A Republican thread arrives. Your code sees democrats.get() is not <= 2, so it waits.
 *  The Problem: What if the next thread is also a Democrat? Now you have 4 Democrats (valid). But what if you have 2 Democrats and 2 Republicans?
 *  Your logic doesn't strictly enforce the "Only 4-0 or 2-2" rule; it just vaguely limits counts.
 * - Barrier Reset Issues: You call drive() and reset counts after the barrier. Because you used notifyAll() after resetting,
 *  you are on the right track for signaling, but because democrats and republicans are shared, the reset logic needs to be handled by exactly one thread (the "leader"), or they will overwrite each other.
 * */
class UberCar {
    CyclicBarrier barrier = new CyclicBarrier(4);
    AtomicInteger democrats = new AtomicInteger();
    AtomicInteger republicans = new AtomicInteger();

    public synchronized void seated(String demoOrRep) throws InterruptedException, BrokenBarrierException {

        if (demoOrRep.equals("R")) {
            System.out.println("Rep came for seating");
            if (democrats.get() <= 2) {
                republicans.addAndGet(1);
                System.out.println("Rep seating successful ");
            } else
                wait();
        } else {
            System.out.println("Demo came for seating");
            if (republicans.get() <= 2) {
                democrats.addAndGet(1);
                System.out.println("Demo seating successful ");
            } else
                wait();
        }

        barrier.await();// The thread calls barrier.await(). Unlike Object.wait(), which releases the synchronized lock, CyclicBarrier.await() does NOT release the lock.
        drive(democrats.get(), republicans.get());
        //problem with this approach is this. democrats and republicans var will be reset by each of 4 thread, so if meanwhile republicans becomes 1 after being reset, it may be reset again.
        democrats.set(0);
        republicans.set(0);
        notifyAll();
    }

    private void drive(int demo, int rep) {
        System.out.println("Driving Started. Total Democrats = " + rep + " and Republican = " + rep);
    }


}

/*
 * To fix this, you should use Semaphores to handle the waiting and a Barrier Action to handle the reset.
 * Semaphores are better because they don't require you to hold a synchronized lock while waiting.
 *
 * Why this is the correct solution:
 * Avoids Deadlock: By using ReentrantLock and calling lock.unlock() before barrier.await(), you prevent the Nested Monitor Lockout that froze your synchronized version.
 * Targeted Signaling: Instead of notifyAll(), which wakes up every thread (inefficient), the release(n) call on the Semaphore wakes up exactly the number of Democrats or Republicans needed for that specific valid ride.
 * The Barrier Action: Passing the drive() method as a Runnable to the CyclicBarrier constructor ensures it is executed exactly once by the last thread to arrive, rather than four times.
 * How the release() logic works:
 * When the 4th thread (the "leader" for that ride) arrives and sees a valid combination:
 * It decrements the global counters so the next ride starts with a fresh count.
 * It releases the specific number of permits needed to wake up the already waiting threads.
 * Because the leader doesn't call acquire(), it passes straight to barrier.await(), joining the 3 threads it just woke up.
 * 
 * 1. How Initial Permits WorkWhen you initialize a Semaphore with new Semaphore(0), you are starting the counter at zero.acquire(): 
 * This checks if the counter is $>0$. Since it's $0$, the thread blocks and waits.release(n): This simply adds $n$ to the counter.
 * \Unlike a BoundedBuffer where you might set a cap, here we start at $0$ because we want every thread to get stuck in the "waiting room" 
 * immediately. They only get to move forward when another thread (the 4th rider) "unlocks" them.2. "Releasing" into a Zero-Permit Semaphore
 * 
 * You asked: How can you release more if you initialized it to 0?In Java, release() does not check an upper bound.If you call release(3) 
 * on a Semaphore(0), the count becomes $3$.This effectively "wakes up" 3 threads that were previously blocked on acquire().3. 
 * Why release(3) instead of notifyAll()?In your original synchronized code, you used notifyAll(). The problem with notifyAll() is 
 * that it wakes up everyone—even the people who shouldn't be in the ride yet.The Thundering Herd: If 100 Democrats are waiting and a 
 * ride forms, notifyAll() wakes all 100. 96 of them will realize it's not their turn and go back to sleep, which wastes a lot of CPU.
 * Targeted Release: By using demWaiting.release(3), the Semaphore wakes up exactly 3 specific threads. The other 97 Democrats stay asleep and undisturbed.
 * */
class UberCarSemaphoresAndBarriers {
    private int dem = 0;
    private int rep = 0;

    // Semaphores act as "waiting rooms" for each party
    private final Semaphore demWaiting = new Semaphore(0);
    private final Semaphore repWaiting = new Semaphore(0);

    // Lock to protect the counting logic
    private final ReentrantLock lock = new ReentrantLock();

    // The Barrier Action runs exactly ONCE when the 4th thread arrives
    private final CyclicBarrier barrier = new CyclicBarrier(4, () -> {
        drive();
    });

    public void seated(String type) throws InterruptedException, BrokenBarrierException {
        boolean ridePossible = false;

        lock.lock(); //
        try {
            if (type.equals("D")) {
                dem++;
                if (dem == 4) {
                    // Case 1: 4 Democrats
                    demWaiting.release(3);
                    dem = 0;
                    ridePossible = true;
                } else if (dem >= 2 && rep >= 2) {
                    // Case 2: 2 Democrats and 2 Republicans
                    demWaiting.release(1);
                    repWaiting.release(2);
                    dem -= 2;
                    rep -= 2;
                    ridePossible = true;
                }
            } else {
                rep++;
                if (rep == 4) {
                    // Case 3: 4 Republicans
                    repWaiting.release(3);
                    rep = 0;
                    ridePossible = true;
                } else if (rep >= 2 && dem >= 2) {
                    // Case 4: 2 Republicans and 2 Democrats
                    repWaiting.release(1);
                    demWaiting.release(2);
                    rep -= 2;
                    dem -= 2;
                    ridePossible = true;
                }
            }
        } finally {
            // CRITICAL: Release lock before waiting to allow other threads in
            lock.unlock();
        }

        if (!ridePossible) {
            // Thread was not the 4th person to complete a set; it must wait
            if (type.equals("D")) {
                demWaiting.acquire();
            } else {
                repWaiting.acquire();
            }
        }

        // All 4 threads call seated() once they are released from semaphores
        System.out.println(Thread.currentThread().getName() + " (" + type + ") is seated.");

        // Wait for all 4 members of the specific ride to be ready
        barrier.await();
    }

    private void drive() {
        System.out.println("--- All seated. Uber is driving away! ---");
    }
}