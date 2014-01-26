import java.awt.Color;
import java.awt.Graphics;

public class BombTurret extends Turret {

	private static int INITIALCOST = 45;

	public BombTurret(int x, int y) {
		super(x, y);
		range = 194;
		totalCost += INITIALCOST;
		firingCoolDown = 40;
	}
	
	public String turretType(){
		return "Bomb";
	}
	
	public void upgrade(){
		
	}

	public void drawTurret(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(locX - 3, locY - 3, size+6,size+6);
		color = new Color(10, 99, 0);
		g.setColor(color);
		g.fillRect(locX - 2, locY - 2, size+4, size+4);
		color = Color.ORANGE;
		g.setColor(color);
		g.fillOval(locX, locY, size, size);
	}
}