import java.util.List;
import java.util.Comparator;

public class LinearSearcher implements Searcher {
    /**
     * Searches a given array for a searchKey using LINEAR SEARCH,
     * returning the first index where the key appears, or -1 if the searchKey
     * is not present. This runs in O(n) time.
     *
     * @param data The data array to search.
     * @param searchKey The integer key to search for.
     * @return The index of the searchKey, or -1 if not found.
     *
     * @author Jerod Dunbar
     * @version 5/4/17
     */
    @Override
    public <E extends Comparable<E>> int[] search(List<? extends E> data, E key){
        int temp[] = new int[data.size() + 1];
        int count = 0;
        boolean found = false;
        for(int i = 0; i < data.size(); i++){
            if(data.get(i) == key){
                count++;
                temp[1] = count;
                temp[0] = i;
                found = true;
            }
            else{
                count++;
            }
        } //End for loop
        if(!found){
            temp[1] = count;
            temp[0] = -1;
        }
        return temp;
    } //End method search

    @Override
    public <E> int[] search(List<? extends E> data, E key, Comparator<E> comparator){
        int temp[] = new int[data.size() + 1]; 
        boolean found = false;
        int count = 0;
            for(int i = 0; i < data.size(); i++){
                if(data.get(i) == key){
                    count++;
                    temp[1] = count;
                    temp[0] = i;
                    found = true;
                }
                else{
                    count++;
                }
            } //End for loop
        if(!found){
            temp[1] = count;
            temp[0] = -1;
        }
        return temp;
    }//End method search
}//End class LinearSearcher