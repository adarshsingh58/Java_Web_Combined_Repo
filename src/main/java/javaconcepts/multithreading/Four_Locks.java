package javaconcepts.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;
/**
 * Synchronized vs Reentrant vs ReentrantReadWrite vs ReentractConditionalLock vs Semaphores 
 * */
public class Four_Locks {
    public static void main(String[] args) {
        Four_Locks instance = new Four_Locks();
        new Thread(() -> instance.accessResourceSync()).start();
        new Thread(() -> instance.accessResourceSync()).start();
        new Thread(() -> instance.accessResourceSync()).start();


        new Thread(() -> instance.accessResourceLock()).start();
        new Thread(() -> instance.accessResourceLock()).start();
        new Thread(() -> instance.accessResourceLock()).start();


        new Thread(() -> instance.accessResource_RWLock()).start();
        new Thread(() -> instance.accessResource_RWLock()).start();
        new Thread(() -> instance.accessResource_RWLock()).start();
    }

    private ReentrantLock reLock = new ReentrantLock();

    private void accessResourceSync() {
        synchronized (this) {// equivalent to reLock.lock()

        }// equivalent to reLock.unlock()

    }

    //Locks are implicit and allow locking and unlocking in any order
    private void accessResourceLock() {
        reLock.lock();
        try {

        } finally {
            reLock.unlock();// this should be inside finally, coz if there is an exception after acquiring a lock the lock must be removed for other threads to use
        }
    }

    private void accessResource_WithTryLock() {
        boolean gotTheLock = reLock.tryLock();
        try {
            if (gotTheLock) {

            } else {

            }

        } finally {
            reLock.unlock();// this should be inside finally, coz if there is an exception after acquiring a lock the lock must be removed for other threads to use
        }
    }

    private ReentrantReadWriteLock reReadWriteLock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = reReadWriteLock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = reReadWriteLock.writeLock();

    /*
    * ReadLock and WriteLock though are 2 separate instances only 1 will be allowed at a time...
    Either
    ReadLock is being used (by n threads)
    OR
    WriteLock is being used (by 1 thread)
    But never both at same time
    * */
    private void accessResource_RWLock() {
        readLock.lock();
        try {

        } finally {
            readLock.unlock();// this should be inside finally, coz if there is an exception after acquiring a lock the lock must be removed for other threads to use
        }

        writeLock.lock();
        try {

        } finally {
            writeLock.unlock();// this should be inside finally, coz if there is an exception after acquiring a lock the lock must be removed for other threads to use
        }
    }

    public void semaphoreLock() {
        Semaphore semaphore = new Semaphore(3);
        ExecutorService service = Executors.newFixedThreadPool(10);
        IntStream.of(100).forEach(i -> {
            service.execute(() -> {
                //some processing
            semaphore.acquireUninterruptibly();
            // Slow IO calls or restricted calls
            semaphore.release();
            //other processing
            });
        });
    }
}
