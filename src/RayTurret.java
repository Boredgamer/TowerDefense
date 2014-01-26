import java.awt.Color;
import java.awt.Graphics;

public class RayTurret extends Turret {
	
	//Ray (Up to 3 beams at once)
		//Increases damage and attack speed over time 
		//Doubles amount of beams 

	private static int INITIALCOST =  70;

	public RayTurret(int x, int y) {
		super(x, y);
		range = 164;
		totalCost += INITIALCOST;
		firingCoolDown = 28;
	}
	
	public String turretType(){
		return "Ray";
	}
	
	public void upgrade(){
		
	}
	
	public void drawTurret(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(locX - 3, locY - 3, size+6,size+6);
		color = new Color(173, 3, 162);
		g.setColor(color);
		g.fillRect(locX - 2, locY - 2, size+4, size+4);
		color = new Color(200, 200, 255);
		g.setColor(color);
		g.fillOval(locX, locY, size, size);
	}
}