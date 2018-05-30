import java.util.List;
import java.util.Comparator;
public interface Searcher {
    /**
     * Searches a List for the given key. Returns any index, i, in the
     * List where data.get(i).equals(key), or -1 if no such
     * element exists. The data in the list is assumed to be in sorted
     * order according to their natural ordering. Assume that if
     * a.compareTo(b) == 0, then a.equals(b).
     *
     * @param data The data to search across. Assumed to be sorted.
     * @param key The value to search for.
     * @param <E> the type of elements stored in the list. Must implement
     *            Comparable
     * @return An array, a, containing exactly two elements:
     *        a[0] = the search result index defined above.
     *        a[1] = the number of times the algorithm called either
     *            .equals() or .compareTo() or .compare().
    */
    <E extends Comparable<E>> int[] search(List<? extends E> data, E key);

    /**
     * Performs the same as search() above except using a custom Comparator
     * that the input is assumed to be in. Assume that if
     * comparator.compare(a,b) == 0, then a.equals(b).
     * @param data The data to search across. Assumed to be sorted.
     * @param key The value to search for.
     * @param comparator the Comparator to use for ordering the elements
     * @param <E> the type of elements stored in the list. Must implement
     *            Comparable
     * @return An array, a, containing exactly two elements:
     *        a[0] = the search result index defined above.
     *        a[1] = the number of times the algorithm called either
     *            .equals() or .compareTo() or .compare().
    */
    <E> int[] search( List<? extends E> data, E key, Comparator<E> comparator);

}
