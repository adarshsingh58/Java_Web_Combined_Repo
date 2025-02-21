package javaconcepts.collections;

import java.util.*;

import static java.lang.System.out;

/**
 * Collection Interface -> Set Interface -> HashSet Class -> LinkedHashSet Class
 *                                       -> SortedSet Interface -> NavigableSet Interface -> TreeSet
 *
 *
 * For the data we put in, a hash is calculated and from this hash we calculate the index at which data must be put.
 * Now as when 2 of the same data is put, same hashcode is generated and which raises the possibility of two data being the same inside code.
 * But code cant be sure because 2 data items or 2 objects can have same  hashcode. Now to confirm, code checks with the .equals() method
 * on these 2 data items. If the result is true now i.e. 2 data items are equal, set doesn’t save it. If not set saves the data as a next
 * element on the same index.
 * When it's HASH in collection, duplicates are not allowed to persist in the system. Here in set, duplicates are blocked even to store,
 * in hashmap duplicate(key) data replaces the old data. But in a nutshell two of the same data cant persist if hashing is used in collection.
 */
public class SetTest {

    public static void main(String[] args) {
        Set<Integer> set = new HashSet<>();
        Set<Integer> linkedSet = new LinkedHashSet<>();
        Set<Integer> treesetNormalOrder = new TreeSet<>();
        Set<Integer> treesetCustomOrder = new TreeSet<>((o1, o2) -> o2.compareTo(o1));

        addDatainSet(set);
        addDatainSet(linkedSet);
        addDatainSet(treesetNormalOrder);
        addDatainSet(treesetCustomOrder);

        out.println("Set: Unordered Unsorted");
        set.forEach(out::println);
        out.println("Linked Set: Ordered Unsorted");
        linkedSet.forEach(out::println);
        out.println("TreeSet : Unordered Sorted");
        treesetNormalOrder.forEach(out::println);
        out.println("tree set CustomOrder: Unordered Custom Sorted");
        treesetCustomOrder.forEach(out::println);
    }

    private static void addDatainSet(Set<Integer> set) {
        set.add(4);
        set.add(3);
        set.add(65);
        set.add(1);
        set.add(-32);
    }
}
