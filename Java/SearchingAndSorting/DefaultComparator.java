import java.util.Comparator;
/**
 *Class for a default comparator.
 *
 *Contains one method: an implementation of "compareTo"
 *
 *@return the result of comparison of two objects
 *
 *@author Jerod Dunbar
 *@version 5/4/17
 */
public class DefaultComparator<E extends Comparable<E>> implements Comparator<E>{
    @Override
    public int compare(E first, E second) {
        return first.compareTo(second);
    }//end of method compare
}//end of class DefaultComparator