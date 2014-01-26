import java.awt.Color;
import java.awt.Graphics;

public class GunTurret extends Turret {
	
	//Gun (Basic turret)
		//Rapid Fire, Faster attack speed
		//Sniper, Much Longer Range, Piereces
	
	private static int INITIALCOST = 20;

	public GunTurret(int x, int y) {
		super(x, y);
		range = 174;
		totalCost += INITIALCOST;
		firingCoolDown = 20;
	}
	
	public String turretType(){
		return "Gun";
	}
	
	public void upgrade(){
		
	}
	
	public void drawTurret(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(locX - 3, locY - 3, size+6,size+6);
		color = new Color(214, 214, 214);
		g.setColor(color);
		g.fillRect(locX - 2, locY - 2, size+4,size+4);
		color = new Color(124, 130, 108);
		g.setColor(color);
		g.fillOval(locX, locY, size, size);
	}
	
}
