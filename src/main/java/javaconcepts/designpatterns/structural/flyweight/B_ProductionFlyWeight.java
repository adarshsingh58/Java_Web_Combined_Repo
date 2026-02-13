package javaconcepts.designpatterns.structural.flyweight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class B_ProductionFlyWeight {

    //Step 1: Flyweight object (intrinsic state only)
    class Glyph {
        final char value;
        final String font;
        final int size;

        Glyph(char value, String font, int size) {
            this.value = value;
            this.font = font;
            this.size = size;
        }

        void draw(int x, int y, String color) {
            // extrinsic state: x, y, color
            System.out.println(
                    value + " at (" + x + "," + y + ") color=" + color
            );
        }
    }

    //    Step 2: Flyweight factory (critical)
    class GlyphFactory {

        private static final Map<String, Glyph> cache = new HashMap<>();

        static Glyph get(char value, String font, int size) {
            String key = value + font + size;
            return cache.computeIfAbsent(
                    key,
                    k -> new B_ProductionFlyWeight().new Glyph(value, font, size)
            );
        }
    }

    //    Step 3: Client code (lightweight objects)
    class CharacterPosition {
        int x, y;
        String color;
        Glyph glyph;

        CharacterPosition(int x, int y, String color, Glyph glyph) {
            this.x = x;
            this.y = y;
            this.color = color;
            this.glyph = glyph;
        }
    }

    /*
     * WHAT CHANGED (this is the key)
     * Before
     *   Every object carried duplicate data
     *   Memory usage scaled with object count
     *   GC overhead huge
     * After
     *   Shared immutable state
     *   Memory scales with unique combinations
     *
     * Flyweight is NOT caching (important)
     * Caching:Improves speed; Flyweight:Reduces memory footprint
     *
     * Mental model (remember this)
     * If you have many similar objects wasting memory → Flyweight
     * or If object state can be split into shared + external → Flyweight
     * */
    public void test() {
        List<CharacterPosition> doc = new ArrayList<>();
        // Only one Glyph object
        // Millions of lightweight references
        for (int i = 0; i < 1_000_000; i++) {
            Glyph g = GlyphFactory.get('a', "Arial", 12);
            doc.add(new CharacterPosition(i, 0, "Black", g));
        }

    }


    /*
     * Real-world Flyweight examples
     * Java standard library
     * Integer a = Integer.valueOf(100);
     * Integer b = Integer.valueOf(100);
     * a == b // true (cached flyweight)
     * Boolean.TRUE == Boolean.TRUE // flyweight
     * String s1 = "hello";
     * String s2 = "hello";
     * s1 == s2 // true (string pool)
     *
     * UI frameworks
     * Icons
     * Fonts
     * Colors
     *
     * Game engines
     * Trees, bullets, particles
     * */
    public static void main(String[] args) {
        new B_ProductionFlyWeight().test();
    }
}
