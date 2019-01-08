public class Room{
	//exits
	Room north;
	Room south;
	Room east;
	Room west;
	//description setter and getter
	private String description;
	private String roomName;
	private String northExit;
	private String southExit;
	private String eastExit;
	private String westExit;
	String exitList;
	
	public Room(String description, String name){
		this.description = description;
		this.roomName = name;
		north = null;
		south = null;
		east = null;
		west = null;
	}//end of constructor
	public Room(){
		north = null;
		south = null;
		east = null;
		west = null;
	}//overloaded constructor
		//north setter
		public String toString(){
			return this.roomName;
		}
		public void setNorth(Room north){
			this.north = north;
		}
		//north getter
		public Room getNorth(){
			return north;
		}
		//south setter
		public void setSouth(Room south){
			this.south = south;
		}
		//south getter
		public Room getSouth(){
			return south;
		}
		//east setter
		public void setEast(Room east){
			this.east = east;
		}
		//east getter
		public Room getEast(){
			return east;
		}
		//west setter
		public void setWest(Room west){
			this.west = west;
		}
		//west getter
		public Room getWest(){
			return west;
		}
		//description getter
		public String getDescription(){
			return description;
		}
		//Exits SETTER AND GETTER
		public void setExits(Room north, Room south, Room east, Room west){
			northExit = ("N: " + north.roomName + " ");
				if (north.roomName == roomName){
					northExit = ("");
				}
			southExit = ("S: " + south.roomName + " ");
				if (south.roomName == roomName){
					southExit = ("");
				}
			eastExit = ("E: " + east.roomName + " ");
				if (east.roomName == roomName){
					eastExit = ("");
				}
			westExit = ("W: " + west.roomName + " ");
				if (west.roomName == roomName){
					westExit = (""); 
				}
			exitList = (northExit + southExit + eastExit + westExit);
		}
}//end of class Room