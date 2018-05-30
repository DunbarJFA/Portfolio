import java.util.ArrayList;
import java.util.Random;
/**
 * A runner for the included searching and sorting classes.
 *
 * Contains six arrays populated with different amounts of integers in order to test the searching and sorting algorithms.
 *
 * Prints the results to the terminal.
 *
 * @author Jerod Dunbar
 * @version 5/4/17
 */
public class Runner{
    public static void main (String[] args){
        Random rand = new Random();
        int [] result = new int [2];

        ArrayList<Integer> list10 = new ArrayList<>(10);
        ArrayList<Integer> list20 = new ArrayList<>(20);
        ArrayList<Integer> list50 = new ArrayList<>(50);
        ArrayList<Integer> list100 = new ArrayList<>(100);
        ArrayList<Integer> list500 = new ArrayList<>(500);
        ArrayList<Integer> list1000 = new ArrayList<>(1000);

        for (int i = 0; i < 9; i++){
            int toAdd = 10;
            list10.add(toAdd);
            toAdd--;//for regular and reverse sorting
            //list10.add(rand.nextInt(10)+1);//for random sorting
        }for (int i = 0; i < 19; i++){
            int toAdd = 20;
            list20.add(toAdd);
            toAdd--;
            //list20.add(rand.nextInt(20)+1);
        }for (int i = 0; i < 49; i++){
            int toAdd = 50;
            list50.add(toAdd);
            toAdd--;
            //list50.add(rand.nextInt(50)+1);
        }for (int i = 0; i < 99; i++){
            int toAdd = 100;
            list100.add(toAdd);
            toAdd--;
            //list100.add(rand.nextInt(100)+1);
        }for (int i = 0; i < 499; i++){
            int toAdd = 500;
            list500.add(toAdd);
            toAdd--;
            //list500.add(rand.nextInt(500)+1);
        }for (int i = 0; i < 999; i++){
            int toAdd = 1000;
            list1000.add(toAdd);
            toAdd--;
            //list1000.add(rand.nextInt(1000)+1);
        }

        LinearSearcher linear = new LinearSearcher();
        BinarySearcher binary = new BinarySearcher();
        SelectionSorter selection = new SelectionSorter();
        InsertionSorter insertion = new InsertionSorter();
        MergeSorter merge = new MergeSorter();

        System.out.println("Linear Search Results");
                result = linear.search(list10,10);
            System.out.println(result[1]);
                result = linear.search(list20,20);
            System.out.println(result[1]);
                result = linear.search(list50,50);
            System.out.println(result[1]);
                result = linear.search(list100,100);
            System.out.println(result[1]);
                result = linear.search(list500,500);
            System.out.println(result[1]);
                result = linear.search(list1000,1000);
            System.out.println(result[1]);

        System.out.println("Binary Search Results");
                result = binary.search(list10,10);
            System.out.println(result[1]);
                result = binary.search(list20,20);
            System.out.println(result[1]);
                result = binary.search(list50,50);
            System.out.println(result[1]);
                result = binary.search(list100,100);
            System.out.println(result[1]);
                result = binary.search(list500,500);
            System.out.println(result[1]);
                result = binary.search(list1000,1000);
            System.out.println(result[1]);
        System.out.println("Insertion Sort Results");
            System.out.println(insertion.sort(list10));
            System.out.println(insertion.sort(list20));
            System.out.println(insertion.sort(list50));
            System.out.println(insertion.sort(list100));
            System.out.println(insertion.sort(list500));
            System.out.println(insertion.sort(list1000));
        System.out.println("Selection Sort Results");
            System.out.println(selection.sort(list10));
            System.out.println(selection.sort(list20));
            System.out.println(selection.sort(list50));
            System.out.println(selection.sort(list100));
            System.out.println(selection.sort(list500));
            System.out.println(selection.sort(list1000));
        System.out.println("Merge Sort Results");
            System.out.println(merge.sort(list10));
            System.out.println(merge.sort(list20));
            System.out.println(merge.sort(list50));
            System.out.println(merge.sort(list100));
            System.out.println(merge.sort(list500));
            System.out.println(merge.sort(list1000));
    }
}