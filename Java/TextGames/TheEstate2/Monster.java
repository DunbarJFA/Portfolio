
public class Monster extends GameCharacter{
	
	private static int exp;
	private int woundCount;
	public static boolean wounded;
	
	private void wound(){
		wounded = true;
		
		if (wounded = true){
			woundCount++;
			if (woundCount == 1){
				this.setHealth(this.getHealth() - 500);
			}
			else if (woundCount == 2){
				this.setHealth(this.getHealth() - 500);
			}
			else if (woundCount == 3){
				this.setHealth(this.getHealth() - 500);
			}
			wounded = false;
		}
	}	
	public static int getExp(){
		return exp;
	}

	public Monster(){
		super(2000, 7);
		this.exp = 1000000;//health carries over from encounter to encounter
		wounded = false;//use a weapon to quickly cause "wounds"
		woundCount = 0;
	}
	
}//end of subclass Monster