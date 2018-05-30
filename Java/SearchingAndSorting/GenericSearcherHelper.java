import java.util.ArrayList;
import static org.junit.Assert.*;

public class GenericSearcherHelper{
    public static void testEmptyList(Searcher Searcher){
        ArrayList<Integer> list = new ArrayList<Integer>();
        assertTrue(Searcher.search(list,0)[0] == -1);
        assertTrue(Searcher.search(list,1)[0] == -1);
    }

    public static void testList(Searcher Searcher){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i = 0; i < 100; i++){
            list.add(i);
        }
        assertTrue(Searcher.search(list, -1)[0] == -1);
        assertTrue(Searcher.search(list, 0)[0] == 0);
        assertTrue(Searcher.search(list, 13)[0] == 13);
        assertTrue(Searcher.search(list, 99)[0] == 99);
        assertTrue(Searcher.search(list, 100)[0] == -1);
    }
}
