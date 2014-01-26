import java.awt.Color;
import java.awt.Graphics;

public class ComTurret extends Turret {
	
	//Communicator(Does not fire, give nearby turrets 10% attack speed, stacks up to 5 times)
		//Nearby turrets give an extra 3 gold per kill
		//Nearby turrets gain 10% damage
	
	private static int INITIALCOST = 95;

	public ComTurret(int x, int y) {
		super(x, y);
		range = 224;
		totalCost += INITIALCOST;
	}
	
	public String turretType(){
		return "Com";
	}
	
	public void upgrade(){
		
	}
	
	public void drawTurret(Graphics g){
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
