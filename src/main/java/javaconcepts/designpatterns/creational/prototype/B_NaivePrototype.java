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
public class B_NaivePrototype {

    public static void main(String[] args) {
        Document invoice = new B_NaivePrototype().new Document(
                "Invoice",
                "Invoice content...",
                Map.of("type", "FINANCE")
        );

        //Instead of creating a new object we create a copy of invoice object
        // and change the title and content so that the heavy operatoin is avoided.
        Document copyForReceipt = new B_NaivePrototype().new Document(invoice);
        copyForReceipt.title = "Receipt";
        copyForReceipt.content = "Receipt content...";

        //But this code is problemmatic. We have accidentally shared internal state.
        // Because metadata is a shared reference, changes to one document affect the other. So now
        // for invoice as well metadata changed. So this is not a perfect Prototype Builder.
        copyForReceipt.metadata.put("owner", "user1");

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

        public Document(Document other) {
            this.title = other.title;
            this.content = other.content;
            this.metadata = other.metadata; // ⚠️ shared reference
        }
    }
}

