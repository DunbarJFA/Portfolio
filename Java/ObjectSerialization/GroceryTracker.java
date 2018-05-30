import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Scanner;
/**
 * Creates a <code>GroceryList</code> instance, manipulates it, and serializes it using
 * Java IO.
 *
 * @author Jerod Dunbar
 * @version 22 February 2017
 * @see GroceryList
 * */
public class GroceryTracker{

    private static String loadFile;

    private static GroceryList groceries = new GroceryList();
/**
* Main method searches for a Command Line Argument and uses it's filepath to
 * save/load <code>GroceryList</code> data. Then uses a while-loop-enclosed
 * series of print statements to implement a UI that allows data viewing, appending,
 * removing, and saving.
 *
 * @throws Several IO, NullPointer, and NumberFormat exceptions. All are dealt with.
* */
    public static void main (String[] args){

        String userInput = null;
        //Variable for holding String value of user's intended addition to a list.

        String userChoice = "0";
        //Variable for holding an int String that designates the user's menu choice.

        if(args.length > 0)
        {
            loadFile = args[0];
        }
        else
        {
            loadFile = "N/A";
        }
        loadList();
        Scanner scan = new Scanner(System.in);

        while (!(userChoice.equals("5"))){
            System.out.println("Welcome to Grocery Tracker!\n");
            System.out.println("Current File: " + Paths.get(loadFile).toAbsolutePath() + "\n");
            System.out.println("Please Select an Option:\n");
            System.out.println("\t1. View List\n\t2. Add an Item\n\t3. Remove an Item\n\t4. Save\n\t5. Quit\nChoice:");

            userChoice = scan.nextLine();

            if (userChoice.equals("1")){
                System.out.println(groceries.toString());
            }
            else if (userChoice.equals("2")){
                System.out.println("\nWhat would you like to add?\n");
                userInput = scan.nextLine();
                groceries.addItem(userInput);
            }
            else if (userChoice.equals("3")){
                try {
                    System.out.println("\nWhich number would you like to remove? \n");
                    userInput = scan.nextLine();
                    groceries.removeItemAtIndex(Integer.parseInt(userInput) - 1);
                }
                catch(NumberFormatException e){
                    System.out.println("Please enter a valid number!");
                }
            }
            else if (userChoice.equals("4")){
                System.out.println("\nWould you like to rename your file? Y or N\n");
                boolean done = false;
                while (done == false) {
                    userInput = scan.nextLine();
                    if (userInput.equals("Y") || userInput.equals("y")) {
                        System.out.println("\nWhat would you like to call your list?\n");
                        loadFile = scan.nextLine();
                        saveList(loadFile);
                        System.out.println("\nYour list has been saved as: " + loadFile + "\n");
                        done = true;
                    } else if (userInput.equals("N") || userInput.equals("n")) {
                        saveList(loadFile);
                        System.out.println("\nYour list has been saved as: " + loadFile + "\n");
                        done = true;
                    } else {
                        System.out.println("\nPlease enter Y or N.\n");
                    }
                }
            }
            else if (userChoice.equals("5")){
                System.out.println("\nThank you for using the Grocery Tracker. Goodbye.\n");
                System.exit(0);
            }
            else{
                System.out.println("\nPlease enter a valid number from 1 to 5.\n");
            }
        }//End of While Loop.
    }//End of Main Method.
    /**
     * Opens/closes output streams to serialize the contents of an Arraylist
     * of strings and subsequently serializes that data.
     *
     * @param String list The name of the file to be saved to.
     * */
    public static void saveList(String list){
        try {
            FileOutputStream fos = new FileOutputStream(loadFile + ".ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(groceries);
            oos.close();
        }
        catch (FileNotFoundException e){
            System.out.println("\nError Opening File for Writing.\n");
        }
        catch (NullPointerException e){
            System.out.println("\nOutput File Initialization Previously Failed.\n");
        }
        catch (IOException e){
            System.out.println("\nError Writing List to Disk.\n");
        }
    }//End of saveList.
/**
 * Opens/closes input streams to read data from the file accessed by the program
 * and adds it to an arraylist of Strings.
 * */
    public static void loadList(){
        try {
            FileInputStream fis = new FileInputStream(loadFile + ".ser");
            ObjectInputStream ois = new ObjectInputStream(fis);

            groceries = ((GroceryList) ois.readObject());
            ois.close();
        }
        catch (FileNotFoundException e){
            System.out.println("\nError Opening File for Writing.\n");
        }
        catch (NullPointerException e){
            System.out.println("\nOutput File Initialization Previously Failed.\n");
        }
        catch (ClassNotFoundException e){
            System.out.println("\nNo object of the declared class could be found.\n");
            System.exit(1);
        }
        catch (EOFException e){
            System.out.println("\nReached the end of the file without finding information.\n");
            System.exit(1);
        }
        catch (IOException e){
            System.out.println("\nError Writing List.\n");
        }
    }//End of loadList.
}//End of class.