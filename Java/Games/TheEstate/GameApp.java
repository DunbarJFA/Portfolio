import java.util.Scanner;

public class GameApp{
	static Dungeon myDungeon = new Dungeon();
	static Room currentRoom = new Room();
	public static void main(String[] args){
		GameLoop();
		
	}//end of main method
	public static void GameLoop(){
		Scanner input = new Scanner(System.in);
		String userInput; 
		boolean doTravel = true;
		
		currentRoom = myDungeon.getRoom0();
			//initialize Room variable currentRoom for navigation
			//print the intro, then the first room's description to begin the gameplay loop
			System.out.printf("%nYou've arrived at the palatial manor of your friend, the Marquise Vito Andolini de Corleone, Esquire.");
			System.out.printf("%n%npress ENTER to continue");
				System.out.printf("%n%n");
				input.nextLine();
				clearScreen();
			System.out.printf("%nThe lord of the house has regretfully informed you that he will be late in meeting with you, so he's offered you full use of his home.");
			System.out.printf("%n%npress ENTER to continue");
				System.out.printf("%n%n");
				input.nextLine();
				clearScreen();
			System.out.printf("%nAre you prepared to enter?");
			System.out.printf("%n%npress ENTER to continue");
				input.nextLine();
				clearScreen();
		while(doTravel != false){
			//begin while loop for room travel
			System.out.println(currentRoom.getDescription());
			System.out.println("In which direction will you travel?");
			System.out.println(currentRoom.exitList);
			
			userInput = input.nextLine();
			clearScreen();
			if (userInput.equals("N") || (userInput.equals ("n"))){
				currentRoom = currentRoom.getNorth();
				
			}
			else if (userInput.equals("S") || (userInput.equals ("s"))){
				currentRoom = currentRoom.getSouth();
				
			}
			else if (userInput.equals("E") || (userInput.equals ("e"))){
				currentRoom = currentRoom.getEast();
				
			}
			else if (userInput.equals("W") || (userInput.equals ("w"))){
				currentRoom = currentRoom.getWest();
				
			}
			else if (userInput.equals("q") || userInput.equals("Q")){
				doTravel = false;
			}
			else {
				System.out.println("Not a valid entry. Please try again.");
			}
		}//end of game loop
	}//end of GameLoop method
	static void clearScreen(){//clears screen of old text
	    final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
    }//End of ClearScreen Method
}//end of class GameApp