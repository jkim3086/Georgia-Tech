import java.util.NoSuchElementException;

public class ArrayWrapper<T> implements SimpleCollection<T> {
    private T[] array;
    private int size;

    public ArrayWrapper() {
        array = (T[]) (new Object[5]);
        size = 0;
    }
    /**
     * Adds an element into the collection.
     * If the new element would exceed the size of the backing array,
     * instead resize the array, doubling it in size and copy over the
     * old elements.
     *
     * @param elem The element being added.
     */
    public void add(T elem) {

        if (array.length == size) {
            T[] temp = (T[]) (new Object[array.length * 2]);
            for (int i = 0; i < array.length; i++) {
                temp[i] = array[i];
            }
            array = temp;
        }
        array[size] = elem;
        size++;
    }
    /**
     * Adds all elements in elems to the collection.
     *
     * @param elems Array of elements to be added.
     */
    public void addAll(T[] elems) {
        for (int i = 0; i < elems.length; i++) {
            add(elems[i]);
        }
    }
    /**
     * Remove elem from the collection. Removing an element
     * should shift all the elements behind it forward, ensuring
     * that the backing array is contiguous. For example:
     *
     * Collection = ["hi", "hello", "wsup", "hey", null]
     * Collection after remove("hello") = ["hi", "wsup", "hey", null, null]
     *
     * @param elem Element to be removed.
     * @return true if the element was removed,
     *         false if it was not in the collection.
     */
    public boolean remove(T elem) {
        int i;
        int index = indexOf(elem);
        if (index >= 0) {
            array[index] = null;
            size--;
            for (i = index; i < size; i++) {
                array[i] = array[i + 1];
            }
            array[i] = null;
            return true;
        }
        return false;
    }
    /**
     * Removes each element in elems from the collection.
     *
     * @param elems Array of elements to be removed.
     * @return true if any elements were removed,
     *         false if no elements were removed.
     */
    public boolean removeAll(T[] elems) {
        int rm = 0;
        for (int i = 0; i < elems.length; i++) {
            if (remove(elems[i])) {
                rm++;
            }
        }
        if (rm > 0) { return true; }
        return false;
    }
    /**
     * Check that targe element is in collection
     * or not by checking the return value of
     * the method indexOf.
     *
     * @param elems Array of elements to be check
     *        its existence in the collection.
     * @return true if any elements were existed,
     *         false if no elements were existed.
     */
    public boolean contains(T elem) {
        return indexOf(elem) >= 0;
    }
    /**
     * Compare the target element to all element
     * in the collection and check the its position.
     *
     * @param elems Array of elements to be check
     *        its existence in the collection.
     * @return non-negative number if any elements were exist,
     *         -1 if no elements were exist.
     */
    public int indexOf(T elem) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(elem)) {
                return i;
            }
        }
        return -1;
    }
    /**
     * Gets an element from the collection, using its 0-based index.
     * If the index is within our backing array but more than our last
     * element, rather than returning null, this should throw
     * a java.util.NoSuchElementException.
     *
     * @param index The index of the element we want.
     * @return The element at the specified index.
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new NoSuchElementException();
        }
        return array[index];
    }
    /**
     * Returns the current number of elements in the collection.
     *
     * @return The size of the collection.
     */
    public int size() {
        return size;
    }
    /**
     * Returns the current capacity of the collection - namely, the
     * size of its backing array.
     *
     * @return The total capacity of the collection.
     */
    public int capacity() {
        return array.length;
    }
    /**
     * Clears the collection, resetting size and starting from a fresh
     * backing array of size 5.
     */
    public void clear() {
        T[] temp = (T[]) (new Object[5]);
        array = temp;
        size = 0;
    }
    /**
     * Tests if the collection is empty, i.e. it contains no elements.
     *
     * @return true if the collection has no elements, false otherwise.
     */
    public boolean isEmpty() {
        return size <= 0;
    }
    /**
     * While having toString be defined in the interface doesn't force you
     * to override the method in the implementing class, the format we
     * expect the toString() is as follows:
     *
     * [element1, element2, element3, ..., elementN]
     *
     * The end of the list should not contain any nulls, even if the
     * backing array is larger than the number of elements.
     *
     * @return [element1, element2, element3, ..., elementN]
     */
    public String toString() {
        String s = "[";
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                s += array[i];
            } else {
                s += array[i] + ", ";
            }
        }
        s += "]";
        return s;
    }
}