package javaconcepts.designpatterns.structural.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Real-life scenario: File system (files + folders) Rules: File and Folder should be treated the same A folder can
 * contain files or other folders Client code should not care
 *
 */
public class A_NoComposite {


    /*
     * What goes wrong
     *   instanceof everywhere
     *   Adding new type → modify all logic
     *   Client must know internal structure
     *   Violates Open–Closed Principle
     * Client is doing tree-walking logic
     * */
    class File {
        void print() {
            System.out.println("File");
        }
    }

    class Folder {
        List<Object> items = new ArrayList<>();

        void print() {
            System.out.println("Folder");
            for (Object o : items) {
                if (o instanceof File) {
                    ((File) o).print();
                } else if (o instanceof Folder) {
                    ((Folder) o).print();
                }
            }
        }
    }

}
