package javaconcepts.eightFeatures;

/**
 * Explore how the Java 8 Optional class improves code safety by avoiding null pointer exceptions. Understand its key
 * methods such as of, empty, isPresent, orElse, and functional style operations like filter and map to handle optional
 * values effectively. Functions of the Optional class
 * <p>
 * We can use Optional.of(x) to wrap a non-null value, Optional.empty() to represent a missing value, or
 * Optional.ofNullable(x) to create an Optional from a reference that may or may not be null.
 * <p>
 * After creating an instance of Optional, we then use isPresent() to determine if there is a value and get() to get the
 * value. The Optional class provides a few other helpful methods for dealing with missing values:
 * <p>
 * orElse(T) – Returns the given default value if the Optional is empty. orElseGet(Supplier<T>) – Calls on the given
 * Supplier to provide a value if the Optional is empty. orElseThrow(Supplier<X extends Throwable>) – Calls on the given
 * Supplier for an exception to throw if the Optional is empty.
 *
 *
 */
public class OptionalTest {
}
