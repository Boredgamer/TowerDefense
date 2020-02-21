import java.awt.*;

public class GunTurret extends Turret {
	
	//Gun (Basic turret)
		//Rapid Fire, Faster attack speed
		//Sniper, Much Longer Range, Piereces for 10% health
	
	private final int INITIALCOST = 25;
	private final int INITIALCOOLDOWN = 100;

	public GunTurret(int x, int y) {
		super(x, y);
		range = 174;
		totalCost += INITIALCOST;
		firingCoolDown = INITIALCOOLDOWN;
	}
	
	public String turretType(){
		return "Gun";
	}
	
	//Draws the Tooltip
	public void drawRolloverTooltip(Graphics g){	
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("Gun Turret", 605, 305);
		g.setFont(new Font("Arial", Font.PLAIN, 18));
		g.drawString("Cost: "+totalCost +" Gold", 605, 330);
		g.drawString("Range: Moderate", 605, 355);
		g.drawString("Damage: Low", 605, 380);
		g.drawString("Attack Speed: High", 605, 405);
		g.drawString("Special: None", 605, 430);
	}
	
	public void drawTurret(Graphics g, boolean faster){
		g.setColor(Color.BLACK);
		g.fillRect(locX - 3, locY - 3, size+6,size+6);
		color = new Color(214, 214, 214);
		g.setColor(color);
		g.fillRect(locX - 2, locY - 2, size+4,size+4);
		color = new Color(124, 130, 108);
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
