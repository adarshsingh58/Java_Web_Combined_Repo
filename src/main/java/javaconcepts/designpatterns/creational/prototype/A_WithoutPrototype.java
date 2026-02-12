package javaconcepts.designpatterns.creational.prototype;

import java.util.Map;

/*
 * If adding a new object type forces changes all over your code, think “creational pattern.”
 * Prototype: You want to create copies without knowing the exact classes
 * eg: Cloning game characters with minor differences
 *
 * Real-life scenario: Creating similar documents
 * Assume:
 * You have a Document, Creation is expensive, Most documents start from a template, Only small fields differ
 *
 * Real-world Prototype examples
 * Java itself
 * ArrayList<Integer> a = new ArrayList<>();
 * ArrayList<Integer> b = (ArrayList<Integer>) a.clone();
 * 
 * Game engines
 * Clone enemies, bullets, particles
 * */
public class A_WithoutPrototype {

    /*
     * Repeated expensive construction
     *   Duplicated setup logic
     *   If defaults change → many places break
     *   You are recreating similar objects manually.
     * */
    public static void main(String[] args) {
        /*Document invoice = new B_NaivePrototype().new Document(
                "Invoice",
                "Invoice content...",
                Map.of("type", "FINANCE")
        );

        Document receipt = new B_NaivePrototype().new Document(
                "Receipt",
                "Receipt content...",
                Map.of("type", "FINANCE")
        );*/

    }

    private class Document {
        String title;
        String content;
        Map<String, String> metadata;

        public Document(String title, String content, Map<String, String> metadata) {
            // imagine heavy setup here
            System.out.println("Expensive document creation");
            this.title = title;
            this.content = content;
            this.metadata = metadata;
        }
    }
}
