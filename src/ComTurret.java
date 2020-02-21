import java.awt.*;


public class ComTurret extends Turret {
	
	//Communicator (Does not fire, give nearby turrets 10% attack speed, stacks up to 5 times)
		//Nearby turrets give an extra 3 gold per kill
		//Nearby turrets gain 10% damage
	
	private final int INITIALCOST = 100;

	public ComTurret(int x, int y) {
		super(x, y);
		range = 234;
		totalCost += INITIALCOST;
	}
	
	public String turretType(){
		return "Com";
	}
	
	//Draws the Tooltip
	public void drawRolloverTooltip(Graphics g){	
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("Com Turret", 605, 305);
		g.setFont(new Font("Arial", Font.PLAIN, 18));
		g.drawString("Cost: "+totalCost +" Gold", 605, 330);
		g.drawString("Range: Very High", 605, 355);
		g.drawString("Damage: None", 605, 380);
		g.drawString("Attack Speed: None", 605, 405);
		g.drawString("Special: Bonus AS Aura", 605, 430);
	}
	
	public void drawTurret(Graphics g, boolean faster){
		g.setColor(Color.BLACK);
		g.fillRect(locX - 3, locY - 3, size+6,size+6);
		color = new Color(245, 171, 49);
		g.setColor(color);
		g.fillRect(locX - 2, locY - 2, size+4, size+4);
		color = new Color(225, 129, 104);
		g.setColor(color);
		g.fillOval(locX, locY, size, size);
	}
}
