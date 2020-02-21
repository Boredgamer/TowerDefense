import java.awt.*;

public class RacialTurret extends Turret {

	/*Racial turrets (CC, depends on race):
		Earth - Enchamber(2.0 seconds)
		Water - Slow (1.5 seconds)
		Air - Knockback (1.0 seconds)
		Fire - Stun (0.5 seconds)
		
		//Increased duration of CC
		//CC'd enemies take more damage (even Earth)
		 
		*Scale attack speed or CC duration  with racialTurretsPlaced?
		*Alternatively, give global CC player ability based on race (like freeze all)
		
		*/
	
	private int playerRace;
	private final int EARTH = 0;
	private final int WATER = 1;
	private final int WIND = 2;
	//private static int FIRE = 3;
	
	private final int INITIALCOST = 75;
	private final int INITIALCOOLDOWN = 250;
	
	public RacialTurret(int x, int y, int pRace) {
		super(x, y);
		playerRace = pRace;
		range = 174;
		totalCost += INITIALCOST;
		firingCoolDown = INITIALCOOLDOWN;
	}
	
	public String turretType(){
		return "Racial";
	}
	
	//Draws the Tooltip
	public void drawRolloverTooltip(Graphics g, int rt){	
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("Racial Turret", 605, 305);
		g.setFont(new Font("Arial", Font.PLAIN, 18));
		g.drawString("Cost: "+totalCost +" Gold", 605, 330);
		g.drawString("Range: Moderate", 605, 355);
		g.drawString("Damage: None", 605, 380);
		g.drawString("Attack Speed: Very Low", 605, 405);
		if(playerRace == EARTH){
			g.drawString("Special: Entomb", 605, 430);
		}
		else if(playerRace == WATER){
			g.drawString("Special: Slow", 605, 430);
		}
		else if(playerRace == WIND){
			g.drawString("Special: Knockback", 605, 430);
		}
		else{
			g.drawString("Special: Stun", 605, 430);
		}
		g.drawString("Total: " + rt +"/10", 605, 455);
	}
	
	public void drawTurret(Graphics g, boolean faster){
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
		else if(playerRace == WIND){
			color = new Color(250, 250, 250);
		}
		else{
			color = new Color(255, 125, 74);
		}
		g.setColor(color);
		g.fillOval(locX, locY, size, size);
		if (faster){
			Graphics2D g2 = (Graphics2D) g;
			g.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(5));
			g.drawLine(locX+size/2, locY+size/2-2, locX+size/2 + 4, locY+size/2+2);
			g.drawLine(locX+size/2, locY+size/2-2, locX+size/2 - 4, locY+size/2+2);
			g.setColor(Color.WHITE);
			g2.setStroke(new BasicStroke(2));
			g.drawLine(locX+size/2, locY+size/2-2, locX+size/2 + 4, locY+size/2+2);
			g.drawLine(locX+size/2, locY+size/2-2, locX+size/2 - 4, locY+size/2+2);
		}
	}
}
