package javaconcepts.multithreading.IntQuestions;

/*
* Problem Statement

Suppose we are given a number _n_based on which a program creates the series 010203…0n. 
* There are three threads t1, t2 and t3 which print a specific type of number from the series. 
* \t1 only prints zeros, t2 prints odd numbers and t3 prints even numbers from the series. The code for the class is given as follows:

class PrintNumberSeries {··
    
    public PrintNumberSeries(int n) {
        this.n = n;· · 
    }

    public void PrintZero() {· · 
    }
    public void PrintOdd() {· · 
    }
    public void PrintEven() {· · 
    }

}

You are required to write a program which takes a user input n and outputs the number series using three threads. 
* The three threads work together to print zero, even and odd numbers. The threads should be synchronized so 
* that the functions PrintZero(), PrintOdd() and PrintEven() are executed in an order.
* */
public class PrintingNumberSeries {
}
