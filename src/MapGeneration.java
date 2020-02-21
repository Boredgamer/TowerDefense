import java.awt.*;
import java.util.ArrayList;


public class MapGeneration {

	ArrayList<Integer> coorX = new ArrayList<Integer>();
	ArrayList<Integer> coorY = new ArrayList<Integer>();
	ArrayList<Rectangle> turrets = new ArrayList<Rectangle>();
	ArrayList<Rectangle> blueLines = new ArrayList<Rectangle>();
	ArrayList<Rectangle> redLines = new ArrayList<Rectangle>();
	
	public MapGeneration(){
	}
	
	//Adds a new turret to check for collision
	public void addTurret(int x, int y){
		turrets.add(new Rectangle(x, y, 26, 26));
	}
	
	public void removeTurret(int turretID){
		turrets.remove(turretID);
	}
	
	//Returns if the turret is not on the track
	public boolean Rhoadside(Rectangle heldTurret){
		//Turrets
		int loop;
		for (loop = 0; loop < turrets.size(); loop++){
			if (turrets.get(loop).intersects(heldTurret)){
				return false;
			}
		}
		
		//Turret Screen
		Rectangle turretScreen = new Rectangle (600, 0, 200, 600);
		if (turretScreen.intersects(heldTurret)){
			return false;
		}
		
		//Blue Path
		Rectangle bluePortal = new Rectangle(24, 274, 52, 52);
		if (bluePortal.intersects(heldTurret)){
			return false;
		}
		
		//Line 1
		blueLines.add(new Rectangle(38, 315, 24, 235));
		//Line 2
		blueLines.add(new Rectangle(50, 538, 438, 24));
		//Line 3
		blueLines.add(new Rectangle(476, 112, 24, 438));
		//Line 4
		blueLines.add(new Rectangle(174, 100, 314, 24));
		//Line 5
		blueLines.add(new Rectangle(162, 112, 24, 62));
		//Line 6
		blueLines.add(new Rectangle(174, 162, 252, 24));
		//Line 7
		blueLines.add(new Rectangle(414, 174, 24, 190));
		//Line 8
		blueLines.add(new Rectangle(236, 352, 190, 24));
		//Line 9
		blueLines.add(new Rectangle(224, 300, 24, 64));
		//Line 10
		blueLines.add(new Rectangle(236, 288, 64, 24));
		
		for (loop = 0; loop < blueLines.size(); loop++){
			if (blueLines.get(loop).intersects(heldTurret)){
				return false;
			}
		}
		
		//Red Path
		Rectangle redPortal = new Rectangle(524, 274, 52, 52);
		if (redPortal.intersects(heldTurret)){
			return false;
		}
		
		//Line 1
		redLines.add(new Rectangle(538, 50, 24, 235));
		//Line 2
		redLines.add(new Rectangle(112, 38, 438, 24));
		//Line 3
		redLines.add(new Rectangle(100, 50, 24, 438));
		//Line 4
		redLines.add(new Rectangle(112, 476, 314, 24));
		//Line 5
		redLines.add(new Rectangle(414, 426, 24, 62));
		//Line 6
		redLines.add(new Rectangle(174, 414, 252, 24));
		//Line 7
		redLines.add(new Rectangle(162, 236, 24, 190));
		//Line 8
		redLines.add(new Rectangle(174, 224, 190, 24));
		//Line 9
		redLines.add(new Rectangle(352, 236, 24, 64));
		//Line 10
		redLines.add(new Rectangle(300, 288, 64, 24));
		
		for (loop = 0; loop < redLines.size(); loop++){
			if (redLines.get(loop).intersects(heldTurret)){
				return false;
			}
		}
		
		//Purple Portal
		Rectangle purplePortal = new Rectangle(280, 280, 40, 40);
		if (purplePortal.intersects(heldTurret)){
			return false;
		}
		return true;
	}
	
	
	//Draws the track
	public void drawTrack(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(24));
		
		//Blue Path
		g.setColor(new Color(0, 0, 0));
		g.fillOval(18, 268, 64, 64);
		g.setColor(new Color(0, 0, 255));
		g.fillOval(20, 270, 60, 60);
		
		g.setColor(new Color(204, 204, 255, 225));
		//Line 1
		g.drawLine(50, 315, 50, 550);
		//Line 2
		g.drawLine(50, 550, 488, 550);
		//Line 3
		g.drawLine(488, 550, 488, 112);
		//Line 4
		g.drawLine(488, 112, 174, 112);
		//Line 5
		g.drawLine(174, 112, 174, 174);
		//Line 6
		g.drawLine(174, 174, 426, 174);
		//Line 7
		g.drawLine(426, 174, 426, 364);
		//Line 8
		g.drawLine(426, 364, 236, 364);
		//Line 9
		g.drawLine(236, 364, 236, 300);
		//Line 10
		g.drawLine(236, 300, 300, 300);
		
		//Red Path
		g.setColor(new Color(0, 0, 0));
		g.fillOval(518, 268, 64, 64);
		g.setColor(new Color(255, 0, 0));
		g.fillOval(520, 270, 60, 60);
		
		g.setColor(new Color(255, 204, 204, 225));
		//Line 1
		g.drawLine(550, 285, 550, 50);
		//Line 2
		g.drawLine(550, 50, 112, 50);
		//Line 3
		g.drawLine(112, 50, 112, 488);
		//Line 4
		g.drawLine(112, 488, 426, 488);
		//Line 5
		g.drawLine(426, 488, 426, 426);
		//Line 6
		g.drawLine(426, 426, 174, 426);
		//Line 7
		g.drawLine(174, 426, 174, 236);
		//Line 8
		g.drawLine(174, 236, 364, 236);
		//Line 9
		g.drawLine(364, 236, 364, 300);
		//Line 10
		g.drawLine(364, 300, 300, 300);
		
		//Purple Portal
		
		g.setColor(new Color (0, 0, 0));
		g.fillOval(274, 274, 52, 52);
		g.setColor(new Color (102, 0, 102));
		g.fillOval(276, 276, 48, 48);
	
	}
	
	//Draws the shop screen
	public void drawShop(Graphics g){
		//Backdrop
		g.setColor(new Color(6, 65, 105));
		g.fillRect(600, 0, 200, 600);
		
		//Hotkeys List
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.drawString("Hotkey List", 665, 150);
		g.setFont(new Font("Arial", Font.PLAIN, 12));
		g.drawString("Buy Turret", 620, 170);
		g.drawRect(617, 173, 60, 105);
		g.drawString("Q: Gun ", 623, 190);
		g.drawString("W: Bomb", 623, 210);
		g.drawString("E: Ray", 623, 230);
		g.drawString("R: Com", 623, 250);
		g.drawString("T: Racial", 623, 270);
		g.drawString("Shift: Sell/Undo", 700, 190);
		g.drawString("ESC: Unfocus", 700, 210);
		g.drawString("Space: ", 700, 230);
		g.drawString("Spawn Wave/", 700, 250);
		g.drawString("Change Speed", 700, 270);
		
	}
}
