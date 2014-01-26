//Credits
//Tristan Lutgen = Lore Writer
//Leo London = Coder and Designer
//Kevin London = Coding Contributer

//Special Thanks
//Stack Overflow for Images
//Oracle for FontMetrics
//Mr. Wile for awesome

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TowerDefense extends JPanel implements ActionListener, MouseListener, MouseMotionListener{

	private int phase = 2;
	private static int TITLE = 0;
	private static int PROLOGUE = 1;
	private static int INVASION = 2;
	
	//Title
	private Player player;
	private String playerName;
	private int playerRace = 0;
	//private static int EARTH = 0;
	//private static int WATER = 1;
	//private static int AIR = 2;
	//private static int FIRE = 3;
	
	private int difficultyLevel;
	private static int EASY = 0;
	private static int NORMAL = 1;
	private static int HARD = 2;
	private static int EXPERT = 3;
	
	private int fader = 0;
	private Timer transitioner = new Timer(1000, this);
	
	//Prologue
	private Timer prologueTyper = new Timer(25, this);
	private JLabel prologueText = new JLabel("", SwingConstants.CENTER);
	
	
	//Invasion
	private MapGeneration environment;
	
	private JFrame frame = new JFrame();
	private JPanel textBox = new JPanel();
	private JPanel textBoxButtons = new JPanel();
	private JPanel bottomScreen = new JPanel();
	private Container canvas = frame.getContentPane();
	
	Turret glassDisplay[] = new Turret[5];
	ArrayList<Turret> turretBuilder = new ArrayList<Turret>();
	ArrayList<Ammunition> shotsFired = new ArrayList<Ammunition>();
	private boolean holdingTurret = false;
	private Timer turretHolder = new Timer(1, this);
	private int turretsPlaced = 0;
	
	//Until I figure out what is going on...
	JButton bottomButtons[] = new JButton[6];
	private JLabel bottomText = new JLabel("Select a difficulty level!", SwingConstants.CENTER);
	
	private Wave spawnedWave;
	private boolean waveActive = false;
	private Timer enemyMover = new Timer(30, this);
	private Timer enemyMoverX2 = new Timer(15, this);
	private Timer bulletTime = new Timer(4, this);
	private Timer bulletTimeX2 = new Timer(2, this);
	private boolean fastPaced = false;
	private int waveNumber = 0;
	private boolean gameActive = true;
	
	private int mX;
	private int mY;
	
	private Image background;
	
	
	public TowerDefense(){
			
		setPreferredSize(new Dimension(800, 600));
		canvas.add(this);
		setBackground(Color.BLACK);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Title
		
		//Prologue
		//fullProlougeText();
		//prologueText.setForeground(Color.WHITE);
		textBox.add(prologueText, BorderLayout.NORTH);
		//prologueTyper.start();		
		
		//music();
		
		//Invasion
		textBox.remove(prologueText);
		//textBox.setLayout(new BorderLayout());
		
		player = new Player(playerRace);
		
		environment = new MapGeneration();
		
		bottomScreen.setLayout(new BorderLayout());
		
		bottomScreen.setPreferredSize(new Dimension(600, 80));
		bottomScreen.setBackground(new Color(105, 56, 6));
		
		canvas.add(bottomScreen, BorderLayout.SOUTH);
		
		ImageIcon bg = new ImageIcon("Cosmic_BG.jpg");
		background = bg.getImage();
		
		glassDisplay[0] = new GunTurret(610, 100);
		glassDisplay[1] = new BombTurret(650, 100);
		glassDisplay[2] = new RayTurret(690, 100);
		glassDisplay[3] = new ComTurret(730, 100);
		glassDisplay[4] = new RacialTurret(770, 100, playerRace);
		
		bottomButtons[0] = new JButton("Easy");
		bottomButtons[1] = new JButton("Normal");
		bottomButtons[2] = new JButton("Hard");
		bottomButtons[3] = new JButton("Expert");
		bottomButtons[4] = new JButton("Spawn Wave");
		bottomButtons[5] = new JButton("Speed x 2");
		
		bottomText.setForeground(Color.WHITE);
		bottomText.setText("Select a difficulty level!");
		bottomText.setFont(new Font("Serif", Font.BOLD, 20));
		textBox.add(bottomText);
		
		int loop;
		for(loop = 0; loop < 6; loop++){
			textBoxButtons.add(bottomButtons[loop]);
			bottomButtons[loop].addActionListener(this);
		}
		bottomButtons[4].setVisible(false);
		bottomButtons[5].setVisible(false);
		
		//bottomScreen.setLayout(new BorderLayout());
		bottomScreen.add(textBox, BorderLayout.NORTH);
		bottomScreen.add(textBoxButtons, BorderLayout.SOUTH);
		textBox.setBackground(new Color(105, 56, 6));
		textBoxButtons.setBackground(new Color(105, 56, 6));
		bottomScreen.setBackground(new Color(105, 56, 6));
		
		addMouseListener(this);
		addMouseMotionListener(this);

		frame.pack();
		frame.setVisible(true);
	}
	
	public void music(){
		
		if (phase == TITLE){
			//Play http://www.youtube.com/watch?v=g_1U7L_uDA4
			//Fade into title screen until 0:09
		}
		
		if (phase == PROLOGUE){
			//Play http://www.youtube.com/watch?v=Z4nIweztlcw
			
		}
		else if (phase == INVASION){
			//Play http://www.youtube.com/watch?v=GuytViFsZUw
		}
	}
	
	public void actionPerformed(ActionEvent a){
		Object source = a.getSource();
		
		if (source == bottomButtons[0]){
			difficultyLevel = EASY;
			difficultySelection();
		}
		
		else if (source == bottomButtons[1]){
			difficultyLevel = NORMAL;
			difficultySelection();
		}
		
		else if (source == bottomButtons[2]){
			difficultyLevel = HARD;
			difficultySelection();
		}
		
		else if (source == bottomButtons[3]){
			difficultyLevel = EXPERT;
			difficultySelection();
		}
		
		else if (source == bottomButtons[4]){
			if (!waveActive){
				waveNumber++;
				spawnedWave = new Wave(waveNumber, difficultyLevel);
				if (!spawnedWave.getTenacity()){
					bottomText.setText("Wave Number: " + waveNumber + ", Status: "+spawnedWave.getStatus());
				}
				else{
					bottomText.setText("Wave Number: " + waveNumber + ", Status: " + spawnedWave.getStatus() + " and Tenacious (Resists Racial Turrets)");
				}
				bottomButtons[5].setVisible(true);
				waveActive = true;
				bottomButtons[4].setEnabled(false);
				bottomButtons[5].setEnabled(true);
				if (!fastPaced){
					enemyMover.start();
					bulletTime.start();
				}
				else{
					enemyMoverX2.start();
					bulletTimeX2.start();
				}
				
			}
		}
		
		else if (source == bottomButtons[5]){
			if (!fastPaced){
				enemyMover.stop();
				bottomButtons[5].setText("Speed x 1");
				enemyMoverX2.start();
				fastPaced = true;
			}
			else{
				enemyMoverX2.stop();
				bottomButtons[5].setText("Speed x 2");
				enemyMover.start();
				fastPaced = false;
			}
		}
		
		else if (source == transitioner){
			
		}
		else if (source == prologueTyper){
			
		}
		
		if (source == enemyMover || source == enemyMoverX2){
			spawnedWave.enemyMover();
			wavePresence();
			checkLossOfLife();
			repaint();
		}
		
		if (source == bulletTime || source == bulletTimeX2){
			checkTurretRanges();
			int loop;
			int moveLoop;
			for (loop = 0; loop < shotsFired.size(); loop++){
				shotsFired.get(loop).targetTracker(spawnedWave.enemiesPresent.get(0).getLocX()+spawnedWave.getSize()/2, spawnedWave.enemiesPresent.get(0).getLocY()+spawnedWave.getSize()/2); 
				for (moveLoop = 0; moveLoop < 10; moveLoop++){
					shotsFired.get(loop).move();
					if (shotsFired.get(loop).bulletHit()){
						spawnedWave.enemiesPresent.get(0).wasAttacked(shotsFired.get(loop).bulletPower(), loop);
						if (!spawnedWave.enemiesPresent.get(0).stillPresent() && !spawnedWave.enemiesPresent.get(0).portalPassing()){
							player.moneyGain(spawnedWave.enemiesPresent.get(0).moneySalvaged());
							spawnedWave.enemiesPresent.remove(0);
						}
						moveLoop = 10;
						shotsFired.remove(loop);
					}
					repaint();
				}
			}
		}
		
		if (source == turretHolder){
			turretBuilder.get(turretsPlaced).updateTurretPosition(mX, mY);
			turretBuilder.get(turretsPlaced).notOnRoad(environment.Rhoadside(turretBuilder.get(turretsPlaced).turretArea()));
			repaint();
		}
	}
	
	public void checkTurretRanges(){
		int loop;
		int turretLoop;
		for (loop = 0; loop < spawnedWave.enemyStrength(); loop++){
			for (turretLoop = 0; turretLoop < turretBuilder.size(); turretLoop++){
				turretBuilder.get(turretLoop).coolOff();
				if (turretBuilder.get(turretLoop).inRange(spawnedWave.enemiesPresent.get(loop).getLocX(), spawnedWave.enemiesPresent.get(loop).getLocY(), spawnedWave.getSize()) && gameActive && turretBuilder.get(turretLoop).turretType() != "Com" &&  turretBuilder.get(turretLoop).firedUp){
					shotsFired.add(new Ammunition(turretBuilder.get(turretLoop).locX+turretBuilder.get(turretLoop).size, turretBuilder.get(turretLoop).locY+turretBuilder.get(turretLoop).size, spawnedWave.enemiesPresent.get(loop).getLocX()+spawnedWave.getSize()/2, spawnedWave.enemiesPresent.get(loop).getLocY()+spawnedWave.getSize()/2, turretBuilder.get(turretLoop).turretType(), playerRace));
					turretBuilder.get(turretLoop).readyToFire(false);
				}
			}
		}
	}
	
	public void wavePresence(){
		spawnedWave.checkPresence();
		if (spawnedWave.checkPresence() == spawnedWave.enemyStrength()){
			bottomButtons[4].setEnabled(true);
			bottomButtons[5].setEnabled(false);
			waveActive = false;
			if (!fastPaced){
				enemyMover.stop();
				bulletTime.stop();
			}
			else{
				enemyMoverX2.stop();
				bulletTimeX2.stop();
			}
		}
	}
	
	public void checkLossOfLife(){
		if (waveNumber != 0){
			int loop;
			for (loop = 0; loop < spawnedWave.enemyStrength(); loop++){
				if (spawnedWave.enemyStrength() == 1){
					int bossLoss;
					for (bossLoss = 0; bossLoss < 10; bossLoss++){
						player.loseALife(spawnedWave.lossOfLife());
					}
				}
				else{
					player.loseALife(spawnedWave.lossOfLife());
				}
				repaint();
				if (player.getLives() <= 0){
					gameOver();
				}
			}
		}
	}
	
	public void difficultySelection(){
		bottomText.setText("Now spawn the wave!");
		int loop;
		for(loop = 0; loop < 4; loop++){
			textBoxButtons.remove(bottomButtons[loop]);
		}
		bottomButtons[4].setVisible(true);
		spawnedWave = new Wave(waveNumber, difficultyLevel);
	}
	
	public void gameOver(){
		textBoxButtons.remove(bottomButtons[4]);
		textBoxButtons.remove(bottomButtons[5]);
		bottomText.setText("GG. ");
		gameActive = false;
	}
	
	public void mouseDragged(MouseEvent e){
		mX = e.getX();
		mY = e.getY();
		
		int loop;
		if (!holdingTurret){
			for (loop = 0; loop < turretBuilder.size(); loop++){
				if (turretBuilder.get(loop).turretArea().contains(mX, mY)){
					turretBuilder.get(loop).changeFocus(true);
				}
				else{
					turretBuilder.get(loop).changeFocus(false);
				}
			}
			for(loop = 0; loop < 5; loop++){
				if (glassDisplay[loop].overTurret(mX, mY)){
					if (glassDisplay[loop].turretType() == "Gun"){
						turretBuilder.add(new GunTurret(mX-13, mY-13));
					}
					else if (glassDisplay[loop].turretType() == "Bomb"){
						turretBuilder.add(new BombTurret(mX-13, mY-13));
					}
					else if (glassDisplay[loop].turretType() == "Ray"){
						turretBuilder.add(new RayTurret(mX-13, mY-13));
					}
					else if (glassDisplay[loop].turretType() == "Com"){
						turretBuilder.add(new ComTurret(mX-13, mY-13));
					}
					else if (glassDisplay[loop].turretType() == "Racial"){
						turretBuilder.add(new RacialTurret(mX-13, mY-13, playerRace));
					}
					if (turretBuilder.get(turretsPlaced).buy() > player.getGold()){
						turretBuilder.remove(turretsPlaced);
					}
					else{
						player.moneyLoss(turretBuilder.get(turretsPlaced).buy());
						holdingTurret = true;
						turretHolder.start();
					}
				}
			} 
		}

	}
	
	public void mouseMoved(MouseEvent e){
		mX = e.getX();
		mY = e.getY();
		
		
	}
	
	public void mouseClicked(MouseEvent e){
		int loop;
		if (!holdingTurret){
			for (loop = 0; loop < turretBuilder.size(); loop++){
				if (turretBuilder.get(loop).turretArea().contains(mX, mY)){
					turretBuilder.get(loop).changeFocus(true);
				}
				else{
					turretBuilder.get(loop).changeFocus(false);
				}
			}
			for(loop = 0; loop < 5; loop++){
				if (glassDisplay[loop].overTurret(mX, mY)){
					if (glassDisplay[loop].turretType() == "Gun"){
						turretBuilder.add(new GunTurret(mX-13, mY-13));
					}
					else if (glassDisplay[loop].turretType() == "Bomb"){
						turretBuilder.add(new BombTurret(mX-13, mY-13));
					}
					else if (glassDisplay[loop].turretType() == "Ray"){
						turretBuilder.add(new RayTurret(mX-13, mY-13));
					}
					else if (glassDisplay[loop].turretType() == "Com"){
						turretBuilder.add(new ComTurret(mX-13, mY-13));
					}
					else{
						turretBuilder.add(new RacialTurret(mX-13, mY-13, playerRace));
					}
					if (turretBuilder.get(turretsPlaced).buy() > player.getGold()){
						turretBuilder.remove(turretsPlaced);
					}
					else{
						player.moneyLoss(turretBuilder.get(turretsPlaced).buy());
						holdingTurret = true;
						turretHolder.start();
					}
					
				}
			} 
		}
		else if (holdingTurret && turretBuilder.get(turretsPlaced).nearTrack()){
			turretHolder.stop();
			holdingTurret = false;
			environment.addTurret(mX-13, mY-13);
			turretBuilder.get(turretsPlaced).powered();
			turretsPlaced++;
		}
		
		repaint();
	}
	 
	public void mouseEntered(MouseEvent e){
	}
	 
	public void mouseExited(MouseEvent e){
		 
	}
	
	public void mousePressed(MouseEvent e){
	}
	
	public void mouseReleased(MouseEvent e){
	}



/*public class Start{
//Start screen

}

public class Player{
//Displays money

}

public class Spells(
//Player Spells
}

public class Turret{
	Upgrade
	Sell
	Place
	Buy
	Shoot
	Type
	Bomb (arcs, make the bomb bigger as it reaches the midpoint)
	Larger Radius, longer cd
	Bounces 2 times, then explodes on last target
	Ray (Up to 3 beams at once)
	Increases damage and attack speed over time 
	Doubles amount of beams 
	Gun (Basic turret)
	Rapid Fire
	Sniper
	Communicator(Does not fire, give nearby turrets 10% attack speed, stacks up to 5 times)
	Nearby turrets give an extra 3 gold per kill
	Nearby turrets gain 10% damage
	Racial turrets(CC, depends on race):
	Earth - Enchamber(2.0 seconds)
	Water - Slow (1.5 seconds)
	Air - Knockback (1.0 seconds)
	Fire - Stun (0.5 seconds)

	public class Ammunition extends Turret{
		Type
		Speed
		On hit

	}

}

public class Waves{

	Enemy amount
	Enemy type
	Kill count
	Wave end
	Wave Number
	Enemy pathing
	Victory
	Defeat

	public class Enemy{
		
		Death
		Type
		Health bar

	}
	
}*/
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		if (phase == PROLOGUE){
			g.setColor(new Color (0, 0, 0, fader));
			g.fillRect(0, 0, getWidth(), getHeight());	
		}
		else if (phase == INVASION){
			g.drawImage(background, 0, 0, this);
			environment.drawShop(g);
			environment.drawTrack(g);
			if (waveActive){
				spawnedWave.drawWave(g);
			}
			int loop;
			for(loop = 0; loop < 5; loop++){
				glassDisplay[loop].drawTurret(g);
			}
			
			for (loop = 0; loop < turretBuilder.size(); loop++){
				turretBuilder.get(loop).drawTurret(g);
			}
			for (loop = 0; loop < turretBuilder.size(); loop++){
				turretBuilder.get(loop).drawRange(g, turretBuilder.get(loop).nearTrack());
			}
			for (loop = 0; loop < shotsFired.size(); loop++){
				shotsFired.get(loop).drawBullet(g);
			}
			//Life and Money Displays
			g.setColor(Color.WHITE);
			g.setFont(new Font("Bell MT", Font.BOLD, 28));
			FontMetrics metrics = g.getFontMetrics(new Font("Bell MT", Font.BOLD, 28));
			int hgt = metrics.getHeight();
			int adv = metrics.stringWidth("Lives: "+player.getLives());
			g.drawString("Lives: "+player.getLives(), getWidth() - adv, hgt-3);
			
			adv = metrics.stringWidth("Gold: "+player.getGold());
			g.drawString("Gold: "+player.getGold(), getWidth() - adv, hgt*2-3);
		}
	}

	public static void main (String[] args){
		new TowerDefense();
	}
	
	public void fullProlougeText(){
		prologueText.setText("\t The stardate is 2505. General Ivan Nomundo, who goes by the name Evil Overlord, is currently massing a force to attack the Yvinian Galaxy. He is targeting this specific galaxy because of the rare earth found in the middle of it. With this resource, Xzoltinium, mined, the Evil Overlord plans to use it to conquer everything, everywhere, and everyone. \n \n\tYou have recently been recruited by G.O.D., also known as the GalaxyÕs Operational Defense, to take care of Ivan before he can retrieve the rare material. The Yvinians have given you their galaxy and their entire arsenal of prototype rift weapons, able to shoot in space, lucky that, huh? It will be up to you to save life as we know it. So... are you ready?");
		
	}
	
}


