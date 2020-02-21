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
	protected int firingCoolDown = 0;
	protected int maximumShots = 1;
	private int coolant = 0;
	private int shotsReady;
	
	public Turret(int x, int y){	
		locX = x;
		locY = y;
		turretSquare = new Rectangle(locX-3, locY-3, size+6, size+6);
	}
	
	public void adjustCost(boolean increase){
		if (increase){
			totalCost += 5;
		}
		else{
			totalCost -= 5;
		}
	}
	
	public int adjustedCost(int turretsPlaced) {
		return totalCost+(5*turretsPlaced);
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
	
	public int sell(int turretsPlaced){
		return (int)(adjustedCost(turretsPlaced)*.75);
	}
	
	public int undo(int turretsPlaced){
		return adjustedCost(turretsPlaced);
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
		shotsReady = maximumShots;
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
	
	public void loadBlown(){
		shotsReady--;
		if(shotsReady == 0){
			firedUp = false;
		}
	}
	
	public void coolOff(boolean buffedUp){
		if (!firedUp && turnedOn){
			coolant++;
			if (coolant == (int)firingCoolDown*.75 && buffedUp){
				firedUp = true;
				coolant = 0;
				shotsReady = maximumShots;
			}
			else if (coolant == firingCoolDown){
				firedUp = true;
				coolant = 0;
				shotsReady = maximumShots;
			}
		}
	}
	
	public void drawRange(Graphics g, boolean nearTrack){
		if (focused){
			//Turret Range
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
	
	public void drawRolloverTooltip(Graphics g){
	}
	
	public void drawRolloverTooltip(Graphics g, int rt){
	}
	
	public void drawFocus(Graphics g, boolean TextSaysUndo){
		if(focused){
			//Tooltip
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString(turretType()+" Turret", 605, 305);
		
			//Sell or Undo button
			g.setColor(new Color(0, 81, 138, 100));
			g.fillRect(620, 500, 160, 80);
			Graphics2D g2 = (Graphics2D) g;
			g.setColor(Color.LIGHT_GRAY);
			g2.setStroke(new BasicStroke(4));
			g.drawRect(620, 500, 160, 80);
			g.setFont(new Font("Bell MT", Font.BOLD, 40));
			FontMetrics metrics = g.getFontMetrics(new Font("Bell MT", Font.BOLD, 40));
			int adv;
			if (!TextSaysUndo){
				adv = metrics.stringWidth("Sell");
				g.drawString("Sell", 700-adv/2, 555);
			}
			else{
				adv = metrics.stringWidth("Undo");
				g.drawString("Undo", 700-adv/2, 555);
			}
		}
	}
	
	public void drawTurret(Graphics g, boolean faster){
	}
	
	public String turretType(){
		return "";
	}
	
}

