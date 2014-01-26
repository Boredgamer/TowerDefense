import java.awt.*;

public class Turret{
	

	protected int totalCost = 0;
	protected int size = 20;
	protected int range = 0;
	protected int attackSpeed = 5;
	protected Color color;
	protected int locX;
	protected int locY;
	protected Rectangle turretSquare;
	protected boolean nearTrack = false;
	protected boolean focused = true;
	protected boolean turnedOn = false;
	protected boolean firedUp = false;
	protected int firingCoolDown;
	private int coolant = 0;
		
	public Turret(int x, int y){	
		locX = x;
		locY = y;
		turretSquare = new Rectangle(locX-3, locY-3, size+6, size+6);
	}
	
	public boolean overTurret(int x, int y){
		if (turretSquare.contains(x, y)){
			return true;
		}
		return false;
	}
	
	public int buy(){
		return totalCost;
	}
	
	public int sell(){
		return (int)totalCost*(4/5);
	}
	
	public boolean nearTrack(){
		return nearTrack;
	}
	
	public void notOnRoad(boolean nextToRoad){
		nearTrack = nextToRoad;
	}
	
	public Rectangle turretArea(){
		turretSquare = new Rectangle(locX-3, locY-3, size+6, size+6);
		return turretSquare;
	}
	
	public void changeFocus(boolean focus){
		focused = focus;
	}
	
	public void powered(){
		turnedOn = true;
		firedUp = true;
	}
	
	public void updateTurretPosition(int x, int y){
		locX = x-size/2;
		locY = y-size/2;
	}
	
	
	public boolean inRange(int x, int y, int locD){
		int disX = (int)(x + locD/2) - ((locX-range/2) + (range+size)/2); 
		int disY = (int)(y + locD/2) - ((locY-range/2) + (range+size)/2); 
		int radii = locD/2 + (range+size)/2;
		return (((disX * disX) + (disY * disY)) < (radii * radii));
	}
	
	public void readyToFire(boolean b){
		firedUp = b;
	}
	
	public void coolOff(){
		if (!firedUp){
			coolant++;
			if (coolant == firingCoolDown){
			firedUp = true;
			coolant = 0;
			}
		}
	}
	
	public void drawRange(Graphics g, boolean nearTrack){
		if (focused){
			g.setColor(new Color(0, 0, 0, 120));
			g.fillOval(locX-(range/2), locY-(range/2), range+size, range+size);
			if (nearTrack){
				g.setColor(new Color(6, 65, 105, 120));
			}
			else{
				g.setColor(new Color(178, 34, 34, 120));
			}
			g.fillOval(locX-(range/2)+2, locY-(range/2)+2, range+size-4, range+size-4);
		}
	}
	
	public void drawTurret(Graphics g){
	}
	
	public String turretType(){
		return "Debug Me! (Main Turret Type)";
	}
	
	public void activity(boolean canShoot){
	}
	
	
}

