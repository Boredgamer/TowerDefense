import java.awt.*;
import java.util.ArrayList;


public class Wave {
	
	private int status;
	private static int BASIC = 0;
	private static int HEAVY = 1;
	private static int LIGHT = 2;
	private boolean tenacious;
	private boolean boss = false;
	
	private int waveNumber = 0;
	private int strengthInNumbers = 10;
	private int difficulty;
	private static int EASY = 0;
	private static int NORMAL = 1;
	private static int HARD = 2;
	private static int EXPERT = 3;
	
	
	private int speed = 10;
	private int size = 18;
	private int health = 50;
	private int temporaryHealth;
	private Color enemyC;
	
	private int enemiesPassed = 0;
	
	ArrayList<Enemy> enemiesPresent = new ArrayList<Enemy>();
	
	ArrayList<Integer> blueEndpointX = new ArrayList<Integer>();
	ArrayList<Integer> blueEndpointY = new ArrayList<Integer>();
	ArrayList<Boolean> blueTurningPoint = new ArrayList<Boolean>();
	
	ArrayList<Integer> redEndpointX = new ArrayList<Integer>();
	ArrayList<Integer> redEndpointY = new ArrayList<Integer>();
	ArrayList<Boolean> redTurningPoint = new ArrayList<Boolean>();
	
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
		private int gold_value = 10;
		private int endpointer = 0;
		private boolean midPoint = false;
		private boolean enemyPresence = true;
		private boolean enemyPassed = false;
		private boolean presenceCheck = false;
		private boolean lossOfLife = false;
		
		private Color enemyColor;
		
		public Enemy(int d, int spacing){
			generalDirection = d;
			path = d;
			
			int x = 0;
			int y = 0;
			if (path == BLUE){
				x = 50;
				y = 300;
				velocityX = -speed;
				direction = LEFT;
				enemyColor = Color.BLUE;
				x += 50*spacing;
			}
			else{
				x = 550;
				y = 300;
				velocityX = speed;
				direction = RIGHT;
				enemyColor = Color.RED;
				x -= 50*spacing;
			}
			
			enemyX = x - size/2;
			enemyY = y - size/2;
			
			health = temporaryHealth;
			if (boss){
				gold_value *= 10;
			}			
		}
		
		public void wasAttacked(int dmg){
			health -= dmg;
			if (health <= 0){
				death();
			}
		}
		
		public int death(){
			enemyPresence = false;
			return gold_value;
		}
		
		
		public void drawEnemy(Graphics g){
			g.setColor(enemyColor);
			if (path == BLUE && enemyPresence && enemyX+size/2 >= blueEndpointX.get(0) && (endpointer != 0 || generalDirection == BACKWARD)){
				g.fillOval(enemyX, enemyY, size, size);
			}
			else if (path == RED && enemyPresence && enemyX+size/2 <= redEndpointX.get(0) && (endpointer != 0 || generalDirection == FORWARD)){
				g.fillOval(enemyX, enemyY, size, size);
			}
			
			
		}
		
		public void move(){
			enemyX += velocityX;
			enemyY += velocityY;
			
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
		}
		
		public void enemyPass(){
			enemyPresence = false;
			enemyPassed = true;
			
			enemyColor = new Color (0, 0, 0, 0);
		}
		
		public boolean portalPassing(){
			return enemyPassed;
		}
		
		public boolean stillPresent(){
			return enemyPresence;
		}
		
		public boolean gotChecked(){
			return presenceCheck;
		}
		
		public void nowChecked(){
			presenceCheck = true;
		}
		
		public boolean lifeLost(){
			return lossOfLife;
		}
		
		public void lifeRemoved(){
			lossOfLife = true;
		}
		
		
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

	public Wave(int waveNumber, boolean tough, int d){
		//status = stats;
		tenacious = tough;
		difficulty = d;
		
		health += waveNumber*10;
		temporaryHealth = health;
		
		if (waveNumber % 10 == 0){
			boss = true;
			strengthInNumbers = 1;
		}
		
		findEndpoints();
		statusChanger();
		enemySpawner();
	}
	
	public void enemyMover(){
		int loop;
		for (loop = 0; loop < enemiesPresent.size(); loop++){
			if (enemiesPresent.get(loop).stillPresent()){
				enemiesPresent.get(loop).move();
			}
		}
	}
	
	public void statusChanger(){
		if (status == HEAVY){
			speed /= 2;
			temporaryHealth *= 2;
		}
		else if(status == LIGHT){
			speed *= 2;
			temporaryHealth /= 2;
		}
		if (boss){
			size *= 2;
			temporaryHealth *= 5;
		}
	}
	
	public boolean getTenacity(){
		return tenacious;
	}
	
	public void enemySpawner(){
		int spawnerLoop;
		int randomDirection;
		if (difficulty == EASY || difficulty == NORMAL){
			for (spawnerLoop = 0; spawnerLoop < strengthInNumbers; spawnerLoop++){
				enemiesPresent.add(new Enemy(0, spawnerLoop));
			}
		}
		else{
			for (spawnerLoop = 0; spawnerLoop < strengthInNumbers; spawnerLoop++){
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
	
	public int checkPresence(){
		int loop;
		for (loop = 0; loop < strengthInNumbers; loop++){
				if (!enemiesPresent.get(loop).stillPresent() && !enemiesPresent.get(loop).gotChecked()){
					enemiesPassed++;
					enemiesPresent.get(loop).nowChecked();
				}
		}
		return enemiesPassed;	
	}
	
	public int enemyStrength(){
		int loop;
		for (loop = 0; loop < strengthInNumbers; loop++){
				if (!enemiesPresent.get(loop).stillPresent() && !enemiesPresent.get(loop).gotChecked()){
					enemiesPassed++;
					enemiesPresent.get(loop).nowChecked();
				}
		}
		return strengthInNumbers;
	}
	
	public boolean lossOfLife(){
		int loop;
		for (loop = 0; loop < strengthInNumbers; loop++){
				if (enemiesPresent.get(loop).portalPassing() && !enemiesPresent.get(loop).lifeLost()){
					enemiesPresent.get(loop).lifeRemoved();
					return true;
				}
		}
		return false;
	}
	
	public void playerVictory(){
		
	}
	
	public void drawWave(Graphics g){
		//waveC = Color.GRAY;
		if (status == HEAVY){
			enemyC = Color.DARK_GRAY;
		}
		else if (status == LIGHT){
			enemyC = Color.LIGHT_GRAY;
		}
		else if (boss){
			enemyC = Color.BLACK;
		}
		int loop;
		for (loop = 0; loop < enemiesPresent.size(); loop++){
				enemiesPresent.get(loop).drawEnemy(g);
		}
		
		
	}
		
}

