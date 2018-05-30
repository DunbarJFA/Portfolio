import java.util.Scanner;

public class Player extends GameCharacter{
	
	private static int exp;
	private static int mana;
	
	static Scanner scan = new Scanner (System.in);
	static String userInput = scan.nextLine();
	
	public Player(){
			super(500, 20);
			this.mana = 0;
		}
		public void basicAttack(GameCharacter target){
				target.takeDamage(this.attackPower);
		}
		
		public void takeTurn(GameCharacter foe){
			
			Scanner newScanner = new Scanner(System.in);
			String userInput = newScanner.nextLine();
			
				System.out.printf("%n%nYour health " + this.getHealth());
				System.out.printf("%n%nThe Beast's health: " + foe.getHealth());
				System.out.printf("%n%nMake a choice: B F%n%n");
				userInput = newScanner.next();
				
				if (userInput.equals("B") || (userInput.equals ("b"))){
					basicAttack(foe);
				}
				else if ((userInput.equals("F") || (userInput.equals ("f")))){
					return;
				}
				else{
					System.out.printf("%n%nThat's not a proper input. Please, try again.");
				}
		}//end of taketurn method
	
		private void levelUp(){
			exp = Monster.getExp();
			if (this.exp == 1000000){
				System.out.printf("After your harrowing experience with the creature, you feel as though you've achieved some kind of arbitrary growth! %n%nThis must be what the kids call \"Leveling Up!\"");
			}
		}//end of levelUp
}//end of subclass Player
