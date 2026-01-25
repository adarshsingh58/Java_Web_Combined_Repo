package javaconcepts.multithreading.IntQuestions;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Problem Statement
 * <p>
 * This is an actual interview question asked at Uber and Oracle. Imagine you have a bucket that gets filled with tokens
 * at the rate of 1 token per second. The bucket can hold a maximum of N tokens. Implement a thread-safe class that lets
 * threads get a token when one is available. If no token is available, then the token-requesting threads should block.
 * The class should expose an API called getToken that various threads can call to get a token.
 * <p>
 * Solution
 * <p>
 * This problem is a naive form of a class of algorithms called the “token bucket” algorithms. A complimentary set of
 * algorithms is called “leaky bucket” algorithms. One application of these algorithms is shaping network traffic flows.
 * This particular problem is interesting because the majority of candidates incorrectly start with a multithreaded
 * approach when taking a stab at the problem. One is tempted to create a background thread to fill the bucket with
 * tokens at regular intervals but there is a far simpler solution devoid of threads and a message to make judicious use
 * of threads. This question tests a candidate’s comprehension prowess as well as concurrency knowledge. The key to the
 * problem is to find a way to track the number of available tokens when a consumer requests for a token. Note the rate
 * at which the tokens are being generated is constant. So if we know when the token bucket was instantiated and when a
 * consumer called getToken() we can take the difference of the two instants and know the number of possible tokens we
 * would have collected so far. However, we’ll need to tweak our solution to account for the max number of tokens the
 * bucket can hold.
 *
 */
public class RateLimitingUsingTokenBucketFilter {

    public static void main(String[] args) throws InterruptedException {
        RateLimitingUsingTokenBucketFilter instance = new RateLimitingUsingTokenBucketFilter();
        instance.m2();

    }

    /*
     * Bucket Object: Thinking from OOPS perspective, we need an Object Bucket which has properties
     * <CANCEL THIS APPROACH>
     * tokensList and size. Since getToken doesnt talk about ordered fetch, we may or may not keep track of indexes. Its behaviour
     * would include addTokens(TokenValue) and getTokens(). addTokens will be invoked once every second and getToken can
     * be invoked any number of times with any frequency.
     * </CANCEL THIS APPROACH>
     * currentAvailableTokens, lastSyncTime and maxTokens. we are only concerned here with adding Tokens and removing them, not with the actual tokens
     * so no need to keep track of tokens. From behavior's perspective we would have getToken only. We can assume that
     * producer is producing 1 token/sec.
     * How lastSyncTime Functions:
     * Instead of having a separate background thread constantly adding tokens, the bucket calculates its current state only at the moment a thread actually asks for a token.
     * Initialization: At the start, lastSyncTime is set to the current time. The bucket is effectively "frozen" at that moment.
     * The Sync Event: When getToken() is called, the code calculates the difference between Now and lastSyncTime.
     * Updating the Anchor: Once you calculate how many tokens to add, you "sync" the bucket by setting lastSyncTime to the current time (or the time represented by the added tokens).
     *
     * Key Concept Check
     * lastSyncTime: This acts as a moving anchor. It ensures we only count "new" time.
     * currentTokens: This is your actual bucket. It grows with time and shrinks when you call the method.
     *
     */
    private void m1() throws InterruptedException {
        // Initialize bucket with a capacity of 5
        BucketSingleThreaded bucket = new BucketSingleThreaded(5);

        // Test 1: Immediate request (should fail if starting at 0)
        System.out.println("Time 0s:");
        bucket.getToken();

        // Test 2: Wait 2 seconds
        Thread.sleep(2050); // Small buffer to ensure the clock crosses the second mark
        System.out.println("\nAfter 2s:");
        bucket.getToken(); // Should succeed
        bucket.getToken(); // Should succeed
        bucket.getToken(); // Should fail (only 2 tokens generated)

        // Test 3: Wait 10 seconds to test Capacity Cap
        System.out.println("\nWaiting 10s for capacity test...");
        Thread.sleep(10050);
        // Even though 10s passed, capacity is 5. We can only take 5 tokens.
        for (int i = 0; i < 7; i++) {
            bucket.getToken();
        }
    }

    /*
     * m1() would work fine for single threaded cases but now if there are multiple consumers then we need to handle concurrent calls.
     *   - Many getToken may be triggered at the same time.
     *   - our logic depends on updating lastSyncTime, so this var must be atomic or synchronized. While making them AtomicLong would make
     *     the variables themselves thread-safe, it wouldn't fix the Race Condition between calculating the time and updating the tokens.
     *     They need to be updated as a single atomic unit of work.
     *   - similarly currentAvailableTokens is updated based on >0 check so this logic should also be executed by 1 thread at a time.
     * Straight forward fix is to make getToken() synchronized but that would defeat making the consumer multithreaded, since only 1
     * item is being taken out of bucket at time if we sync entire method. Also requirement is that if no tokens are present, the getToken
     * API should be waiting/blocked instead of returning or continuous polling so we need a signaling system. Instead of syncing entire method,
     * we would sync the block where Update is happening.
     *
     * - What if i add a wait() in else of if (currentAvailableTokens > 0) and call notify() after 1 sec?
     * using wait() to park threads when the bucket is empty is correct but for notify its wrong.
     * the "1 token per second" is a rate, but tokens can be requested at any millisecond. The notification should ideally happen as soon as the math says tokens > 0.
     *
     * Instead of a fixed 1-second notification, you want to use the passage of time to determine how long a thread should wait.
     * Calculation: If the bucket is empty, the thread can calculate exactly how many milliseconds are left until the next token is generated.
     * Timed Wait: You can use wait(timeout) where the timeout is that calculated millisecond value. This way, the thread wakes itself up exactly when a token is due.
     *
     * */
    private void m2() throws InterruptedException {
        // 1. Initialize bucket with capacity of 1 to force strict 1-second gaps
        final BucketMultiThreaded bucket = new BucketMultiThreaded(1);

        // 2. Create a pool of 5 threads
        ExecutorService executor = Executors.newFixedThreadPool(5);

        System.out.println("Starting test at: " + System.currentTimeMillis() / 1000);

        // 3. Submit 5 tasks simultaneously
        for (int i = 1; i <= 5; i++) {
            executor.submit(() -> {
                try {
                    bucket.getToken();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // 4. Shutdown gracefully
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("Test finished at: " + System.currentTimeMillis() / 1000);
    }

    /*
     * The previous solution consisted of manipulating pointers in time, thus avoiding threads altogether.
     * Another solution is to use threads to solve the token bucket filter problem. We instantiate one thread
     * to add a token to the bucket after every one second. The user thread invokes the getToken() method
     * and is granted one if available.
     * One simplification as a result of using threads is we now only need to remember the current number
     * of tokens held by the token bucket filter object. We'll add an additional method daemonThread()
     * that will be executed by the thread that adds a token every second to the bucket.
     *
     * The logic of the daemon thread is simple. It sleeps for one second, wakes up, checks if the number of
     * tokens in the bucket is less than the maximum allowed tokens, if yes increments the possibleTokens
     * variable and if not goes back to sleep for a second again.
     * The implementation of the getToken() is even simpler. The user thread checks if the number of tokens
     * is greater than zero, if yes it simulates taking away a token by decrementing the variable possibleTokens.
     * If the number of available tokens is zero then the user thread must wait and be notified only when the daemon
     * thread has added a token. We can use the current token bucket object this to wait and notify.
     *
     * */
    private void m3() throws InterruptedException {
        Set<Thread> allThreads = new HashSet<Thread>();
        BucketBackgroundThread tokenBucketFilter = new BucketBackgroundThread(5);

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                try {
                    tokenBucketFilter.getToken();
                } catch (InterruptedException ie) {
                    System.out.println("We have a problem");
                }
            });
            thread.setName("Thread_" + (i + 1));
            allThreads.add(thread);
        }

        for (Thread t : allThreads)
            t.start();

        for (Thread t : allThreads)
            t.join();
    }

    class BucketSingleThreaded {
        long lastRequestTime;
        long currentAvailableTokens;
        long maxTokens;

        BucketSingleThreaded(int maxTokens) {
            this.maxTokens = maxTokens;
            this.lastRequestTime = System.currentTimeMillis();
            this.currentAvailableTokens = 0;
        }

        public void getToken() {
            long now = System.currentTimeMillis();
            // 1. Calculate how many seconds passed since the LAST successful sync
            long secondsSinceLastSync = (now - lastRequestTime) / 1000;

            if (secondsSinceLastSync > 0) {
                // 2. Add tokens based on lapsed time, but cap at max capacity
                currentAvailableTokens = Math.min(maxTokens, currentAvailableTokens + secondsSinceLastSync);
                // 3. Move the sync time forward by the number of seconds we just "processed"
                lastRequestTime = lastRequestTime + (secondsSinceLastSync * 1000);
            }

            if (currentAvailableTokens > 0) {
                currentAvailableTokens--;
                System.out.println("Token removed. Current Bucket: " + currentAvailableTokens);
            } else {
                System.out.println("No Tokens available.");
            }
        }
    }

    class BucketMultiThreaded {
        long lastRequestTime;
        long currentAvailableTokens;
        long maxTokens;

        BucketMultiThreaded(int maxTokens) {
            this.maxTokens = maxTokens;
            this.lastRequestTime = System.currentTimeMillis();
            this.currentAvailableTokens = 0;
        }

        public synchronized void getToken() throws InterruptedException {
            // Step 1: Always try to refill based on time passed since last sync
            refill();

            // Step 2: If empty, wait until at least one token is available
            while (currentAvailableTokens == 0) {
                // Calculate how many ms until the next token is generated
                // Since rate is 1 token/1000ms:
                long nextTokenAt = lastRequestTime + 1000;
                long waitTime = nextTokenAt - System.currentTimeMillis();

                if (waitTime > 0) {
                    // Thread wakes itself up exactly when a token is due
                    this.wait(waitTime);
                }

                // Step 3: After waking up, we MUST refill again to update currentTokens
                refill();
            }

            currentAvailableTokens--;
            System.out.println(Thread.currentThread().getName() +
                    " took token. Remaining: " + currentAvailableTokens);
        }

        private void refill() {
            long now = System.currentTimeMillis();
            long secondsPassed = (now - lastRequestTime) / 1000;

            if (secondsPassed > 0) {
                currentAvailableTokens = Math.min(maxTokens, currentAvailableTokens + secondsPassed);
                // Move lastSyncTime forward by full seconds processed
                lastRequestTime = lastRequestTime + (secondsPassed * 1000);

                // Notify any other waiting threads that tokens might be available
                this.notifyAll();
            }
        }
    }

    class BucketBackgroundThread {
        private long possibleTokens = 0;
        private final int MAX_TOKENS;
        private final int ONE_SECOND = 1000;

        public BucketBackgroundThread(int maxTokens) {

            MAX_TOKENS = maxTokens;
            // Never start a thread in a constructor
            Thread dt = new Thread(this::daemonThread);
            dt.setDaemon(true);
            dt.start();
        }

        private void daemonThread() {

            while (true) {
                synchronized (this) {
                    if (possibleTokens < MAX_TOKENS) {
                        possibleTokens++;
                    }
                    this.notify();
                }
                try {
                    Thread.sleep(ONE_SECOND);
                } catch (InterruptedException ie) {
                    // swallow exception
                }
            }
        }

        void getToken() throws InterruptedException {

            synchronized (this) {
                while (possibleTokens == 0) {
                    this.wait();
                }
                possibleTokens--;
            }
            System.out.println(
                    "Granting " + Thread.currentThread().getName() + " token at " + System.currentTimeMillis() / 1000);
        }
    }

}
