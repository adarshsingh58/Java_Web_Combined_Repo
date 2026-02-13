package javaconcepts.designpatterns.structural.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Real-life scenario: File system (files + folders) Rules: File and Folder should be treated the same A folder can
 * contain files or other folders Client code should not care
 *
 */
public class B_ProductionComposite {

    /*
     * WHAT CHANGED (this is the key)
     * ❌ Before
     *  Client code knew concrete types
     *  Conditional logic everywhere
     *  Structure handling duplicated
     *
     * ✅ After
     *  Client treats leaf & composite uniformly
     *  No type checks
     *  Tree traversal is implicit
     *  Structure is extensible
     * 
     * Mental model (remember this)
     * If clients should treat part and whole the same → Composite
     * or
     * If you’re writing instanceof in a tree → Composite
     * */
    //    Step 1: Common component interface
    interface FileSystemNode {
        void print();
    }

    //Step 2: Leaf
    class FileNode implements FileSystemNode {
        private final String name;

        FileNode(String name) {
            this.name = name;
        }

        @Override
        public void print() {
            System.out.println("File: " + name);
        }
    }

    //Composite
    class FolderNode implements FileSystemNode {
        private final String name;
        private final List<FileSystemNode> children = new ArrayList<>();

        FolderNode(String name) {
            this.name = name;
        }

        void add(FileSystemNode node) {
            children.add(node);
        }

        @Override
        public void print() {
            System.out.println("Folder: " + name);
            for (FileSystemNode node : children) {
                node.print(); // recursion happens naturally
            }
        }
    }

    //Step 4: Client code (key difference)
    public void test() {
        FolderNode root = new FolderNode("root");
        root.add(new FileNode("a.txt"));
        FolderNode images = new FolderNode("images");
        images.add(new FileNode("photo.png"));
        root.add(images);
        root.print();
    }

    public static void main(String[] args) {
        new B_ProductionComposite().test();
    }
}
