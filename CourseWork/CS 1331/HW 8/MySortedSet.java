import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * An implementation of the FunctionalSortedSet interface that uses an ArrayList
 * as the backing data structure.
 *
 * @author Joe Rossi
 * @version 1.0
 * @param <E> A comparable object that is contained within this sorted set.
 */
public class MySortedSet<E extends Comparable<? super E>>
    implements FunctionalSortedSet<E> {

    private ArrayList<E> list;
    private Comparator<E> c;

    /**
     * Creates a MySortedSet using the Comparable's compareTo as Comparator
     */
    public MySortedSet() {
        this((E a, E b) -> a.compareTo(b));
    }

    /**
     * Creates a MySortedSet using a specific Comparator
     *
     * @param c a Comparator that either "has" or "is" one int valued method
     */
    public MySortedSet(Comparator<E> c) {
        this.c = c;
        list = new ArrayList<E>();
    }

    //-----------FunctionalSortedSet METHODS - ONLY MODIFY filter!!------------
    /**
     * The filter method takes in a Predicate, which has a single boolean
     * valued method. This Predicate can be passed in using a lambda expression,
     * or "anonymous method" (one that you don't need to put inside a class).
     *
     * @param p a Predicate that either "has" or "is" a boolean valued method
     * @return  a new FunctionalSortedSet with elements whose predicate calls
     *          returned false removed
     */
    @Override
    public MySortedSet<E> filter(Predicate<E> p) {
        MySortedSet<E> newSet = new MySortedSet<E>();
        for (int i = 0; i < list.size(); i++) {
            E element = list.get(i);
            if (p.test(element)) {
                newSet.add(element);
            }
        }
        return newSet;
    }

    @Override
    public MySortedSet<E> sort(Comparator<E> comparator) {
        MySortedSet<E> ret = new MySortedSet<E>(comparator);
        ret.addAll(this.list);
        return ret;
    }

    //------SortedSet METHODS - ONLY MODIFY subSet and tailSet!!---------------

    @Override
    public Comparator<? super E> comparator() {
        return c;
    }

    @Override
    public E first() {
        return list.get(0);
    }

    /**
     * Returns a MySortedSet object with all elements [first, toElement) using a
     * functional programming expression.
     *
     * @param toElement The element the returned set should stop before.
     * @return A sorted set containing elements strictly less than toElement.
     */
    @Override
    public MySortedSet<E> headSet(E toElement) {
        return list.subList(0, list.indexOf(toElement)).stream().collect(
                            Collectors.toCollection(()
                            -> new MySortedSet<>(c)));
    }

    /**
     * Return a MySortedSet object with all elements [fromElement, toElement)
     * using a functional programming expression.
     *
     * @param fromElement The first element the returned set should contain.
     * @param toElement The element the returned set should stop before.
     * @return A sorted set containing elements fromElement to toElement.
     */
    @Override
    public MySortedSet<E> subSet(E fromElement, E toElement) {
        return list.subList(list.indexOf(fromElement),
                            list.indexOf(toElement)).stream().collect(
                            Collectors.toCollection(()
                            -> new MySortedSet<>(c)));
    }

    /**
     * Return a MySortedSet object with all elements [fromElement, last]
     * using a functional programming expression.
     *
     * @param fromElement The first element the returned set should contain.
     * @return A sorted set containing elements fromElement and onwards.
     */
    @Override
    public MySortedSet<E> tailSet(E fromElement) {
        return list.subList(list.indexOf(fromElement),
            list.indexOf(this.last())).stream().collect(
            Collectors.toCollection(()
            -> new MySortedSet<>(c)));
    }

    @Override
    public E last() {
        return list.get(list.size() - 1);
    }

    //-----------Set METHODS - TODO---------------------------------------------
    /**
     * Add an element into a set
     *
     * @param e an element to add
     * @return true if the addiction is succeeded
     */
    @Override
    public boolean add(E e) {
        if (!list.contains(e)) {
            int i;
            for (i = 0; i < list.size(); i++) {
                if (c.compare(e, list.get(i)) < 0) {
                    list.add(i, e);
                    return true;
                }
            }
            list.add(e);
            return true;
        } else {
            return false;
        }
    }
    /**
     * remove an element in a set
     *
     * @param e an element to remove
     * @return true if the deletion is succeeded
     */
    @Override
    public boolean remove(Object e) {
        return list.remove(e);
    }
    /**
     * Checking the existence of the given element in the set
     *
     * @param e an element to check
     * @return true if element is already in the set
     */
    @Override
    public boolean contains(Object e) {
        return list.contains(e);
    }
    /**
     * Checking the existence of the given collection of elements in the set
     *
     * @param col a collection to check
     * @return true if elements in the collection are already in the set
     */
    @Override
    public boolean containsAll(Collection<?> col) {
        return list.containsAll(col);
    }
    /**
     * Checking the set is empty or not
     *
     * @return true if the set is empty
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
    /**
     * Get size of a set
     *
     * @return number size of the set
     */
    @Override
    public int size() {
        return list.size();
    }
    /**
     * Add elements in a collection into a set
     *
     * @param col a collection
     * @return true if any elements in the collection is added into the set
     */
    @Override
    public boolean addAll(Collection<? extends E> col) {
        int originSize = list.size();
        for (E temp : col) {
            add(temp);
        }
        return list.size() > originSize;
    }
    /**
     * remove elements in a collection into a set
     *
     * @param col a collection
     * @return true if any elements in the collection is added into the set
     */
    @Override
    public boolean removeAll(Collection<?> col) {
        return list.removeAll(col);
    }
    /**
     * retain elements in a collection into a set
     *
     * @param col a collection
     * @return true if any elements in the collection is retained into the set
     */
    @Override
    public boolean retainAll(Collection<?> col) {
        return list.retainAll(col);
    }
    /**
     * retain elements in a collection into a set
     *
     * @return iterator iterator of the set
     */
    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }
    /**
     * clear the set
     */
    @Override
    public void clear() {
        list.clear();
    }
    /**
     * store elements in the set into array
     *
     * @return an array contain all elements in the set
     */
    @Override
    public Object[] toArray() {
        return list.toArray();
    }
    /**
     * store elements in the set into array
     *
     * @return an array contain all elements in the set
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }
    /**
     * The string representation consists of
     * a list of the set's elements in a proper format
     *
     * @return string represents elements in the set
     */
    @Override
    public String toString() {
        String temp = "";
        for (int i = 0; i < list.size(); i++) {
            temp += list.get(i) + "\n";
        }
        return temp;
    }
}
