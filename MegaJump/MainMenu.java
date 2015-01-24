//MainMenu.java
//Vasav Shah, Kevin Shi
//there are two class in this file
//the Main Menu class makes the JFrame and allows the user to switch between the game and menu screen

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.sound.sampled.AudioSystem;
import java.applet.*;


public class MainMenu extends JFrame implements ActionListener{
	javax.swing.Timer myTimer;
	javax.swing.Timer player1Timer;
	MenuPanel panel = new MenuPanel();
	GamePanel game = new GamePanel("android",1);
	
    public MainMenu() {
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------
//Creation of panels and frames    	
    	super("Mega Jump");
    	setLayout(null);
    	setSize(975,639);
    	panel.setSize(975,639);
    	panel.setLocation(0,0); 
    	add(panel);
    	panel.setVisible(true);
    	game.setSize(479,768);
	    game.setLocation(0,0);
    	add(game);
    	game.setVisible(false);
    	setVisible(true);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------
//Timers    	
    	myTimer=new javax.swing.Timer (10,this);
    	myTimer.start();
    	player1Timer= new javax.swing.Timer(5000,this);	//5 seconds for the powerup
    	
    }
    public void actionPerformed(ActionEvent evnt){
    	Object source = evnt.getSource();
    	if (panel.getGame()){
    		if (!game.getOn()){
    			game.startMusic();
    		}
    		
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------
//Game Panel 
    		panel.setVisible(false);	//if the user is in on the gamePanel set the menu panel to not visible
	    	game.setVisible(true);			//now the user is focused on the game
	    	game.requestFocus();	
		    game.repaint(); 
		    setSize(479,768);
		    if (game.getPowerUp().equals("P1")){
				player1Timer.start();
			}
			if (source==player1Timer && game.getPowerUp().equals("P1")){
				game.resetPower();
				player1Timer.stop();
			}
			if(game.getMenu()){
				panel.setGame();
				game.setVisible(false);
			}
    	}
    	else{	//here the user is on the main menu screen
    		if (panel.getOn()==false){
    			panel.startMusic();
    		}
    		
    		panel.setVisible(true);
    		game.setVisible(false);
    		setSize(975,639);
    		panel.requestFocus();
    		panel.repaint();
    		game.setMenu();
    		game.stopMusic();
    		game.resetValues(panel.getLevel(),panel.getCharacter());
    	}	
    }
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static void main(String[] arguments){
		MainMenu menu = new MainMenu();
		
    }
}
class MenuPanel extends JPanel implements MouseListener,MouseMotionListener,KeyListener,MouseWheelListener{
	
	private Image mainBackground;
	private int mx,my;
	boolean click;
	
	AudioClip bckGrndMusic;	//Audio Files
	AudioClip clicked;//<--sounds when a button is clicked
	
	private Font scoreFont;
	
	SButton startGame,rightArrowC,leftArrowC,rightArrowL,leftArrowL,play,lvlSelect,store,options,help,blocks,powerup,enemies,howToPlay,backMenu,backOptn,backLvl,backHelp,play1,play2,coin,powerupStoreb,playerStoreb,backStore,rightArrow,leftArrow,buyBtn;
	//ArrayLists of buttons that show up on each screen.

	//Setup for arraylists
	ArrayList <SButton> mainBtnList  = new ArrayList <SButton>();
	ArrayList <SButton> modesBtnList = new ArrayList <SButton>();
	ArrayList <SButton> playBtnList	 = new ArrayList <SButton>();
	ArrayList <SButton> lvlBtnList   = new ArrayList <SButton>();
	ArrayList <SButton> storeBtnList = new ArrayList <SButton>();
	ArrayList <SButton> optnBtnList  = new ArrayList <SButton>();
	ArrayList <SButton> helpBtnList  = new ArrayList <SButton>();
	ArrayList <SButton> hTPBtnList   = new ArrayList <SButton>();
	ArrayList <SButton> blockBtnList = new ArrayList <SButton>();
	ArrayList <SButton> powerupBtnList  = new ArrayList <SButton>();
	ArrayList <SButton> enemBtnList  = new ArrayList <SButton>();
	ArrayList <SButton> coinBtnList	 = new ArrayList <SButton>();
	ArrayList <SButton> powerupStoreBtnList = new ArrayList<SButton>();
	ArrayList <SButton> charStoreBtnList	 = new ArrayList<SButton>();
	
	ArrayList <String> charPickList	 = new ArrayList<String>();//Characters have not been unloicked
	int[]levelPickList = {1,2,3,4,5,6,7,8,9,10};//<All levels have been unlocked. You're welcome.
	
	//Character and Level selection
	ArrayList<Image> charPics = new ArrayList<Image>();
	ArrayList<Image> levelPics = new ArrayList<Image>();
	ArrayList <String> stats = new ArrayList<String>();
	int charCounter;
	int levelCounter;
	
	//Variables that keep track of stats
	int coins, highscore,maxHeight,totalDist;
	
	Image displayPanel = new ImageIcon("Store/displayPanel.png").getImage();
	Image lock = new ImageIcon("Store/lock.png").getImage();
	
	Store str=  new Store("");
	
	String screen;
	
	Slider s =new Slider(0,0);
	boolean [] keys;	
	private boolean on=true;
	public MenuPanel(){
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------
//Setup for Menu		
		setFocusable(true);
    	grabFocus();
    	addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		addMouseWheelListener(this);
		keys=new boolean[2000];	
		mx=0;my=0;			//<---mouse position
		click = false;		//<---mouse click
		screen = "main";	//keeps track of which screen the menu currently is on
		
		try{scoreFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("BRLNSB.ttf"))).deriveFont(0,48);}//<Font Stuff
		catch(IOException ioe){System.out.println("error loading BRLNSB.tff");}
		catch(FontFormatException ffe){System.out.println("Something went wrong with the font.");}
		
		charCounter = 0;
		levelCounter= 0;
		
		for(int i=1;i<11;i++){
			levelPics.add(new ImageIcon("levelSelect/level"+i+".png").getImage());
		}
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------
//Sounds
		bckGrndMusic= Applet.newAudioClip(getClass().getResource("bgmusic00.wav"));
		clicked= Applet.newAudioClip(getClass().getResource("grav_step_4.wav"));
		bckGrndMusic.loop();
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------
//Buttons
		play 	  	= new SButton(510,250,"play","playModes");
		
		play1		= new SButton(200,200,"1PlayerButton","menu");
		play2		= new SButton(300,200,"2PlayerButton","menu");
		store	  	= new SButton(530,370,"store","store");
		options	  	= new SButton(10,10,"options","options");
		help		= new SButton(800,10,"help","help");
		backMenu  	= new SButton(700,490,"back","menu");
		backLvl		= new SButton(50,500,"lvlSelect","playModes");
		backHelp	= new SButton(20,510,"help","help");
		
		howToPlay	= new SButton(50,15,"howToPlay","howToPlay");
		blocks		= new SButton(50,135,"blocks","blocks");
		powerup		= new SButton(50,255,"powerup","powerup");
		enemies		= new SButton(50,375,"enemies","enemies");
		coin		= new SButton(50,495,"coin","coin");
		backStore	= new SButton(20,510,"store","store");
		
		startGame	= new SButton(300,490,"play","menu");
		leftArrowC  = new SButton(10,250,"leftArrow","leftChar");
		rightArrowC = new SButton(455,250,"rightArrow","rightChar");
		leftArrowL  = new SButton(500,250,"leftArrow","leftLevel");
		rightArrowL = new SButton(910,250,"rightArrow","rightLevel");
		

		powerupStoreb = new SButton(50,135,"powerup_store","powerup_store");
		playerStoreb  = new SButton(50,255,"char_store","char_store");
		rightArrow    = new SButton(885,200,"rightArrow","right");
		leftArrow     = new SButton(30,200,"leftArrow","left");
		buyBtn	      = new SButton(700,370,"buy","buy");
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------
//Adding Buttons into lists
		storeBtnList.add(powerupStoreb);storeBtnList.add(playerStoreb);
		mainBtnList.add(play);mainBtnList.add(store);mainBtnList.add(options);mainBtnList.add(help);
		modesBtnList.add(startGame);modesBtnList.add(rightArrowC);modesBtnList.add(leftArrowC);modesBtnList.add(rightArrowL);modesBtnList.add(leftArrowL);modesBtnList.add(backMenu);
		lvlBtnList.add(backLvl);lvlBtnList.add(backMenu);lvlBtnList.add(play1);lvlBtnList.add(play2);
		storeBtnList.add(backMenu);
		optnBtnList.add(backMenu);
		helpBtnList.add(howToPlay);helpBtnList.add(blocks);helpBtnList.add(powerup);helpBtnList.add(enemies);helpBtnList.add(backMenu);helpBtnList.add(coin);
		
		hTPBtnList.add(backHelp);blockBtnList.add(backHelp);powerupBtnList.add(backHelp);enemBtnList.add(backHelp);coinBtnList.add(backHelp);
		hTPBtnList.add(backMenu);blockBtnList.add(backMenu);powerupBtnList.add(backMenu);enemBtnList.add(backMenu);coinBtnList.add(backMenu);
		
		powerupStoreBtnList.add(backStore);powerupStoreBtnList.add(backMenu);powerupStoreBtnList.add(rightArrow);powerupStoreBtnList.add(leftArrow);powerupStoreBtnList.add(buyBtn);
		charStoreBtnList.add(backStore);charStoreBtnList.add(backMenu);charStoreBtnList.add(rightArrow);charStoreBtnList.add(leftArrow);charStoreBtnList.add(buyBtn);
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------
		
		mainBackground  = new ImageIcon("menus/mainMenu.png").getImage();
		
	}
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------
//Mouse stuff	
	public void mouseEntered(MouseEvent e){}
    public void mouseReleased(MouseEvent e){click = false;}
    public void mousePressed(MouseEvent e){
    	click = true;//<--keeps track of if the mouse is clicked or not
    	
    	//First it checks which screen the user is on. Then it goes through the list of buttons for the menu and checks to see if they
    	//have been clicked or not
    	if(screen.equals("main")){	//<---When the screen is on the main menu, then the buttons on the main menu
    		for(SButton i : mainBtnList){
    			if(i.clicked(mx,my,click) && i.checkScreen()){
    				clicked.play();//<-----fancy sound
    				screen = i.getScreen();
    			}
    		}
    	}
    	else if(screen.equals("playModes")){
    		for(SButton i : modesBtnList){
    			if (i.clicked(mx,my,click)&&i.checkScreen()){
	    			if(i.clicked(mx,my,click) && i.getType().equals("play")){
	    				clicked.play();
	    				screen="1playergame";
	    			}
	    			//These are character and level selection
	    			//When these arrows are clicked, it changes the position of the lists that contain the levels and characters
	    			else if(i.clicked(mx,my,click) && i.getScreen().equals("leftChar")){
	    				clicked.play();
	    				charCounter-=1;
	    				if(charCounter<0){
	    					charCounter = charPics.size()-1;
	    				}
	    			}
	    			else if(i.clicked(mx,my,click) && i.getScreen().equals("rightChar")){
	    				clicked.play();
	    				charCounter+=1;
	    				if(charCounter>charPics.size()-1){
	    					charCounter = 0;
	    				}
	    			}
	    			else if(i.clicked(mx,my,click) && i.getScreen().equals("leftLevel")){
	    				clicked.play();
	    				levelCounter-=1;
	    				if(levelCounter<0){
	    					levelCounter = 9;
	    				}
	    			}
	    			else if(i.clicked(mx,my,click) && i.getScreen().equals("rightLevel")){
	    				clicked.play();
	    				levelCounter+=1;
	    				if(levelCounter>9){
	    					levelCounter = 0;
	    				}
	    			}
	    			else{
	    				clicked.play();
	    				screen = i.getScreen();
	    			}
    			}
    			
    		}
    	}
    	else if(screen.equals("help")){
    		s.reset();
    		for(SButton i : helpBtnList){
    			if(i.clicked(mx,my,click) && i.checkScreen()){
    				clicked.play();
    				screen = i.getScreen();
    			}
    		}
    	}
    	else if(screen.equals("howToPlay") || screen.equals("blocks") || screen.equals("powerup") || screen.equals("enemies") || screen.equals("coin")){
    		//These menus all have the same buttons so only one statement was needed
    		for(SButton i : hTPBtnList){
    			if(i.clicked(mx,my,click) && i.checkScreen()){
    				clicked.play();
    				screen = i.getScreen();
    			}
    		}
    	}
    	else if (screen.equals("store")){
    		for (SButton i:storeBtnList){
    			if (i.clicked(mx,my,click)&&i.checkScreen()){
    				clicked.play();
    				screen=i.getScreen();
    			}
    		}
    	}
    	else if (screen.equals("powerup_store")){
    		//When left and right buttons are clicked, changes position in the list (which shows what item is currently selected)
    		for (SButton i :powerupStoreBtnList){
    			if (i.clicked(mx,my,click)&&i.checkScreen()){
    				if (i.getScreen().equals("right")){
    					clicked.play();
    					str.goRight();
    				}
    				else if (i.getScreen().equals("left")){
    					clicked.play();
    					str.goLeft();
    				}
    				else if (i.getScreen().equals("buy")){
    					//buys an item and updates the textfile
    					clicked.play();
    					str.buy("powerup");
    					str.updateFile();
    				}
    				else{
    					screen=i.getScreen();
    				}
    			}
    		}
    	}
    	else if (screen.equals("char_store")){
    		//When left and right buttons are clicked, changes position in the list (which shows what item is currently selected)
    		for (SButton i:charStoreBtnList){
    			if (i.clicked(mx,my,click)&&i.checkScreen()){
    				if (i.getScreen().equals("right")){
    					clicked.play();
    					str.goRight();
    				}
    				else if (i.getScreen().equals("left")){
    					clicked.play();
    					str.goLeft();
    				}
    				else if (i.getScreen().equals("buy")){
    					//buys an item and updates the textfile
    					clicked.play();
    					str.buy("char");
    					str.updateFile();
    				}
    				else{
    					screen=i.getScreen();
    				}
    			}	
    		}
    	}
    	if(! screen.equals("main")){
    		//this is the back button that every screen has
    		if(backMenu.clicked(mx,my,click)){
    			clicked.play();
    			screen = "main";
    		}
    	}
    }
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void mouseExited(MouseEvent e){}
    public void mouseClicked(MouseEvent e){}
    public void mouseDragged(MouseEvent e){mx = e.getX();my = e.getY();}
    public void mouseMoved(MouseEvent e){mx = e.getX();my = e.getY();}
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void mouseWheelMoved(MouseWheelEvent e){
		//This is for scrolling on the tutorial screens
		if(screen.equals("howToPlay") || screen.equals("blocks") || screen.equals("powerup") || screen.equals("enemies") || screen.equals("coin")){
			if (e.getWheelRotation()>0){
				s.moveDown(e.getScrollAmount()*2);
			}
			else if (e.getWheelRotation()<0){
				s.moveUp(Math.abs(e.getScrollAmount())*2);
			}
		}
	}
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void keyPressed(KeyEvent evt){			//if i pressed an keydown then set it to equal true
		int i=evt.getKeyCode();
		keys[i]=true;
	}
	public void keyReleased(KeyEvent evt){		//one i let go set it to equal false
		int i=evt.getKeyCode();
		keys[i]=false;
	}
	public void keyTyped(KeyEvent evt){	
	}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void readFile(String name){			//reads the file and puts everything in the textfile
		Scanner inFile=null;
		stats=new ArrayList<String>();
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
		//Goes through the arraylists and manages them
		int c=0;
		c++;
		for (int i = 0; i<Integer.parseInt(stats.get(1));i++){
			c++;
		}
		c++;
		charPickList= new ArrayList <String>();
		charPics= new ArrayList<Image>();
		for(int i = c+1;i<c+Integer.parseInt(stats.get(c))+1;i++){
			charPickList.add(stats.get(i));
		}
		for(String name : charPickList){
			charPics.add(new ImageIcon("charSelect/"+name+".png").getImage());
		}
	}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void drawPowerUpScreen(Graphics g,String screen){
    	g.drawImage(new ImageIcon("menus/"+screen+"Menu.png").getImage(),0,0,this);
    	g.drawImage(displayPanel,100,25,displayPanel.getWidth(null),displayPanel.getHeight(null),this);
    	g.drawImage(str.getPowerPic(),100,25,this);
    	g.drawImage(new ImageIcon("InGameMenu/coin.png").getImage(),300,515,this);
    	g.setFont(scoreFont);
		g.setColor(Color.WHITE);
		g.drawString(""+str.getPowerUpPrice(),600,400);
    	g.drawString(""+str.getMoney(),370,553);
    	if (str.getPowerLocked()){
    		g.drawImage(lock,150,300,this);
    		//g.drawImage(buyBtn.getPic(mx,my),buyBtn.getX(),buyBtn.getY(),this);
    	}
    	
    }
    public void drawCharScreen(Graphics g,String screen){
    	g.drawImage(new ImageIcon("menus/"+screen+"Menu.png").getImage(),0,0,this);
    	g.drawImage(displayPanel,100,25,displayPanel.getWidth(null),displayPanel.getHeight(null),this);
    	g.drawImage(str.getCharPic(),100,25,this);
    	g.drawImage(new ImageIcon("InGameMenu/coin.png").getImage(),300,515,this);
    	g.setFont(scoreFont);
		g.setColor(Color.WHITE);
		g.drawString(""+str.getCharPrice(),550,400);
    	g.drawString(""+str.getMoney(),370,553);
    	if (str.getCharLocked()){
    		g.drawImage(lock,150,300,this);
    	//	g.drawImage(buyBtn.getPic(mx,my),buyBtn.getX(),buyBtn.getY(),this);
    	}
    //	str.drawEverything(g,this);
    }
    public void drawHelpScreen(Graphics g, String screen){
		g.drawImage(new ImageIcon("menus/"+screen+"help.png").getImage(),0,s.move(new ImageIcon("menus/"+screen+"help.png").getIconHeight())*-1,this);
		g.drawImage(new ImageIcon("menus/sliderBigBar.png").getImage(),958,0,this);
		g.drawImage(new ImageIcon("menus/sliderSmallBar.png").getImage(),957,s.getY(),this);
		if (keys[KeyEvent.VK_UP]){
			s.moveUp(1);
		}
		if (keys[KeyEvent.VK_DOWN]){
			s.moveDown(1);
		}
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void paintComponent(Graphics g){
    	readFile("stats");
		manageStuff();
		highscore = Integer.parseInt(stats.get(stats.size()-3));
		maxHeight = Integer.parseInt(stats.get(stats.size()-2));
		totalDist = Integer.parseInt(stats.get(stats.size()-1));
        if(screen.equals("main")){
        	
        	g.drawImage(mainBackground,0,0,this);
        	displayButtons(g,mainBtnList);
        }
        if (screen.equals("1playergame")){
    	//	game.repaint();
    	}
    	for (SButton i: powerupStoreBtnList){
    		if (screen.equals(i.getScreen())){
    			g.drawImage(new ImageIcon("menus/"+i.getScreen()+"Menu.png").getImage(),0,0,this);
    		}
    	}
    	if (screen.equals("powerup_store")){
    		drawPowerUpScreen(g,"powerup_store");
    		displayButtons(g,powerupStoreBtnList);}
    	if (screen.equals("char_store")){drawCharScreen(g,screen);displayButtons(g,charStoreBtnList);}
        for(SButton i : mainBtnList){
        	if(screen.equals(i.getScreen())){
        		g.drawImage(new ImageIcon("menus/"+i.getScreen()+"Menu.png").getImage(),0,0,this);
        		if(i.getScreen().equals("playModes")){
        			displayButtons(g,modesBtnList);
        			g.drawImage(charPics.get(charCounter),100,150,this);
        			g.drawImage(levelPics.get(levelCounter),570,150,this);
        		}
        		if(i.getScreen().equals("store")){displayButtons(g,storeBtnList);}
        		if(i.getScreen().equals("options")){
        			displayButtons(g,optnBtnList);
        			g.setFont(scoreFont);
					g.setColor(Color.BLACK);
        			g.drawString("Coins:  "+stats.get(0),50,100);
        			g.drawString("Highscore:  "+highscore+"  points",50,200);
        			g.drawString("Highest Point Reached:  "+ maxHeight+" m",50,300);
        			g.drawString("Total Distance Travelled: "+ totalDist+" m",50,400);
        			}
        		if(i.getScreen().equals("help")){displayButtons(g,helpBtnList);}
        	}
        }
        for(SButton i : modesBtnList){
        	if(screen.equals(i.getScreen())){
        		g.drawImage(new ImageIcon("menus/"+i.getScreen()+"Menu.png").getImage(),0,0,this);
        		if(i.getScreen().equals("lvlSelect")){displayButtons(g,lvlBtnList);}
        	}
        }
        for(SButton i : helpBtnList){
        	if(screen.equals(i.getScreen())){
        		g.drawImage(new ImageIcon("menus/"+i.getScreen()+"Menu.png").getImage(),0,0,this);
//------------------------------------------------------------------------------------------------------------------------------------------------------------
        		if(i.getScreen().equals("howToPlay")){		//CODED ADDED FOR SLIDER BAR REMOVE LATER
        			drawHelpScreen(g,"howtoplay");								
//------------------------------------------------------------------------------------------------------------------------
        			displayButtons(g,hTPBtnList);}
        		if(i.getScreen().equals("blocks")){
					drawHelpScreen(g,"blocks");	
					displayButtons(g,blockBtnList);
        		}
        		if(i.getScreen().equals("powerup")){
        			drawHelpScreen(g,"powerup");	
        			displayButtons(g,powerupBtnList);
        		}
        		if(i.getScreen().equals("enemies")){
        			drawHelpScreen(g,"enemies");	
        			displayButtons(g,enemBtnList);
        		}
        		if (i.getScreen().equals("coin")){
        			drawHelpScreen(g,"coin");
        			displayButtons(g,coinBtnList);
        		}
        	}
        }
    }
    public int getLevel(){
    	return levelPickList[levelCounter];
    	
    }
    public String getCharacter(){
    	return charPickList.get(charCounter);
    }
    public void setGame(){
    	screen="main";
    //	bckGrndMusic.loop();
    } 
    public void startMusic(){
    	if (on==false){
    		bckGrndMusic.loop();
    		on=true;
    	}
    }
    public boolean getOn(){
    	return on;
    }
    public boolean getGame(){
    	if (screen.equals("1playergame")){
    		bckGrndMusic.stop();
    		on=false;
    		return true;
    	}else{
    		return false;
    	}
    }
    private void displayButtons(Graphics g, ArrayList <SButton> buttons){
    	for(SButton i : buttons){
    		g.drawImage(i.getPic(mx,my),i.getX(),i.getY(),this);
    	}
    }
}