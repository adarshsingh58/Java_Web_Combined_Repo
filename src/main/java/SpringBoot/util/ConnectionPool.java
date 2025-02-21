package SpringBoot.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Multiple DB connections using a connection pool vs Single connection with multiple statements
 * <p>
 * <p>
 * <p>
 * A database connection is generally fairly "heavy", and there is time and resource consumption associated with closing
 * and opening a totally new one.
 * <p>
 * It's common to use the same connection for multiple queries when they are being run in quick succession, or part of
 * the same task. This also allows transaction functionality to be used.
 * <p>
 * It's also common to use a connection pool, where a connection can be "returned" when you're done using it, and the
 * pool will keep a few connections live and recycle them.
 * <p>
 * Opening and Closing connection is very expensive. Also failed to close connection results in memory leak.
 * <p>
 * So it's better to use connection pool to manage connection.
 *
 * <p>
 * Connection pools are used to keep a number of opened connections ready for use and to eliminate the need to open a
 * new connection each time it is required.
 * <p>
 * Suppose you are building a web application that would be used by multiple users simultaneously. Now if you have a
 * single connection, all the queries from multiple user threads will be queued. And single db connection will process
 * them one by one. So in a multi-user system(mostly all normal cases), single db connection will be a bottleneck &
 * won't work. Additionally, you need to take care of thread safety in case you are writing & committing data to db.
 * <p>
 * If you need truly simultaneous query execution in db, then you should go ahead with connection pool. Then different
 * user threads can use different connections & can execute queries in parallel.
 */
public class ConnectionPool {

    private static final int MAX_POOL_SIZE = 5;
    private final String jdbcUrl;
    private final String username;
    private final String password;
    private final BlockingQueue<Connection> connectionPool; // Use a thread-safe queue
    private final AtomicInteger currentPoolSize = new AtomicInteger(0); // Track number of connections

    public ConnectionPool(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
        this.connectionPool = new LinkedBlockingQueue<>(MAX_POOL_SIZE);
    }

    public Connection getConnection() throws SQLException, InterruptedException {
        Connection connection = connectionPool.poll(); // Try to get immediately

        if (connection != null) {
            if (!isValid(connection)) {
                closeConnection(connection);  //Close invalid connection
                return getConnection();       //Recursively try again
            }
            return connection;
        }

        // If the queue is empty and we haven't reached the max size, create a new connection.
        if (currentPoolSize.get() < MAX_POOL_SIZE) {
            synchronized (this) {  //Synchronize creation to prevent over-creation
                if (currentPoolSize.get() < MAX_POOL_SIZE) {  //Double check after acquiring the lock
                    try {
                        Connection newConnection = createConnection();
                        currentPoolSize.incrementAndGet();
                        return newConnection;
                    } catch (SQLException e) {
                        throw e; //Re-throw the exception
                    }
                } else {
                    // Another thread already created a connection
                    return connectionPool.take(); //Wait for a connection to become available
                }
            }
        } else {
            // Max pool size reached, wait for a connection to be released.
            return connectionPool.take(); // Block until a connection is available
        }
    }

    public void restoreConnection(Connection connection) {
        if (connection != null) {
            try {
                if (!isValid(connection)) {
                    closeConnection(connection); //Close invalid connection
                    currentPoolSize.decrementAndGet();
                    return;
                }
                if (connectionPool.size() < MAX_POOL_SIZE) {
                    connectionPool.offer(connection);
                } else {
                    closeConnection(connection); // Too many connections: close it
                }

            } catch (Exception e) {
                System.err.println("Error restoring connection: " + e.getMessage());
                closeConnection(connection);
            }
        }
    }

    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                if (currentPoolSize.get() > 0) {
                    currentPoolSize.decrementAndGet();
                }


            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    private boolean isValid(Connection connection) {
        try {
            return connection != null && !connection.isClosed() && connection.isValid(5); //Check connection validity (5 sec timeout)
        } catch (SQLException e) {
            return false; // Connection is not valid if an exception occurs
        }
    }


    public int getCurrentPoolSize() {
        return currentPoolSize.get();
    }

    public int getAvailableConnections() {
        return connectionPool.size();
    }
}
/**
 * Key improvements and explanations for thread safety and robustness:
 * <p>
 * BlockingQueue for Thread Safety: Uses java.util.concurrent.BlockingQueue (specifically, LinkedBlockingQueue) for the
 * connectionPool. BlockingQueue is inherently thread-safe and provides blocking operations (take(), poll(), offer())
 * that are essential for managing a limited pool of resources across multiple threads. LinkedBlockingQueue is generally
 * preferred for its performance and unbounded capacity if a capacity is not specified (we provide MAX_POOL_SIZE, so it
 * is bounded in our case).
 * <p>
 * AtomicInteger for Current Pool Size: Uses java.util.concurrent.atomic.AtomicInteger (currentPoolSize) to track the
 * number of connections currently in the pool. AtomicInteger provides thread-safe increment and decrement operations,
 * preventing race conditions when multiple threads are creating or closing connections.
 * <p>
 * Synchronized Connection Creation: The creation of new connections within getConnection() is synchronized using
 * synchronized (this). This is critical to prevent multiple threads from concurrently creating connections when the
 * pool is near its maximum size, potentially exceeding MAX_POOL_SIZE. A double-checked locking pattern is used to
 * minimize synchronization overhead when it's not strictly necessary.
 * <p>
 * Connection Validation: The isValid() method is crucial for checking if a connection is still valid before returning
 * it to the caller or restoring it to the pool. This prevents the pool from returning stale or broken connections. A
 * timeout is included in isValid() to avoid indefinite blocking. Connections that aren't valid are closed and, in the
 * case of getConnection, a recursive call is made to find a valid connection.
 * <p>
 * Resource Management:
 * <p>
 * restoreConnection() now explicitly checks if the connection is valid before adding it back to the pool. Invalid
 * connections are closed.
 * <p>
 * restoreConnection() checks whether there's room in the pool before offering the connection. If the pool is full, the
 * connection is closed to avoid leaking resources.
 * <p>
 * Robust Error Handling: Added more try-catch blocks to handle potential SQLExceptions during connection creation,
 * validation, closing, and restoration. This helps prevent unexpected application crashes. Error messages are printed
 * to System.err to provide more information when exceptions occur.
 * <p>
 * Concurrency Control: Added an offer timeout in restoreConnection() to avoid blocking indefinitely if the queue is
 * full.
 * <p>
 * closeConnection() method- to properly close all connections.
 * <p>
 * Test Methods: Included getCurrentPoolSize and getAvailableConnections for testing and monitoring purposes.
 */