import java.util.List;
import java.util.Comparator;

public class BinarySearcher implements Searcher {
    private int[] result = new int[2];
    /**
     * Searches a given array for a searchKey using BINARY SEARCH,
     * returning an index where the key appears, or -1 if the searchKey is not
     * present. Requires that the input array is in ascending sorted order.
     * This runs in O(lg(n)) time.
     *
     * PRECONDITION: THe data must be in ascending order.
     *
     * @param data The data array to search. Must be in sorted order.
     * @param searchKey The integer key to search for.
     * @return The index of the searchKey, or -1 if not found.
     *
     * @author Jerod Dunbar
     * @version 5/4/17
     */
    @Override
    public <E extends Comparable<E>> int[] search(List<? extends E> data, E key){
        return search(data, key, new DefaultComparator<E>());
    } //End method search


    @Override
    public <E> int[] search(List<? extends E> data, E key, Comparator<E> comparator){
        result[0] = -1;
        int low = 0;                      //Smallest possible index.
        int high = data.size() - 1;       //Largest possible index
        int index;//Current index
        int count = 0;
        while(low <= high){
            index = (low + high) / 2;
            if(comparator.compare(data.get(index), key) == 0){
                //We found the key at the current index. Just return it.
                count++;
                result[0] = index;
                result[1] = count;
                return result;
            }
            else if(comparator.compare(data.get(index), key) > 0){
                //The search value is smaller than the current value.
                //It must appear to the left side.
                high = index -1;
                count++;
            }
            else if(comparator.compare(data.get(index), key) < 0){
                //The search value is larger than the current value.
                //It must appear to the right side.
                low = index + 1;
                count++;
            }
        }
        result[1] = count;
        return result;
    } //End method search
} //End class BinarySearcher
