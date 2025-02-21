package javaconcepts.singleton;

/**
 * The most popular approach is to implement a Singleton by creating a regular class and making sure it has:
 * <p>
 * a private constructor a static field containing its only instance a static factory method for obtaining the instance
 */
public class SingletonClass {
    private static SingletonClass instance;

    private SingletonClass() {
    }

    public static synchronized SingletonClass getInstance() {//The implementation with a private constructor that we presented above isn’t thread-safe. It works well in a single-threaded environment, but in a multi-threaded one, we should use the synchronization technique to guarantee the atomicity of the operation
        if (instance == null) {
            return instance = new SingletonClass();
        }
        return instance;
    }
}
