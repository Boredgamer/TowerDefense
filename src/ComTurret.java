import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class ComTurret extends Turret {
	
	//Communicator(Does not fire, give nearby turrets 10% attack speed, stacks up to 5 times)
		//Nearby turrets give an extra 3 gold per kill
		//Nearby turrets gain 10% damage
	
	private int range;
	private Color color;


	public ComTurret(int x, int y) {
		super(x, y);
		locX = x;
		locY = y;
		range = 220;
	}
	
	public int buy(){
		int cost = 50;
		total_cost += cost;
		return cost;
	}
	
	public String turretType(){
		return "Com";
	}
	
	public void updateTurretPosition(int x, int y){
		locX = x-size/2;
		locY = y-size/2;
	}
	
	public void sell(){
		
	}
	
	public void upgrade(){
		
	}
	
	public Rectangle turretArea(){
		turretSquare = new Rectangle(locX-3, locY-3, size+6, size+6);
		return turretSquare;
	}
	
	public int moneyBack(){
		return (int)total_cost*(4/5);
	}
	
	public void changeFocus(boolean focus){
		focused = focus;
	}
	
	public void powered(boolean canShoot){
		turnedOn = canShoot;
	}
	
	public void shoot(){
		if (turnedOn){
			
		}
	}
	
	public class Ammunition{
		
		public Ammunition(){
		}
	}
	
	public void drawRange(Graphics g, boolean nearTrack){
		if (focused){
			g.setColor(new Color(0, 0, 0, 120));
			g.fillOval(locX-(range/2)-2, locY-(range/2)-2, range+size+4, range+size+4);
			if (nearTrack){
				g.setColor(new Color(6, 65, 105, 120));
			}
			else{
				g.setColor(new Color(178, 34, 34, 120));
			}
			g.fillOval(locX-(range/2), locY-(range/2), range+size, range+size);
		}
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
