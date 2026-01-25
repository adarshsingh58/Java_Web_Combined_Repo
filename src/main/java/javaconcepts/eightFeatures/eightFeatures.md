What is Included in Java 8?

Explore the essential features introduced in Java 8 including lambda expressions, streams, method references, default methods, the Optional class, and the new Date/Time API. Understand how these changes modernized Java and why upgrading to Java 8 is crucial for current production systems.
What’s included?

As of publication, Java 8 is the de-facto standard version of Java. It is already being used in many production systems. So if we are currently using an older version of Java, it’s time to upgrade. Java 8 includes the following:

    Lambda expressions
    Method references
    Default Methods (Defender methods)
    A new Stream API
    Optional
    A new Date/Time API
    Nashorn, the new JavaScript engine
    Removal of the Permanent Generation

Lambda Expressions

Explore the fundamentals of Java 8 lambda expressions, including syntax, scope, and method references. Understand functional interfaces and how to leverage them for cleaner, more concise code.
Introduction

The biggest new feature of Java 8 is the language level support for lambda expressions (Project Lambda). A lambda expression is like syntactic sugar for an anonymous class with one method whose type is inferred. However, it will have huge implications for simplifying development.

Method references

Since a lambda expression is like an object-less method, wouldn’t it be nice if we could refer to existing methods instead of using a lambda expression? This is exactly what we can do with method references.

For example, imagine we frequently need to filter a list of files based on file types. Assume we have the following set of methods for determining a file’s type:


**`public class FileFilters {
public static boolean fileIsPdf(File file) {/*code*/}
public static boolean fileIsTxt(File file) {/*code*/}
public static boolean fileIsRtf(File file) {/*code*/}
}`**

`Stream<File> pdfs = getFiles().filter(FileFilters::fileIsPdf);
Stream<File> txts = getFiles().filter(FileFilters::fileIsTxt);
Stream<File> rtfs = getFiles().filter(FileFilters::fileIsRtf);
`
Method references can point to:

    Static methods
    Instance methods
    Methods on particular instances
    Constructors (i.e., TreeSet::new)

