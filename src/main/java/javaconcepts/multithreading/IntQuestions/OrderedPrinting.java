package javaconcepts.multithreading.IntQuestions;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
* Problem Statement

Suppose there are three threads t1, t2 and t3. t1 prints First, t2 prints Second and t3 prints Third. The code for the class is as follows:

public class OrderedPrinting {

    public void printFirst() {
       System.out.print("First"); 
    }
 
    public void printSecond() {
       System.out.print("Second");
    }

    public void printThird() {
       System.out.print("Third"); 
    }

}

Thread t1 calls printFirst(), thread t2 calls printSecond(), and thread t3 calls printThird(). The threads can run in any order. 
* You have to synchronize the threads so that the functions printFirst(), printSecond() and printThird() are executed in order.
* */
public class OrderedPrinting {

    public static void main(String[] args) {
        OrderedPrinting orderedPrinting = new OrderedPrinting();
        try {
            orderedPrinting.m4();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    /*
     * One obvioud solution is to join previous thread before starting the next one.
     * This way control would stay at t1.join() unless thread t1 is completed and then
     * process next line which is to start t2 thread and so on. Problem here is that this
     * is purely a synchronous processing, no point of multithreading.
     * */
    private void m1() throws InterruptedException {
        PrintInOrder instance = new PrintInOrder();
        Thread t1 = new Thread(instance::printFirst);
        Thread t2 = new Thread(instance::printSecond);
        Thread t3 = new Thread(instance::printThird);

        t1.start();
        t1.join();
        t2.start();
        t2.join();
        t3.start();
    }

    /*
     * Here because all the 3 method are synchronized that means the lock is present on the PrintInOrderSync
     * when these methods are invoked. This may give wrong result in case 2nd thread got the first lock.
     * eg if we add a Thread.sleep in printFirst, other threads would take over.
     * for this simple use case this is fine but if there is big computation in these methods the order
     * may not be gaurented.
     * */
    private void m2() throws InterruptedException {
        PrintInOrderSync instance = new PrintInOrderSync();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(instance::printFirst);
        executorService.submit(instance::printSecond);
        executorService.submit(instance::printThird);

        executorService.shutdown();
    }

    /*
     * Here our class  consists of a private variable; count. The class consists of 3 functions printFirst(),printSecond() and printThird().
     * In printFirst(), “First” is printed. We do not need to check the value of count here. After printing, count is incremented for
     * the next word to be printed. Any waiting threads are then notified via notifyAll(), signalling them to proceed.
     * In the second method, the value of count is checked. If it is not equal to 2, the calling thread goes into wait.
     * When the value of count reaches 2, the while loop is broken and “Second” is printed. The value of count is incremented for the
     * next number to be printed and notifyAll() is called.
     * The third method checks works in the same way as the second. The only difference being the check for count to be equal to 3.
     * If it is, then “Third” is printed otherwise the calling thread waits.
     * */
    private void m3() throws InterruptedException {

        PrintInOrderSyncWaitNotify instance = new PrintInOrderSyncWaitNotify();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(instance::printFirst);
        executorService.submit(instance::printSecond);
        executorService.submit(instance::printThird);

        executorService.shutdown();
    }

    /*
     * Here we are making use of LatchCountdown, a synchronization utility used to achieve concurrency.
     * It manages multithreading where a certain sequence of operations or tasks is required. Everytime a
     * thread finishes its work, countdown() is invoked, decrementing the counter by 1. Once this count
     * reaches zero, await() is notified and control is given back to the main thread that has been waiting for others to finish.
     * The basic structure of the class OrderedPrinting is the same as presented in solution 1 with the only difference of using
     * countdownlatch instead of volatile variable. We have 2 countdownlatch variables that get initialized with 1 each.
     * In printFirst() method, latch1 decrements and reaches 0, waking up the waiting threads consequently. printSecond() ,
     * if latch1 is free (reached 0), then the printing is done and latch2 is decremented. Similarly in the third method printThird(),
     * latch2 is checked and printing is done. The latches here act like switches/gates that get closed and opened for particular actions to pass.
     * */
    private void m4() throws InterruptedException {
        PrintInOrdersWithLatch instance = new PrintInOrdersWithLatch();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(instance::printFirst);
        executorService.submit(instance::printSecond);
        executorService.submit(instance::printThird);

        executorService.shutdown();
    }

}

class PrintInOrder {
    public void printFirst() {
        System.out.println("First");
    }

    public void printSecond() {
        System.out.println("Second");
    }

    public void printThird() {
        System.out.println("Third");
    }
}

class PrintInOrderSync {

    public synchronized void printFirst() {
        System.out.println("First");
    }

    public synchronized void printSecond() {
        System.out.println("Second");
    }

    public synchronized void printThird() {
        System.out.println("Third");
    }
}

class PrintInOrderSyncWaitNotify {

    int count;

    public PrintInOrderSyncWaitNotify() {
        count = 1;
    }

    public void printFirst() {

        synchronized (this) {
            System.out.println("First");
            count++;
            this.notifyAll();
        }
    }

    public void printSecond() {

        synchronized (this) {
            while (count != 2) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Second");
            count++;
            this.notifyAll();
        }

    }

    public void printThird() {

        synchronized (this) {
            while (count != 3) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Third");
        }
    }
}

class PrintInOrdersWithLatch {
    CountDownLatch latch1;
    CountDownLatch latch2;

    PrintInOrdersWithLatch() {
        latch1 = new CountDownLatch(1);
        latch2 = new CountDownLatch(1);
    }

    public void printFirst() {
        System.out.println("First");
        latch1.countDown();
    }

    public void printSecond() {
        try {
            latch1.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Second");
        latch2.countDown();
    }

    public void printThird() {
        try {
            latch2.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Third");
    }
}