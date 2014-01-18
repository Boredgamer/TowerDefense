
public class Player {
	
	private static int STARTING_GOLD = 100;
	private int gold;
	private int lives = 100;
	
	private int playerRace;
	private static int EARTH = 0;
	private static int WATER = 1;
	private static int AIR = 2;
	private static int FIRE = 3;
	
	public Player(int pRace){
		playerRace = pRace;
		gold = STARTING_GOLD;
	}
	
	public void loseALife(boolean passed){
		if (passed == true){
			lives--;
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
