import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class GunTurret extends Turret {
	
	//Gun (Basic turret)
		//Rapid Fire, Faster attack speed
		//Sniper, Much Longer Range, Piereces

	public GunTurret(int x, int y) {
		super(x, y);
		range = 170;
		initialCost = 20;
	}
	
	public String turretType(){
		return "Gun";
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
		return (int)totalCost*(4/5);
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
			g.fillOval(locX-(range/2)-3, locY-(range/2)-3, range+size+6, range+size+6);
			if (nearTrack){
				g.setColor(new Color(6, 65, 105, 100));
			}
			else{
				g.setColor(new Color(178, 34, 34, 100));
			}
			g.fillOval(locX-(range/2), locY-(range/2), range+size, range+size);
		}
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
