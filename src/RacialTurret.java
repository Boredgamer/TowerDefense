import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class RacialTurret extends Turret {

	/*Racial turrets(CC, depends on race):
		Earth - Enchamber(2.0 seconds)
		Water - Slow (1.5 seconds)
		Air - Knockback (1.0 seconds)
		Fire - Stun (0.5 seconds)*/
	
	private int playerRace;
	private static int EARTH = 0;
	private static int WATER = 1;
	private static int AIR = 2;
	private static int FIRE = 3;
	
	private int range;
	private Color color;
	
	public RacialTurret(int pRace, int x, int y) {
		super(x, y);
		playerRace = pRace;
		locX = x;
		locY = y;
		range = 160;
	}
	
	public int buy(){
		int cost = 40;
		total_cost += cost;
		return cost;
	}
	
	public String turretType(){
		return "Racial";
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
		//color = new Color(250, 246, 200);
		color = new Color(30, 30, 30);
		g.setColor(color);
		g.fillRect(locX - 2, locY - 2, size+4, size+4);
		if(playerRace == EARTH){
			color = new Color(191, 120, 57);
		}
		else if(playerRace == WATER){
			color = new Color(76, 113, 224);
		}
		else if(playerRace == AIR){
			color = new Color(250, 250, 250);
		}
		else{
			color = new Color(255, 125, 74);
		}
		g.setColor(color);
		g.fillOval(locX, locY, size, size);
	}
}
