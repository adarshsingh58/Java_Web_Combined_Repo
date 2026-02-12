package javaconcepts.multithreading.IntQuestions;


/*
 * Merge Sort
 * Merge sort is a typical text-book example of a recursive algorithm and the poster-child of the divide and conquer strategy. 
 * The idea is very simple, we divide the array into two equal parts, sort them recursively and then combine the two sorted arrays. 
 * The base case for recursion occurs when the size of the array reaches a single element. An array consisting of a single element is already sorted.
 * The running time for a recursive solution is expressed as a recurrence equation. An equation or inequality that describes a 
 * function in terms of its own value on smaller inputs is called a recurrence equation. The running time for a recursive algorithm 
 * is the solution to the recurrence equation. The recurrence equation for recursive algorithms usually takes on the following form:
 *
 * Running Time = Cost to divide into n subproblems + n * Cost to solve each of the n problems + Cost to merge all n problems
 * In the case of merge sort, we divide the given array into two arrays of equal size, i.e. we divide the original problem into sub-problems to be solved recursively.
 * Following is the recurrence equation for merge sort.
 * Running Time = Cost to divide into 2 unsorted arrays + 2 * Cost to sort half the original array + Cost to merge 2 sorted arrays
 *
 *     T(n)=Cost to divide into 2 unsorted arrays+2∗T(n2)+Cost to merge 2 sorted arrayswhenn>1
 *     T(n)=Costtodivideinto2unsortedarrays+2∗T(2n​)+Costtomerge2sortedarrayswhenn>1
 *
 *     T(n)=O(1)  when n=1
 *     T(n)=O(1)whenn=1
 *
 * Remember the solution to the recurrence equation will be the running time of the algorithm on an input of size n. 
 * Without getting into the details of how we’ll solve the recurrence equation, the running time of merge sort is   O(nlgn)
 * where n is the size of the input array.
 * 
 * */
public class MultithreadedMergeSort {
}
