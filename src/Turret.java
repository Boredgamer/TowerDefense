import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;


public class Turret{
	
	int total_cost = 0;
	int size = 20;
	Color color;
	int locX;
	int locY;
	Rectangle turretSquare;
	boolean nearTrack = false;
	boolean focused = true;
	boolean turnedOn = false;
	
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
		return total_cost;
	}
	
	public boolean nearTrack(){
		return nearTrack;
	}
	
	public void notOnRoad(boolean nextToRoad){
		nearTrack = nextToRoad;
	}
	
	public void changeFocus(boolean focus){
		focused = focus;
	}
	
	public Rectangle turretArea(){
		turretSquare = new Rectangle(locX-3, locY-3, size+6, size+6);
		return turretSquare;
	}
	
	public void updateTurretPosition(int x, int y){
		locX = x-size/2;
		locY = y-size/2;
	}
	
	public void drawRange(Graphics g, boolean nearTrack){
	}
	
	public void drawTurret(Graphics g){
	}
	
	public String turretType(){
		return "";
	}
	
	public void activity(boolean canShoot){
	}
	
	
}

