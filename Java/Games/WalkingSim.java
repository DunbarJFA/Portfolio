/**
 * Walking Sim
 *User takes a stroll through a manor using arrays and a sense of adventure.
 *@author    Jerod Dunbar
 *@version   10/06/2016
 */
import java.util.Scanner;
 public class WalkingSim{
	static int currentRoom;
	     //variable for current position on the map
	static int roomChange;
	     //create a failsafe variable for ensuring that the current room isn't overwritten by the sentinel value of the travel array
	 
	public static void main (String[] args){
		String[] roomDescription = new String[9];
		  //create an array to hold descriptive information of rooms
		  //array for holding descriptive strings related to the different rooms of the map
		 
        	roomDescription[0] = "You are in an enormous room decorated from floor to ceiling with elaborate tilework and statues of water-bearing goddesses. A shallow circular pool lies in the center of the room. Two bathrooms and a curtained room labeled \"Parfum\" lie on the west wall. A set of large wooden banquet doors is to the North.     Exits are to the North (n) and East (e). Press \"q\" to quit.";			
			  //Opulent Bathroom
			roomDescription[1] = "You cross the threshold of the manor and emerge into the foyer. It's a resplendant room of marble floors, magnificent paintings, and a designated coat room. You are asked to leave your shoes at the door. On the eastern wall you notice a large curtained archway decorated in tile with a water motif. There is a room to the north that appears to be prepared for a gathering.     Exits to the North (n) and West (w). Press \"q\" to quit.";	        
			  //Foyer
			roomDescription[2] = "You enter a room dimly-lit by brass lamps. A dark mahogany desk sits in the middle on the room; a mess of scattered books lies atop it beneath the soft glow of a table lamp. An obnoxiously huge and ornately gilded canopy bed sits on a dais at the rear of the room.     Exit is to the North (n).";
			  //Master Bedroom
			roomDescription[3] = "You emerge into a utilitarian, yet noticeably well-stocked kitchen. A steel galley table runs the length of the room while pots and pans of all shapes and sizes are suspended overhead. Despite the pervasive residual heat and smell of a recently-cooked meal, the room is immaculately clean. There's a set of large banquet doors to the South and a small door of simple design to the North.    Exits are to the North (n) and South (s). Press \"q\" to quit.";			
			  //Kitchen
			roomDescription[4] = "You've come upon a decadent sight. A banquet table built for a throng sits at the center of the room loaded with a bounty of freshly-prepared meats, cheeses, soups, vegetables, and glorious sweets. To the North is a greenhouse door to the rear yard. To the West is a set of masterfully-carved French doors. To the East is an archway leading to an open room. To the South is the house entranceway.     Exits are in all directions. (n, s, e, w) Press \"q\" to quit.";	        
			  //Dining Room
			roomDescription[5] = "You've found a large room built to offer every comfort, likely for entertaining guests. Plush chairs and couches suround a central space. The walls are lined with rows upon rows of books. As your gaze drifts upward, you discover that this room is actually multi-storied. To the North is a massive stone staircase to the next level. To the South is a small door of rich, dark wood.     Exits are to the North (n), South (s), and West (w). Press \"q\" to quit.";
			  //Drawing Room
			roomDescription[6] = "You enter a very short hallway flanked by four identical, modestly-furnished bedrooms. They appear rather drab, yet comfotable. These appear to belong to the serving staff.     Exit to the South (s). Press \"q\" to quit.";			
			  //Servants' Quarters
			roomDescription[7] = "You exit the Dining Room and step out onto the grounds of the manor. Acres of flowers and vegetables sit laid out in an intricate maze of swirls throughout the magnificently oak-lined property. It's truly a treat for the eyes.     Exit to the South (s). Press \"q\" to quit.";	        
			  //The Gardens
			roomDescription[8] = "You've ascended the Drawing Room staircase to its upper level. More luxurious seating surrounds the central railing. There's no other furniture about, however, as anything more would only serve to obstruct the stupendous view. The walls and ceiling of the upper level make up a giant skylight, opening this atrium to the sky.     Exit to the South (s). Press \"q\" to quit.";
	          //Second Floor Atrium
		int[][] travel = new int[9][4];
		
	      //multi-dimensional array for containing the values that represent the relationships between rooms (exits)
          //first column of each of the following groups represents the "rows" of the array while the second column represents the "columns"
          //ex:[2][3] is third row, fourth column
		  //each index is populated with an int representing the "exits" that connect each room of the "map"
		  //-1 is sentinel value for when there is no exit
		 
		    travel[0][0] = 3;            travel[1][0] = 4;            travel[2][0] = 5;
			travel[0][1] = -1;           travel[1][1] = -1;           travel[2][1] = -1;
			travel[0][2] = 1;            travel[1][2] = -1;           travel[2][2] = -1;
			travel[0][3] = -1;           travel[1][3] = 0;            travel[2][3] = -1;


			travel[3][0] = 6;            travel[4][0] = 7;            travel[5][0] = 8;
			travel[3][1] = 0;            travel[4][1] = 1;            travel[5][1] = 2;
			travel[3][2] = 4;            travel[4][2] = 5;            travel[5][2] = -1;
			travel[3][3] = -1;           travel[4][3] = 3;            travel[5][3] = 4;


			travel[6][0] = -1;           travel[7][0] = -1;           travel[8][0] = -1;
			travel[6][1] = 3;            travel[7][1] = 4;            travel[8][1] = 5;
			travel[6][2] = -1;           travel[7][2] = -1;           travel[8][2] = -1;
			travel[6][3] = -1;           travel[7][3] = -1;           travel[8][3] = -1;
		
		currentRoom = 1;
		roomChange = 1;
		  //initialze currentRoom and roomChange with a reference to Room 1 in the travel array
		  //prompt user for directional choice
        
		Scanner begin = new Scanner(System.in);
		System.out.printf("%nYou've arrived at the palatial manor of your friend, the Marquise Vito Andolini de Corleone, Esquire.");
			System.out.printf("%n%npress ENTER to continue");
				System.out.printf("%n%n");
				begin.nextLine();
				clearScreen();
		System.out.printf("%nThe lord of the house has regretfully informed you that he will be late in meeting with you, so he's offered you full use of his home.");
			System.out.printf("%n%npress ENTER to continue");
				System.out.printf("%n%n");
				begin.nextLine();
				clearScreen();
		System.out.printf("%nAre you prepared to enter?");
		    System.out.printf("%n%npress ENTER to continue");
			    begin.nextLine();
			    clearScreen();
		System.out.println(roomDescription[currentRoom]);//print description of new room
		String userInput;
		  //declare a variable to contain user input regarding directional travel (holds a character in order to simplify navigation)
		boolean doTravel = true;
		
		while (doTravel != false){
			try{//start of try block
				
				Scanner input = new Scanner(System.in);
				userInput = input.nextLine();
				
			  //create if statements for user input regarding character movement
				if (userInput.equals("n") || userInput.equals("N")){
					
					roomChange = travel[currentRoom][0];
					
					if (roomChange != -1){
					currentRoom = roomChange;
					clearScreen();
					System.out.println(roomDescription[currentRoom]);//print description of new room
				    }
				    else if (roomChange == -1){
					    System.out.printf("%nThere is nowhere to go in that direction.");
						System.out.printf("%n%npress ENTER to continue");
						System.out.printf("%n%n");
						input.nextLine();
						clearScreen();
						System.out.println(roomDescription[currentRoom]);//reprint description of current room
				    }
				//if else statements used to prevent sentinel value from being used to reference an out of bounds index
				}
				else if (userInput.equals("s") || userInput.equals("S")){
					
					roomChange = travel[currentRoom][1];
	                
					if (roomChange != -1){
					currentRoom = roomChange;
					clearScreen();
					System.out.println(roomDescription[currentRoom]);//print description of new room
				    }
				    else if (roomChange == -1){
					    System.out.printf("%nThere is nowhere to go in that direction.");
						System.out.printf("%n%npress ENTER to continue");
						System.out.printf("%n%n");
						input.nextLine();
						clearScreen();
						System.out.println(roomDescription[currentRoom]);//reprint description of current room
				    }
				//if else statements used to prevent sentinel value from being used to reference an out of bounds index
				}
				else if (userInput.equals("e") || userInput.equals("E")){
					
					roomChange = travel[currentRoom][2];
					
					if (roomChange != -1){
					currentRoom = roomChange;
					clearScreen();
					System.out.println(roomDescription[currentRoom]);//print description of new room
				    }
				    else if (roomChange == -1){
						System.out.printf("%nThere is nowhere to go in that direction.");
						System.out.printf("%n%npress ENTER to continue");
						System.out.printf("%n%n");
						input.nextLine();
						clearScreen();
						System.out.println(roomDescription[currentRoom]);//reprint description of current room
				    }
					  //if else statements used to prevent sentinel value from replacing roomChange variable and being used to reference an out of bounds index
				}
				else if (userInput.equals("w") || userInput.equals("W")){
					
					roomChange = travel[currentRoom][3];
					
					if (roomChange != -1){
					currentRoom = roomChange;
					clearScreen();
					System.out.println(roomDescription[currentRoom]);//print description of new room
				    }
				    else if (roomChange == -1){
						System.out.printf("%nThere is nowhere to go in that direction.");
						System.out.printf("%n%npress ENTER to continue");
						System.out.printf("%n%n");
						input.nextLine();
						clearScreen();
						System.out.println(roomDescription[currentRoom]);//reprint description of current room
				    }
				//if else statements used to prevent sentinel value from being used to reference an out of bounds index
				}
				else if (userInput.equals("q") || userInput.equals("Q")){
					System.out.printf("%nYou exit the manor, step into the coach provided to you, and return home.");//output Quit statement before terminating game
					doTravel = false;
				}
				else {
					System.out.printf("%nYou seem confused... Perhaps you'd like to try again?");
					System.out.printf("%n%npress ENTER to continue");
					System.out.printf("%n%n");
					input.nextLine();
					clearScreen();
					System.out.println(roomDescription[currentRoom]);//reprint description of current room
				}
				
				
			}//end of try block for navigation
			catch (ArrayIndexOutOfBoundsException e){//start of catch block
		        
				System.out.println("There is nowhere to go in that direction.");
				
			}//end of catch block for navigation exceptions
		}//end of gameplay loop
	}//end of main method
	static void clearScreen(){//clears screen of old text
	    final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
    }//End of ClearScreen Method
}//end of class WalkingSim