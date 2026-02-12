package javaconcepts.multithreading.IntQuestions;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
* 
* Problem Statement

Suppose there are two threads t1 and t2. t1 prints Foo and t2 prints Bar. You are required to write a program which takes a user input n. Then the two threads print Foo and Bar alternately n number of times. The code for the class is as follows:

class PrintFooBar {··
    
    public void PrintFoo() {· · 
        for (int i = 1 i <= n;··i++){
        System.out.print("Foo");····
        }··
    }

    public void PrintBar() {· · 
        for (int i = 1; i <= n; i++) {· · · 
        System.out.print("Bar");· · 
        }··
    }
}

The two threads will run sequentially. You have to synchronize the two threads so that the functions PrintFoo() and PrintBar() are executed in an order. 
* */
public class PrintingFooBarnTimes {
    public static void main(String[] args) {

        PrintingFooBarnTimes printingFooBarnTimes = new PrintingFooBarnTimes();
        printingFooBarnTimes.m2(4);
    }

    private void m1(int n) {
        PrintFooBar printFooBar = new PrintFooBar(n);
        // Since its specifically mentioned that there are 1 threads we will use 2 threads.
        Thread t1 = new Thread(printFooBar::printFoo);
        Thread t2 = new Thread(printFooBar::printBar);

        t1.start();
        t2.start();
    }


    /*
     * Here, we have used reentrantLock with 2 condition once for printing foo and
     * other for printing bar. Its like there are 2 waiting rooms not, one for task foo
     * other for task bar.
     * When Flag is 0 foo is printed and barC is signalled to proceed and flag is switched.
     * similarly happens in print Bar.
     * */
    private void m2(int n) {
        PrintFooBarReentrantCondition printFooBar = new PrintFooBarReentrantCondition(n);
        // Since its specifically mentioned that there are 1 threads we will use 2 threads.
        Thread t1 = new Thread(printFooBar::printFoo);
        Thread t2 = new Thread(printFooBar::printBar);

        t1.start();
        t2.start();
    }
}

class PrintFooBar {
    int n;

    PrintFooBar(int n) {
        this.n = n;
    }

    public void printFoo() {
        try {
            for (int i = 0; i < n; i++) {
                synchronized (this) {
                    System.out.print("Foo");
                    notifyAll();
                    wait();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void printBar() {
        try {
            for (int i = 0; i < n; i++) {
                synchronized (this) {
                    wait();
                    System.out.println("Bar");
                    notifyAll();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class PrintFooBarReentrantCondition {
    int n;
    int flag;
    Lock reentrantLock = new ReentrantLock();
    Condition fooC = reentrantLock.newCondition();
    Condition barC = reentrantLock.newCondition();


    PrintFooBarReentrantCondition(int n) {
        this.n = n;
        this.flag = 0;
    }

    public void printFoo() {
        try {
            for (int i = 0; i < n; i++) {
                reentrantLock.lock();
                while (flag == 1)
                    fooC.await();
                System.out.print("Foo");
                flag = 1;
                barC.signal();
                reentrantLock.unlock();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void printBar() {
        try {
            for (int i = 0; i < n; i++) {
                reentrantLock.lock();
                while (flag == 0)
                    barC.await();
                System.out.println("Bar");
                flag = 0;
                fooC.signal();
                reentrantLock.unlock();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}