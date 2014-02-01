import java.awt.*;


public interface InMySights {
	public void ammuntionType(String type, int playerRace);
	public int attackDamage();
	public void targetTracker(int trackedX, int trackedY);
	public int getTarget();
	public boolean bulletHit();
	public void move();
	public void drawBullet(Graphics g);
}