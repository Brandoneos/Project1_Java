import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

public abstract class DictionaryTest {
    public abstract ProjOneDictionary<Integer,String> newDictionary();

    @Test
    void testEmptyFind(){
        ProjOneDictionary<Integer,String> dict = newDictionary();
        assertNull(dict.find(3),"Incorrect empty find behavior");
    }
    @Test
    void testSingleFind() throws NullValueException {
        ProjOneDictionary<Integer,String> dict = newDictionary();

            dict.insert(3,"three");
            assertEquals("three",dict.find(3),"Incorrect single element find behavior");


    }
    @Test
    void testSingleDelete() throws NullValueException{
        ProjOneDictionary<Integer,String> dict = newDictionary();
        boolean trueFalse = dict.insert(3,"three");
        assertTrue(dict.delete(3));
        assertNull(dict.find(3),"Incorrect single element delete behavior");
    }

    @Test
    void testIteratorSingle() throws NullValueException{
        ProjOneDictionary<Integer,String> dict = newDictionary();
        dict.insert(3,"three");
        Iterator<Integer> iter = dict.iterator();
        assertTrue(iter.hasNext(), "Incorrect single element hasNext Iterator behavior");
        assertEquals(3,iter.next(), "Incorrect single element iterator behavior");
        assertFalse(iter.hasNext(), "Incorrect emptied hasNext Iterator behavior");
    }
//    //Additional tests
    //Functions : insert, find, delete, getSize
    // Options are assert: assertTrue, assertFalse, assertNull, assertEquals
    //1) insert() + getSize()
    @Test
    void testInsertSingle() throws NullValueException{
        ProjOneDictionary<Integer,String> dict = newDictionary();
        dict.insert(5,"five");

        assertEquals(1,dict.getSize());
        Iterator<Integer> iter = dict.iterator();
        assertTrue(iter.hasNext(), "Incorrect single element insert() behavior");
        assertEquals(5,iter.next(), "Incorrect single element insert() behavior");
        assertFalse(iter.hasNext(), "Incorrect emptied hasNext insert() behavior");
    }

    @Test
    void testInsertEmpty() throws NullValueException{
        ProjOneDictionary<Integer,String> dict = newDictionary();
        Iterator<Integer> iter = dict.iterator();
        assertFalse(iter.hasNext(), "Incorrect empty list insert() behavior");
        assertEquals(0,dict.getSize());
    }

@Test
void testInsertMany() throws NullValueException{
    ProjOneDictionary<Integer,String> dict = newDictionary();

    dict.insert(5,"five");
    dict.insert(6,"six");
    assertEquals(2,dict.getSize());
    dict.insert(7,"seven");
    assertEquals(3,dict.getSize());
    Iterator<Integer> iter = dict.iterator();
    assertTrue(iter.hasNext(), "Incorrect multiple element insert() behavior");
    assertEquals(5,iter.next(), "Incorrect multiple element insert() behavior");
    assertTrue(iter.hasNext(), "Incorrect multiple element insert() behavior");
    assertEquals(6,iter.next(), "Incorrect multiple element insert() behavior");
    assertTrue(iter.hasNext(), "Incorrect multiple element insert() behavior");
    assertEquals(7,iter.next(), "Incorrect multiple element insert() behavior");
    assertFalse(iter.hasNext(), "Incorrect multiple element insert() behavior");
}
    //2) find()
    @Test
    void testFindSingle() throws NullValueException{
        ProjOneDictionary<Integer,String> dict = newDictionary();
        dict.insert(5,"five");
        assertEquals("five",dict.find(5),"Incorrect single element find behavior");

    }

    @Test
    void testFindEmpty() throws NullValueException{
        ProjOneDictionary<Integer,String> dict = newDictionary();
        assertNull(dict.find(5),"Incorrect empty find behavior");
    }

    @Test
    void testFindMany() throws NullValueException{
        ProjOneDictionary<Integer,String> dict = newDictionary();
        dict.insert(5,"five");
        assertEquals("five",dict.find(5),"Incorrect multiple element find behavior");
        dict.insert(6,"six");
        assertEquals("six",dict.find(6),"Incorrect multiple element find behavior");
        dict.insert(7,"seven");
        assertEquals("seven",dict.find(7),"Incorrect multiple element find behavior");
    }
    //3) delete() + getSize()
    @Test
    void testDeleteSingle() throws NullValueException{
        ProjOneDictionary<Integer,String> dict = newDictionary();
        dict.insert(5,"five");
        assertEquals(1,dict.getSize());
        assertTrue(dict.delete(5));
        assertNull(dict.find(5),"Incorrect single element delete behavior");
        assertEquals(0,dict.getSize());
    }

    @Test
    void testDeleteEmpty() throws NullValueException{
        ProjOneDictionary<Integer,String> dict = newDictionary();
        assertEquals(0,dict.getSize());
        assertFalse(dict.delete(5), "Incorrect empty list delete() behavior");
    }


    @Test
    void testDeleteMany() throws NullValueException{
        ProjOneDictionary<Integer,String> dict = newDictionary();
        dict.insert(5,"five");
        assertEquals(1,dict.getSize());
        assertTrue(dict.delete(5));
        assertNull(dict.find(5),"Incorrect multiple element delete behavior");
        assertEquals(0,dict.getSize());
        dict.insert(6,"six");
        dict.insert(7,"seven");
        dict.insert(8,"eight");
        dict.insert(9,"nine");

        assertFalse(dict.delete(5), "Incorrect multiple element delete() behavior");//error here
        assertTrue(dict.delete(6));//failed for hashmap
        assertEquals(3,dict.getSize());
        assertNull(dict.find(6),"Incorrect multiple element delete behavior");
        assertTrue(dict.delete(7));
        assertEquals(2,dict.getSize());
        assertNull(dict.find(7),"Incorrect multiple element delete behavior");
        assertTrue(dict.delete(8));
        assertEquals(1,dict.getSize());
        assertNull(dict.find(8),"Incorrect multiple element delete behavior");
        assertTrue(dict.delete(9));
        assertEquals(0,dict.getSize());
        assertNull(dict.find(9),"Incorrect multiple element delete behavior");
    }

    @Test //Tests delete with many elements and iterator
    void testDeleteManyIterator() throws NullValueException{
        ProjOneDictionary<Integer,String> dict = newDictionary();
        dict.insert(6,"six");
        dict.insert(7,"seven");
        dict.insert(8,"eight");
        dict.insert(9,"nine");
        assertTrue(dict.delete(8));
        assertTrue(dict.delete(7));
        Iterator<Integer> iter = dict.iterator();
        assertTrue(iter.hasNext(), "Incorrect multiple element insert() behavior");
        assertEquals(6,iter.next(), "Incorrect multiple element insert() behavior");
        assertTrue(iter.hasNext(), "Incorrect multiple element insert() behavior");
        assertEquals(9,iter.next(), "Incorrect multiple element insert() behavior");

    }

    //4) insert() + delete() + find() + getSize()
    @Test
    void testInsertDeleteFindGetSize() throws NullValueException{
        ProjOneDictionary<Integer,String> dict = newDictionary();
        dict.insert(4,"four");
        dict.insert(5,"five");
        dict.insert(6,"six");
        assertEquals(3,dict.getSize());
        assertTrue(dict.delete(5));
        assertEquals(2,dict.getSize());
        assertNull(dict.find(9),"Incorrect multiple methods behavior");
        assertNull(dict.find(5),"Incorrect multiple methods behavior");
        assertFalse(dict.delete(5));//error here
        Iterator<Integer> iter = dict.iterator();
        assertTrue(iter.hasNext(), "Incorrect multiple methods + hasNext Iterator behavior");
        assertEquals(4,iter.next(), "Incorrect multiple methods + iterator behavior");
        assertTrue(iter.hasNext(), "Incorrect multiple methods + hasNext Iterator behavior");
        assertEquals(6,iter.next(), "Incorrect multiple methods + iterator behavior");
        assertFalse(iter.hasNext(), "Incorrect multiple methods + hasNext Iterator behavior");
        assertTrue(dict.delete(4));
        assertTrue(dict.delete(6));
        //insert again
        dict.insert(7,"seven");
        assertEquals(1,dict.getSize());
        assertEquals("seven",dict.find(7),"Incorrect multiple methods behavior");

    }



//15 total tests -> 30 total tests run with 2 files
}
