import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import org.junit.Test;

/**
 * Unit test for ArrayWrapper Homework
 * @author Aaron Friesen
 * @version 1.0
 */
public class ArrayWrapperTest {

    @Test
    public void genericTest() {
        SimpleCollection<String> sC = new ArrayWrapper<String>();
        SimpleCollection<Integer> sD = new ArrayWrapper<Integer>();
        SimpleCollection<Object> sE = new ArrayWrapper<Object>();
    }

    @Test
    public void initialCapacityTest() {
        SimpleCollection<String> sC = new ArrayWrapper<String>();
        assertEquals(sC.capacity(), 5);
    }

    @Test
    public void addTest() {
        SimpleCollection<String> sC = new ArrayWrapper<String>();
        sC.add("hi");
        sC.add("hello");
        sC.add("wsup");
    }

    @Test
    public void resizeTest() {
        SimpleCollection<String> sC = new ArrayWrapper<String>();
        for (int i = 0; i < 100; i++) {
            sC.add("hey! listen!");
        }
        assertEquals(sC.capacity(), 160);
    }

    @Test
    public void containsTest() {
        SimpleCollection<String> sC = new ArrayWrapper<String>();
        assertEquals(sC.contains("hi"), false);
        sC.add("hi");
        assertEquals(sC.contains(new String("hi")), true);
        for (int i = 0; i < 100; i++) {
            sC.add("yo");
        }
        sC.add("no");
        assertEquals(sC.contains("no"), true);
    }

    @Test
    public void removeTest() {
        SimpleCollection<Integer> sC = new ArrayWrapper<Integer>();
        sC.add(1);
        sC.add(2);
        sC.add(3);
        sC.add(4);

        sC.remove(2);

        assertEquals(sC.contains(2), false);
        assertEquals(sC.get(1), new Integer(3));
    }

    @Test
    public void addAllTest() {
        SimpleCollection<String> sC = new ArrayWrapper<String>();
        String[] arr = {"who", "what", "when", "where", "why", "how"};
        sC.addAll(arr);
    }

    @Test
    public void removeAllTest() {
        SimpleCollection<Integer> sC = new ArrayWrapper<Integer>();
        for (int i = 0; i < 10; i++) {
            sC.add(i);
        }

        Integer[] arr = {0, 2, 4, 6, 8};
        assertEquals(sC.removeAll(arr), true);
        assertEquals(sC.removeAll(arr), false);
        assertEquals(sC.size(), 5);
        assertEquals(sC.capacity(), 10);
    }

    @Test
    public void clearTest() {
        SimpleCollection<Integer> sC = new ArrayWrapper<Integer>();
        for (int i = 0; i < 100; i++) {
            sC.add(i);
        }
        sC.clear();
        assertEquals(sC.size(), 0);
        assertEquals(sC.capacity(), 5);
        for (int i = 0; i < 100; i++) {
            assertEquals(sC.contains(i), false);
        }
    }

    @Test
    public void isEmptyTest() {
        SimpleCollection<Integer> sC = new ArrayWrapper<Integer>();
        assertEquals(sC.isEmpty(), true);
        sC.add(1);
        assertEquals(sC.isEmpty(), false);
        sC.remove(1);
        assertEquals(sC.isEmpty(), true);
    }

    @Test(expected = NoSuchElementException.class)
    public void getTest() {
        SimpleCollection<Integer> sC = new ArrayWrapper<Integer>();
        sC.add(0);
        sC.add(1);
        sC.add(2);
        sC.add(3);

        for (int i = 0; i < sC.size(); i++) {
            assertEquals(sC.get(i), new Integer(i));
        }
        int j = sC.get(4);
    }

    @Test
    public void sizeTest() {
        SimpleCollection<Integer> sC = new ArrayWrapper<Integer>();
        sC.add(1);
        assertEquals(sC.size(), 1);
        sC.add(2);
        assertEquals(sC.size(), 2);

        sC.remove(1);
        assertEquals(sC.size(), 1);
        sC.clear();
        assertEquals(sC.size(), 0);
    }

    @Test
    public void toStringTest() {
        SimpleCollection<Integer> sC = new ArrayWrapper<Integer>();
        sC.add(4);
        sC.add(4);
        sC.add(3);
        sC.add(1);

        ArrayList<Integer> aC = new ArrayList<Integer>();
        aC.add(4);
        aC.add(4);
        aC.add(3);
        aC.add(1);

        assertEquals(sC.toString(), aC.toString());
    }
}
