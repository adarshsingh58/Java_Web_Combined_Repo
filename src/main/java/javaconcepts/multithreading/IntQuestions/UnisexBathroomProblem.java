package javaconcepts.multithreading.IntQuestions;

import java.util.concurrent.Semaphore;

/**
 * Problem Statement
 * <p>
 * A bathroom is being designed for the use of both males and females in an office but requires the following
 * constraints to be maintained:
 * <p>
 * There cannot be men and women in the bathroom at the same time. There should never be more than three employees in
 * the bathroom simultaneously.
 * <p>
 * The solution should avoid deadlocks.
 * 
 * Male And Female are The threads, the useBathRoom is a task (runnable or callable)
 *
 */
public class UnisexBathroomProblem {

    public static void main(String args[]) throws InterruptedException {
        UnisexBathroomSemaphore.runTest();
    }

}

/*
 * We’ll need two APIs, one that is called by a male to use the bathroom and another one that is called by the woman to use the bathroom. Initially our class looks like the following
 * Let us try to address the first problem of allowing either men or women to use the bathroom. We’ll worry about the max employees later.
 * We need to maintain state in a variable which would tell us which gender is currently using the bathroom. Let’s call this variable inUseBy.
 *  To make the code more readable we’ll make the type of the variable inUseBy a string which can take on the values men, women or none.
 * We’ll also have a method useBathroom() that’ll mock a person using the bathroom. The implementation of this method will simply sleep the thread
 * using the bathroom for some time. Assume there’s no one in the bathroom and a male thread invokes the maleUseBathroom() method, the thread has
 * to check first whether the bathroom is being used by a female thread. If it is indeed being used by a female, then the male thread has to wait for the bathroom to be empty.
 * If the male thread already finds the bathroom empty, which in our scenario it does, the thread simply updates the inUseBy variable to “MEN” and proceeds to use the bathroom.
 * After using the bathroom, however, it must let any waiting female threads know that it is done and they can now use the bathroom.
 * The astute reader would immediately realize that we’ll need to guard the variable inUseBy since it can possibly be both read and written to by
 * different threads at the same time. Does that mean we should mark our methods as synchronized? The wary reader would know that doing so would essentially
 * make the threads serially access the methods, i.e., if one male thread is accessing the bathroom, then another one can’t access the bathroom even though
 * the problem says that more than one male should be able to use the bathroom. This requires us to take synchronization to a finer granular level rather than
 * implementing it at the method level. So far what we discussed looks like the below when translated into code
 *
 * The code so far allows any number of men or women to gather in the bathroom. However, it allows only one gender to do so.
 * The methods are mirror images of each other with only gender-specific variable changes. Let’s discuss the important portions of the code.
 * */
class UnisexBathroomAnyLimit {

    static String WOMEN = "women";
    static String MEN = "men";
    static String NONE = "none";

    String inUseBy = NONE;
    int empsInBathroom = 0;
    Semaphore maxEmps = new Semaphore(3);

    void useBathroom(String name) throws InterruptedException {
        System.out.println("\n" + name + " using bathroom. Current employees in bathroom = " + empsInBathroom + " " + System.currentTimeMillis());
        Thread.sleep(3000);
        System.out.println("\n" + name + " done using bathroom " + System.currentTimeMillis());
    }

    void maleUseBathroom(String name) throws InterruptedException {

        synchronized (this) {
            while (inUseBy.equals(WOMEN)) {
                this.wait();
            }
            maxEmps.acquire();
            empsInBathroom++;
            inUseBy = MEN;
        }

        useBathroom(name);
        maxEmps.release();

        synchronized (this) {
            empsInBathroom--;

            if (empsInBathroom == 0) inUseBy = NONE;
            this.notifyAll();
        }
    }

    void femaleUseBathroom(String name) throws InterruptedException {

        synchronized (this) {
            while (inUseBy.equals(MEN)) {
                this.wait();
            }
            maxEmps.acquire();
            empsInBathroom++;
            inUseBy = WOMEN;
        }

        useBathroom(name);
        maxEmps.release();

        synchronized (this) {
            empsInBathroom--;

            if (empsInBathroom == 0) inUseBy = NONE;
            this.notifyAll();
        }
    }

    public static void runTest() throws InterruptedException {

        final UnisexBathroomAnyLimit unisexBathroom = new UnisexBathroomAnyLimit();

        Thread female1 = new Thread(new Runnable() {
            public void run() {
                try {
                    unisexBathroom.femaleUseBathroom("Lisa");
                } catch (InterruptedException ie) {

                }
            }
        });

        Thread male1 = new Thread(new Runnable() {
            public void run() {
                try {
                    unisexBathroom.maleUseBathroom("John");
                } catch (InterruptedException ie) {

                }
            }
        });

        Thread male2 = new Thread(new Runnable() {
            public void run() {
                try {
                    unisexBathroom.maleUseBathroom("Bob");
                } catch (InterruptedException ie) {

                }
            }
        });

        Thread male3 = new Thread(new Runnable() {
            public void run() {
                try {
                    unisexBathroom.maleUseBathroom("Anil");
                } catch (InterruptedException ie) {

                }
            }
        });

        Thread male4 = new Thread(new Runnable() {
            public void run() {
                try {
                    unisexBathroom.maleUseBathroom("Wentao");
                } catch (InterruptedException ie) {

                }
            }
        });

        female1.start();
        male1.start();
        male2.start();
        male3.start();
        male4.start();

        female1.join();
        male1.join();
        male2.join();
        male3.join();
        male4.join();

    }
}

/*
 * Now we need to incorporate the logic of limiting the number of employees of a given gender that can be in
 * the bathroom at the same time. Limiting access, intuitively leads one to use a semaphore. A semaphore would
 * do just that - limit access to a fixed number of threads, which in our case is 3.
 * 
 * */
class UnisexBathroomSemaphore {

    static String WOMEN = "women";
    static String MEN = "men";
    static String NONE = "none";

    String inUseBy = NONE;
    int empsInBathroom = 0;
    Semaphore maxEmps = new Semaphore(3);

    void useBathroom(String name) throws InterruptedException {
        System.out.println("\n" + name + " using bathroom. Current employees in bathroom = " + empsInBathroom + " " + System.currentTimeMillis());
        Thread.sleep(3000);
        System.out.println("\n" + name + " done using bathroom " + System.currentTimeMillis());
    }

    void maleUseBathroom(String name) throws InterruptedException {

        synchronized (this) {
            while (inUseBy.equals(WOMEN)) {
                this.wait();
            }
            maxEmps.acquire();
            empsInBathroom++;
            inUseBy = MEN;
        }

        useBathroom(name);
        maxEmps.release();

        synchronized (this) {
            empsInBathroom--;

            if (empsInBathroom == 0) inUseBy = NONE;
            this.notifyAll();
        }
    }

    void femaleUseBathroom(String name) throws InterruptedException {

        synchronized (this) {
            while (inUseBy.equals(MEN)) {
                this.wait();
            }
            maxEmps.acquire();
            empsInBathroom++;
            inUseBy = WOMEN;
        }

        useBathroom(name);
        maxEmps.release();

        synchronized (this) {
            empsInBathroom--;

            if (empsInBathroom == 0) inUseBy = NONE;
            this.notifyAll();
        }
    }

    public static void runTest() throws InterruptedException {

        final UnisexBathroomSemaphore unisexBathroom = new UnisexBathroomSemaphore();

        Thread female1 = new Thread(new Runnable() {
            public void run() {
                try {
                    unisexBathroom.femaleUseBathroom("Lisa");
                } catch (InterruptedException ie) {

                }
            }
        });

        Thread male1 = new Thread(new Runnable() {
            public void run() {
                try {
                    unisexBathroom.maleUseBathroom("John");
                } catch (InterruptedException ie) {

                }
            }
        });

        Thread male2 = new Thread(new Runnable() {
            public void run() {
                try {
                    unisexBathroom.maleUseBathroom("Bob");
                } catch (InterruptedException ie) {

                }
            }
        });

        Thread male3 = new Thread(new Runnable() {
            public void run() {
                try {
                    unisexBathroom.maleUseBathroom("Anil");
                } catch (InterruptedException ie) {

                }
            }
        });

        Thread male4 = new Thread(new Runnable() {
            public void run() {
                try {
                    unisexBathroom.maleUseBathroom("Wentao");
                } catch (InterruptedException ie) {

                }
            }
        });

        female1.start();
        male1.start();
        male2.start();
        male3.start();
        male4.start();

        female1.join();
        male1.join();
        male2.join();
        male3.join();
        male4.join();

    }
}