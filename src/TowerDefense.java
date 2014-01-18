//Credits
//Tristan L. = Writer
//Leo L. = Coder and Designer

//Special Thanks
//Stack Overflow for Images
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
	private int player_race = 2;
	private static int EARTH = 0;
	private static int WATER = 1;
	private static int AIR = 2;
	private static int FIRE = 3;
	
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
	private boolean holdingTurret = false;
	private Timer turretHolder = new Timer(1, this);
	private int turretsPlaced = 0;
	
	//Until I figure out what is going on...
	JButton bottomButtons[] = new JButton[6];
	private JLabel bottomText = new JLabel("Select a difficulty level!", SwingConstants.CENTER);
	
	private Wave spawnedWave;
	private boolean waveActive = false;
	private Timer enemyMover = new Timer(26, this);
	private Timer enemyMoverX2 = new Timer(13, this);
	private boolean fastPaced = false;
	private int waveNumber = 0;
	
	private int mX;
	private int mY;
	
	private JLabel lifeAndMoneyDisplay;
	
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
		
		player = new Player(player_race);
		lifeAndMoneyDisplay = new JLabel("Lives: <3 "+player.getLives()+"\nGold: $ "+player.getGold(), SwingConstants.RIGHT);
		
		environment = new MapGeneration();
		
		bottomScreen.setPreferredSize(new Dimension(600, 80));
		bottomScreen.setBackground(new Color(105, 56, 6));
		
		canvas.add(bottomScreen, BorderLayout.SOUTH);
		
		ImageIcon bg = new ImageIcon("Cosmic_BG.jpg");
		background = bg.getImage();
		
		glassDisplay[0] = new GunTurret(610, 100);
		glassDisplay[1] = new BombTurret(650, 100);
		glassDisplay[2] = new RayTurret(690, 100);
		glassDisplay[3] = new ComTurret(730, 100);
		glassDisplay[4] = new RacialTurret(player_race, 770, 100);
		
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
		
		lifeAndMoneyDisplay.setForeground(Color.WHITE);
		lifeAndMoneyDisplay.setFont(new Font("Serif", Font.BOLD, 24));
		add(lifeAndMoneyDisplay, BorderLayout.NORTH);
		
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
				spawnedWave = new Wave(waveNumber, false, difficultyLevel);
				bottomText.setText("Wave Number: " + waveNumber);
				bottomButtons[5].setVisible(true);
				waveActive = true;
				bottomButtons[4].setEnabled(false);
				bottomButtons[5].setEnabled(true);
				if (!fastPaced){
					enemyMover.start();
				}
				else{
					enemyMoverX2.start();
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
		
		if (source == turretHolder){
			turretBuilder.get(turretsPlaced).updateTurretPosition(mX, mY);
			turretBuilder.get(turretsPlaced).notOnRoad(environment.Rhoadside(turretBuilder.get(turretsPlaced).turretArea()));
			repaint();
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
			}
			else{
				enemyMoverX2.stop();
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
				lifeAndMoneyDisplay.setText("Lives:  <3 "+player.getLives()+"\nGold: $ "+player.getGold());
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
		spawnedWave = new Wave(waveNumber, false, difficultyLevel);
	}
	
	public void gameOver(){
		textBoxButtons.remove(bottomButtons[4]);
		textBoxButtons.remove(bottomButtons[5]);
		bottomText.setText("GG. ");
		if (waveActive){
			waveActive = false;
			if (!fastPaced){
				enemyMover.stop();
			}
			else{
				enemyMoverX2.stop();
			}
		}
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
						turretBuilder.add(new RacialTurret(player_race, mX-13, mY-13));
					}
					if (turretBuilder.get(turretsPlaced).buy() > player.getGold()){

						turretBuilder.remove(turretsPlaced);;
					}
					else{
						player.moneyLoss(turretBuilder.get(turretsPlaced).buy());
						lifeAndMoneyDisplay.setText("Lives:  <3 "+player.getLives()+"\nGold: $ "+player.getGold());
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
						turretBuilder.add(new RacialTurret(player_race, mX-13, mY-13));
					}
					if (turretBuilder.get(turretsPlaced).buy() > player.getGold()){

						turretBuilder.remove(turretsPlaced);;
					}
					else{
						player.moneyLoss(turretBuilder.get(turretsPlaced).buy());
						lifeAndMoneyDisplay.setText("Lives:  <3 "+player.getLives()+"\nGold: $ "+player.getGold());
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
			g.setColor(Color.WHITE);
			g.setFont(new Font("Serif", Font.BOLD, 20));
			g.drawString("Lives:  <3 "+player.getLives()+"\nGold: $ "+player.getGold(), 600, 100);
		}
	}

	public static void main (String[] args){
		new TowerDefense();
	}
	
	public void fullProlougeText(){
		prologueText.setText("\t The stardate is 2505. General Ivan Nomundo, who goes by the name 'Evil Overlord', is currently massing a force to attack the Yvinian Galaxy. He is targeting this specific galaxy because of the rare earth found in the middle of it. With this resource, Xzoltinium, mined, the Evil Overlord plans to use it to conquer everything, everywhere, and everyone. \n \n\tYou have recently been recruited by G.O.D., also known as the Galaxy's Operational Defense, to take care of Ivan before he can retrieve the rare material. The Yvinians have given you their galaxy and their entire arsenal of prototype rift weapons, able to shoot in space, lucky that, huh? It will be up to you to save life as we know it. So... are you ready?");

		
	}
	
}


