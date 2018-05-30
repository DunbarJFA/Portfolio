public class GameCharacter{
		
	protected int health;
	protected int attackPower;
	
		public GameCharacter(int health, int attackPower){
			this.health = health;
			this.attackPower = attackPower;
		}//end of constructor
		
		protected void basicAttack(GameCharacter target){
			target.takeDamage(this.attackPower);
		}//end of basicattack
		
		protected void takeTurn(GameCharacter target){
			basicAttack(target);
		}//end of taketurn
		
		protected void takeDamage(int damage){
			this.health -= damage;
		}//end of takedamage
		
		public int getAttack(){
			return this.attackPower;
		}//end of getattack
		
		public int getHealth(){
			return this.health;
		}//end of gethealth
	
		public void setHealth(int newHealth) {
			this.health = newHealth;
		}//end of setHealth
		/*
		public String toString(){
			return this.health + this.attackPower;
		}//end of tostring
		*/
}//end of class Game character