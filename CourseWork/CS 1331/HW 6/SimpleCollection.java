/**
 * This interface defines things that simple collections can do.
 * @author Aaron Friesen
 * @version 1.0
 */
public interface SimpleCollection<T> {
    /**
     * Adds an element into the collection.
     * If the new element would exceed the size of the backing array,
     * instead resize the array, doubling it in size and copy over the
     * old elements.
     *
     * @param elem The element being added.
     */
    void add(T elem);

    /**
     * Adds all elements in elems to the collection.
     * Hint: can this be implemented in terms of add(T elem)?
     *
     * @param elems Array of elements to be added.
     */
    void addAll(T[] elems);

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
    boolean remove(T elem);

    /**
     * Removes each element in elems from the collection.
     * Hint: can this be implemented in terms of remove(T elem)?
     *
     * @param elems Array of elements to be removed.
     * @return true if any elements were removed,
     *         false if no elements were removed.
     */
    boolean removeAll(T[] elems);

    /**
     * Checks to see if the collection contains a given element.
     *
     * @param elem The element we are checking for.
     * @return true if the collection contains elem, false otherwise.
     */
    boolean contains(T elem);

    /**
     * Gets an element from the collection, using its 0-based index.
     * If the index is within our backing array but more than our last
     * element, rather than returning null, this should throw
     * a java.util.NoSuchElementException.
     *
     * @param index The index of the element we want.
     * @return The element at the specified index.
     */
    T get(int index);

    /**
     * Returns the current number of elements in the collection.
     *
     * @return The size of the collection.
     */
    int size();

    /**
     * Returns the current capacity of the collection - namely, the
     * size of its backing array.
     *
     * @return The total capacity of the collection.
     */
    int capacity();

    /**
     * Clears the collection, resetting size and starting from a fresh
     * backing array of size 5.
     */
    void clear();

    /**
     * Tests if the collection is empty, i.e. it contains no elements.
     *
     * @return true if the collection has no elements, false otherwise.
     */
    boolean isEmpty();

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
    String toString();
}
