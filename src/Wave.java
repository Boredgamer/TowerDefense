import java.awt.*;
import java.util.ArrayList;


public class Wave {
	
	private String status = "Basic";
	private boolean tenacious;
	private boolean boss = false;
	
	private int waveNumber;
	private int strengthInNumbers = 0;
	private int difficulty;
	private final int EASY = 0;
	private final int NORMAL = 1;
	private final int HARD = 2;
	//private final int EXPERT = 3;
	
	private final int EARTH = 0;
	private final int WATER = 1;
	private final int WIND = 2;
	private final int FIRE = 3;
	
	
	private int speed = 4;
	private int size = 18;
	private int waveHealth = 40;
	
	private int enemiesPassed = 0;
	
	ArrayList<Enemy> enemiesPresent = new ArrayList<Enemy>();
	
	//Checkpoints for enemy pathing
	ArrayList<Integer> blueEndpointX = new ArrayList<Integer>();
	ArrayList<Integer> blueEndpointY = new ArrayList<Integer>();
	ArrayList<Boolean> blueTurningPoint = new ArrayList<Boolean>();
	
	ArrayList<Integer> redEndpointX = new ArrayList<Integer>();
	ArrayList<Integer> redEndpointY = new ArrayList<Integer>();
	ArrayList<Boolean> redTurningPoint = new ArrayList<Boolean>();
	
	//Enemies in the Wave
	public class Enemy{
		
		private int enemyX;
		private int enemyY;
		
		private int generalDirection;
		private int FORWARD = 0;
		private int BACKWARD = 1;
		
		private int velocityX;
		private int velocityY = 0;
		
		private int direction;
		private int UP = 0;
		private int DOWN = 1;
		private int LEFT = 2;
		private int RIGHT = 3;
		
		private int path;
		private int BLUE = 0;
		private int RED = 1;
		
		private int health;
		private double percentHealth;
		private int goldValue = 9;
		
		private int endpointer = 0;
		private int enemyID;
		private boolean midPoint = false;
		private boolean enemyPresence = true;
		private boolean enemyPassed = false;
		private boolean presenceCheck = false;
		private boolean lossOfLife = false;
		private boolean spawned = false;
		
		//Debuffs
		private boolean jolly = true;
		private int cc;
		private boolean entombed = false;
		private boolean slowed = false;
		private boolean knockedback = false;
		private boolean stunned = false;
		private int recovery = 0;
		
		private Color enemyColor;
		private Color debuffedColor;
		
		public Enemy(int d, int spacing){
			generalDirection = d;
			path = d;
			//For ammunition tracking
			enemyID = spacing;
			goldValue += waveNumber*2;
			
			int x = 0;
			int y = 0;
			if (path == BLUE){
				x = 50;
				y = 300;
				velocityX = -speed;
				direction = LEFT;
				enemyColor = Color.BLUE;
				debuffedColor = Color.CYAN;
				x += 60*spacing;
			}
			else{
				x = 550;
				y = 300;
				velocityX = speed;
				direction = RIGHT;
				enemyColor = Color.RED;
				debuffedColor = Color.ORANGE;
				x -= 60*spacing;
			}
			
			enemyX = x - size/2;
			enemyY = y - size/2;
			
			if (boss){
				goldValue *= 10;
			}			
			
			health = waveHealth;
		}
		
		//Subtracts the enemy's health when it is hit and activates any debuffs
		public void wasAttacked(int dmg, int debuff){
			health -= dmg;
			if (health <= 0){
				death();
			}
			if (dmg == 0 && !tenacious && jolly){
				cc = debuff;
				jolly = false;
				crowdControlled(cc);
			}
		}
		
		//Determines how the debuff affects the enemy
		public void crowdControlled(int debuff){
			if (debuff == EARTH){
				entombed = true;
			}
			else if (debuff == WATER){
				slowed = true;
			}
			else if (debuff == WIND){
				knockedback = true;
			}
			else{
				stunned = true;
			}
		}
		
		//Enemy shrugging off the debuff
		public void debuffRecovery(){
			recovery++;
			if (cc == EARTH && recovery >= 80){
				entombed = false;
				jolly = true;
				recovery = 0;
			}
			else if (cc == WATER && recovery >= 60){
				slowed = false;
				jolly = true;
				recovery = 0;
			}
			else if (cc == WIND && recovery >= 40){
				knockedback = false;
				jolly = true;
				recovery = 0;
			}
			else if(cc == FIRE && recovery >= 20){
				stunned = false;
				jolly = true;
				recovery = 0;
			}
		}
		
		//Returns the tracking ID
		public int getID(){
			return enemyID;
		}
		
		//Makes the enemy no longer present
		public void death(){
			enemyPresence = false;
			spawned = false;
		}
		
		//Returns the money received from killing the enemy
		public int moneySalvaged(){
			return goldValue;
		}
		
		//Returns if the enemy has passed its respective portal and is shootable
		public boolean getSpawned(){
			return spawned;
		}
		
		//Draw method for enemy if it has passed its respective portal and is shootable
		public void drawEnemy(Graphics g){
			g.setColor(enemyColor);
			if (!jolly){
				g.setColor(debuffedColor);
			}
			if (path == BLUE && enemyPresence && enemyX >= 0 && (endpointer != 0 || generalDirection == BACKWARD)){
				g.fillOval(enemyX, enemyY, size, size);
				drawHealth(g);
				spawned = true;
				
			}
			else if (path == RED && enemyPresence && enemyX+size<= 600 && (endpointer != 0 || generalDirection == FORWARD)){
				g.fillOval(enemyX, enemyY, size, size);
				drawHealth(g);
				spawned = true;
			}
		}
		
		//Draw method for the health of the enemy
		public void drawHealth(Graphics g){
			percentHealth = (double)health/waveHealth;
			
			//Draws Outer Rectangles
			g.setColor(Color.BLACK);
			g.fillRect(enemyX - 4, enemyY - 14, size+8, 10);
	
			//Draws Inner Rectangle
			g.setColor(new Color(0, 204, 0));
			if (percentHealth <= .2){
				g.setColor(new Color(204, 0, 0));
			}
			g.fillRect(enemyX - 3, enemyY - 13, (int)((size+6)*percentHealth), 8);
			
		}
		
		public boolean entombment(){
			return entombed;
		}
		
		public boolean skipping(){
			return jolly;
		}
		
		//Moves the enemy based on its velocity and turns it if a checkpoint is reached
		public void move(){
			if (slowed){
				enemyX += velocityX/2;
				enemyY += velocityY/2;
			}
			else if (knockedback){
				enemyX -= velocityX;
				enemyY -= velocityY;
				if (enemyX <= 0){
					enemyX = 0;
				}
				else if (enemyX +size >= 600){
					enemyX = 600 - size;
				}
				else if (enemyY <= 0){
					enemyY = 0;
				}
				else if (enemyY +size >= 600){
					enemyY = 600 - size;
				}
			}
			else if (!entombed && !stunned){
				enemyX += velocityX;
				enemyY += velocityY;
			}
			
			//Purple Portal
			if (generalDirection == FORWARD && enemyX+size/2 >= blueEndpointX.get(endpointer) && endpointer == 10 && !midPoint){
				if (difficulty == EASY || difficulty == HARD){
					enemyX = blueEndpointX.get(endpointer) - size/2;
					enemyY = blueEndpointY.get(endpointer) - size/2;
					endpointer++;
					generalDirection = BACKWARD;
					midPoint = true;
					direction = LEFT;
					velocityX = -velocityX;
					reverseEnder();
				}
				else{
					enemyPass();
				}
			}
			else if (generalDirection == BACKWARD && enemyX+size/2 <= redEndpointX.get(endpointer) && endpointer == 10 && !midPoint){
				if (difficulty == EASY || difficulty == HARD){
					enemyX = blueEndpointX.get(endpointer) - size/2;
					enemyY = blueEndpointY.get(endpointer) - size/2;
					endpointer++;
					generalDirection = FORWARD;
					midPoint = true;
					direction = RIGHT;
					velocityX = -velocityX;
					reverseEnder();
				}
				else{
					enemyPass();
				}
			}
			
			//Blue Path
			else if (path == BLUE){
				if (direction == DOWN && enemyX+size/2 == blueEndpointX.get(endpointer) && enemyY+size/2 >= blueEndpointY.get(endpointer)){
					if (generalDirection == FORWARD){
						changeDirection(blueTurningPoint.get(endpointer));
					}
					else{
						changeDirection(!blueTurningPoint.get(endpointer));
					}
					enemyX = blueEndpointX.get(endpointer) - size/2;
					enemyY = blueEndpointY.get(endpointer) - size/2;
					endpointer++;
					reverseEnder();
				}
				else if (direction == UP && enemyX+size/2 == blueEndpointX.get(endpointer) && enemyY+size/2 <= blueEndpointY.get(endpointer)){
					if (generalDirection == FORWARD){
						changeDirection(blueTurningPoint.get(endpointer));
					}
					else{
						changeDirection(!blueTurningPoint.get(endpointer));
					}
					enemyX = blueEndpointX.get(endpointer) - size/2;
					enemyY = blueEndpointY.get(endpointer) - size/2;
					endpointer++;
					reverseEnder();
				}
				else if (direction == LEFT && enemyX+size/2 <= blueEndpointX.get(endpointer) && enemyY+size/2 == blueEndpointY.get(endpointer)){
					if (generalDirection == FORWARD){
						changeDirection(blueTurningPoint.get(endpointer));
					}
					else{
						changeDirection(!blueTurningPoint.get(endpointer));
					}
					enemyX = blueEndpointX.get(endpointer) - size/2;
					enemyY = blueEndpointY.get(endpointer) - size/2;
					endpointer++;
					reverseEnder();
				}
				else if (direction == RIGHT && enemyX+size/2 >= blueEndpointX.get(endpointer) && enemyY+size/2 == blueEndpointY.get(endpointer)){
					if (generalDirection == FORWARD){
						changeDirection(blueTurningPoint.get(endpointer));
					}
					else{
						changeDirection(!blueTurningPoint.get(endpointer));
					}
					enemyX = blueEndpointX.get(endpointer) - size/2;
					enemyY = blueEndpointY.get(endpointer) - size/2;
					endpointer++;
					reverseEnder();
				}
			}
			
			//Red Path
			else if (path == RED){
				if (direction == DOWN && enemyX+size/2 == redEndpointX.get(endpointer) && enemyY+size/2 >= redEndpointY.get(endpointer)){
					if (generalDirection == BACKWARD){
						changeDirection(redTurningPoint.get(endpointer));
					}
					else{
						changeDirection(!redTurningPoint.get(endpointer));
					}
					enemyX = redEndpointX.get(endpointer) - size/2;
					enemyY = redEndpointY.get(endpointer) - size/2;
					endpointer++;
					reverseEnder();
				}
				else if (direction == UP && enemyX+size/2 == redEndpointX.get(endpointer) && enemyY+size/2 <= redEndpointY.get(endpointer)){
					if (generalDirection == BACKWARD){
						changeDirection(redTurningPoint.get(endpointer));
					}
					else{
						changeDirection(!redTurningPoint.get(endpointer));
					}
					enemyX = redEndpointX.get(endpointer) - size/2;
					enemyY = redEndpointY.get(endpointer) - size/2;
					endpointer++;
					reverseEnder();
				}
				else if (direction == LEFT && enemyX+size/2 <= redEndpointX.get(endpointer) && enemyY+size/2 == redEndpointY.get(endpointer)){
					if (generalDirection == BACKWARD){
						changeDirection(redTurningPoint.get(endpointer));
					}
					else{
						changeDirection(!redTurningPoint.get(endpointer));
					}
					enemyX = redEndpointX.get(endpointer) - size/2;
					enemyY = redEndpointY.get(endpointer) - size/2;
					endpointer++;
					reverseEnder();
				}
				else if (direction == RIGHT && enemyX+size/2 >= redEndpointX.get(endpointer) && enemyY+size/2 == redEndpointY.get(endpointer)){
					if (generalDirection == BACKWARD){
						changeDirection(redTurningPoint.get(endpointer));
					}
					else{
						changeDirection(!redTurningPoint.get(endpointer));
					}
					enemyX = redEndpointX.get(endpointer) - size/2;
					enemyY = redEndpointY.get(endpointer) - size/2;
					endpointer++;
					reverseEnder();
				}
			}
			if (!jolly){
				debuffRecovery();
			}
		}
		
		//Returns the X coordinate of the enemy's position
		public int getLocX(){
			return enemyX;
		}
		
		//Returns the Y coordinate of the enemy's position
		public int getLocY(){
			return enemyY;
		}
		
		//Checks if an enemy has ended its path and was not killed
		public void enemyPass(){
			enemyPresence = false;
			enemyPassed = true;	
			spawned = false;
		}
		
		//Returns if the enemy's path is ended and was not killed
		public boolean portalPassing(){
			return enemyPassed;
		}
		
		//Returns if the enemy is still present on the map
		public boolean stillPresent(){
			return enemyPresence;
		}
		
		//Returns if the enemy was checked for its presence
		public boolean gotChecked(){
			return presenceCheck;
		}
		
		//Shows the check for presence is done
		public void nowChecked(){
			presenceCheck = true;
		}
		
		//Returns if the player has lost a life or a 10 lives if the enemy is a boss
		public boolean lifeLost(){
			return lossOfLife;
		}
		
		//Makes sure the enemy does not remove more than the intended amount of lives
		public void lifeRemoved(){
			lossOfLife = true;
		}
		
		//Changes the course of the enemy if the difficulty is EASY or HARD to return to its original portal
		public void reverseEnder(){
			if (midPoint){
				if (endpointer != 0){
					endpointer--;
				}
				endpointer--;
				if (endpointer == -1){
					enemyPass();	
				}
			}
		}
		
		//Changes the direction of the enemy if it reaches a checkpoint
		public void changeDirection(boolean turnLeft){
			if (direction == UP){
				velocityX = velocityY;
				velocityY = 0;
				if (turnLeft){
					direction = LEFT;
				}
				else{
					velocityX = -velocityX;
					direction = RIGHT;
				}
			}
			else if (direction == DOWN){
				velocityX = velocityY;
				velocityY = 0;
				if (turnLeft){
					direction = RIGHT;	
				}
				else{
					velocityX = -velocityX;
					direction = LEFT;
				}
			}
			else if (direction == LEFT){
				velocityY = velocityX;
				velocityX = 0;
				if (turnLeft){
					direction = DOWN;
					velocityY = -velocityY;
				}
				else{
					direction = UP;
				}	
			}
			else{
				velocityY = velocityX;
				velocityX = 0;
				if (turnLeft){
					direction = UP;
					velocityY = -velocityY;
				}
				else{
					direction = DOWN;
				}
			}	
		}
	}

	public Wave(int n, int d){
		waveNumber = n;
		difficulty = d;
		waveHealth += waveNumber*10;	
		strengthInNumbers += waveNumber;
		
		if (waveNumber % 10 == 0){
			boss = true;
			strengthInNumbers = 1;
		}
		
		if (waveNumber % 3 == 0){
			status = "Heavy";
		}
		else if(waveNumber % 4 == 0){
			status = "Light";
		}
		if (waveNumber % 5 == 0){
			tenacious = true;
		}
		
		findEndpoints();
		statusChanger();
		enemySpawner();
	}
	
	//Moves each enemy
	public void enemyMover(){
		int loop;
		for (loop = 0; loop < enemiesPresent.size(); loop++){
			if (enemiesPresent.get(loop).stillPresent()){
				enemiesPresent.get(loop).move();
			}
		}
	}
	
	//Changes stats based on status
	public void statusChanger(){
		if (status == "Heavy"){
			speed /= 2;
			waveHealth *= 2;
		}
		else if(status == "Light"){
			speed *= 2;
			waveHealth /= 2;
		}
		if (boss){
			size *= 2;
			waveHealth *= 5;
		}
	}
	
	//Returns the status of the enemies
	public String getStatus(){
		return status;
	}
	
	//Returns if the enemies resist racial turrets
	public boolean getTenacity(){
		return tenacious;
	}
	
	public void enemySpawner(){
		int spawnerLoop;
		int randomDirection;
		for (spawnerLoop = 0; spawnerLoop < strengthInNumbers; spawnerLoop++){
			if (difficulty == EASY || difficulty == NORMAL){
				enemiesPresent.add(new Enemy(0, spawnerLoop));
			}
			else{
				randomDirection = (int)((100)*Math.random() + 1);
				if (randomDirection >= 50){
					enemiesPresent.add(new Enemy(0, spawnerLoop));
				}
				else{
					enemiesPresent.add(new Enemy(1, spawnerLoop));
				}
			}
		}
	}
	
	//Lists the checkpoints for the enemy pathing
	public void findEndpoints(){
		//Point 0
		blueEndpointX.add(50);
		blueEndpointY.add(300);
		blueTurningPoint.add(true);
		//Point 1
		blueEndpointX.add(50);
		blueEndpointY.add(550);
		blueTurningPoint.add(true);
		//Point 2
		blueEndpointX.add(488);
		blueEndpointY.add(550);
		blueTurningPoint.add(true);
		//Point 3
		blueEndpointX.add(488);
		blueEndpointY.add(112);
		blueTurningPoint.add(true);
		//Point 4
		blueEndpointX.add(174);
		blueEndpointY.add(112);
		blueTurningPoint.add(true);
		//Point 5
		blueEndpointX.add(174);
		blueEndpointY.add(174);
		blueTurningPoint.add(true);
		//Point 6
		blueEndpointX.add(426);
		blueEndpointY.add(174);
		blueTurningPoint.add(false);
		//Point 7
		blueEndpointX.add(426);
		blueEndpointY.add(364);
		blueTurningPoint.add(false);
		//Point 8
		blueEndpointX.add(236);
		blueEndpointY.add(364);
		blueTurningPoint.add(false);
		//Point 9
		blueEndpointX.add(236);
		blueEndpointY.add(300);
		blueTurningPoint.add(false);
		//Point 10
		blueEndpointX.add(300);
		blueEndpointY.add(300);
		
		//Point 0
		redEndpointX.add(550);
		redEndpointY.add(300);
		redTurningPoint.add(true);
		//Point 1
		redEndpointX.add(550);
		redEndpointY.add(50);
		redTurningPoint.add(true);
		//Point 2
		redEndpointX.add(112);
		redEndpointY.add(50);
		redTurningPoint.add(true);
		//Point 3
		redEndpointX.add(112);
		redEndpointY.add(488);
		redTurningPoint.add(true);
		//Point 4
		redEndpointX.add(426);
		redEndpointY.add(488);
		redTurningPoint.add(true);
		//Point 5
		redEndpointX.add(426);
		redEndpointY.add(426);
		redTurningPoint.add(true);
		//Point 6
		redEndpointX.add(174);
		redEndpointY.add(426);
		redTurningPoint.add(false);
		//Point 7
		redEndpointX.add(174);
		redEndpointY.add(236);
		redTurningPoint.add(false);
		//Point 8
		redEndpointX.add(364);
		redEndpointY.add(236);
		redTurningPoint.add(false);
		//Point 9
		redEndpointX.add(364);
		redEndpointY.add(300);
		redTurningPoint.add(false);
		//Point 10
		redEndpointX.add(300);
		redEndpointY.add(300);
		
	}
	
	//Returns the physical size of the enemies
	public int getSize(){
		return size;
	}
	
	//Returns if the targeted enemy is still present
	public boolean IDscanner(int trackingNumber){
		int loop;
		for (loop = 0; loop < enemiesPresent.size(); loop++){
				if (enemiesPresent.get(loop).getID() == trackingNumber){
					return true;
				}
		}
		return false;
	}
	
	//Returns the position in the enemiesPresent ArrayList of the targeted enemy
	public int IDTracker(int trackingNumber){
		int loop;
		for (loop = 0; loop < enemiesPresent.size(); loop++){
				if (enemiesPresent.get(loop).getID() == trackingNumber){
					return loop;
				}
		}
		return 0;
	}
	
	//Check which enemies went through the portal
	public int checkPresence(){
		int loop;
		for (loop = 0; loop < enemiesPresent.size(); loop++){
				if (!enemiesPresent.get(loop).stillPresent() && !enemiesPresent.get(loop).gotChecked() && enemiesPresent.get(loop).portalPassing()){
					enemiesPassed++;
					enemiesPresent.get(loop).nowChecked();
				}
		}
		return enemiesPassed;	
	}
	
	//Check the number of remaining enemies
	public int enemyStrength(){
		int loop;
		for (loop = 0; loop < enemiesPresent.size(); loop++){
				if (!enemiesPresent.get(loop).stillPresent() && !enemiesPresent.get(loop).gotChecked()){
					enemiesPassed++;
					enemiesPresent.get(loop).nowChecked();
				}
		}
		return enemiesPresent.size();
	}
	
	//If an enemy passes, the player losses a life for each enemy
	//If the enemy is a boss, 10 lives are lost
	public boolean lossOfLife(){
		int loop;
		for (loop = 0; loop < enemiesPresent.size(); loop++){
				if (enemiesPresent.get(loop).portalPassing() && !enemiesPresent.get(loop).lifeLost()){
					enemiesPresent.get(loop).lifeRemoved();
					return true;
				}
		}
		return false;
	}
	
	//Draws each enemy
	public void drawWave(Graphics g){
		int loop;
		for (loop = 0; loop < enemiesPresent.size(); loop++){
				enemiesPresent.get(loop).drawEnemy(g);
		}
		
		
	}
		
}

