import java.util.List;
import java.util.Comparator;

public class SelectionSorter implements Sorter {
    /**
     * Sorts an array using SELECTION SORT.
     *
     * ALGORITHM: Advance from the beginning to the end of the list, finding
     * the appropriate element that belongs at each position by searching
     * forward in the array. This runs in O(n^2) time.
     *
     * @param data The data array to sort.
     *
     * @author Jerod Dunbar
     * @version 5/4/17
     */
    @Override
    public <E extends Comparable<E>> int sort(List<E> data){
        return sort(data, new DefaultComparator<E>());
    } //End method sort

    @Override
    public <E> int sort(List<E> data, Comparator<E> comparator){
        int operations = 0;

        for(int index = 0; index < data.size(); index++)
        {
            int small = index; // first index of the remaining array
                // find the smallest element
            for(int index2 = index + 1; index2 < data.size(); index2++)
            {
                operations++;
                if((comparator.compare(data.get(index2), data.get(small)) < 0))
                {
                    small = index2;
                }
            } //End for loop
            //Swap the smallest value we found with the current value
            swap(data,index,small);
        } //End for loop

        return operations;
    } //End method sort

    /**
     * A helper method to swap two values in a List
     *
     * @param data the List of data containing the value to swap
     * @param first index the location of the first element
     * @param second index the location of the second element
     *
     */
    private static <E> void swap(List<E> data, int index, int smallest){
        E temp = data.get(index); //Save a temporary value.
        data.set(index, data.get(smallest));
        data.set(smallest, temp);

    } //End method swap

} //End class SelectionSorter

