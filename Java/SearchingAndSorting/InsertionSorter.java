import java.util.List;
import java.util.Comparator;
import java.util.Collections;

public class InsertionSorter implements Sorter {
    private int operations = 0;
    /**
     * Sorts an array using INSERTION SORT.
     *
     * ALGORITHM: Advance from the beginning to the end of the list. For each
     * new item, insert it in the correct spot with respect to the part of the
     * list we've encountered already. This runs in O(n^2) time.
     *
     * @param data The data array to sort.
     *
     * @author Jerod Dunbar
     * @version 5/4/17
     */
    @Override
    public <E extends Comparable<E>> int sort(List<E> data){
        for(int next = 1; next < data.size(); next++){
            E insert = data.get(next);
            int moveItemIndex = next;
            while(moveItemIndex > 0 && data.get(moveItemIndex - 1).compareTo(insert) > 0) {
                data.set(moveItemIndex, data.get(moveItemIndex - 1));
                moveItemIndex--;
                operations++;
            } //End while loop
            operations++;
            data.set(moveItemIndex, insert);
        } //End for loop
        return operations;
    } //End method

    @Override
    public <E> int sort(List<E> data, Comparator<E> comparator){
        for(int next = 1; next < data.size(); next++){
            E insert = data.get(next);
            int moveItemIndex = next;
                while(moveItemIndex > 0 && comparator.compare(data.get(moveItemIndex - 1), insert) > 0){
                    data.set(moveItemIndex, data.get(moveItemIndex - 1));
                    moveItemIndex--;
                    operations++;
                } //End while loop
            operations++;
            data.set(moveItemIndex, insert);
        } //End for loop
        return operations;
    } //End method
} //End class InsertionSorter