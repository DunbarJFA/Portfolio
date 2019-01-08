import java.util.Scanner;

public class GameApp{
	static Dungeon myDungeon = new Dungeon();
	static Room currentRoom = new Room();
	static Player newPlayer = new Player();
	static Monster newMonster = new Monster();
	static Scanner input = new Scanner(System.in);

	private static int story = 0;
	private static boolean doTravel = true;
	
	public static void main(String[] args){
		
		GameLoop();
		
	}//end of main method
	public static void GameLoop(){
		Scanner input = new Scanner(System.in);
		String userInput; 
		
		
		currentRoom = myDungeon.getRoom0();
			//initialize Room variable currentRoom for navigation
			//print the intro, then the first room's description to begin the gameplay loop
			System.out.printf("%nYou've arrived at the palatial manor of your friend, the Marquise Vito Andolini de Corleone, Esquire.");
			System.out.printf("%n%npress ENTER to continue");
				System.out.printf("%n%n");
				input.nextLine();
				clearScreen();
			System.out.printf("%nThe lord of the house has regretfully informed you that he will be late in meeting with you, so he's offered you full use of his home in the mean time.");
			System.out.printf("%n%npress ENTER to continue");
				System.out.printf("%n%n");
				input.nextLine();
				clearScreen();
			System.out.printf("%nYou arrive at the manor exactly 30 seconds early, impeccably dressed in your best new fowling suit, but no servants greet you at the door...");
			System.out.printf("%n%npress ENTER to continue");
				System.out.printf("%n%n");
				input.nextLine();
				clearScreen();
			System.out.printf("%nNo reception? No fanfare? No crumpets?! Surely, something is amiss...");
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
			Encounters();
			System.out.printf(currentRoom.getDescription());
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
	
	public static void Encounters(){
		if (currentRoom.toString().equals("Dining Room") && story == 0){
			monsterMash("first");
			story++;
		} 
		else if (currentRoom.toString().equals("Atrium") && story == 1){
			monsterMash("second");
			story++;
		}
		else if (currentRoom.toString().equals("Manor Gardens") && story == 2){
			monsterMash("third");
			story++;
		}
		else if (currentRoom.toString().equals("Foyer") && story == 3){
			monsterMash("final");
		}
	}//end of Encounters
	
	public static void monsterMash(String sequence){
		if (sequence.equals("first")){
			System.out.println("A screeching howl shatters your concentration! You are suddenly accosted by a ghoulish sight! A fierce, humanoid creature springs forth from above, a whirlwind of claws and teeth.");
			battleMap();
		}
		else if (sequence.equals("second")){
			System.out.println("As you peruse the Marquise' vast book collection (only shoving ONE into your coat this time), you are again beset by that boorish beast!");
			battleMap();
		}
		else if (sequence.equals("third")){
			System.out.println("The night air fills you with vigor! As you trace the familiar silhouettes of the oaks in the twilight, your meditation is mangled by the reappearance of the haggard monster.");
			battleMap();
		}
		else if (sequence.equals("final")){
			System.out.println("Beaten, bruised, and bloody... Every breath a ragged gasp... With alarmingly severe bleeding and a couple of oddly twisted limbs, the sad, pitiable animal approaches you as if to beg for euthanasia. Poor sod.");
			battleMap();
		}
	}//end of monsterMash
	
	
	public static void battleMap(){
		
		Scanner input = new Scanner(System.in);
		
		while(newPlayer.getHealth() > 0 && newMonster.getHealth() > 0 && newMonster.wounded == false){
			newPlayer.takeTurn(newMonster);
			newMonster.takeTurn(newPlayer);
			if (newMonster.getHealth() == 1500 || newMonster.getHealth() == 1000 || newMonster.getHealth() == 500){
				woundMonster();
				return;
			}
			else if (newPlayer.getHealth() <= 0){
				System.out.printf("You've met with a terrible fate...");
				System.out.printf("%n%npress ENTER to exit");
					System.out.printf("%n%n");
					input.nextLine();
					clearScreen();
				System.exit(0);
			}
			else if (newMonster.getHealth() <= 0){
				System.out.println("You've slain the beast! With a sudden overwhelming weariness, you decide to make for home... You snatch a silver candelabra on the way out.");
				System.out.printf("%n%npress ENTER to exit");
					System.out.printf("%n%n");
					input.nextLine();
					clearScreen();
				System.exit(0);
			}
		}
	}//end of battleMap
	
	public static void woundMonster(){
		System.out.printf("%n%n%nYou've grievously wounded the monster! It flees in pain!");
			System.out.printf("%n%npress ENTER to continue");
				System.out.printf("%n%n");
				input.nextLine();
				clearScreen();
			return;
	}//end of woundMonster
	
	static void clearScreen(){//clears screen of old text
	    final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
    }//End of ClearScreen Method
}//end of class GameApp