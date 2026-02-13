package javaconcepts.designpatterns.structural.flyweight;

import java.util.ArrayList;
import java.util.List;

/*
 *Real-life scenario: Rendering millions of characters in a text editor
 * Assume:
 * Each character has:
 *   value ('a', 'b')
 *   font
 *   size
 *   color
 * Document can have millions of characters
 * */
public class A_NoFlyWeight {

    class Character {
        char value;
        String font;
        int size;
        String color;

        Character(char value, String font, int size, String color) {
            this.value = value;
            this.font = font;
            this.size = size;
            this.color = color;
        }
    }

    /*
     * What goes wrong
     *   Huge memory waste
     *   Repeated identical data
     *   GC pressure
     *   Poor cache locality
     * You’re duplicating immutable data unnecessarily
     * */
    public void test() {
        List<Character> doc = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            doc.add(new Character('a', "Arial", 12, "Black"));
        }
    }

    public static void main(String[] args) {
        new A_NoFlyWeight().test();
    }
}
