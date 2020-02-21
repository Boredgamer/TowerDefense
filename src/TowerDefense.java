//Credits
//Tristan Lutgen = Lore Writer
//Leo London = Coder and Designer
//Kevin London = Coding Contributer

//Special Thanks
//Nintendo for the game over screen
//Blizzard for the BGM
//Stack Overflow for Images
//Oracle for FontMetrics
//Mr. Wile for being awesome

import javax.sound.sampled.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

public class TowerDefense extends JPanel implements ActionListener, MouseListener, MouseMotionListener, KeyListener{

	private int phase = 0;
	private final int TITLE = 0;
	private final int PROLOGUE = 1;
	private final int INVASION = 2;
	
	//Title
	private Player player;
	private String playerName;
	private int playerRace = 0;
	private final int EARTH = 0;
	private final int WATER = 1;
	private final int WIND = 2;
	private final int FIRE = 3;
	
	private int difficultyLevel;
	private final int EASY = 0;
	private final int NORMAL = 1;
	private final int HARD = 2;
	private final int EXPERT = 3;
	
	private JTextField name = new JTextField("Smik", 10);
	private JButton nameInput = new JButton("Submit");
	
	private Clip bgm;
	
	
		
	//Invasion
	private MapGeneration environment;
	
	private JFrame frame = new JFrame();
	private JPanel textBox = new JPanel();
	private JPanel textBoxButtons = new JPanel();
	private JPanel bottomScreen = new JPanel();
	private Container canvas = frame.getContentPane();
	
	private Turret glassDisplay[] = new Turret[5];
	private Rectangle sellOrUndo;
	private ArrayList<Turret> turretBuilder = new ArrayList<Turret>();
	private ArrayList<Ammunition> shotsFired = new ArrayList<Ammunition>();
	private boolean holdingTurret = false;
	private Timer turretHolder = new Timer(1, this);
	private int turretsPlaced = 0;
	private int racialTurretsPlaced = 0;
	
	private JButton bottomButtons[] = new JButton[6];
	private JLabel bottomText = new JLabel("Please enter your name.", SwingConstants.CENTER);
	
	private Wave spawnedWave;
	private boolean waveActive = false;
	private Timer enemyMover = new Timer(30, this);
	private Timer enemyMoverX2 = new Timer(15, this);
	private Timer bulletTime = new Timer(4, this);
	private Timer bulletTimeX2 = new Timer(2, this);
	private boolean fastPaced = false;
	private int waveNumber = 0;
	private boolean difficultySelected = false;
	private boolean gameActive = true;
	private boolean victoryAchieved = false;
	
	private int mX;
	private int mY;
	
	private Image background;
	
	
	public TowerDefense(){
			
		setPreferredSize(new Dimension(800, 600));
		canvas.add(this);
		setBackground(Color.BLACK);
		frame.setResizable(false);
		frame.setTitle("Defend the Xzoltinium");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		bottomScreen.setPreferredSize(new Dimension(600, 80));
		bottomScreen.setBackground(Color.BLACK);
		
		canvas.add(bottomScreen, BorderLayout.SOUTH);
		ImageIcon bg = new ImageIcon("Title_BG.jpg");
		background = bg.getImage();
		
		bottomScreen.setLayout(new BorderLayout());
		bottomButtons[0] = new JButton("Clay (Earth)");
		bottomButtons[1] = new JButton("Omi (Water)");
		bottomButtons[2] = new JButton("Raimundo (Wind)");
		bottomButtons[3] = new JButton("Kimiko (Fire)");
		
		bottomText.setForeground(Color.WHITE);
		bottomText.setFont(new Font("Serif", Font.BOLD, 20));
		textBox.add(bottomText);
		textBox.setBackground(Color.BLACK);
		bottomScreen.add(textBox, BorderLayout.NORTH);
		textBoxButtons.add(bottomButtons[0]);
		textBoxButtons.add(bottomButtons[1]);
		textBoxButtons.add(bottomButtons[2]);
		textBoxButtons.add(bottomButtons[3]);
		textBoxButtons.setBackground(Color.BLACK);
		bottomScreen.add(textBoxButtons);
		int loop;
		for(loop = 0; loop < 4; loop++){
			bottomButtons[loop].setVisible(false);
			bottomButtons[loop].addActionListener(this);
		}
		textBoxButtons.add(name);
		textBoxButtons.add(nameInput);
		nameInput.addActionListener(this);

		frame.pack();
		frame.setVisible(true);
	}
	
	//Transitions from the title screen to the prologue screen
	public void titleToPrologue(){
		bottomButtons[0].setText("My body is ready!");
		bottomButtons[1].setVisible(false);
		bottomButtons[2].setVisible(false);
		bottomButtons[3].setVisible(false);
		textBox.setVisible(false);
		phase = PROLOGUE;
		playSound("Prologue_BGM.wav");
		repaint();
	}
	
	//Transitions from the prologue screen to the invasion screen
	public void prologueToInvasion(){
		
		player = new Player();
		
		environment = new MapGeneration();
				
		ImageIcon bg = new ImageIcon("Invasion_BG.jpg");
		background = bg.getImage();
		
		glassDisplay[0] = new GunTurret(610, 100);
		glassDisplay[1] = new BombTurret(650, 100);
		glassDisplay[2] = new RayTurret(690, 100);
		glassDisplay[3] = new ComTurret(730, 100);
		glassDisplay[4] = new RacialTurret(770, 100, playerRace);
		sellOrUndo = new Rectangle(620, 500, 160, 80);
		
		bottomButtons[0].setText("Easy");
		bottomButtons[1].setText("Normal");
		bottomButtons[2].setText("Hard");
		bottomButtons[3].setText("Expert");
		bottomButtons[4] = new JButton("Spawn Wave");
		bottomButtons[5] = new JButton("Speed x 2");
		
		bottomText.setText("Select a difficulty level!");

		int loop;
		for(loop = 4; loop < 6; loop++){
			textBoxButtons.add(bottomButtons[loop]);
			bottomButtons[loop].addActionListener(this);
			bottomButtons[loop].setVisible(false);
			bottomButtons[loop].setFocusable(false);
		}
		textBox.setVisible(true);
		bottomButtons[1].setVisible(true);
		bottomButtons[2].setVisible(true);
		bottomButtons[3].setVisible(true);
		
		
		textBox.setBackground(new Color(105, 56, 6));
		textBoxButtons.setBackground(new Color(105, 56, 6));
		bottomScreen.setBackground(new Color(105, 56, 6));
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
		
		frame.pack();
		phase = INVASION;
		stopBGM();
		playSound("Invasion_BGM.wav");
		repaint();
	}
	
	
	
	//Plays background music and sounds
	public void playSound(String path){ 
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile()); 
			if (path == "TurretPurchase.wav"){
				Clip clip = AudioSystem.getClip();                 
				clip.open(audioInputStream);           
				clip.start();
			}
			else{
				bgm = AudioSystem.getClip();                 
				bgm.open(audioInputStream);           
				bgm.start();
				bgm.loop(bgm.LOOP_CONTINUOUSLY);
			}
		}  
		catch(Exception ex) {          
			System.out.println("Error with playing sound.");          
			ex.printStackTrace();  
		} 
	}
	
	//Stops current background music
	public void stopBGM(){
		bgm.stop();
	}
	
	public void actionPerformed(ActionEvent a){
		Object source = a.getSource();
		if (phase == TITLE){
			if (source == nameInput && !name.getText().equals("")){
				playerName = name.getText();
				raceSelection();
			}
			else if (source == bottomButtons[0]){
				playerRace = EARTH;
				titleToPrologue();
			}
			
			else if (source == bottomButtons[1]){
				playerRace = WATER;
				titleToPrologue();
			}
			
			else if (source == bottomButtons[2]){
				playerRace = WIND;
				titleToPrologue();
			}
			
			else if (source == bottomButtons[3]){
				playerRace = FIRE;
				titleToPrologue();
			}
			
		}
		
		else if (phase == PROLOGUE){
			prologueToInvasion();
		}
		
		else if (phase == INVASION){
			//Difficulty Selectors
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
			
			//Spawns the Wave
			else if (source == bottomButtons[4] && gameActive && !waveActive){
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
			
			//Changes the pace of the game flow
			else if (source == bottomButtons[5] && gameActive){
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
		}
		
		//Moves the enemies in the wave
		if (source == enemyMover || source == enemyMoverX2){
			spawnedWave.enemyMover();
			wavePresence();
			checkLossOfLife();
			repaint();
		}
		
		//Turrets firing at enemies
		if (source == bulletTime || source == bulletTimeX2){
			checkTurretRanges();
			bulletTrain();
		}
		
		//Updates the position of the held turret
		if (source == turretHolder){
			turretBuilder.get(turretsPlaced).updateTurretPosition(mX, mY);
			turretBuilder.get(turretsPlaced).notOnRoad(environment.Rhoadside(turretBuilder.get(turretsPlaced).turretArea()));
			repaint();
		}
		if (source == bottomButtons[4] && !gameActive){
			new TowerDefense();
		}
		
		if (source == bottomButtons[5] && !gameActive){
			gameActive = true;
			bottomButtons[4].setText("Spawn Wave");
			if (!fastPaced){
				bottomButtons[5].setText("Speed x 2");

			}
			else{
				bottomButtons[5].setText("Speed x 1");
			}
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
	
	//Allows for selection of the player race
	public void raceSelection(){
		name.setVisible(false);
		nameInput.setVisible(false);
		bottomScreen.remove(name);
		bottomScreen.remove(nameInput);
		
		bottomText.setText("Choose your race (and element)!");
		int loop;
		for(loop = 0; loop < 4; loop++){
			bottomButtons[loop].setVisible(true);
			bottomButtons[loop].setFocusable(true);
		}
	}
	
	//Allows for spawning of waves after the difficulty has been chosed
	public void difficultySelection(){
		bottomText.setText("Now spawn the wave!");
		bottomButtons[3].setVisible(false);
		int loop;
		for(loop = 0; loop < 4; loop++){
			textBoxButtons.remove(bottomButtons[loop]);
		}
		bottomButtons[4].setVisible(true);
		spawnedWave = new Wave(waveNumber, difficultyLevel);
		difficultySelected = true;
		requestFocus();
	}
	
	//Checks if the turrets can shoot at an enemy
	public void checkTurretRanges(){
		int loop;
		int turretLoop;
		for (turretLoop = 0; turretLoop < turretBuilder.size(); turretLoop++){
			for (loop = 0; loop < spawnedWave.enemyStrength(); loop++){
				if (turretBuilder.get(turretLoop).inRange(spawnedWave.enemiesPresent.get(loop).getLocX(), spawnedWave.enemiesPresent.get(loop).getLocY(), spawnedWave.getSize()) && spawnedWave.enemiesPresent.get(loop).getSpawned() && !spawnedWave.enemiesPresent.get(loop).entombment() && gameActive && turretBuilder.get(turretLoop).turretType() != "Com" && (spawnedWave.enemiesPresent.get(loop).skipping() || turretBuilder.get(turretLoop).turretType() != "Racial") && turretBuilder.get(turretLoop).firedUp){
					shotsFired.add(new Ammunition(spawnedWave.enemiesPresent.get(loop).getID(), turretBuilder.get(turretLoop).locX+turretBuilder.get(turretLoop).size/2, turretBuilder.get(turretLoop).locY+turretBuilder.get(turretLoop).size/2, spawnedWave.enemiesPresent.get(loop).getLocX()+spawnedWave.getSize()/2, spawnedWave.enemiesPresent.get(loop).getLocY()+spawnedWave.getSize()/2, turretBuilder.get(turretLoop).turretType(), playerRace));
					turretBuilder.get(turretLoop).loadBlown();
				}
			}
			turretBuilder.get(turretLoop).coolOff(communicatorBuffDetector(turretLoop));
		}
	}
	
	//Checks if the turret is in the range of a Com turret buff
	public boolean communicatorBuffDetector(int turretLoop){
		int comLoop;
		for (comLoop = 0; comLoop < turretBuilder.size(); comLoop++){
			if (turretBuilder.get(comLoop).turretType() == "Com" && turretBuilder.get(comLoop).turnedOn && turretBuilder.get(comLoop).inRange(turretBuilder.get(turretLoop).locX-3,turretBuilder.get(turretLoop).locY-3, turretBuilder.get(turretLoop).size+6)){
					return true;
			}
		}
		return false;
	}
	
	//Moves the in-flight ammunition
	public void bulletTrain(){
		int loop;
		int moveLoop;
		for (loop = 0; loop < shotsFired.size(); loop++){
			if (spawnedWave.IDscanner(shotsFired.get(loop).getTarget())){
				shotsFired.get(loop).targetTracker(spawnedWave.enemiesPresent.get(spawnedWave.IDTracker(shotsFired.get(loop).getTarget())).getLocX()+spawnedWave.getSize()/2, spawnedWave.enemiesPresent.get(spawnedWave.IDTracker(shotsFired.get(loop).getTarget())).getLocY()+spawnedWave.getSize()/2); 
				for (moveLoop = 0; moveLoop < 2; moveLoop++){
					shotsFired.get(loop).move();
					if (shotsFired.get(loop).bulletHit()){
						spawnedWave.enemiesPresent.get(spawnedWave.IDTracker(shotsFired.get(loop).getTarget())).wasAttacked(shotsFired.get(loop).attackDamage(), playerRace);
						if (!spawnedWave.enemiesPresent.get(spawnedWave.IDTracker(shotsFired.get(loop).getTarget())).stillPresent() && !spawnedWave.enemiesPresent.get(spawnedWave.IDTracker(shotsFired.get(loop).getTarget())).portalPassing()){
							player.moneyGain(spawnedWave.enemiesPresent.get(spawnedWave.IDTracker(shotsFired.get(loop).getTarget())).moneySalvaged());
							spawnedWave.enemiesPresent.remove((spawnedWave.IDTracker(shotsFired.get(loop).getTarget())));
						}
						moveLoop = 2;
						shotsFired.remove(loop);
					}
					repaint();
				}
			}
			else{
				shotsFired.remove(loop);
			}
		}
	}
	
	//Checks if all enemies have been cleared and marks the wave over
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
			shotsFired.clear();
			if (waveNumber == 50){
				victory();
			}
		}
	}
	
	//Checks if enemies have passed and if the player should lose any lives
	public void checkLossOfLife(){
		if (waveNumber != 0){
			int loop;
			for (loop = 0; loop < spawnedWave.enemyStrength(); loop++){
				if (spawnedWave.enemyStrength() == 1){
					player.loseALife(spawnedWave.lossOfLife(), true);
				}
				else{
					player.loseALife(spawnedWave.lossOfLife(), false);
				}
				repaint();
				if (player.getLives() <= 0){
					gameOver();
				}
			}
		}
	}
	
	//Displays the victory screen if the player makes it past wave 50
	public void victory(){
		bottomButtons[4].setText("Again!");
		bottomButtons[5].setText("Keep fighting dummy minions!");
		bottomButtons[5].setEnabled(true);
		if (difficultyLevel == EXPERT){
			bottomText.setText("Wonderous job, "+playerName+". You saved the galaxy from Ivan! You are an expert commander!");
		}
		else{
			bottomText.setText("Fantastic job, "+playerName+". You saved the galaxy from Ivan!");
		}
		gameActive = false;
		victoryAchieved = true;
	}
	
	//Displays the game over screen if all lives are lost
	public void gameOver(){
		if (victoryAchieved){
			victoryGameOver();
		}
		else{
			bottomButtons[4].setText("Unless");
			bottomButtons[4].setEnabled(true);
			bottomButtons[5].setVisible(false);
			ImageIcon bg = new ImageIcon("GameOver_BG.png");
			background = bg.getImage();
			bottomText.setText("The Evil Overlord has conquered everything. All is lost...");
			gameActive = false;
			repaint();
		}
	}
	
	public void victoryGameOver(){
		gameActive = false;
		bottomButtons[4].setText("Again!");
		bottomButtons[5].setVisible(false);
		repaint();
	}
	
	public void mouseDragged(MouseEvent e){
		mX = e.getX();
		mY = e.getY();
		
		int loop;
		if (!holdingTurret){
			//Buys new turret
			for(loop = 0; loop < 5; loop++){
				if (glassDisplay[loop].overTurret(mX, mY)){
					if (glassDisplay[loop].turretType() == "Gun"){
						turretBuilder.add(new GunTurret(mX-13, mY-13));
						financeManager();
					}
					else if (glassDisplay[loop].turretType() == "Bomb"){
						turretBuilder.add(new BombTurret(mX-13, mY-13));
						financeManager();
					}
					else if (glassDisplay[loop].turretType() == "Ray"){
						turretBuilder.add(new RayTurret(mX-13, mY-13));
						financeManager();
					}
					else if (glassDisplay[loop].turretType() == "Com" && turretsPlaced != 0){
						turretBuilder.add(new ComTurret(mX-13, mY-13));
						financeManager();
					}
					else if (glassDisplay[loop].turretType() == "Racial" && racialTurretsPlaced < 10){
						turretBuilder.add(new RacialTurret(mX-13, mY-13, playerRace));
						financeManager();
					}
					
				}
			} 
		}
		repaint();

	}
	
	public void mouseMoved(MouseEvent e){
		mX = e.getX();
		mY = e.getY();	
		
		repaint();
	}
	
	public void mouseClicked(MouseEvent e){
		int loop;	
		if (!holdingTurret){
			//Sells the focused turret
			if (sellOrUndo.contains(mX, mY) && turretTracker() != -1){
				player.moneyGain(turretBuilder.get(turretTracker()).sell(turretsPlaced-1));
				for(loop = 0; loop < 5; loop++){
					glassDisplay[loop].adjustCost(false);
				}
				environment.removeTurret(turretTracker());
				if (turretBuilder.get(turretTracker()).turretType().equals("Racial")) {
					racialTurretsPlaced--;
				}
				turretBuilder.remove(turretTracker());
				turretsPlaced--;
			}
			//Adjusts Focus
			for (loop = 0; loop < turretBuilder.size(); loop++){
				if (turretBuilder.get(loop).turretArea().contains(mX, mY)){
					turretBuilder.get(loop).changeFocus(true);
				}
				else{
					turretBuilder.get(loop).changeFocus(false);
				}
			}
			//Buys new turret
			for(loop = 0; loop < 5; loop++){
				if (glassDisplay[loop].overTurret(mX, mY)){
					if (glassDisplay[loop].turretType() == "Gun"){
						turretBuilder.add(new GunTurret(mX-13, mY-13));
						financeManager();
					}
					else if (glassDisplay[loop].turretType() == "Bomb"){
						turretBuilder.add(new BombTurret(mX-13, mY-13));
						financeManager();
					}
					else if (glassDisplay[loop].turretType() == "Ray"){
						turretBuilder.add(new RayTurret(mX-13, mY-13));
						financeManager();
					}
					else if (glassDisplay[loop].turretType() == "Com" && turretsPlaced != 0){
						turretBuilder.add(new ComTurret(mX-13, mY-13));
						financeManager();
					}
					else if(glassDisplay[loop].turretType() == "Racial" && racialTurretsPlaced < 10){
						turretBuilder.add(new RacialTurret(mX-13, mY-13, playerRace));
						financeManager();
					}
					
				}
			}
		}
		//Places turret
		else if (holdingTurret && turretBuilder.get(turretsPlaced).nearTrack()){
			turretHolder.stop();
			holdingTurret = false;
			environment.addTurret(mX-13, mY-13);
			turretBuilder.get(turretsPlaced).powered();
			if (turretBuilder.get(turretsPlaced).turretType().equals("Racial")) {
				racialTurretsPlaced++;
			}
			turretsPlaced++;
		}
		
		//Undos purhcase
		else if (holdingTurret && sellOrUndo.contains(mX, mY)){
			player.moneyGain(turretBuilder.get(turretTracker()).undo(turretsPlaced));
			for(loop = 0; loop < 5; loop++){
				glassDisplay[loop].adjustCost(false);
			}
			turretBuilder.remove(turretTracker());
			holdingTurret = false;
			turretHolder.stop();
		}
		repaint();
	}
	
	//Adjusts Focus over Sell/Undo button
	public void mousePressed(MouseEvent e){
		int loop;
		if (!holdingTurret && !sellOrUndo.contains(mX, mY)){
			for (loop = 0; loop < turretBuilder.size(); loop++){
				if (turretBuilder.get(loop).turretArea().contains(mX, mY)){
					turretBuilder.get(loop).changeFocus(true);
				}
				else{
					turretBuilder.get(loop).changeFocus(false);
				}
			}
		}
		repaint();
	}
	
	//Finds the turret based on focus
	public int turretTracker(){
		int loop;
		for (loop = 0; loop < turretBuilder.size(); loop++){
				if (turretBuilder.get(loop).focused){
					return loop;
				}
		}
		return -1;
	}
	
	//Checks if the player cannot afford the tower
	public void financeManager(){
		if (turretBuilder.size() != 0){
			if (turretBuilder.get(turretsPlaced).buy()+turretsPlaced*5 > player.getGold()){
				turretBuilder.remove(turretsPlaced);
			}
			else{
				player.moneyLoss(turretBuilder.get(turretsPlaced).buy()+turretsPlaced*5);
				int loop;
				for(loop = 0; loop < 5; loop++){
					glassDisplay[loop].adjustCost(true);
				}
				holdingTurret = true;
				playSound("TurretPurchase.wav");
				turretHolder.start();
			}
			repaint();
		}
	}
	
	//Hotkeys!
	public void keyPressed(KeyEvent e){
		int keyID = e.getKeyCode();
		
		if (keyID == KeyEvent.VK_SPACE && difficultySelected) { 
			if (waveActive){
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
			else{
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
		if (!holdingTurret){
			//Sells the focused turret
			if (keyID == KeyEvent.VK_SHIFT && turretTracker() != -1){
				player.moneyGain(turretBuilder.get(turretTracker()).sell(turretsPlaced-1));
				int loop;
				for(loop = 0; loop < 5; loop++){
					glassDisplay[loop].adjustCost(false);
				}
				environment.removeTurret(turretTracker());
				if (turretBuilder.get(turretTracker()).turretType().equals("Racial")) {
					racialTurretsPlaced--;
				}
				turretBuilder.remove(turretTracker());
				turretsPlaced--;
			}
		
			else if (keyID == KeyEvent.VK_Q){
				if (turretTracker() != -1){
					turretBuilder.get(turretTracker()).changeFocus(false);
				}
				turretBuilder.add(new GunTurret(mX-13, mY-13));
				financeManager();
			}
			else if (keyID == KeyEvent.VK_W){
				if (turretTracker() != -1){
					turretBuilder.get(turretTracker()).changeFocus(false);
				}
				turretBuilder.add(new BombTurret(mX-13, mY-13));
				financeManager();
			}
			else if (keyID == KeyEvent.VK_E){
				if (turretTracker() != -1){
					turretBuilder.get(turretTracker()).changeFocus(false);
				}
				turretBuilder.add(new RayTurret(mX-13, mY-13));
				financeManager();
			}
			else if (keyID == KeyEvent.VK_R && turretsPlaced != 0){
				if (turretTracker() != -1){
					turretBuilder.get(turretTracker()).changeFocus(false);
				}
				turretBuilder.add(new ComTurret(mX-13, mY-13));
				financeManager();
			}
			else if (keyID == KeyEvent.VK_T && racialTurretsPlaced < 10){
				if (turretTracker() != -1){
					turretBuilder.get(turretTracker()).changeFocus(false);
				}
				turretBuilder.add(new RacialTurret(mX-13, mY-13, playerRace));
				financeManager();
			}
			else if (keyID == KeyEvent.VK_ESCAPE && turretTracker() != -1){
				turretBuilder.get(turretTracker()).changeFocus(false);
			}
		}
		
		//Undos purhcase
		if (keyID == KeyEvent.VK_SHIFT && holdingTurret){
			player.moneyGain(turretBuilder.get(turretTracker()).undo(turretsPlaced));
			int loop;
			for(loop = 0; loop < 5; loop++){
				glassDisplay[loop].adjustCost(false);
			}
			turretBuilder.remove(turretTracker());
			holdingTurret = false;
			turretHolder.stop();
		}
		repaint();
	}

	public void keyReleased(KeyEvent e){
	}
	
	public void keyTyped(KeyEvent e){
	}
	 
	public void mouseEntered(MouseEvent e){
	}
	 
	public void mouseExited(MouseEvent e){
		 
	}
	
	public void mouseReleased(MouseEvent e){
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		if (phase == TITLE){
			//Title
			g.drawImage(background, 0, -10, this);
			g.setFont(new Font("Arial Black", Font.ITALIC, 100));
			g.setColor(Color.WHITE);
			FontMetrics metrics = g.getFontMetrics(new Font("Arial Black", Font.ITALIC, 100));
			int hgt = metrics.getHeight();
			int adv = metrics.stringWidth("Defend the");
			g.drawString("Defend the", getWidth()/2 - adv/2, getHeight()/2-hgt/2);
			adv = metrics.stringWidth("Xzoltinium");
			g.drawString("Xzoltinium", getWidth()/2 - adv/2, getHeight()/2+hgt/2);
			g.setFont(new Font("Arial Black", Font.ITALIC, 50));
			g.drawString("by Leo London", getWidth()/2 - 50, getHeight()/2+hgt);
		}
		
		else if (phase == PROLOGUE){
			g.setColor(new Color (0, 0, 0));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.WHITE);
			g.setFont(new Font("Bell MT", Font.BOLD, 50));
			FontMetrics metrics = g.getFontMetrics(new Font("Bell MT", Font.BOLD, 50));
			int hgt = metrics.getHeight();
			int adv = metrics.stringWidth("Prologue");	
			g.drawString("Prologue", getWidth()/2-adv/2, hgt);
			
			g.setFont(new Font("Times New Roman", Font.PLAIN, 24));
			g.drawString("The stardate is 2505. General Ivan Nomundo, who goes by the name " , 90, 130);
			g.drawString("Evil Overlord, is currently massing a force to attack the Yvinian Galaxy. He is", 30, 170);
			g.drawString("targeting this specific galaxy because of the rare earth found in the middle of it.", 30, 210);
			g.drawString("With this resource, Xzoltinium, mined, the Evil Overlord plans to use it to ", 30, 250);
			g.drawString("conquer everything, everywhere, and everyone...", 30, 290);
			g.drawString("You, "+playerName+", have recently been recruited by G.O.D.,", 90, 340);
			g.drawString("also known as the Galaxy's Operational Defense, to take care of Ivan's forces", 30, 380);
			g.drawString("before they can retrieve the rare material. The Yvinians have given you their", 30, 420);
			g.drawString("galaxy and their entire arsenal of prototype rift weapons, able to shoot in space,", 30, 460);
			g.drawString("lucky that, huh? It will be up to you to save life as we know it.", 30, 500);
			g.drawString("So... are you ready?", 90, 550);
		}
		else if (phase == INVASION){
			if (gameActive || victoryAchieved){
				//Draws the background and track
				g.drawImage(background, 0, 0, this);
				environment.drawShop(g);
				environment.drawTrack(g);
				int loop;
				for(loop = 0; loop < 5; loop++){
					glassDisplay[loop].drawTurret(g, false);
						if (glassDisplay[loop].overTurret(mX, mY) && !holdingTurret && turretTracker() == -1){
							if (glassDisplay[loop].turretType() == "Racial") {
								glassDisplay[loop].drawRolloverTooltip(g, racialTurretsPlaced);
							}
							else {
								glassDisplay[loop].drawRolloverTooltip(g);
							}
						}
				}
				//Draws Turret and the Sell/Undo button and its tooltip
				for (loop = 0; loop < turretBuilder.size(); loop++){
					turretBuilder.get(loop).drawFocus(g, holdingTurret);
					turretBuilder.get(loop).drawTurret(g, communicatorBuffDetector(loop));
				}
				if (waveActive){
					spawnedWave.drawWave(g);
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
			else{
				g.drawImage(background, 0, 0, this);
			}
		}
	}

	public static void main (String[] args){
		new TowerDefense();
	}
	
}


