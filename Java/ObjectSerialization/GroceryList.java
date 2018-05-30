import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.lang.StringBuilder;
import java.io.IOException;
import java.io.Serializable;
/**
 * A class that models a list of grocery items.
 *
 *@author Jerod Dunbar 
 *@version February 22, 2017
 */
public class GroceryList implements Serializable{

    public ArrayList<String> groceries = new ArrayList<String>();
    //List of groceries to process.
    //Constructs a new GroceryList object that is initially empty.
     
    public GroceryList(){

    }//end of GroceryList constructor.

    /**
     * Returns the number of items currently in the grocery list.
     *
     * @return The number of items in the list.
     */
    public int getItemCount(){
        int count = 0;
        //Variable for containing the int value of the ArrayList size.
        if(this.groceries.size()<1) {
            return 0;
        }
        else {
            count = this.groceries.size();
            return count;
        }
    }//end of getItemCount.

    /**
     * If the given item String is not in the list yet (ignoring capitalization
     * and leading/trailing whitespace), appends the item to the end
     * of the list. Otherwise, does nothing. A GroceryList should be able
     * to hold as many unique item names as the user desires. If the item
     * contains no actual text other than whitespace, this should not be added.
     *
     * @param item The item to add.
     */
    public void addItem(final String item){
        String newItem = null;
        //Local variable for storing formatted version of String parameter.
        LinkedHashSet<String> addItem = new LinkedHashSet<String>(groceries);
        //LinkedHashSet copies the contents of the groceries ArrayList and uses its own Set qualities to prevent duplicates from being appended.
        if (!(item == null) && !(item.trim().isEmpty() == true)){
            StringBuffer formattedItem = new StringBuffer(item.toLowerCase().trim());
            //Changes all characters of String item to lowercase and
            // trims leading/trailing whitespace before storing in StringBuffer.
            String[] itemSplit = formattedItem.toString().split(" ");
            //Populates a String array with Strings created by splitting
            // formattedItem around spaces.
            formattedItem.delete(0,formattedItem.length());
            for (int i = 0; i < itemSplit.length; i++){
                formattedItem.append(Character.toUpperCase(itemSplit[i].charAt(0))).append(itemSplit[i].substring(1)).append(" ");
                //Capitalizes the first letter of each String in each index of
                // itemSplit array then appends lowercase letters and one unit
                // of trailing whitespace, in case of a multi-String entry.
                newItem = formattedItem.toString().trim();
                //Contains the final vetted, formatted String to be appended to groceries.
            }
            addItem.add(newItem);
            groceries.clear();
            //Groceries is first cleared to avoid duplicates.
            groceries.addAll(addItem);
            //Groceries is repopulated with the Strings stored in the LinkedHashSet addItem.
        }
        else{
            System.out.println("Cannot Add Blank Items.");
            return;
        }
    }//End of addItem.

    /**
     * Removes the item at the specified index. If the index specified is
     * >= this.getItemCount(), an IllegalArgumentException should be thrown.
     * After removal of an item, the index of all items past the specified index
     * should be decremented. E.g.:
     *
     * Before:
     * 0 => Eggs
     * 1 => Milk
     * 2 => Spinach
     *
     * list.removeItemAtIndex(1);
     *
     * After:
     * 0 => Eggs
     * 1 => Spinach
     *
     * @param index The index (zero-based) to remove.
     */
    public void removeItemAtIndex(final int index){
        try {
            if (groceries.isEmpty() == true) {
                throw new IllegalArgumentException("Sorry, you don't have that many items in the list.");
            } else {
                groceries.remove(index);
            }
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("You don't have that many items on the list!");
        }
    }//End of removeItemAtIndex.

    /**
     * Returns the item String at the specified index. If the index specified
     * is >= this.getItemCount(), an IllegalArgumentException should be thrown. The
     * String returned should be given in "canonical" form", that is, with no leading/trailing
     * whitespace and the first letter of each individual word capitalized, regardless of
     * how the item was added initially. E.g.:
     *
     * "eggs" => "Eggs"
     * "toilet paper" => "Toilet Paper"
     * "MILK" => "Milk"
     * "  coffee " => "Coffee"
     *
     * @param index The index (zero-based) to fetch.
     * @return The grocery item text at the given index, in the canonical form specified above.
     */
    public String getItemAtIndex(final int index){
        if(index >= this.getItemCount()) {
            throw new IllegalArgumentException("Sorry, you don't have that many items in the list.");
        }
        else{
            return groceries.get(index);
        }
    }//End of getItemAtIndex.

    /**
     * @{inheritDoc}
     *
     * Returns a String representation of this list. Should use the StringBuilder class to build
     * up the result. A representation of a GroceryList is a human-readable series of lines with
     * a line number (1-based), followed by a period and space (". "), followed by the item. There
     * should be no trailing newline. If the list is empty, the words "(Empty List)" should be returned.
     *
     * E.g. for GroceryList {0 => "Eggs", 1 => "Bacon", 2 => "Bread"}, it should return:
     *
     * "1. Eggs
     * 2. Bacon
     * 3. Bread"
     */
    @Override
    public String toString(){
        StringBuilder list = new StringBuilder();
        if (groceries.size() == 0){
            return ("(Empty List)");
        }
        else{
            for(int i = 0; i < groceries.size(); i++){
                list.append(((i+1) + ". " + groceries.get(i) + "\n"));
            }
        }
        return list.toString().trim();
    }//End of toString Override.
}//End of class GroceryList.
