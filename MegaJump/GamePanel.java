//Kevin Shi,Vasav Shah
//This is where the all the game logic is done.
//We used KeyListener, MouseListener, and MouseMotion Listener for variuos thing
//KeyListener is used to move the player, and the mosuseListenr is used for Pause button and diffrent buttons
import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.sound.sampled.AudioSystem;
import java.applet.*;

public class GamePanel extends JPanel implements MouseListener,MouseMotionListener,KeyListener{
	private boolean [] keys;					//keeps track of the keys pressed
	private boolean click;						//checks if mouse is clicked

	private Image gameBckgrnd;					//The backGround Image is huge
	private Image decoImage=null;				// keeps track of the current Decoration Image
	private Image coinPic;						// The picture of coin that keeps track of the coins collected

	AudioClip coinSound;						// keeps track of the coinSound which is played when the coin is hit
	AudioClip clicked;							// when a button clicked
	AudioClip starSound;						// sound which is played when you hit a star
	AudioClip bounce;							// bounce sound
	AudioClip bckGrndMusic;						// background music
	private Player player1;

	private boolean newImage=false;
	private boolean ground;	//keeps track of if the guy is on the ground
	
	boolean pause,lvlClear;					
	
	private boolean die;						// if the guy died
	private boolean on=false;					// if the music is played in this class or MainMenu class

	private boolean musicOn;					// This is keeps track of the mute button

	private int mx,my;							//mouse x and mouse y postions
	private int backx,backy,midx,midy,frontx,fronty;			//position for the 3 layers
	private int distance,score,coins,height,dieHeight,dieMenuHeight;	//diffrent variables that display the scores and everything else
	
	private int maxCoins;
	private int maxScore;
	private int maxLevel;
	private int maxDist;
	private int totalCoins;
	private int totalDist;
	
	private double count=0;
	//This is all just laoding images
	// These images are used to show the power up the user has at the moment.
	private Image ballPower = new ImageIcon("gamelayerstuff/powerups/Ball1.png").getImage();
	private Image sheildPower = new ImageIcon("gamelayerstuff/powerups/Sheild1.png").getImage();
	private Image umbrellaPower = new ImageIcon("gamelayerstuff/powerups/Umbrella1.png").getImage();

	private Font scoreFont;		//Just the font that display the score
	
	private String screen="game";			//keeps track of the screen th user is currently on
	private Image scoreImage= new ImageIcon("gamelayerstuff/display/score.png").getImage();
	private Image coinImage= new ImageIcon("gamelayerstuff/display/coin.png").getImage();
	private Image heightImage= new ImageIcon("gamelayerstuff/display/height.png").getImage();

	private ArrayList<String> chars = new ArrayList<String>();		//used for reading the text files and storing the chars
	private ArrayList<String> powerUps = new ArrayList<String>();	//used for reading textfiles, stores all the powerups
	private ArrayList <String> stats = new ArrayList<String>();		//stores everything from a textfile
	private int money=0;											//keeps track of the money	
	PrintWriter outFile;

	private ArrayList<Rectangle>rectList = new ArrayList<Rectangle>();			//All of these are used for storing the rectangles of all the objects.. Making sure they don't overlap
	private ArrayList <Image> magnetList = new ArrayList<Image>();				//Rest of these just store object that are used in the game
	//private ArrayList <Image> abootList = new ArrayList <Image>();			
	private ArrayList<Decorations>decoList = new ArrayList<Decorations>();
	private ArrayList<Enemy>enemyList = new ArrayList<Enemy>();
	private ArrayList<Coin>coinList = new ArrayList<Coin>();
	private ArrayList<Box> boxList = new ArrayList<Box>();
	private ArrayList<Star> starList = new ArrayList<Star>();
	private ArrayList <Spikes> spikeList = new ArrayList<Spikes>();
	private ArrayList <Jumper> jumperList = new ArrayList<Jumper>();
	private ArrayList <Powerup> pupList = new ArrayList<Powerup>();
	private ArrayList <Poof> poofList= new ArrayList<Poof> ();
	
	
	private ArrayList<Enemy>eRemove = new ArrayList<Enemy>();				//These keeps track of all the objects to delete
	private ArrayList<Coin>cRemove = new ArrayList<Coin>();
	private ArrayList<Box> bRemove = new ArrayList<Box>();
	private ArrayList<Star> sRemove = new ArrayList<Star>();
	private ArrayList <Spikes> spRemove = new ArrayList <Spikes> ();
	private ArrayList <Jumper> jRemove = new ArrayList <Jumper>();
	private ArrayList <Powerup> pupRemove = new ArrayList<Powerup>();
	private ArrayList <Poof> pRemove = new ArrayList<Poof>();
	
	private ArrayList <SButton> pauseBtns= new ArrayList<SButton>();			//Holds all the Buttons for the pause screen
	
	private ArrayList <Integer> probs = new ArrayList<Integer>(Arrays.asList(26,32,37,45,48,55,56,77,82,83));		//number of texfilse per level
	
	private SButton playB,pauseB,muteB,unmuteB,resumeB,menuB;				//KJust hodls all the buttons to be displayed
	
	int level;
	int prevLvl;
	
    public GamePanel(String name,int l){
		setFocusable(true);
    	grabFocus();
    	addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		keys= new boolean [10000];
		
		try{scoreFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("BRLNSB.ttf"))).deriveFont(0,32);}
		catch(IOException ioe){System.out.println("error loading BRLNSB.tff");}
		catch(FontFormatException ffe){System.out.println("Something went wrong with the font.");}
		
		gameBckgrnd = new ImageIcon ("Backgroundimage.png").getImage();
		for(int i=1;i<7;i++){magnetList.add(new ImageIcon("gamelayerstuff/powerups/magnet"+i+".png").getImage());}
		
		coinPic = new ImageIcon("gamelayerstuff/coins/byellowcoin1.png").getImage();
		player1= new Player(200,300,100,"sheldon");
		
		click=false;
		ground = true;
		pause = false;
		lvlClear = false;
		die = false;
		musicOn = true;
		
		player1.setVelo(150);
		player1.setInvi(true);
//------------------------------------------------------------------------------------------------------------------------------------
//Sound
		coinSound= Applet.newAudioClip(getClass().getResource("coin_pickup_2.wav"));
		clicked= Applet.newAudioClip(getClass().getResource("menu_deselect.wav"));
		starSound= Applet.newAudioClip(getClass().getResource("star.wav"));
		bounce= Applet.newAudioClip(getClass().getResource("grav_step_4.wav"));
		bckGrndMusic = Applet.newAudioClip(getClass().getResource("bgmusic00.wav"));
		bckGrndMusic.loop();
//------------------------------------------------------------------------------------------------------------------------------------

		//distance=0;
		score=0;
		coins=0;
		level = 1;
		prevLvl=level;
		backy=0;
		height=backy;
		dieHeight=0;
		dieMenuHeight=0;
		
		pauseB = new SButton(400,670,"pause","");
		resumeB= new SButton(100,500,"resume","");
		menuB = new SButton(100,620,"back","");
		muteB = new SButton(400,600,"mute","");
		unmuteB = new SButton(400,600,"unmute","");
		System.out.println("characters/"+name+"/"+name+"35.png");
    }
//------------------------------------------------------------------------------------------------------------------------------------
//Mouse Listener Methods
	public void mouseEntered(MouseEvent e){}
    public void mouseReleased(MouseEvent e){click = false;}
    public void mousePressed(MouseEvent e){click = true;}
    public void mouseExited(MouseEvent e){}
    public void mouseClicked(MouseEvent e){
    	//Buttons
   		pauseClicked();
   		resumeClicked();
   		musicClicked();
   		menuClicked();
    }
    public void mouseDragged(MouseEvent e){
    	mx = e.getX();my = e.getY();
    	//Buttons
    	pauseClicked();
   		resumeClicked();
   		musicClicked();
   		menuClicked();
    }
    public void mouseMoved(MouseEvent e){mx = e.getX();my = e.getY();}
//------------------------------------------------------------------------------------------------------------------------------------
//Buttons clicked
	public void pauseClicked(){
		//Pauses game
		//Checks if pause button is clicked
		if (pauseB.clicked(mx,my,true) && pause == false){
			clickedSound();
    		pause=true;
   		}
	}
	public void resumeClicked(){
		//Resumes game
		//Checks if resume button is clicked
		if(resumeB.clicked(mx,my,true) && pause == true && die == false){
			clickedSound();
   			pause=false;
   			lvlClear = false;
   		}
	}
	public void musicClicked(){
		//Mutes/unmutes music
		//checks to see if mute/unmute button is clicked
		if(muteB.clicked(mx,my,true) && pause == true && musicOn == true){
			clickedSound();
   			musicOn = false;
   			clicked.stop();
   			bckGrndMusic.stop();
   			coinSound.stop();
			starSound.stop();
			bounce.stop();
   			
   		}
   		else if(unmuteB.clicked(mx,my,true) && pause == true && musicOn == false){
   			clickedSound();
   			musicOn = true;
   			bckGrndMusic.loop();
   		}
	}
	public void menuClicked(){
		//Returns to back menu
		//Checks to see if back button has been clicked
		if(menuB.clicked(mx,my,true) && pause == true){
			clickedSound();
			screen="menu";
			pause = false;
			die = false;
		}
	}
	public void clickedSound(){
		//plays button sound when button is clicked
		if(musicOn){
			clicked.play();
		}
	}
//------------------------------------------------------------------------------------------------------------------------------------
//Keyboard ListenerMethods
   	public void keyPressed(KeyEvent evt){keys[evt.getKeyCode()]=true;}
	public void keyReleased(KeyEvent evt){
		keys[evt.getKeyCode()]=false;
		//reset the counters for for the sprite s
		player1.resetCounter();
		if (!player1.getPower().equals("Balloon")){
			player1.resetPicCounter();			
		}
	}
	public void keyTyped(KeyEvent evt){}
//------------------------------------------------------------------------------------------------------------------------------------
//In game menus
	public void pauseMenu(Graphics g){
		//Pause menu
		if(pause == true && lvlClear == false){
			g.setFont(scoreFont);
			g.setColor(Color.BLACK);
			g.drawImage(new ImageIcon("InGameMenu/pauseBckgrnd.png").getImage(),0,0,this);
			g.drawString("P A U S E D",155,200);
			displayScore2(g,115,350,185,380,110,440,110,490); //Displays stats
			g.drawImage(resumeB.getPic(mx,my),resumeB.getX(),resumeB.getY(),this);//Draws the resume button
			g.drawImage(menuB.getPic(mx,my),menuB.getX(),menuB.getY(),this);		//Draws the back to menu button
			
			if(musicOn == true){													//Draws the mute or unmute button (based on if music is on or not)
				g.drawImage(muteB.getPic(mx,my),muteB.getX(),muteB.getY(),this);
			}
			if(musicOn == false){
				g.drawImage(unmuteB.getPic(mx,my),unmuteB.getX(),unmuteB.getY(),this);
			}
		}
	}
	public void lvlClearMenu(Graphics g){
	//Menu that shows up after a level has been cleared
		if(pause == true && lvlClear == true){	
			g.drawImage(new ImageIcon("InGameMenu/pauseBckgrnd.png").getImage(),0,0,this);	//<Graphics stuff
			g.drawImage(new ImageIcon("InGameMenu/stageClearPic.png").getImage(),100,0,this);
			displayScore2(g,115,350,185,380,110,440,110,490);			//Displays stats
			g.drawImage(resumeB.getPic(mx,my),resumeB.getX(),resumeB.getY(),this);	//Draws resume button
			g.drawImage(menuB.getPic(mx,my),menuB.getX(),menuB.getY(),this);		//Draws the back to menu button
		}
	}
	public void gameOverMenu(Graphics g){	//CHANGE THIS TO INCLUDE HIGHSCORES AND STUFF AFTER TEXTFILES ARE MADE
	//Menu that shows up when user gets Game Over
		if(pause == true && die == true){
			g.drawImage(new ImageIcon("InGameMenu/pauseBckgrnd.png").getImage(),0,0,this);	//Graphics stuff
			g.setFont(scoreFont);
			g.setColor(Color.BLACK);
			g.drawImage(new ImageIcon("InGameMenu/GameOver.png").getImage(),10,200,this);
			displayScore2(g,115,350,185,385,110,440,110,490);	//Shows stats
			g.drawImage(menuB.getPic(mx,my),menuB.getX(),menuB.getY(),this);	//Draws menu button
		}
	}
//------------------------------------------------------------------------------------------------------------------------------------
//Keep track of Levels and Leveling up
	public void atLevel(){
		//Changes the level so new text patterns can be called. The level is changed by seeing if the backy value is within a range
		if(0/0.1<=backy && backy<1210/0.1){level=1;}
	   	else if	(1210/0.1<=backy && backy<3640/0.1){level=2;}
	   	else if	(3640/0.1<=backy && backy<5540/0.1){level=3;}
	   	else if	(5540/0.1<=backy && backy<7890/0.1){level=4;}
	   	else if	(7890/0.1<=backy && backy<11340/0.1){level=5;}
	   	else if	(11340/0.1<=backy && backy<13420/0.1){level=6;}
	   	else if	(13420/0.1<=backy && backy<17140/0.1){level=7;}
	   	else if	(17140/0.1<=backy && backy<18390/0.1){level=8;}
	   	else if	(18390/0.1<=backy && backy<20840/0.1){level=9;}
	   	else if	(20840/0.1<=backy && backy<23040/0.1){level=10;}
	   	else{level=1;}
	}
	public void levelUp(){
		//checks to see if the user is on a new lvl or not. This is done by comparing the previous level witht he current level.
		//If the previous level is less than the current level, then a new level is reached and sets lvlclear=ture and pause=true
		if(level == prevLvl+1){
			pause = true;lvlClear = true;
			prevLvl = level;
		}
	}
	public void changeHeight(){
		//Changes the highest height
		if(backy > height){
			int h = backy-height;
			height += h;
			score +=h;		//<-score = highest height+coins
			dieHeight = height-250;	//<-height reached to start death animations
			dieMenuHeight = height-350;//<-heaight reached to display death menu
		}
	}
	public void showDieMenu(){
		//When death line is reached, draw death menu and updates files
		if(backy <=dieMenuHeight){
			pause = true;die = true;
			readFile("stats");
			manageStuff();
			updateFile();
		}
	}
//------------------------------------------------------------------------------------------------------------------------------------------------------
//All collision stuff
	public void checkAllCollisions(){
		checkEnemyCollision();			//checks all the collison between all the objects
		checkCoinCollision();
		checkBoxCollision();
		checkSpikeCollision();
		checkStarCollision();
		checkJumperCollision();
		checkPupCollision();
	}
	public void checkEnemyCollision(){
		for (Enemy e: enemyList){
			if (e.getOnScreen()){	//can be removed later on
			//goes through all the enemies and checks if they collide
				if (e.checkCollision(e.getPics().get(e.getCounter()),player1)){
					if (player1.getInvi()==false){
						//If the player is not invisble then you get spiked.
						if (player1.getVelocity()>0){		//if the player hits it from the bottom
							player1.setSpikeVelo();	
							loseCoins();
						}
						else{
							player1.setVelo(50); //if the player is on top instead the player bounces off the enemy 
							if(musicOn){
								bounce.play();
							}
							
						}						
					}
					eRemove.add(e);		//once we hit, we remove the enemy
				}
			}
			else{
				eRemove.add(e);		// if the enemy goes of the screen, we remove
			}
		}
		for (Enemy e: eRemove){
			poofList.add(new Poof(e.getX(),e.getY(),1));		//removes all the enemies
			enemyList.remove(e);
		}
		eRemove = new ArrayList<Enemy>();
	}
	public void checkStarCollision(){
		for (Star s: starList){
			if (s.getOnScreen()){	//if the star is on the screen we need to check if player collides
				if (s.checkCollision(s.getPics().get(s.getCounter()),player1)){		//if the player collides with the star we remove it then change the velosity to the distance that the star provides
					sRemove.add(s);								//remove star once you collide with it
					player1.setVelo(s.getDist());				//changes the velocity
					player1.setInvi(true);						//sets the player invisble for a few seconds
					score+=s.getPoints();						//points increase by the star type
					if(musicOn){
						starSound.play();						//playthe star sound
					}
					
				}
			}
			else{	
				sRemove.add(s);									// remove the star if its not on the screen
			}
		}
		for (Star s: sRemove){
			poofList.add(new Poof (s.getX(),s.getY(),s.getNum()+3));		//make the poof effect
			starList.remove(s);
		}
		sRemove = new ArrayList<Star>();
	}
	public void checkCoinCollision(){
		for (Coin c:coinList){
			if (c.getOnScreen()){// check if the coin is on the screen
				if (c.checkCollision(c.getPics().get(c.getCounter()),player1)){		//if the player collides with the coin
					cRemove.add(c);											//remove the coin
					player1.setVelo(50);									//set the velocity so the player moves up
					player1.setDown(false);									//set the down false (players moving up)
					coins+=c.getValue();									//check the coins collected
					score+=c.getPoints();									//get the score
					if(musicOn){
						coinSound.play();									//play the sound
					}
					
				}
			}
			else{
				cRemove.add(c);												//remove the coin
			}
		}
		for (Coin c:cRemove){
			poofList.add(new Poof(c.getX(),c.getY(),0));
			coinList.remove(c);
		}
		cRemove = new ArrayList<Coin>();
	}
	public void checkSpikeCollision(){									// checks the spike collision
		for (Spikes s:spikeList){
			if (s.getOnScreen()){										//if the spike is on the screen
				if (s.checkCollision(s.getImage(),player1)){			//if the user collides with the spike
					if(player1.getInvi()==false){						//if player is not invisiblity
						player1.setSpikeVelo();						//set the spike velocity
						loseCoins();
						player1.setDown(true);
					}
					spRemove.add(s);
				}
			}
			else{
				spRemove.add(s);			//remove the spike
			}
		}
		for (Spikes s:spRemove){
			spikeList.remove(s);
		}
		spRemove = new ArrayList<Spikes>();
	}
	public void checkPupCollision(){
	//pretty much the same thing as before, however it alsoe does the powerup effects
		for (Powerup p:pupList){
				if (p.getOnScreen()){//can be removed later on
					if (p.checkCollision(player1)){
						pupRemove.add(p);
						player1.setPower(p.getType());
						player1.setVelo(50);
					}
				}
				else{
					pupRemove.add(p);
				}
			}
			if (player1.getPower().equals("Lucky")){			//changes everything to stars
				for (Coin c: coinList){
					starList.add(new Star(c.getX(),c.getY(),2));
					cRemove.add(c);
				}
				for (Box b: boxList){
					starList.add(new Star(b.getX(),b.getY(),2));
					bRemove.add(b);
				}
				for (Enemy e: enemyList){
					starList.add(new Star(e.getX(),e.getY(),2));
					eRemove.add(e);
				}
			}
			else if (player1.getPower().equals("Magnet")){		//moves the coins towards the player
				for (Coin c: coinList){
					c.moveTowards(player1);
				}
			}
			else{	//else do nothing 

			}
			for (Powerup p:pupRemove){
				poofList.add(new Poof(p.getX(),p.getY(),2));
				pupList.remove(p);
			}
			pupRemove = new ArrayList<Powerup>();
	}
	public void checkBoxCollision(){		//pretty much the same thing as all the other collisions
		for (Box b:boxList){
			if (b.getOnScreen()){// checks if its on the screen
				if (b.checkCollision(b.getImage(),player1)){
					player1.setVelo(50);
					player1.setDown(false);
					b.setPcount(b.getPcount()-1);			//sets the type to one less so then you can  bounce on it twice
					if (b.getPcount()==0){			
						bRemove.add(b);						//removes the box
					}
					if(musicOn){
						bounce.play();
					}
					
				}
			}
			else{
				bRemove.add(b);
			}
		}
		for (Box b:bRemove){
			boxList.remove(b);
		}
		bRemove = new ArrayList<Box>();
	}
	public void checkJumperCollision(){
		//same as all the other collsions
		for (Jumper j:jumperList){
			if (j.getOnScreen()){//
				if (j.checkCollision(j.getImage(),player1)){
					player1.setVelo(50);
					player1.setDown(false);
					if(musicOn){
						bounce.play();
					}
				
				}
			}
			else{
				jRemove.add(j);
			}
		}
		for (Jumper j:jRemove){
			jumperList.remove(j);
		}
		jRemove = new ArrayList<Jumper>();
	}
	public boolean checkSpawn(Rectangle object){
		for(Rectangle i : rectList){
			if(object.intersects(i)){
				return false;
			}
		}
		return true;
	}

//-------------------------------------------------------------------------------------------------------------------------------------------------------
//Drawing
	public void drawCoin(Graphics g){
		//draws the coins
		for(Coin i : coinList){
			g.drawImage(i.getPics().get(i.getCounter()),i.getX(),i.getY(),i.getWidth(),i.getHeight(),null);
			i.count();
			i.setDirection();
		}
	}
	public void drawStar(Graphics g){
		//draws the stars
		for(Star i : starList){
			g.drawImage(i.getPics().get(i.getCounter()),i.getX(),i.getY(),i.getWidth(),i.getHeight(),null);
			i.count();
		}
	}
	public void drawEnemy(Graphics g){
		Graphics2D g2d = ( Graphics2D ) g;
		for(Enemy i : enemyList){
			if (i.getFlip()){
				g2d.drawImage(i.getPics().get(i.getCounter()),i.getX()+i.getWidth(),i.getY(),-i.getWidth(),i.getHeight(),null); //flips the image if the flip is true
			}
			else{
				g.drawImage(i.getPics().get(i.getCounter()),i.getX(),i.getY(),i.getWidth(),i.getHeight(),null);
			}
			i.count();
			i.setDirection();
			i.move();
		}
	}
	// draws all the stuff , its pretty basic
	public void drawPup(Graphics g){
		for(Powerup i : pupList){
			g.drawImage(i.getImage(),i.getX(),i.getY(),i.getWidth(),i.getHeight(),null);
		}
	}
	public void drawBox(Graphics g){
		for(Box i : boxList){
			g.drawImage(i.getImage(),i.getX(),i.getY(),i.getWidth(),i.getHeight(),null);
		}
	}
	public void drawSpike(Graphics g){
		for(Spikes i : spikeList){
			g.drawImage(i.getImage(),i.getX(),i.getY(),i.getWidth(),i.getHeight(),null);
		}
	}
	public void drawJumper(Graphics g){
		for(Jumper i : jumperList){
			g.drawImage(i.getImage(),i.getX(),i.getY(),i.getWidth(),i.getHeight(),null);
		}
	}
	public void drawPoof(Graphics g){
		for(Poof i : poofList){
			g.drawImage(i.getImage(),i.getX(),i.getY(),i.getWidth(),i.getHeight(),null);
			i.count();
		}
	}
//-------------------------------------------------------------------------------------------------------------------------------------------------------
//Decorations
	public void newDecos(){
		int prob = (int)(Math.random()*100);		//randomly spawnst he decoration
		if(prob == 1){
			if(decoList.size() < 1){			
				int prob2 = (int)(Math.random()*2);
				if(prob2 == 1){							//right side or left side
					decoList.add(new Decorations(-700,(int) (backy*0.1)%23080,false));
				}
				else{
					decoList.add(new Decorations(-700,(int)(backy*0.1)%23080,true));
				}
			}
		}
		Boolean check = true;
		for(Decorations i : decoList){
			if(i.getYTop()>=2000){
				check = false;
				break;
			}
		}
		if(check ==false && decoList.size()>0){		//theres only on in the lsit but for consitency we kept it as a list
			decoList.remove(0);
		}
	}
//-------------------------------------------------------------------------------------------------------------------------------------------------------
//Poofs	
	public void removePoof(){		// once the poof animation is the poof is removed from the list
		for (Poof p: poofList){
			if (p.isDone()){
				pRemove.add(p);
			}
		}
		for (Poof p:pRemove){
			poofList.remove(p);
		}
		pRemove= new ArrayList<Poof>();
	}
//-------------------------------------------------------------------------------------------------------------------------------------------------------
//Patterns
	public void newPattern(){
		int prob = (int)(Math.random()*probs.get(level-1)+1);
		Pattern p = new Pattern(0, -300, prob, (int)(backy*0.1));
		if(checkSpawn(new Rectangle(p.getX(),p.getY(),p.getWidth(),p.getHeight())) == true){
			for(Coin i : p.getCoins()){coinList.add(i);}
			for(Star i : p.getStars()){starList.add(i);}
			for(Box i : p.getBoxes()){boxList.add(i);}
			for(Jumper i : p.getJumpers()){jumperList.add(i);}
			for(Enemy i : p.getEnemies()){enemyList.add(i);}
			for(Spikes i : p.getSpikes()){spikeList.add(i);}
			for(Powerup i : p.getPowerUps()){pupList.add(i);}
			for(Rectangle i : p.getRects()){rectList.add(i);}
		}
	}
	
//-------------------------------------------------------------------------------------------------------------------------------------------------------
//Graphics
	public void paintComponent(Graphics g){
		if (pause==false){	//if the pause screen is paused then don't move anything and go to the pause screen
			DrawEveryThing(g);
			displayScore(g,10,10,80,42,300,60,300,32);					//shows the score
			drawPowerUpIcon(g);											//draws the powerup icon
			g.drawImage(pauseB.getPic(mx,my),pauseB.getX(),pauseB.getY(),this);	//Pause button in bottom right-head corner
			changeHeight();												//changes the height
			if(die){
				showDieMenu();	
			}
		}
		else{
			pauseMenu(g);							//draws the pause menu , lvlClearmenu or gameOverMenu  depending what screen the user wants to go to
			lvlClearMenu(g);
			gameOverMenu(g);
		}
	}
	public void DrawEveryThing(Graphics g){
		moveLayerOne(g);		//moves the background makes it parallax scrolling
		moveLayerTwo(g);		//the decorations
		drawPowerUpEffect(g);	//draws the powerup effect
		moveLayerThree(g);		//moves all the other stuff
		newPattern();			//makes a new PAttern
		newDecos();				//makes new decorations
		atLevel();			
		levelUp();				//changes the level
		checkAllCollisions();
		removePoof();				//remove the poofs
		moveEverything();			//moves everything
		player1.changeVelocity();
	}
	public void moveLayerOne(Graphics g){//drawing the background, we have to make this into percentage afterwards
		g.drawImage(gameBckgrnd,0,(((int) (backy*0.1))%23080)-23080,this);	//makes it continues and i have an extra image on top just to be safe
	}
	public void moveLayerTwo(Graphics g){//making it look fancy and shit
		Graphics2D g2d = ( Graphics2D ) g;
		for(Decorations i : decoList){
			if(i.isFlipped() == true){		//if its flipped then flip the image or don't
				g2d.drawImage(i.getImage(),479,i.getYTop(),-i.getImage().getWidth(null),i.getImage().getHeight(null),null);
			}
			else{
				g.drawImage(i.getImage(),0,i.getYTop(),this);
			}
		}
	}
	public void moveLayerThree(Graphics g){
		backy+=(int)(player1.getVelocity()*0.1);
		midy+=(int)(player1.getVelocity()*0.5);
		drawEnemy(g);
		drawCoin(g);
		drawBox(g);
		drawPoof(g);
		drawStar(g);
		drawJumper(g);
		drawSpike(g);
		drawPup(g);
		if (backy<=dieHeight){
			System.out.println(die);
			g.drawImage(player1.move(2),player1.getX(),player1.getY(),this);
			if (player1.animationComplete()){
				die=true;
			}
		}
		else{
			if (backy<=dieHeight){
				player1.resetCounter();
			}
			if (keys[KeyEvent.VK_RIGHT]){
				g.drawImage(player1.move(1),player1.getX(),player1.getY(),this);
			}
			else if (keys[KeyEvent.VK_LEFT]){
				g.drawImage(player1.move(-1),player1.getX(),player1.getY(),this);
			}else{
				g.drawImage(player1.move(0),player1.getX(),player1.getY(),this);
			}	
		}

	}
	public void drawPowerUpEffect(Graphics g){
		if (player1.getPower().equals("Magnet")){
			Image magpic = magnetList.get((int)count%6);
			g.drawImage(magpic,player1.getX()-((magpic.getWidth(null)-player1.getWidth())/2),player1.getY()-((magpic.getHeight(null)-player1.getHeight())/2),magpic.getWidth(null),magpic.getHeight(null),this);
			count+=0.1;
		}
		else if (player1.getPower().equals("Ball")){
			g.drawImage(ballPower,player1.getX()-ballPower.getWidth(null)/2+17,player1.getY()+player1.getHeight()-20,ballPower.getWidth(null),ballPower.getHeight(null),this);
		}
		else if (player1.getPower().equals("Sheild")){
			g.drawImage(sheildPower,player1.getX()-((sheildPower.getWidth(null)-player1.getWidth())/2),player1.getY()-((sheildPower.getHeight(null)-player1.getHeight())/2),sheildPower.getWidth(null),sheildPower.getHeight(null),this);
		}
		else if (player1.getPower().equals("Umbrella")){
			g.drawImage(umbrellaPower,player1.getX()-(umbrellaPower.getWidth(null)/2)+20,player1.getY()-umbrellaPower.getHeight(null)+40,umbrellaPower.getWidth(null),umbrellaPower.getHeight(null),this);
		}
		else if (player1.getPower().equals("")){
		}
	}
	public void displayScore(Graphics g, int cx,int cy, int sx1, int sy1, int sx2, int sy2, int sx3, int sy3){
		//displayes the score and stuff
		g.setFont(scoreFont); 
		g.setColor(Color.WHITE);
		g.drawImage(new ImageIcon("InGameMenu/coin.png").getImage(),cx,cy,this);
		g.drawString(""+coins,sx1,sy1);
		g.drawString(""+score,sx2,sy2);
		g.drawString(""+height+" m",sx3,sy3);
	}
	public void displayScore2(Graphics g, int cx,int cy, int sx1, int sy1, int sx2, int sy2, int sx3, int sy3){
		g.setFont(scoreFont); 
		g.setColor(Color.WHITE);
		g.drawImage(new ImageIcon("InGameMenu/coin.png").getImage(),cx,cy,this);
		g.drawString(""+coins,sx1,sy1);
		g.drawString("Score:  "+score,sx2,sy2);
		g.drawString("Height:  "+height+" m",sx3,sy3);
	}
//-------------------------------------------------------------------------------------------------------------------------------------------------------
//Movement
	public void moveEverything(){		//call all the scrolling stuff so it makes the object move up and down depending on the player movement
		scrollCoins();
		scrollEnemies();
		scrollStars();
		scrollBoxes();
		scrollSpikes();
		scrollJumpers();
		scrollPowerUps();
		scrollRects();
		scrollDecorations();
		scrollPoofs();
		moveEnemies();
		moveCoins();
	}
	public void scrollCoins(){
		for(Coin i : coinList){
			i.setY(i.getY()+(int)(player1.getVelocity()*0.3));
			i.setYPos(i.getYPos()+(int)(player1.getVelocity()*0.3));
			i.setYMax(i.getYMax()+(int)(player1.getVelocity()*0.3));
		}
	}
	public void scrollEnemies(){
		for(Enemy i : enemyList){
			i.setY(i.getY()+(int)(player1.getVelocity()*0.3));
			i.setYPos(i.getYPos()+(int)(player1.getVelocity()*0.3));
			i.setYMax(i.getYMax()+(int)(player1.getVelocity()*0.3));
		}
	}
	public void scrollBoxes(){
		for(Box i : boxList){
			i.setY(i.getY()+(int)(player1.getVelocity()*0.3));
		}
	}
	public void scrollStars(){
		for(Star i : starList){
			i.setY(i.getY()+(int)(player1.getVelocity()*0.3));
		}
	}
	public void scrollJumpers(){
		for(Jumper i : jumperList){
			i.setY(i.getY()+(int)(player1.getVelocity()*0.3));
		}
	}
	public void scrollSpikes(){
		for(Spikes i : spikeList){
			i.setY(i.getY()+(int)(player1.getVelocity()*0.3));
		}
	}
	public void scrollDecorations(){
		for(Decorations i :decoList){
			i.setY(i.getYTop()+(int)(player1.getVelocity()*0.45));
		}
	}
	public void scrollPowerUps(){
		for(Powerup i : pupList){
			i.setY(i.getY()+(int)(player1.getVelocity()*0.3));
		}
	}
	public void scrollPoofs(){
		for (Poof i:poofList){
			i.setY(i.getY()+(int)(player1.getVelocity()*0.3));
		}
	}
	public void scrollRects(){
		for(Rectangle i : rectList){
			i.setLocation((int)(i.getX()),(int)(i.getY()+(int)(player1.getVelocity()*0.3)));
		}
	}
	public void moveEnemies(){
		for(Enemy i : enemyList){
			i.move();
		}
	}
	public void moveCoins(){
		for(Coin i : coinList){
			i.move();
		}
	}
//-------------------------------------------------------------------------------------------------------------------------------------------------------
	public String getPowerUp(){
		if (!player1.getPower().equals("")){
			return ("P1");
		}
		else{
			return "none";
		}
	}
	public void loseCoins(){
		int x= player1.getX();
		int y =player1.getY();
		double losePercentage = 0.1;
		for (int i=0; i<(int)coins*losePercentage;i++){			//makes the user lose 10 percent of the coin and draws them in a circle 
			//System.out.println(i);
			int xPos=x+(int)(100*Math.cos(Math.toRadians((360/(coins*losePercentage))*i)));
			int yPos=y-(int)(100*Math.sin(Math.toRadians((360/(coins*losePercentage))*i)));
			coinList.add(new Coin(xPos,yPos,3));
		}
		coins-= (int)(coins*losePercentage);
	}
	public void resetPower(){
		//resets the power up
		player1.setPower("");
		player1.setInvi(false);
	}
	public void drawPowerUpIcon(Graphics g){
		//draws the powerup the player has on the top 
		if(player1.getPower().equals("Umbrella")){g.drawImage(new ImageIcon("gamelayerstuff/powerups/UmbrellaSymbol.png").getImage(),200,10,this);}	
		else if(player1.getPower().equals("Ball")){g.drawImage(new ImageIcon("gamelayerstuff/powerups/BallSymbol.png").getImage(),200,10,this);}
		else if(player1.getPower().equals("Boost")){g.drawImage(new ImageIcon("gamelayerstuff/powerups/BoostSymbol.png").getImage(),200,10,this);}	
		else if(player1.getPower().equals("Balloon")){g.drawImage(new ImageIcon("gamelayerstuff/powerups/BalloonSymbol.png").getImage(),200,10,this);}
		else if(player1.getPower().equals("Sheild")){g.drawImage(new ImageIcon("gamelayerstuff/powerups/SheildSymbol.png").getImage(),200,10,this);}
		else if(player1.getPower().equals("Magnet")){g.drawImage(new ImageIcon("gamelayerstuff/powerups/MagnetSymbol.png").getImage(),200,10,this);}
		else{g.drawImage(new ImageIcon("gamelayerstuff/powerupbox.png").getImage(),200,10,this);}
	}
	public void setMenu(){
		screen="game";
	}
	public boolean getMenu(){
		if (screen.equals("menu")){
			return true;
		}
		return false;
	}
	public void resetValues(int lvl,String character){
		if(lvl == 1){backy = (int)(0/0.1);}
		if(lvl == 2){backy = (int)(1210/0.1);}
		if(lvl == 3){backy = (int)(3640/0.1);}
		if(lvl == 4){backy = (int)(5540/0.1);}
		if(lvl == 5){backy = (int)(7890/0.1);}
		if(lvl == 6){backy = (int)(11340/0.1);}
		if(lvl == 7){backy = (int)(13420/0.1);}
		if(lvl == 8){backy = (int)(17140/0.1);}
		if(lvl == 9){backy = (int)(18390/0.1);}
		if(lvl == 10){backy = (int)(20840/0.1);}

		player1= new Player(200,300,100,character);
		die=false;
		coins=0;
		score=0;
		prevLvl = lvl;
		dieHeight=0;
		dieMenuHeight=0;
		height=backy;
	}
	public void updateFile(){
			//writes highscore, coins and updates eveything else
			try{
				outFile=new PrintWriter(new BufferedWriter(new FileWriter("stats.txt")));
				outFile.println(""+(money+coins));
				outFile.println(""+powerUps.size());
				for(int i=0;i<powerUps.size();i++){
					outFile.println(""+powerUps.get(i));		//writes the highscore in the file
				}
				outFile.println(""+chars.size());
				for(int i=0;i<chars.size();i++){
					outFile.println(""+chars.get(i));
				}
				if (Integer.parseInt(stats.get(stats.size()-3))<score){
					outFile.println(score);
				}else{
					outFile.println(stats.get(stats.size()-3));
				}
				if (Integer.parseInt(stats.get(stats.size()-2))<score){
					outFile.println(height);
				}else{
					outFile.println(stats.get(stats.size()-2));
				}
				outFile.println(Integer.parseInt(stats.get(stats.size()-1))+height);

				
				outFile.close();
			}
			catch(IOException ex){
				System.out.println("yooo stop noobing out");
			}
	    }
	public void readFile(String name){			//reads the file and puts everything in the stats list
		Scanner inFile=null;
		stats=new ArrayList<String>();
		chars= new ArrayList<String>();
		powerUps= new ArrayList<String>();
    	try{
    		inFile=new Scanner (new BufferedReader (new FileReader(name+".txt")));   	
	    	while (inFile.hasNextLine()){
	    			stats.add(inFile.nextLine());
	    	}
    	}
    	catch(IOException ex){
    		System.out.println("Did you forget to make the"+name+".txt file?");
    	}
	}
	public void manageStuff(){
		//organizes the the stuff in the stats list to useable stuff
		int c=0;
		money= Integer.parseInt(stats.get(c));
		c++;
		for (int i = 0; i<Integer.parseInt(stats.get(1));i++){
			powerUps.add(stats.get(i+2));
			c++;
		}
		c++;
		System.out.println(Integer.parseInt(stats.get(1))+1);
		System.out.println(c);
		for(int i = c+1;i<c+Integer.parseInt(stats.get(c))+1;i++){
			chars.add(stats.get(i));
		}
	}
	public void stopMusic(){
		//stops the background music
		bckGrndMusic.stop();
		on=false;
	}
	public void startMusic(){
		//starts the background music
		bckGrndMusic.loop();
		on=true;
	}
	public boolean getOn(){
		//returns if the music is on in this class
		return on;
	}
}
