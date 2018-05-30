public class Dungeon{
		static Room foyer = new Room("You cross the threshold of the manor and emerge into the foyer. %n%nIt's a cozy room resplendent with marble floors and magnificent paintings. %n%n%nOn the eastern wall, you notice a large curtained archway decorated in tile with a water motif. %n%nThere is a room to the north that appears to be prepared for a gathering.		%n%n%nPress \"q\" to quit. " , "Foyer");
			//Foyer	
		static Room opulentBath = new Room("You are in an enormous room decorated from floor to ceiling with elaborate tilework and statues of water-bearing goddesses. %n%nA shallow circular pool lies in the center of the room. %n%nTwo bathrooms and a curtained room labeled \"Parfum\" lie on the west wall. %n%n%nA set of large wooden banquet doors is to the North.			%n%n%nPress \"q\" to quit. " , "Opulent Bath");
			//Opulent Bath
		static Room masterBed = new Room("You enter a room dimly-lit by brass lamps. %n%nA dark mahogany desk sits in the middle on the room; a mess of scattered books lies atop it beneath the soft glow of a table lamp. %n%nAn obnoxiously huge and ornately gilded canopy bed sits on a dais at the rear of the room.			%n%n%nPress \"q\" to quit. " , "Master Bed");
			//Master Bed
		static Room kitchen = new Room("You emerge into a utilitarian, yet noticeably well-stocked kitchen. %n%nA steel galley table runs the length of the room while pots and pans of all shapes and sizes are suspended overhead. %n%nDespite the pervasive residual heat and smell of a recently-cooked meal, the room is immaculately clean. %n%n%nThere's a set of large banquet doors to the South and a small door of simple design to the North.				%n%n%nPress \"q\" to quit.  " , "Kitchen");
			//Kitchen
		static Room diningRoom = new Room("You've come upon a decadent sight. %n%nA banquet table built for a throng sits at the center of the room loaded with a bounty of freshly-prepared meats, cheeses, soups, vegetables, and glorious sweets. %n%n%nTo the North is a greenhouse door to the rear yard. %n%nTo the West is a set of masterfully-carved French doors. %n%nTo the East is an archway leading to an open room. %n%nTo the South is the house entranceway. 			%n%n%nPress \"q\" to quit." , "Dining Room");
			//Dining Room
		static Room drawingRoom = new Room("You've found a large room built to offer every comfort, likely for entertaining guests. %n%nPlush chairs and couches suround a central space and the walls are lined with rows upon rows of books. %n%nAs your gaze drifts upward, you discover that this room is actually multi-storied. %n%n%nTo the North is a massive stone staircase to the next level. %n%nTo the South is a small door of rich, dark wood.			%n%n%nPress \"q\" to quit." , "Drawing Room");
			//Drawing Room
		static Room servantsQuarters = new Room("You enter a very short hallway flanked by four identical, modestly-furnished bedrooms. %n%nThey appear rather drab, yet comfortable... %n%nThese appear to belong to the serving staff.				%n%n%nPress \"q\" to quit. " , "Servants' Quarters");
			//Servant's Quarters
		static Room manorGrounds = new Room("You exit through the enormous glass rear entry to the home and step out onto the grounds of the manor. %n%nAcres of flowers and vegetables sit laid out in an intricate maze of swirls throughout the magnificently oak-lined property. %n%nIt's truly a treat for the eyes.				%n%n%nPress \"q\" to quit." , "Manor Gardens");
			//Manor Grounds
		static Room atrium = new Room("You've ascended the Drawing Room staircase to its upper level. %n%nMore plush seating surrounds the central railing. There's no other furniture about, however, as anything more would only serve to obstruct the stupendous view. %n%nThe walls and ceiling of the upper level make up a giant glass dome, opening this atrium to the sky.				%n%n%nPress \"q\" to quit. " , "Atrium");
			//Atrium
		static Room room0 = new Room("Welcome to the Thunderdome, BIOTCH!!!" , "Foyer");
		//getRoom0 method
	
		public static Room getRoom0(){
			return foyer;
		}
	public Dungeon(){//constructor
		//START OF ROOM CONNECTIONS
			foyer.setNorth(diningRoom);							
			foyer.setWest(opulentBath);	
			foyer.setEast(foyer);
			foyer.setSouth(foyer);
			foyer.setExits(foyer.north, foyer.south, foyer.east, foyer.west);									
		
			opulentBath.setNorth(kitchen);
			opulentBath.setEast(foyer);
			opulentBath.setWest(opulentBath);
			opulentBath.setSouth(opulentBath);
			opulentBath.setExits(opulentBath.north, opulentBath.south, opulentBath.east, opulentBath.west);
		
			masterBed.setNorth(drawingRoom);
			masterBed.setSouth(masterBed);
			masterBed.setEast(masterBed);
			masterBed.setWest(masterBed);
			masterBed.setExits(masterBed.north, masterBed.south, masterBed.east, masterBed.west);								
			
			kitchen.setWest(servantsQuarters);														
			kitchen.setEast(diningRoom);														
			kitchen.setSouth(opulentBath);
			kitchen.setNorth(kitchen);
			kitchen.setExits(kitchen.north, kitchen.south, kitchen.east, kitchen.west);
			
			diningRoom.setNorth(manorGrounds);					
			diningRoom.setSouth(foyer);							
			diningRoom.setEast(drawingRoom);					
			diningRoom.setWest(kitchen);				
			diningRoom.setExits(diningRoom.north, diningRoom.south, diningRoom.east, diningRoom.west);
			
			drawingRoom.setNorth(atrium);	
			drawingRoom.setSouth(masterBed);
			drawingRoom.setWest(diningRoom);
			drawingRoom.setEast(drawingRoom);
			drawingRoom.setExits(drawingRoom.north, drawingRoom.south, drawingRoom.east, drawingRoom.west);
			
			atrium.setSouth(drawingRoom);
			atrium.setNorth(atrium);
			atrium.setEast(atrium);
			atrium.setWest(atrium);
			atrium.setExits(atrium.north, atrium.south, atrium.east, atrium.west);									
			
			manorGrounds.setSouth(diningRoom);
			manorGrounds.setNorth(manorGrounds);
			manorGrounds.setEast(manorGrounds);
			manorGrounds.setWest(manorGrounds);
			manorGrounds.setExits(manorGrounds.north, manorGrounds.south, manorGrounds.east, manorGrounds.west);
			
			servantsQuarters.setEast(kitchen);
			servantsQuarters.setNorth(servantsQuarters);
			servantsQuarters.setSouth(servantsQuarters);
			servantsQuarters.setWest(servantsQuarters);
			servantsQuarters.setExits(servantsQuarters.north, servantsQuarters.south, servantsQuarters.east, servantsQuarters.west);
		//END OF ROOM CONNECTIONS
	}//end of constructor
	
}//end of class Dungeon