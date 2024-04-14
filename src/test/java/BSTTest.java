import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Iterator;


public class BSTTest extends DictionaryTest {
    @Override
    public ProjOneDictionary<Integer, String> newDictionary() {
        //BinarySearchTreeDict<Integer,String> dict = (BinarySearchTreeDict<Integer, String>) newDictionary();
        return new BinarySearchTreeDict<Integer,String>();
    }
    //Do Iterator Testing Here
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

    @Test //BST should have Breadth for search iterator
    void testManyIterator1() throws NullValueException{
        BinarySearchTreeDict<Integer,String> dict = (BinarySearchTreeDict<Integer, String>) newDictionary();
        dict.insert(7,"seven");
        dict.insert(8,"eight");
        dict.insert(9,"nine");
        dict.insert(11,"ten");
        dict.insert(10,"eleven");
        dict.insert(12,"twelve");
        assertTrue(dict.delete(8));
        assertTrue(dict.delete(7));
        Iterator<Integer> iter = dict.iterator();

        assertTrue(iter.hasNext(), "Incorrect multiple element insert() behavior");
        assertEquals(9,iter.next(), "Incorrect multiple element insert() behavior");
        assertTrue(iter.hasNext(), "Incorrect multiple element insert() behavior");
        assertEquals(11,iter.next(), "Incorrect multiple element insert() behavior");
        assertTrue(iter.hasNext(), "Incorrect multiple element insert() behavior");
        assertEquals(10,iter.next(), "Incorrect multiple element insert() behavior");
        assertTrue(iter.hasNext(), "Incorrect multiple element insert() behavior");
        assertEquals(12,iter.next(), "Incorrect multiple element insert() behavior");
        assertFalse(iter.hasNext(), "Incorrect multiple element insert() behavior");
        assertNull(iter.next(), "Incorrect multiple element insert() behavior");

    }



}
