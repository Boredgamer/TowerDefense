import java.awt.*;

public class BombTurret extends Turret {
	
	//Bomb (slow but stronk)
		//Longer range and short stun on-hit
		//Bonus damage vs. bosses (1.5x or 2x)
	
	private final int INITIALCOST = 50;
	private final int INITIALCOOLDOWN = 200;

	public BombTurret(int x, int y) {
		super(x, y);
		range = 214;
		totalCost += INITIALCOST;
		firingCoolDown = INITIALCOOLDOWN;

	}
	
	public String turretType(){
		return "Bomb";
	}
	
	//Draws the Tooltip
	public void drawRolloverTooltip(Graphics g){	
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("Bomb Turret", 605, 305);
		g.setFont(new Font("Arial", Font.PLAIN, 18));
		g.drawString("Cost: "+totalCost +" Gold", 605, 330);
		g.drawString("Range: High", 605, 355);
		g.drawString("Damage: High", 605, 380);
		g.drawString("Attack Speed: Low", 605, 405);
		g.drawString("Special: None", 605, 430);
	}
	
	public void drawTurret(Graphics g, boolean faster){
		g.setColor(Color.BLACK);
		g.fillRect(locX - 3, locY - 3, size+6,size+6);
		color = new Color(10, 99, 0);
		g.setColor(color);
		g.fillRect(locX - 2, locY - 2, size+4, size+4);
		color = Color.ORANGE;
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