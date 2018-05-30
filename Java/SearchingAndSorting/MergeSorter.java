import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;

public class MergeSorter implements Sorter {
    private int operations = 0;
        /**
         * Sorts an array using MERGE SORT.
         *
         * ALGORITHM: Divide the list into two halves. Sort each half recursively,
         * then combine the sorted halves. This runs in O(n*lg(n)) time, roughly
         * because we have to divide the array until we encounter the base case,
         * an O(lg(n)) operation, each time performing the merge operation, an
         * O(n) operation.
         *
         * @param data The data array to sort.
         *
         * @author Jerod Dunbar
         * @version 5/4/17
         */
    @Override
    public <E extends Comparable<E>> int sort(List<E> data){
        int rightIndex = data.size() - 1;
        int leftIndex = 0;
        return sort(data, leftIndex, rightIndex, new DefaultComparator<E>());
    } //End method sort

    @Override
    public <E> int sort(List<E> data, Comparator<E> comparator){
        int leftIndex = 0;
        int rightIndex = data.size() - 1;
        return sort(data, leftIndex, rightIndex, comparator);
    }//End method override sort

    public <E> int sort(List<E> data, int left, int right, Comparator comparator){
        int leftIndex = left;
        int rightIndex = right;
        int mid1 = (leftIndex + rightIndex) / 2;
        int mid2 = mid1 + 1;
        if(rightIndex - leftIndex >= 1){
            sort(data, left, mid1, comparator);
            operations++;
            sort(data, mid2, right, comparator);
            operations++;
            merge(data, left, mid1, mid2, right, comparator);
        } //End if statement
        return operations;
    } //End method sort

      /**
       * Sorts an array that is already pre-sorted into two sorted halves.
       *
       * Works by creating a temporary array to house the elements within the
       * specified range. Uses the merge algorithm to fill this list by
       * examining the top elements on the left and right side until the copy is
       * full. Then overwrites the range in the original array with the new
       * copied data.
       *
       * @param data The entire data array to merge.
       * @param leftStart The first index of the left side.
       * @param rightStart The first index of the right side (>= leftStart).
       * @param rightEnd The last index of the right side (>= rightStart and < data.length).
       */
    private <E> void merge(List<E> data, int left, int mid1, int mid2, int right, Comparator<E> comparator){
        int leftIndex = left;
        int rightIndex = mid2;
        E[] combined = (E[]) new Object[data.size()];
        int combinedIndex = left;
        while(leftIndex <= mid1 && rightIndex <= right){
            if(comparator.compare(data.get(leftIndex), data.get(rightIndex)) <= 0){
                combined[combinedIndex] = data.get(leftIndex);
                combinedIndex++;
                leftIndex++;
                operations++;
            }
            else{
                combined[combinedIndex] = data.get(rightIndex);
                combinedIndex++;
                rightIndex++;
            }
        } //End while loop
        if(leftIndex == mid2){
            while(rightIndex <= right){
                combined[combinedIndex] = data.get(rightIndex);
                combinedIndex++;
                rightIndex++;
            } //End while loop
        }
        else{
            while(leftIndex <= mid1){
                combined[combinedIndex] = data.get(leftIndex);
                combinedIndex++;
                leftIndex++;
            } //End while loop
        }
        for(int i = left; i <= right; i++){
            data.set(i, combined[i]);
        } //End foor loop
    } //End method merge
} //End class