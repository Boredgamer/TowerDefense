import java.awt.*;

public class RayTurret extends Turret {
	
	//Ray (Up to 3 beams at once)
		//Increases damage and attack speed over time 
		//Doubles amount of beams 

	private final int INITIALCOST =  75;
	private final int INITIALCOOLDOWN = 150;

	public RayTurret(int x, int y) {
		super(x, y);
		range = 154;
		totalCost += INITIALCOST;
		firingCoolDown = INITIALCOOLDOWN;
		maximumShots = 3;
	}
	
	public String turretType(){
		return "Ray";
	}
	
	//Draws the Tooltip
	public void drawRolloverTooltip(Graphics g){	
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("Ray Turret", 605, 305);
		g.setFont(new Font("Arial", Font.PLAIN, 18));
		g.drawString("Cost: "+totalCost +" Gold", 605, 330);
		g.drawString("Range: Low", 605, 355);
		g.drawString("Damage: Moderate", 605, 380);
		g.drawString("Attack Speed: Moderate", 605, 405);
		g.drawString("Special: Multi-shot", 605, 430);
	}
	
	public void drawTurret(Graphics g, boolean faster){
		g.setColor(Color.BLACK);
		g.fillRect(locX - 3, locY - 3, size+6,size+6);
		color = new Color(173, 3, 162);
		g.setColor(color);
		g.fillRect(locX - 2, locY - 2, size+4, size+4);
		color = new Color(200, 200, 255);
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