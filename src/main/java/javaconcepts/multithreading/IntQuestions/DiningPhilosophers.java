package javaconcepts.multithreading.IntQuestions;

/**
 * Problem Statement
 * <p>
 * This is a classical synchronization problem proposed by Dijkstra.
 * <p>
 * Imagine you have five philosopher’s sitting on a roundtable. The philosopher’s do only two kinds of activities. One
 * they contemplate, and two they eat. However, they have only five forks between themselves to eat their food with.
 * Each philosopher requires both the fork to his left and the fork to his right to eat his food. The arrangement of the
 * philosophers and the forks are shown in the diagram. Design a solution where each philosopher gets a chance to eat
 * his food without causing a deadlock.
 * <p>
 * For no deadlock to occur at all and have all the philosopher be able to eat, we would need ten forks, two for each
 * philosopher. With five forks available, at most, only two philosophers will be able to eat while letting a third
 * hungry philosopher to hold onto the fifth fork and wait for another one to become available before he can eat. Think
 * of each fork as a resource that needs to be owned by one of the philosophers sitting on either side. Let’s try to
 * model the problem in code before we even attempt to find a solution. Each fork represents a resource that two of the
 * philosophers on either side can attempt to acquire. This intuitively suggests using a semaphore with a permit value
 * of 1 to represent a fork. Each philosopher can then be thought of as a thread that tries to acquire the forks to the
 * left and right of it.
 *
 */
public class DiningPhilosophers {
}
