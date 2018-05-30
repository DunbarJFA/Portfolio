/**
 * A small game where the user attempts to defeat the smarmy Goblin.
 *
 *@author    Jerod Dunbar
 *@version   9/04/2016
 */
 import java.util.Scanner;
 public class CombatCalculatorHoly
 {
    public static void main(String[] args)
    {
		/*Monster data variables*/
		
		//Declare variable for monster's name and initialize it to "Goblin"
	    String enemy = "Goblin";
		//Declare variable for monster's health and initialize it to 100
		int GoblinHealth = 100;
		//Declare variable for monster's attack power and initialize it to 15
		int GoblinAttack = 15;
		
		/* Hero data variables*/
		
		//Declare variable for Hero's health and initialize it to 100
		int HeroHealth = 100;
		//Declare variable for Hero's attack power and initialize it to 12
		int HeroAttack = 12;
		//Declare variable for Hero's magic power and initialize it to 0 
		int MP = 0;
		//Print the Monster's Name
		    System.out.println("");
			System.out.println ("A " + enemy + " appears!");
			System.out.println("");
			System.out.println("Goblin: \"You don’t frighten me, English pig dog!"); 
					System.out.println("Go and boil your bottom, you son of a silly person! "); 
					System.out.println("I blow my nose at you, so-called \'Hero\' thing!\"");
			System.out.println("");
		/* Loop control */
		//Declare loop control variable and initialize it to true
		int UserInput = 0;
	    while (GoblinHealth >= 0 && HeroHealth >= 0  && UserInput != 5)
	    {
		//While the loop control variable is true:
		
		    /*Report Combat Stats*/
		
		    
		    //Print the Monster's Health
		    System.out.println ("The monster's HP: " + GoblinHealth);
	        //Print the Player's Health
		    System.out.println("");
			System.out.println ("Your HP: " + HeroHealth);
		    //Print the Player's MP
		    System.out.println ("You've counted to: " + MP);
		    System.out.println (" ");
		    /*Combat Menu Prompt*/
		    System.out.println ("Combat Options:");
			System.out.println ("   1.) Examine");
		    //Print Option 1: Sword Attack
		    System.out.println ("   2.) Attack with Excaliber");
		    //Print Option 2: Cast Spell
		    System.out.println ("   3.) Toss the Holy Hand Grenade of Antioch");
		    //Print Option 3: Charge Mana
		    System.out.println ("   4.) Count to three");
		    //Print Option 4: Run Away
		    System.out.println ("   5.) Run Awaaaaaaay!!!");
		    //Prompt Player for Action
		    System.out.println("");
			System.out.print("How do you proceed?  ");
			
			//Declare Variable for User Input (as number) and acquire value from Scanner
		    Scanner input = new Scanner(System.in);
		    UserInput = input.nextInt();
			System.out.println("");
			
			if (UserInput == 1)
			{
				System.out.println("The stout creature stands before you menacingly brandishing a spiked wooden club and "); 
				System.out.println("uttering obsenities... in an outrageous French accent. What an eccentric performance...");
				System.out.println("");
			}
		
		    //If player chose option 1, (check with equality operator)
		    else if (UserInput == 2)
		    { 
       	        //Calculate damage and update monster health using subtraction
		        //Calculation: new monster health is old monster health minus hero attack power
			    GoblinHealth = GoblinHealth - HeroAttack;
		        //Print attack text:
			    //"You strike the <Monster Name> with your sword for <Hero Attack> damage!"
			    System.out.println("You score a blow against the " + enemy + " with your holy blade!");
				System.out.println("");
				
		    }
	        //Else if player chose option 2, (check equality operator)
		    else if (UserInput == 3)
		    {
			    //If player has 3 or more MP
				if (MP >= 3)
				{	
    				//Calculate damage and update monster health using division
	    	        //Calculation: new monster health is old monster health divided by 2
		    	    GoblinHealth = GoblinHealth / 2;
					//Weaken lowers the Goblin's attack
					GoblinAttack = GoblinAttack - 5;
					//Reduce Player MP by the spell cost using subtraction
					//Calculation: new MP is old MP minus 3
					MP = MP - 3;					
		            //Print spell text:
    		        System.out.println("\"Once the number three, being the third number, be reached, ");
					    System.out.println("then lobbest thou thy Holy Hand Grenade of Antioch towards thy foe, "); 
					    System.out.println("who, being naughty in my sight, shall snuff it.\"");
	    			    System.out.println("");
				    System.out.println("Goblin: \"Just a flesh wound!\""); 
					System.out.println("");
				}	
				//Else
			    else if (MP < 3)				
                //Print message: "You don't have enough mana!"
			    {
			        System.out.println("\"First shalt thou take out the Holy Pin. Then shalt thou count to three, no more–no less."); 
		                System.out.println("Three shall be the number thou shalt count, and the number of the counting shall be three."); 
				        System.out.println("Four shalt thou not count, neither count thou two, excepting that thou then proceed to three."); 
		                System.out.println("Five is right out.\"");
				        System.out.println("");
				}	
			}
		    //Else if player chose option 3, (check equality operator)
		    else if (UserInput == 4)
		    {
			    //Increment magic points and update player's MP using addition
		        //Calculation: new hero magic is old hero magic plus one
			    MP = MP + 1;
		        //Print charging text:
			    //"You focus and charge your magic power."
			    if (MP == 1)
				{
				    System.out.println("");
					System.out.println("One...");
				        System.out.println("");
						System.out.println("");
				}
				else if (MP == 2)
				{
					System.out.println("");
					System.out.println("Two...");
				        System.out.println("");
						System.out.println("");
				}
				else if (MP == 3)
				{
					System.out.println("");
					System.out.println("FIVE!");
					    System.out.println("");
				        System.out.println("Your servant, whispers: \"Three, sir.\"");
						System.out.println("");
				        System.out.println("...Three!");
				        System.out.println("");
						System.out.println("");
				}
				
		    //Else if player chose option 4, (check equality operator)
		    }
		    else if (UserInput == 5)
		    {
				//Stop combat loop by setting control variable to false
				
		        //Print retreat text:
			    //"You run away!"
		    	System.out.println("The minstrels still sing of your bravery:");
				    System.out.println("");
				    System.out.println("\"Brave Sir Hero ran away, bravely ran away away!");
				    System.out.println("When danger reared his ugly head, he bravely turned his tail and fled!"); 
				    System.out.println("Yes, brave Sir Hero turned about, he turned his tail, he chickened out!"); 
				    System.out.println("Bravely taking to his feet, he beat a very brave retreat. A brave retreat by our Hero...\"");
				    System.out.println("");
		    //Else the player chose incorrectly
		    }
		        else
		        {			
		        //Print error message:
		    	//"I don't understand that command."
		    	System.out.println("I don't understand that command...");
				System.out.println("");
		        }	
			//If monster health is 0 or below
			if (GoblinHealth <= 0) 
			{
			    //Stop combat loop by setting control variable to false
				//Print victory message: "You defeated the <monster name>!"
				System.out.println("You've defeated the " + enemy + "!");
			}
			if (HeroHealth > 0 && GoblinHealth > 0 && UserInput != 5 && UserInput != 1)
			{
				HeroHealth = HeroHealth - GoblinAttack;
				System.out.println("The " + enemy + " attacks!");
				    System.out.println("");
			}
			if (HeroHealth <= 0)
			{
				System.out.println("");
				System.out.println("You've met a terrible fate...");
			        System.out.println("");
			}
			{if (GoblinHealth == 88)
			{
				System.out.println("Goblin: \"No chance, English bed-wetting types! I burst my pimples at you and"); 
			        System.out.println("call your feeble effort a silly thing; you tiny-brained wiper of other peoples’ bottoms!\"");	
				System.out.println("");
	
	        }}
			if (GoblinHealth == 76)
			{
				System.out.println("Goblin:\"Go away, or I shall taunt you a second time!\"");
				System.out.println("");
			}
			if (GoblinHealth == 64)
			{
				System.out.println("Goblin: \"So, you think you can outclever us French folks with your silly, knees-bent,");
					System.out.println("running-about, advancing behavior? I wave my private parts at your aunties, you cheesy-leather,"); 
					System.out.println("second-hand, electric donkey bottom biter!\"");
				System.out.println("");
			}
			if (GoblinHealth == 52)
			{
				System.out.println("Goblin: \" I don’t want to talk to you no more,"); 
			        System.out.println("you empty headed animal food trough wiper!"); 
				    System.out.println("I fart in your general direction!"); 
				    System.out.println("Your mother was a hamster and your father smelt of elderberries!\"");
				System.out.println("");
			}
			if (0 < GoblinHealth && GoblinHealth <= 5)
			{
				System.out.println("Goblin: \"Help! Help! I’m being repressed! Come see the violence inherent in the system!\"");
				System.out.println("");
			}
			if (GoblinHealth == 0)
			{
				System.out.println("");
			    System.out.println("Goblin: \"I'M INVINCIBLLLLLE!!!\"");
			}
	    }
	
	
	}	
	 
	 
	 
 }