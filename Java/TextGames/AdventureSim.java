/**
 * A small game where the user hunts monsters in the wilderness.
 *
 *@author    Jerod Dunbar
 *@version   9/21/2016
 */
import java.util.Scanner;
import java.security.SecureRandom;
public class AdventureSim{
	//initialize stat and stat point variables
            String heroName;
			static String userInput = "\0";
			static int heroHealth = 60;//Base pool of health before stat point allocation
			static int healthRefresh;// Set up int to mirror value in heroHealth after character creation so that heroHealth can be reset between battles
			static int heroAtk = 10;/////Base attack value before stat point allocation
			static int heroMagic = 3;////Initialized Hero Stats, Magic Charge, and Stat Points
			static int magicCharge = 0;//Variable for measuring spell readiness (must have 1 charge before spell can be cast)
			static int statPoints = 20;//Player-allocated to customize hero

			static int goblinHealth;//Initialized Goblin Variables
			static int goblinAttack;//
		  
			static int orcHealth;//Initialized Orc Variables
			static int orcAttack;//
		    
			static int trollHealth;//Initialized Troll Variables
			static int trollAttack;//
		
			static int crimGuardHealth;//Initialized Crimson Guard Variables
			static int crimGuardAttack;//
			
			static int bigBossHealth = 1;//Initialized Boss variables
			static int bigBossAttack;//
			
			static int monsterRoll;//Initialized Storage Variable for Randomly-Chosen Monster Number
			static int enemy;//Initialized Storage Variable for Monster Data going into Combat Calc
			static int bossKey = 0;//Number of Crimson Guard Defeated (at 3, Boss spawns)
			static int outcome;
			static int result;//variable that determines program's response to battle outcomes
			static boolean rolling = false;
	public static void main (String[] args){
			//Greet Player
		System.out.printf("%n%n%n");
		System.out.println("     db    8888b.  Yb    dP 888888 88b 88 888888 88   88 88\"\"Yb 888888   .dPTY8 88 8b    d8 "); 
        System.out.println("    dPYb    8I  Yb  Yb  dP  88__   88Yb88   88   88   88 88__dP 88__     `Ybo.  88 88b  d88 "); 
        System.out.println("   dP__Yb   8I  dY   YbdP   88\"\"   88 Y88   88   Y8   8P 88\"Yb  88\"\"     . `Y8b 88 88YbdP88 ");
        System.out.println("  dP    Yb 8888Y      YP    888888 88  Y8   88   \"YbodP\" 88  Yb 888888    8bodP 88 88 YY 88 "); 
	    System.out.printf("%n%npress ENTER to continue");
		System.out.printf("%n%n%n");
	    Scanner enterContinue = new Scanner(System.in);
	    enterContinue.nextLine();
		clearScreen();
		hero();//call method to get Hero name
		heroBuilder();// call method to allocate hero stat points
		System.out.printf("%n%n\"Hmm... Methinks you're just the person for a rather rough job.\"");
		    enterContinue.nextLine();
		    clearScreen();
		System.out.printf("%n%n\"Monsters have been terrorizing the town more than usual. The wilderness is teeming with them.\"");
		    enterContinue.nextLine();
		    clearScreen();
		System.out.printf("%n%n\"They're ripe for the slaying, if you're interested. The city would be forever grateful.\"");
		    enterContinue.nextLine();
		    clearScreen();
		System.out.printf("%n%nAre you ready to venture forth? %n%n%n Enter to continue: ");//ask if player ready for monster encounter	
		    enterContinue.nextLine();
		    clearScreen();
		while (bigBossHealth > 0 && bossKey != 3){
			enemy = monsterSummon();
            result = combatCalculator (enemy, goblinHealth, goblinAttack, orcHealth, orcAttack, trollHealth, trollAttack, crimGuardHealth, crimGuardAttack);
		if (result == 1){
			clearScreen();
			System.out.printf("%n%nCongratulations! You've returned victorious!%n%n");
			System.out.printf("\"Are you ready to venture forth?\" %n%n%n Enter to continue: ");//ask if player ready for monster encounter	
		    enterContinue.nextLine();
		    clearScreen();
			heroHealth = healthRefresh;
		}		
		else if (result == 2){
			clearScreen();
			System.out.printf ("%n%nYou've sufferred a terrible fate...");
		}
		else if (result == 3){
			clearScreen();
			System.out.printf ("%n%nThe world will never sing of a victory that never happens...%n%n");
			System.out.printf("Are you ready to venture forth? %n%n%n Enter to continue: ");//ask if player ready for monster encounter	
		    enterContinue.nextLine();
		    clearScreen();
			heroHealth = healthRefresh;
		}
		else if (result == 4){
			++bossKey;
			clearScreen();
			System.out.printf("%n%nYou've vanquished a mighty foe! Huzzah!");
			enterContinue.nextLine();
			System.out.printf("%n%n\"The Crimson Guard's body turns to ash and dissipates, but a small violently red crystal is left behind. ");
			enterContinue.nextLine();
		    System.out.printf("%n%n\"You pocket the object and feel the urge to collect more.\"");
			enterContinue.nextLine();
			System.out.printf("%n%n\"Are you ready to venture forth?\" %n%n%n Enter to continue: \"");//ask if player ready for next monster encounter
			enterContinue.nextLine();
		    clearScreen();
			heroHealth = healthRefresh;
		}
		
		}//end of monster spawn loop
		if (bossKey == 3){//summons final encounter
			result = bossSummon();
		}
		if (result == 5){
			clearScreen();
			System.out.printf ("%n%nWith a screeching wail, the demon recoils as your blade finally finds its heart. It claws weakly at the sword protruding from its chest"); 
            System.out.printf ("%n%nbefore falling forward in a heap, your weapon buried beneath its still form. With the creature slain, the forest exudes a feeling of quiet");
            System.out.printf ("%n%ntranquility, as if nature itself is relieved at your success.");
            System.out.printf ("%n%n ENTER to continue");			
                enterContinue.nextLine();
                clearScreen();
            System.out.printf ("%n%nYou return to town a celebrity.");
			System.out.printf ("%n%n ENTER to continue");
			    enterContinue.nextLine();
                clearScreen();
            System.out.printf ("%n%nEventually, you settle down with your (husband/wife), have/adopt children, and live happily ever after (maybe).");
			System.out.printf ("%n%n ENTER to continue");
			    enterContinue.nextLine();
                clearScreen();
			System.out.printf ("%n%nYears later, tales are still told of the epic battle; the corpse of the Rage Demon stands statuesque as a testiment to the truth.");
			System.out.printf ("%n%nGAME OVER");
			System.out.printf ("%n%nENTER to close");
			    enterContinue.nextLine();
                clearScreen();
		    }
	}//end of main
//methods live in this space
    static void hero(){
	    System.out.printf("Greetings, traveller. Welcome to my inn! What is your name? %n%n");//Get Hero Name
		Scanner name = new Scanner(System.in);
		String heroName = name.nextLine();
		clearScreen();
		System.out.printf("Well met, " + heroName +". Tell me about yourself...%n%n");
	//Print Hero Stats Page and Allocate Points
    }
	static void heroBuilder(){
		while (statPoints >= 1){	//set character creation menu loop
			System.out.println("Health:" + heroHealth + " Attack:" + heroAtk + " Magic:" + heroMagic);
			System.out.println("");
			System.out.println("1) +10 Health");
			System.out.println("2) +1 Attack");
			System.out.println("3) +3 Magic");
			System.out.println("");	
			System.out.println("You have " + statPoints + " stat points remaining.");
	//Increment Hero Stats according to stat point distribution
		Scanner stats = new Scanner(System.in);
		int userStats = stats.nextInt();
		
			if (userStats == 1){
				heroHealth += 10;
				statPoints--;
				clearScreen();
			}
			else if (userStats == 2){
				heroAtk++;
				statPoints--;
				clearScreen();
			}
			else if (userStats == 3){
				heroMagic += 3;
				statPoints--;
				clearScreen();
			}
			else {
				clearScreen();
				System.out.printf("%n%nThat doesn't seem right... Try again. %n%n");
			}
		}//End of Creation Loop
		healthRefresh = heroHealth;
	}// End of Hero Creation Method
	static int monsterSummon(){//Method for randomly selecting monster
		SecureRandom randomMonster = new SecureRandom();//declare rng for monster selection
			int monsterMarker = randomMonster.nextInt(4);//RNG selects a monster marker
				if(monsterMarker == 0){//assign monster marker - 0
					monsterRoll = 0;
					//Invoke CombatCalculator w/ Argument object "enemyGoblin"
				}
				else if(monsterMarker == 1){//assign monster marker - 1
					monsterRoll = 1;
					//Invoke CombatCalculator w/ Argument object "enemyOrc"
				}
				else if(monsterMarker == 2){//assign monster marker - 2
					monsterRoll = 2;
					//Invoke CombatCalculator w/ Argument object "enemyTroll"
				}
				else{//assign monster marker - 3
					monsterRoll = 3;
					//Invoke CombatCalculator w/ Argument object "enemyCrimGuard"
			}
		return monsterRoll;
	}
	
	public static int combatCalculator(int foe, int goblinHealth, int goblinAttack, int orcHealth, int orcAttack, int trollHealth, int trollAttack, int crimGuardHealth, int crimGuardAttack){
		
		SecureRandom randomStats = new SecureRandom();//declare rng for monster stat rolls
		if (foe == 0){
			int playerAction = 0;
			goblinHealth = randomStats.nextInt(20) + 80;//set health range
			goblinAttack = (randomStats.nextInt(2) + 3) + 4;//set attack range
	        while (goblinHealth > 0 && heroHealth > 0  && playerAction != 4){//implement goblin combat
		        System.out.println ("A Goblin appears!");//Print the Monster's Name
				System.out.println ("The monster's HP: " + goblinHealth);//Print the Monster's Health
				System.out.println ("Your HP: " + heroHealth);  //Print the Player's Health
				System.out.println ("Your Magic Strength: " + heroMagic);//Print the Player's MP
				System.out.println (" ");
				System.out.println ("Combat Options:");/*Combat Menu Prompt*/
				System.out.println ("   1.) Sword Attack"); //Print Option 1
				System.out.println ("   2.) Cast Spell");//Print Option 2
				System.out.println ("   3.) Prepare Spell");//Print Option 3
				System.out.println ("   4.) Run Away");//Print Option 4
				System.out.print("How do you proceed?  ");//Prompt Player for Input
		    
			    Scanner input = new Scanner(System.in);
		        playerAction = input.nextInt();//Declare Variable for User Input and acquire value from Scanner
			    System.out.println("");
				
		        if (playerAction == 1){ //If player chose option 1//Sword Attack
                    clearScreen();				
			        goblinHealth = goblinHealth - heroAtk;
			        System.out.println("You score a blow against the Goblin with your blade!");
				    System.out.println("");
				    System.out.println("");
					heroHealth = heroHealth - goblinAttack;
		        }
		        else if (playerAction == 2){//Weaken Spell
				    clearScreen();
				    if (magicCharge >= 1){
		    	        goblinHealth = goblinHealth - (goblinHealth / 4);//Calculate Monster Damage--1/4 of health is lost
					    goblinAttack = goblinAttack * ((1 / 5) + (1 / (heroMagic / 3)));//Weaken lowers the Goblin's attack--Amount depends on Magic Power
					    magicCharge = magicCharge - 1;//Reduce Player MP by the spell cost using subtraction					
		                System.out.println("You cast the \"Weaken\" spell!"); 
	    			    System.out.println("");
				    }	
				    else{	
				        System.out.println("That spell isn't prepared!");//Print Spell Prep Message
					    System.out.println("");
					}
				}					
			    else if (playerAction == 3){//Spell Preparation
				    clearScreen();
			        magicCharge = magicCharge + 1;//Increment magic points and update player's MP using addition
			        System.out.println("You quiet your mind and prepare to cast the \"Weaken\" spell.");
				    System.out.println("");
					heroHealth = heroHealth - goblinAttack;
		        }
				else if (playerAction == 4){
					clearScreen();
		    	    System.out.println("You flee to safety to rest and recover.");//Print retreat text
				    System.out.println("");
		        }
		        else{//Else the player chose incorrectly
				    clearScreen();
		    	    System.out.println("I don't understand that command...");//Print error message:"I don't understand that command."
				    System.out.println("");
		        }
			    if (goblinHealth <= 0){//victory basic
				    outcome = 1;
			    }
		        else if (heroHealth <= 0) {//player death
			        outcome = 2;
			    }
			    else if (playerAction == 4){//player flee
				    outcome = 3;
			    }
			}//end of Goblin Combat
		}
		else if (foe == 1){//implement orc
			int playerAction = 0;
			orcHealth = randomStats.nextInt(20) + 150;//set health range
			orcAttack = randomStats.nextInt(6) + 10;//set attack range
	        while (orcHealth > 0 && heroHealth > 0  && playerAction != 4){//implement orc combat
		        System.out.println ("An Orc appears!");//Print the Monster's Name
				System.out.println ("The monster's HP: " + orcHealth);//Print the Monster's Health
				System.out.println ("Your HP: " + heroHealth);  //Print the Player's Health
				System.out.println ("Your Attack: " + heroAtk); //Print Player's Attack
				System.out.println ("Your Magic Strength: " + heroMagic);//Print the Player's MP
				System.out.println (" ");
				System.out.println ("Combat Options:");/*Combat Menu Prompt*/
				System.out.println ("   1.) Sword Attack"); //Print Option 1
				System.out.println ("   2.) Cast Spell");//Print Option 2
				System.out.println ("   3.) Prepare Spell");//Print Option 3
				System.out.println ("   4.) Run Away");//Print Option 4
				System.out.print("How do you proceed?  ");//Prompt Player for Input
		    
			    Scanner input = new Scanner(System.in);
		        playerAction = input.nextInt();//Declare Variable for User Input and acquire value from Scanner
			    System.out.println("");
				
		        if (playerAction == 1){ //If player chose option 1//Sword Attack 
				    clearScreen();
			        orcHealth = orcHealth - heroAtk;
			        System.out.println("You score a blow against the Orc with your blade!");
				    System.out.println("");
				    System.out.println("");
					heroHealth = heroHealth - orcAttack;
		        }
		        else if (playerAction == 2){//Weaken Spell
				    clearScreen();
				    if (magicCharge >= 1){
		    	        orcHealth = orcHealth - (orcHealth / 4);//Calculate Monster Damage--1/4 of health is lost
					    orcAttack = orcAttack * ((1 / 5) + (1 / (heroMagic / 3)));//Weaken lowers the Monster's attack--Amount depends on Magic Power
					    magicCharge = magicCharge - 1;//Reduce Player MP by the spell cost using subtraction					
		                System.out.println("You cast the \"Weaken\" spell!"); 
	    			    System.out.println("");
				    }	
				    else{	
				        System.out.println("That spell isn't prepared!");//Print Spell Prep Message
					    System.out.println("");
					}
				}					
			    else if (playerAction == 3){//Spell Preparation
				    clearScreen();
			        magicCharge = magicCharge + 1;//Increment magic points and update player's MP using addition
			        System.out.println("You quiet your mind and prepare to cast the \"Weaken\" spell.");
				    System.out.println("");
					heroHealth = heroHealth - orcAttack;
		        }
				else if (playerAction == 4){
					clearScreen();
		    	    System.out.println("You flee to safety to rest and recover.");//Print retreat text
				    System.out.println("");
		        }
		        else{//Else the player chose incorrectly
				    clearScreen();
		    	    System.out.println("I don't understand that command...");//Print error message:"I don't understand that command."
				    System.out.println("");
		        }
			if (orcHealth <= 0){//victory basic
				outcome = 1;
			}
		    else if (heroHealth <= 0) {//player death
			    outcome = 2;
			}
			else if (playerAction == 4){
				outcome = 3;
			}
			}
		}
		else if (foe == 2){//implement troll
			int playerAction = 0;
			trollHealth = randomStats.nextInt(25) + 225;//set health range
			trollAttack = randomStats.nextInt(7) + 17;//set attack range
	        while (trollHealth > 0 && heroHealth >= 0  && playerAction != 4){//implement troll combat
		        System.out.println ("A Troll appears!");//Print the Monster's Name
				System.out.println ("The monster's HP: " + trollHealth);//Print the Monster's Health
				System.out.println ("Your HP: " + heroHealth);  //Print the Player's Health
				System.out.println ("Your Attack: " + heroAtk); //Print Player's Attack
				System.out.println ("Your Magic Strength: " + heroMagic);//Print the Player's MP
				System.out.println (" ");
				System.out.println ("Combat Options:");/*Combat Menu Prompt*/
				System.out.println ("   1.) Sword Attack"); //Print Option 1
				System.out.println ("   2.) Cast Spell");//Print Option 2
				System.out.println ("   3.) Prepare Spell");//Print Option 3
				System.out.println ("   4.) Run Away");//Print Option 4
				System.out.print("How do you proceed?  ");//Prompt Player for Input
		    
			    Scanner input = new Scanner(System.in);
		        playerAction = input.nextInt();//Declare Variable for User Input and acquire value from Scanner
			    System.out.println("");
				
		        if (playerAction == 1){ //If player chose option 1//Sword Attack 
				    clearScreen();
			        trollHealth = trollHealth - heroAtk;
			        System.out.println("You score a blow against the Troll with your blade!");
				    System.out.println("");
				    System.out.println("");
					heroHealth = heroHealth - trollAttack;
		        }
		        else if (playerAction == 2){//Weaken Spell
				    clearScreen();
				    if (magicCharge >= 1){
		    	        trollHealth = trollHealth - (trollHealth / 4);//Calculate Monster Damage--1/4 of health is lost
					    trollAttack = trollAttack * ((1 / 5 )+ (1 / (heroMagic / 3)));//Weaken lowers the Monster's attack--Amount depends on Magic Power
					    magicCharge = magicCharge - 1;//Reduce Player MP by the spell cost using subtraction					
		                System.out.println("You cast the \"Weaken\" spell!"); 
	    			    System.out.println("");
				    }	
				    else{	
				        System.out.println("That spell isn't prepared!");//Print Spell Prep Message
					    System.out.println("");
					}
				}					
			    else if (playerAction == 3){//Spell Preparation
				    clearScreen();
			        magicCharge = magicCharge + 1;//Increment magic points and update player's MP using addition
			        System.out.println("You quiet your mind and prepare to cast the \"Weaken\" spell.");
				    System.out.println("");
					heroHealth = heroHealth - trollAttack;
		        }
				else if (playerAction == 4){
					clearScreen();
		    	    System.out.println("You flee to safety to rest and recover.");//Print retreat text
				    System.out.println("");
		        }
		        else{//Else the player chose incorrectly
				    clearScreen();
		    	    System.out.println("I don't understand that command...");//Print error message:"I don't understand that command."
				    System.out.println("");
		        }
			if (trollHealth <= 0){//victory basic
				outcome = 1;
			}
		    else if (heroHealth <= 0) {//player death
			    outcome = 2;
			}
			else if (playerAction == 4){
				outcome = 3;
			}
			}
		}
		else if (foe == 3){//implement crimson guard
			int playerAction = 0;
			crimGuardHealth = randomStats.nextInt(30) + 250;//set health range
			crimGuardAttack = randomStats.nextInt(7) + 20;//set attack range
	        while (crimGuardHealth > 0 && heroHealth > 0  && playerAction != 4){//implement Crimson Guard combat
		        System.out.println ("A nightmarish Crimson Guard appears!");//Print the Monster's Name
				System.out.println ("The monster's HP: " + crimGuardHealth);//Print the Monster's Health
				System.out.println ("Your HP: " + heroHealth);  //Print the Player's Health
				System.out.println ("Your Attack: " + heroAtk); //Print Player's Attack
				System.out.println ("Your Magic Strength: " + heroMagic);//Print the Player's MP
				System.out.println (" ");
				System.out.println ("Combat Options:");/*Combat Menu Prompt*/
				System.out.println ("   1.) Sword Attack"); //Print Option 1
				System.out.println ("   2.) Cast Spell");//Print Option 2
				System.out.println ("   3.) Prepare Spell");//Print Option 3
				System.out.println ("   4.) Run Away");//Print Option 4
				System.out.print("How do you proceed?  ");//Prompt Player for Input
		    
			    Scanner input = new Scanner(System.in);
		        playerAction = input.nextInt();//Declare Variable for User Input and acquire value from Scanner
			    System.out.println("");
				
		        if (playerAction == 1){ //If player chose option 1//Sword Attack
                    clearScreen();				
			        crimGuardHealth = crimGuardHealth - heroAtk;
			        System.out.println("You score a blow against the Crimson Guard with your blade!");
				    System.out.println("");
				    System.out.println("");
					heroHealth = heroHealth - crimGuardAttack;
		        }
		        else if (playerAction == 2){//Weaken Spell
				    clearScreen();
				    if (magicCharge >= 1){
		    	        crimGuardHealth = crimGuardHealth - (crimGuardHealth / 4);//Calculate Monster Damage--1/4 of health is lost
					    crimGuardAttack = crimGuardAttack * ((1 / 5) + (1 / (heroMagic / 3)));//Weaken lowers the Monster's attack--Amount depends on Magic Power
					    magicCharge = magicCharge - 1;//Reduce Player MP by the spell cost using subtraction					
		                System.out.println("You cast the \"Weaken\" spell!"); 
	    			    System.out.println("");
				    }	
				    else{	
				        System.out.println("That spell isn't prepared!");//Print Spell Prep Message
					    System.out.println("");
					}
				}					
			    else if (playerAction == 3){//Spell Preparation
				    clearScreen();
			        magicCharge = magicCharge + 1;//Increment magic points and update player's MP using addition
			        System.out.println("You quiet your mind and prepare to cast the \"Weaken\" spell.");
				    System.out.println("");
					heroHealth = heroHealth - crimGuardAttack;
		        }
				else if (playerAction == 4){
					clearScreen();
		    	    System.out.println("You flee to safety to rest and recover.");//Print retreat text
				    System.out.println("");
		        }
		        else{//Else the player chose incorrectly
				    clearScreen();
		    	    System.out.println("I don't understand that command...");//Print error message:"I don't understand that command."
				    System.out.println("");
		        }
			if (crimGuardHealth <= 0){//Crimson Victory
				outcome = 4;
			}
		    else if (heroHealth <= 0) {//player death
			    outcome = 2;
			}
			else if (playerAction == 4){
				outcome = 3;
			}
			}
		}
		
		return outcome;//returns result of combat algorithm to main method
	}
	public static int bossSummon(){
		    Scanner enterContinue = new Scanner(System.in);
			SecureRandom randomStats = new SecureRandom();//declare rng for monster stat rolls
			int playerAction = 0;
			bigBossHealth = randomStats.nextInt(35) + 300;//set health range
			bigBossAttack = randomStats.nextInt(10) + 30;//set attack range
	            System.out.printf("%n%nThe three crystal fragments that you've collected pulse with a deep crimson light and begin to fuse.%n%n");//Print boss "cinematic"
	            System.out.printf("The wilderness, once raucous with the sound of battle, has grown suddenly silent and still.%n%n Enter to Continue");
				enterContinue.nextLine();
		        clearScreen();
				System.out.printf("%n%nThe throbbing light within the fusing crystal grows brighter and quickens as the shards become one. %n%n");
	            System.out.printf("You notice that the air has become charged with fierce magical energy.%n%n Enter to Continue");
				enterContinue.nextLine();
		        clearScreen();
				System.out.printf("%n%nWhen its last few creeping imperfections disappear, the crystal flies from your hand into the clearing before you.%n%n Enter to Continue");
				enterContinue.nextLine();
		        clearScreen();
				System.out.printf("%n%nThe air rips as a demonic portal opens.%n%n Enter to Continue");
	            enterContinue.nextLine();
		        clearScreen();
	            System.out.printf("%n%nAn indescribable presence approaches...%n%n Enter to Continue");
	            enterContinue.nextLine();
		        clearScreen(); 
			while (bigBossHealth > 0 && heroHealth > 0){//implement boss combat
		        
	            System.out.println("You've encountered a Rage Demon!");//Print the Monster's Name
				System.out.println ("The monster's HP: " + bigBossHealth);//Print the Monster's Health
				System.out.println ("Your HP: " + heroHealth);  //Print the Player's Health
				System.out.println ("Your Magic Strength: " + heroMagic);//Print the Player's MP
				System.out.println (" ");
				System.out.println ("Combat Options:");/*Combat Menu Prompt*/
				System.out.println ("   1.) Sword Attack"); //Print Option 1
				System.out.println ("   2.) Cast Spell");//Print Option 2
				System.out.println ("   3.) Prepare Spell");//Print Option 3
				System.out.println ("   4.) Run Away");//Print Option 4
				System.out.print("How do you proceed?  ");//Prompt Player for Input
		    
			    Scanner input = new Scanner(System.in);
		        playerAction = input.nextInt();//Declare Variable for User Input and acquire value from Scanner
			    System.out.println("");
				
		        if (playerAction == 1){ //If player chose option 1//Sword Attack
                    clearScreen();				
			        bigBossHealth = bigBossHealth - heroAtk;
			        System.out.println("You score a blow against the demon with your blade!");
				    System.out.println("");
				    System.out.println("");
					clearScreen();
					heroHealth = heroHealth - bigBossAttack;
		        }
		        else if (playerAction == 2){//Weaken Spell
				    clearScreen();
				    if (magicCharge >= 1){
		    	        bigBossHealth = bigBossHealth - (bigBossHealth / 4);//Calculate Monster Damage--1/4 of health is lost
					    bigBossAttack = bigBossAttack * ((1 / 5) + (1 / (heroMagic / 3)));//Weaken lowers the Goblin's attack--Amount depends on Magic Power
					    magicCharge = magicCharge - 1;//Reduce Player MP by the spell cost using subtraction					
		                System.out.println("You cast the \"Weaken\" spell!"); 
	    			    System.out.println("");
				    }	
				    else{	
				        System.out.println("That spell isn't prepared!");//Print Spell Prep Message
					    System.out.println("");
					}
				}					
			    else if (playerAction == 3){//Spell Preparation
				    clearScreen();
			        magicCharge = magicCharge + 1;//Increment magic points and update player's MP using addition
			        System.out.println("You quiet your mind and prepare to cast the \"Weaken\" spell.");
				    System.out.println("");
					heroHealth = heroHealth - bigBossAttack;
		        }
				else if (playerAction == 4){
					clearScreen();
		    	    System.out.println("There is no escape!");//Print retreat text
				    System.out.println("");
		        }
		        else{//Else the player chose incorrectly
				    clearScreen();
		    	    System.out.println("I don't understand that command...");//Print error message:"I don't understand that command."
				    System.out.println("");
		        }
			    
				if (bigBossHealth <= 0){//Boss victory
				    outcome = 5;
			    }
				else if (heroHealth <= 0) {//player death
			    outcome = 2;
				}
			}
			return outcome;
}
	
	
	static void clearScreen(){//clears screen of old text
	    final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
    }//End of ClearScreen Method
	
}