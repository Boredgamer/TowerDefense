
public class Player {
	
	private static int STARTING_GOLD = 105;
	private int gold;
	private int lives = 100;
	
	public Player(){
		gold = STARTING_GOLD;
	}
	
	public void loseALife(boolean passed, boolean boss){
		if (passed == true){
			if (!boss){
				lives--;
			}
			else{
				lives -= 10;
			}
		}
		if (lives <= 0){
			lives = 0;
		}
	}
	
	public int getLives(){
		return lives;
	}
	
	public int getGold(){
		return gold;
	}
	
	public void moneyLoss(int cost){
		gold -= cost;
	}
	
	public int moneyGain(int pay){
		gold += pay;
		return gold;
	}

}
