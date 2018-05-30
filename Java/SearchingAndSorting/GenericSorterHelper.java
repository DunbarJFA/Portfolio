import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;

public class GenericSorterHelper{
    private static ArrayList<Integer> intList0;
    private static ArrayList<Integer> intList1;
    private static ArrayList<Integer> intList2;
    private static ArrayList<Integer> intList10;

    private static ArrayList<String> strList0;
    private static ArrayList<String> strList1;
    private static ArrayList<String> strList2;
    private static ArrayList<String> strList10;

    private static Comparator<Integer> intReverseComparator;
    private static Comparator<String> strReverseComparator;

    public static void initializeLists(){
        intList0 = new ArrayList<Integer>();

        intList1 = new ArrayList<Integer>();
        intList1.add(42);

        intList2 = new ArrayList<Integer>();
        intList2.add(17);
        intList2.add(13);

        intList10 = new ArrayList<Integer>();
        intList10.add(80);
        intList10.add(72);
        intList10.add(85);
        intList10.add(54);
        intList10.add(55);
        intList10.add(22);
        intList10.add(37);
        intList10.add(71);
        intList10.add(96);
        intList10.add(78);

        strList0 = new ArrayList<String>();

        strList1 = new ArrayList<String>();
        strList1.add("a");

        strList2 = new ArrayList<String>();
        strList2.add("B");
        strList2.add("AA");

        strList10 = new ArrayList<String>();
        strList10.add("~");
        strList10.add("aa");
        strList10.add("A");
        strList10.add("BB");
        strList10.add("STu");
        strList10.add("STU");
        strList10.add("zz");
        strList10.add("]");
        strList10.add("C");
        strList10.add("");

        intReverseComparator = Collections.reverseOrder();
        strReverseComparator = Collections.reverseOrder();
    }

    public static void testEmptyLists(Sorter theSorter){
        theSorter.sort(intList0);
        assertTrue(isInOrder(intList0));
        theSorter.sort(intList0, intReverseComparator);
        assertTrue(isInOrder(intList0, intReverseComparator));

        theSorter.sort(strList0);
        assertTrue(isInOrder(strList0));
        theSorter.sort(strList0, strReverseComparator);
        assertTrue(isInOrder(strList0, strReverseComparator));
    }

    public static void testIntegerLists(Sorter theSorter){
        // Test list of length 1
        theSorter.sort(intList1);
        assertTrue(isInOrder(intList1));
        // Shuffle the list
        Collections.shuffle(intList1); // silly for list of length 1
        // sort in reverse order
        theSorter.sort(intList1, intReverseComparator);
        assertTrue(isInOrder(intList1, intReverseComparator));

        // Test list of length 2
        theSorter.sort(intList2);
        assertTrue(isInOrder(intList2));
        Collections.shuffle(intList2);
        theSorter.sort(intList2, intReverseComparator);
        assertTrue(isInOrder(intList2, intReverseComparator));

        // Test list of length 10
        theSorter.sort(intList10);
        assertTrue(isInOrder(intList10));
        Collections.shuffle(intList10);
        theSorter.sort(intList10, intReverseComparator);
        assertTrue(isInOrder(intList10, intReverseComparator));
    }

    public static void testStringLists(Sorter theSorter){
        // Test list of length 10
        theSorter.sort(strList1);
        assertTrue(isInOrder(strList1));
        // Shuffle the list
        Collections.shuffle(strList1);
        // sort in reverse order
        theSorter.sort(strList1, strReverseComparator);
        assertTrue(isInOrder(strList1, strReverseComparator));

        // Test list of length 10
        theSorter.sort(strList2);
        assertTrue(isInOrder(strList2));
        Collections.shuffle(strList2);
        theSorter.sort(strList2, strReverseComparator);
        assertTrue(isInOrder(strList2, strReverseComparator));

        // Test list of length 10
        theSorter.sort(strList10);
        assertTrue(isInOrder(strList10));
        Collections.shuffle(strList10);
        theSorter.sort(strList10, strReverseComparator);
        assertTrue(isInOrder(strList10, strReverseComparator));
    }

    public static void testStringListsByLengthThenNatural(Sorter theSorter){
        Collections.shuffle(strList10);
        // sort using comparator that compares on length, with
        // ties bring broken by the natural ordering of strings
        theSorter.sort(strList10, new StringLengthAlphabeticalComparator());
        //Check that all 10 values are correct
        assertTrue(strList10.get(0).equals(""));
        assertTrue(strList10.get(1).equals("A"));
        assertTrue(strList10.get(2).equals("C"));
        assertTrue(strList10.get(3).equals("]"));
        assertTrue(strList10.get(4).equals("~"));
        assertTrue(strList10.get(5).equals("BB"));
        assertTrue(strList10.get(6).equals("aa"));
        assertTrue(strList10.get(7).equals("zz"));
        assertTrue(strList10.get(8).equals("STU"));
        assertTrue(strList10.get(9).equals("STu"));
    }

    private static <E extends Comparable<E>> boolean isInOrder(List<E> data){
        for(int i = 0; i < data.size()-1; i++){
            if(data.get(i).compareTo(data.get(i+1)) > 0){
                return false;
            }
        }
        return true;
    }

    private static <E extends Comparable<E>> boolean isInOrder(List<E> data, Comparator<E> comparator){
        for(int i = 0; i < data.size()-1; i++){
            if(comparator.compare(data.get(i),data.get(i+1)) > 0){
                return false;
            }
        }
        return true;
    }
}
