package javaconcepts.multithreading;

/**
 * Most of the compute devices we have today have more than one core. So, a typical desktop computer has four cores, and
 * on the servers, we generally have 16 cores or 32 cores or more. To take advantage of this much compute power, we
 * create multiple parallel threads in our application. So we can do that using new threat or start your thread pool of
 * up join pool and so on and so forth in Java. But creating too many parallel threads can cause a problem, and that is
 * because of how  java works. In Java, every thread that you create is actually an operating system thread, which is
 * also called as native thread or kernel thread. So Java itself will have variables for every thread like program
 * counter, Java stacks, the stack frames, and so on and so forth. But what every thread there will be a corresponding
 * OS thread, which consumes a lot of memory. That limits the number of parallel threads on number of active threads
 * that you can have in your JVM, that is, in your application. So you cannot have tens of thousands of active threads
 * in Java. It will throw an out of memory exception, and your program will shut down. And once you have too many
 * threads, there are other problems that come up. So let's say you have a lot of threads, and you have a CPU which has
 * only two cores. So every core will have some local cache. And let's say this core one is running a version of thread
 * one, and the local cache has all the data which is required by thread one. Now if there are a lot of threads, that
 * means you have to schedule some other thread at some point in time. So let's say here you want to schedule thread
 * three. For that, you'll have to plus the cache, that is, you have to move on the data which belong to thread one, and
 * you have to put all the data which will be required by thread three. And then the core one can remove or stop thread
 * one and can start the thread three. Again, when there is a context switch, when it switches back to thread one, again
 * it has to reverse that operation of flushing the local cache, removing all the data for thread three and adding the
 * data for thread one back. That is called Data Locality issues. Also there would be Data Scheduling overhead on CPU
 * when having too many threads.
 * <p>
 * This issue of having too many threads is even more heightened when you're trying to do I/O operations. So let's say I
 * have a main thread, and it's trying to do some operation related to the I/O. It could be a file I/O operation, it
 * could be a network operation. Within the network itself, you can have some HTTP call to a microservice, or you can
 * have some database operation done. Now since this is a network operation or a file I/O operation, it's going to take
 * some time. And when it triggers that particular operation, the main thread will go into a wait state until that
 * operation is completed. The main thread cannot do anything else, and all your CPU cycles are wasted. Once the network
 * I/O or the net file I/O is completed, then the thread goes back into a runnable state, and it can process the data
 * that was returned by this I/O operation. And this problem of having a blocking thread, which does not do any other
 * operations while that operation is being performed, limits your capacity to scale your I/O in your apps. So you
 * cannot have thousands and thousands of apps doing I/O operations because every one of them will block, and your CPU
 * is not being used efficiently. So what you ideally want is a non-blocking I/O Processing facility. So what you want
 * is your method triggering the operation and giving it a method saying that, "Okay, you perform the operation, and
 * whenever you are ready with the result, with the data, you call this callback method in a separate thread." Once you
 * have triggered that operation, your main thread will not block, and it can keep on performing the subsequent
 * operations. This is what the ideal scenario would be. Secondly, there
 *
 */
public class Eight_AsyncProgramming {


    public static void main(String[] args) {

    }

    /*
     * synchronous API is nothing but the callback , where we take the same
     * example of fetching the employee, then fetching the tax rate, and then doing the subsequent operations. But
     *  */
    private void syncDemo() {
       /* for (Integer id : employeelds) {
        Future<Employee> future = service.submit(new EmployeeFetcher(id));// Step 1: Fetch Employee details from DB
        Employee emp = future.get(); // blocking
        Future<TaxRate> rateFuture = service.submit(new TaxRateFetcher(emp));// Step 2: Fetch Employee tax rate from REST service
        TaxRate taxRate = rateFuture.get(); // blocking
        BigDecimal tax = calculateTax(emp, taxRate);// Step 3: Calculate current year tax
        service.submit(new SendEmail(emp, tax));// Step 4: Send email to employee using REST service
    }*/
    }

    /*
     * Here
     * we are using CompletableFuture for it. In CompletableFuture, we say supplyAsync fetch the employee for that
     * particular ID. Once you have fetched that employee, apply the fetch tax rate. Once you have done the fetch tax
     * rate, apply the calculate tax, and so on and so forth. Here, the main thread will not stop for any of the
     * operation to be performed, just an algorithm being mentioned to the CompletableFuture. So this for loop will be
     * completed very quickly. And behind the scenes, it will be the responsibility of CompletableFuture to maintain all
     * the scheduling of the tasks on particular threads, which is generally using ForkJoinPool. And this concept of
     * having callbacks, so these are the methods that you are sending it out for mentioning the algorithm that needs to
     * be run, and then chaining of those callbacks, it's very similar to what we do in JavaScript. So that is what is
     * the difference between a synchronous API, where you could do future.get which is a blocking operation, and an
     * asynchronous API where you pass in the callback methods, where you pass in the algorithms for the next steps to
     * be performed, and then internally the platform or the framework can take care of scheduling the threads for the
     * non-blocking I/O part. In Java 7, Java introduced a concept of non-blocking I/O, which is also called as the NIO
     * package. Here, there are classes like channels and selectors. We'll not go into the detail of it, but it exposes
     * a low-level API for operating in an asynchronous and a non-blocking manner for files or sockets, which is the
     * network operations. Like before, where we talk about an asynchronous API, here also it works on the basis of
     * callbacks.
     *
     */
    private void asyncDemo() {
       /* for (Integer id : employeelds) {
            CompletableFuture.supplyAsync(() > fetchEmployee(id))
                        •thenApplyAsync(employee -> fetchTaxRate(employee))
                        •thenApplyAsync(taxRate -> calculateTax(taxRate))
                    .thenAcceptAsyne(taxValue > sendEmail(taxValue));
        }*/
    }
}
