package javaconcepts.multithreading.IntQuestions;

/*
 * Problem Statement
 * <p>
 * FizzBuzz is a common interview problem in which a program prints a number series from 1 to n such that for every
 * number that is a multiple of 3 it prints “fizz”, for every number that is a multiple of 5 it prints “buzz” and for
 * every number that is a multiple of both 3 and 5 it prints “fizzbuzz”. We will be creating a multi-threaded solution
 * for this problem. Suppose we have four threads t1, t2, t3 and t4. Thread t1 checks if the number is divisible by 3
 * and prints fizz. Thread t2 checks if the number is divisible by 5 and prints buzz. Thread t3 checks if the number is
 * divisible by both 3 and 5 and prints fizzbuzz. Thread t4 prints numbers that are not divisible by 3 or 5.
 *
 * The code for the class is as follows:
 *
 * class MultithreadedFizzBuzz {
 *
 *     private int n;
 *
 *     public MultithreadedFizzBuzz(int n) {
 *         this.n = n;
 *     }
 *
 *     public void fizz() {
 *         System.out.print("fizz");
 *     }
 *
 *     public void buzz() {
 *         System.out.print("buzz");
 *     }
 *
 *     public void fizzbuzz() {
 *         System.out.print("fizzbuzz");
 *     }
 *
 *     public void number(int num) {
 *         System.out.print(num);
 *     }
 * }
 *
 * For an input integer n, the program should output a string containing the words fizz, buzz and fizzbuzz representing certain
 * numbers. For example, for n = 15, the output should be: 1, 2, fizz, 4, buzz, fizz, 7, 8, fizz, buzz, 11, fizz, 13, 14, fizzbuzz.
 */
public class FizzBuzzProblem {
}

class MultithreadedFizzBuzz {

    private int n;
    private int num = 1;

    public MultithreadedFizzBuzz(int n) {
        this.n = n;
    }

    public synchronized void fizz() throws InterruptedException {
        while (num <= n) {
            if (num % 3 == 0 && num % 5 != 0) {
                System.out.println("fizz");
                num++;
                notifyAll();
            } else {
                wait();
            }
        }
    }

    public synchronized void buzz() throws InterruptedException {
        while (num <= n) {
            if (num % 3 != 0 && num % 5 == 0) {
                System.out.println("buzz");
                num++;
                notifyAll();
            } else {
                wait();
            }
        }
    }

    public synchronized void fizzbuzz() throws InterruptedException {
        while (num <= n) {
            if (num % 15 == 0) {
                System.out.println("fizzbuzz");
                num++;
                notifyAll();
            } else {
                wait();
            }
        }
    }

    public synchronized void number() throws InterruptedException {
        while (num <= n) {
            if (num % 3 != 0 && num % 5 != 0) {
                System.out.println(num);
                num++;
                notifyAll();
            } else {
                wait();
            }
        }
    }
}