import java.util.List;
import java.util.Comparator;
public interface Sorter {
    /**
     * Sorts the given data list, replacing the values in sorted values
     * according to their
     * natural ordering. That is, {@literal i > j ==> data.get(i) >= data.get(j)}.
     *
     * @param data The data to sort.
     * @param <E> the type of elements stored in the list. Must implement
     *          Comparable
     * @return The number of times the search algorithm called either
     *        .equals() or .compareTo() or .compare().
    */
    <E extends Comparable<E>> int sort(List<E> data);

    /**
     * Performs the same as sort() above except using a custom Comparator
     * to determine the following order.
     *
     * @param data The data to sort.
     * @param comparator The ordering to use.
     * @param <E> the type of elements stored in the list.
     * @return The number of times the search algorithm called either
     *        .equals() or .compareTo() or .compare().
    */
    <E> int sort(List<E> data, Comparator<E> comparator);
}
