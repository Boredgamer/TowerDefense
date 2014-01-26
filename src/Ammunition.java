import java.awt.*;

public class Ammunition {

	private static int EARTH = 0;
	private static int WATER = 1;
	private static int AIR = 2;
	//private static int FIRE = 3;
	
	private int bulletX;
	private int bulletY;
	private int bulletSize;
	private Color bulletColor;
	private int enemyX;
	private int enemyY;
	private int bulletDamage = 0;
	private boolean packageRecieved = false;
	
	public Ammunition(int originX, int originY, int trackedX, int trackedY, String type, int playerRace) {
		ammuntionType(type, playerRace);
		bulletX = originX-bulletSize;
		bulletY = originY-bulletSize;
		enemyX = trackedX;
		enemyY = trackedY;
	}
	
	public void ammuntionType(String type, int playerRace){
		if (type == "Gun"){
			bulletColor = Color.LIGHT_GRAY;
			bulletSize = 8;
			bulletDamage = 20;
		}
		else if (type == "Bomb"){
			bulletColor = Color.DARK_GRAY;
			bulletSize = 14;
			bulletDamage = 40;
		}
		else if (type == "Ray"){
			bulletColor = Color.CYAN;
			bulletSize = 10;
			bulletDamage = 30;
		}
		else if (type == "Racial"){
			if (playerRace == EARTH){
				bulletColor = new Color(191, 120, 57);
			}
			else if (playerRace == WATER){
				bulletColor = new Color(76, 113, 224);
			}
			else if(playerRace == AIR){
				bulletColor = new Color(250, 250, 250);
			}
			else{
				bulletColor = new Color(255, 125, 74);
			}
			bulletSize = 12;
		}
	}
	
	public int bulletPower(){
		return bulletDamage;
	}
	
	public void targetTracker(int trackedX, int trackedY){
		enemyX = trackedX;
		enemyY = trackedY;
	}
	
	public boolean bulletHit(){
		return packageRecieved;
	}
	
	public void move(){
		if (bulletX + bulletSize/2 < enemyX){
			bulletX++;
		}
		if (bulletX + bulletSize/2 > enemyX){
			bulletX--;
		}
		if (bulletY + bulletSize/2 < enemyY){
			bulletY++;
		}
		if (bulletY + bulletSize/2 > enemyY){
			bulletY--;
		}
		if (bulletX + bulletSize/2 == enemyX && bulletY + bulletSize/2 == enemyY){
			packageRecieved = true;
		}
	}
	
	public void drawBullet(Graphics g){
		g.setColor(Color.BLACK);
		g.fillOval(bulletX, bulletY, bulletSize, bulletSize);
		g.setColor(bulletColor);
		g.fillOval(bulletX+1, bulletY+1, bulletSize-2, bulletSize-2);
	}

}
