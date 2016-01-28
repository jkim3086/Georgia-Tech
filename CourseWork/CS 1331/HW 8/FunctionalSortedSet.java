import java.util.function.Predicate;
import java.util.Comparator;
import java.util.SortedSet;

/**
 * An interface that extends SortedSet, which extends Set,
 * which extends Collection. It provides Functional behaviors that will make
 * use of Java 8's new Stream API.
 * A FunctionalSortedSet maintains a sorted property based on a Comparator,
 * and can be resorted using a new one, or filtered based on a Predicate.
 *
 * @author Joe Rossi
 * @version 1.0
 * @param <E> The type of elements the FunctionalSortedSet contains.
 */
public interface FunctionalSortedSet<E> extends SortedSet<E> {

    /**
     * The filter method takes in a Predicate, which has a single boolean
     * valued method. This Predicate can be passed in using a lambda expression,
     * or "anonymous method" (one that you don't need to put inside a class).
     *
     * For example:
     *
     * someSet.filter((int a, int b) -> a == b);
     *
     * where we define a and b as the parameters and a == b as the logic of this
     * predicate. More complicated predicates can be defined like this:
     *
     * someSet.filter((double a, double b) -> {
     *     int average = (a + b) / 2;
     *     return average > 90;
     * });
     *
     * You can also pass in a method defined in some other class like so:
     *
     * someSet.filter(ArrayWrapper::isEmpty);
     *
     * @param p a Predicate that either "has" or "is" a boolean valued method
     * @return  a new FunctionalSortedSet with elements whose predicate calls
     *          returned false removed
     */
    FunctionalSortedSet<E> filter(Predicate<E> p);

    /**
     * This method is similar to filter, except it takes in a Comparator, which
     * either "has" or "is" a method that takes in two parameters and returns
     * an int (similar to compareTo in the Comparable interface).
     *
     * For example:
     *
     * someSet.sort((int a, int b) -> a - b);
     *
     * @param c a Comparator that either "has" or "is" an int valued method
     * @return  a new FunctionalSortedSet with the elements sorted based on c
     */
    FunctionalSortedSet<E> sort(Comparator<E> c);
}
