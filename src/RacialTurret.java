import java.awt.Color;
import java.awt.Graphics;

public class RacialTurret extends Turret {

	/*Racial turrets(CC, depends on race):
		Earth - Enchamber(2.0 seconds)
		Water - Slow (1.5 seconds)
		Air - Knockback (1.0 seconds)
		Fire - Stun (0.5 seconds)*/
	
	private static int playerRace;
	private static int EARTH = 0;
	private static int WATER = 1;
	private static int AIR = 2;
	//private static int FIRE = 3;
	
	private static int INITIALCOST = 70;
	
	public RacialTurret(int x, int y, int pRace) {
		super(x, y);
		playerRace = pRace;
		range = 164;
		totalCost += INITIALCOST;
		firingCoolDown = 32;
	}
	
	public String turretType(){
		return "Racial";
	}
	
	public void upgrade(){
		
	}
	
	public void drawTurret(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(locX - 3, locY - 3, size+6,size+6);
		//color = new Color(250, 246, 200);
		color = new Color(30, 30, 30);
		g.setColor(color);
		g.fillRect(locX - 2, locY - 2, size+4, size+4);
		if(playerRace == EARTH){
			color = new Color(191, 120, 57);
		}
		else if(playerRace == WATER){
			color = new Color(76, 113, 224);
		}
		else if(playerRace == AIR){
			color = new Color(250, 250, 250);
		}
		else{
			color = new Color(255, 125, 74);
		}
		g.setColor(color);
		g.fillOval(locX, locY, size, size);
	}
}
