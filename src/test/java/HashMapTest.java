import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HashMapTest extends DictionaryTest{
    @Override
    public ProjOneDictionary<Integer, String> newDictionary() {
        return new HashMapDict<Integer,String>();
    }
    //HashMapDict<Integer,String> dict = (HashMapDict<Integer, String>) newDictionary();
    //Testing of the iterator and the table resizing should occur here

    @Test
    void testIteratorEmpty1() throws NullValueException{
        ProjOneDictionary<Integer,String> dict = newDictionary();
        Iterator<Integer> iter = dict.iterator();
        assertFalse(iter.hasNext(), "Incorrect emptied hasNext Iterator behavior");
        assertNull(iter.next());
    }
    @Test
    void testIteratorSingle1() throws NullValueException{
        ProjOneDictionary<Integer,String> dict = newDictionary();
        dict.insert(3,"three");
        Iterator<Integer> iter = dict.iterator();
        assertTrue(iter.hasNext(), "Incorrect single element hasNext Iterator behavior");
        assertEquals(3,iter.next(), "Incorrect single element iterator behavior");
        assertFalse(iter.hasNext(), "Incorrect emptied hasNext Iterator behavior");
    }
    //Tests the iterator when resized
    @Test
    void testManyIterator1() throws NullValueException{
        HashMapDict<Integer,String> dict = (HashMapDict<Integer, String>) newDictionary();
        dict.insert(7,"seven");
        dict.insert(8,"eight");
        dict.insert(9,"nine");
        dict.insert(10,"ten");
        dict.insert(11,"eleven");
        dict.insert(12,"twelve");
        assertTrue(dict.delete(8));
        assertTrue(dict.delete(7));
//        dict.printHashmap();
        Iterator<Integer> iter = dict.iterator();
        assertTrue(iter.hasNext(), "Incorrect multiple element insert() behavior");
        assertNotNull(iter.next());
//        checks if not null as may not iterate through hashmap in the right order
        assertTrue(iter.hasNext(), "Incorrect multiple element insert() behavior");
        assertNotNull(iter.next());
        assertTrue(iter.hasNext(), "Incorrect multiple element insert() behavior");
        assertNotNull(iter.next());
        assertTrue(iter.hasNext(), "Incorrect multiple element insert() behavior");
        assertNotNull(iter.next());

        assertFalse(iter.hasNext(), "Incorrect multiple element insert() behavior");
        assertNull(iter.next());

    }
}
