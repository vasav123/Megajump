//Pattern.java
//Vasav Shah, Kevin Shi
//Creates pattern objects. Pattern objects hold coins, stars, boxes, jumpers, enemies, spikes, and rectangles.
//It also contains a x-value, a y-value, a width, a height, and an exp value (exclusive x-probability).
//It takes a x value- a y-value, a pattern number and a by value in the constructor.
//The x and y values determine the position of the pattern.
//The pattern number is randomly decided in the game panel and decides the textfile that is read.
//the by value is the backy value from the game panel

//The pattern object reads textfiles based on the position of the backy value of the game. Each folder of textfiles
//are designed for specific levels and are read once the level is reached(determined by backy value).
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Pattern{
	String name;
	int x,y,w,h,by,exp;	//exclusive x-probability
	int x2;
	int num;
	private ArrayList<Coin>	cList = new ArrayList<Coin>();
	private ArrayList<Star> sList = new ArrayList<Star>();
	private ArrayList<Box>  bList = new ArrayList<Box> ();
	private ArrayList<Jumper> jList = new ArrayList<Jumper>();
	private ArrayList<Enemy>eList = new ArrayList<Enemy>();
	private ArrayList<Spikes> spList = new ArrayList<Spikes>();
	private ArrayList<Powerup> pList = new ArrayList<Powerup>();
	private ArrayList<Rectangle> rList = new ArrayList<Rectangle>();
	
	public Pattern(int x, int y, int pattern, int by){
		this.y = y;
		this.x = x;
		this.by= by;
		this.name = "pattern"+pattern;
		readPattern();
	}
	public void readPattern(){
		//This reads the patterns based on the backy values. When the backy value is within these ranges (determining the level), then 
		//certain textfile folders are opened and then the textfile is read. The program goes through the textfile and creates objects
		//based on the lines in the textfile.
		Scanner inFile=null;
	   	try{
	   		if(0<=by && by<2304){inFile=new Scanner (new BufferedReader (new FileReader("pattern texts/level1/"+name+".txt")));}
	   		else if	(1210<=by && by<3640){inFile=new Scanner (new BufferedReader (new FileReader("pattern texts/level2/"+name+".txt")));}
	   		else if	(3640<=by && by<5540){inFile=new Scanner (new BufferedReader (new FileReader("pattern texts/level3/"+name+".txt")));}
	   		else if	(5540<=by && by<7890){inFile=new Scanner (new BufferedReader (new FileReader("pattern texts/level4/"+name+".txt")));}
	   		else if	(7890<=by && by<11340){inFile=new Scanner (new BufferedReader (new FileReader("pattern texts/level5/"+name+".txt")));}
	   		else if	(11340<=by && by<13420){inFile=new Scanner (new BufferedReader (new FileReader("pattern texts/level6/"+name+".txt")));}
	   		else if	(13420<=by && by<17140){inFile=new Scanner (new BufferedReader (new FileReader("pattern texts/level7/"+name+".txt")));}
	   		else if	(17140<=by && by<18390){inFile=new Scanner (new BufferedReader (new FileReader("pattern texts/level8/"+name+".txt")));}
	   		else if	(18390<=by && by<20840){inFile=new Scanner (new BufferedReader (new FileReader("pattern texts/level9/"+name+".txt")));}
	   		else if	(20840<=by && by<23040){inFile=new Scanner (new BufferedReader (new FileReader("pattern texts/level10/"+name+".txt")));}
	   		else{inFile=new Scanner (new BufferedReader (new FileReader("pattern texts/level1/"+name+".txt")));}
	   		//intervals of 1920 until the last 2 levels, which are doubled
	   	}
	   	catch(IOException ex){}
	   	try{
		   	String[] line = inFile.nextLine().split(",");
		   	int numLines = Integer.parseInt(line[0]);	//The number of	objects in the file
		   	w = Integer.parseInt(line[1]);				//The width of the entire pattern
		   	h = Integer.parseInt(line[2]);				//The height of the entire pattern
		   	exp = Integer.parseInt(line[3]);			//The exclusion value. This value helps
		   												//Equal to 480-exp
		   	
		   	this.x2 = (int)(Math.random()*(480-exp));
		   	num = numLines;
		   	
		   	for(int i=1; i<numLines+1; i++){
		   		//Takes a line of a textfile and splits it by commas
		   		//First part decides type of object
		   		//Second and third part decide the x and y value
		   		//The third line is only used by coins, enemies, and stars and decides which type of it of coin,enemies, etc.
		   		//These objects are then added to their respective lists
		   		//A rectangle object is then created and added to the list of rectangles so that it can be used in collision checks
		   		line = inFile.nextLine().split(",");
		   		if (line[0].equals("c")){	//Coin
		   			Coin coin = new Coin(Integer.parseInt(line[1])+x2,Integer.parseInt(line[2])+y,Integer.parseInt(line[3]));
		   			cList.add(coin);
		   			rList.add(new Rectangle(coin.getX(),coin.getY(),coin.getWidth(),coin.getHeight()));
		   		}
		   		else if(line[0].equals("rc")){//Random Special Coin
		   			int rand = (int)(Math.random()*2+1);
		   			Coin coin = new Coin(Integer.parseInt(line[1])+x2,Integer.parseInt(line[2])+y,rand);
		   			cList.add(coin);
		   			rList.add(new Rectangle(coin.getX(),coin.getY(),coin.getWidth(),coin.getHeight()));
		   		}
		   		else if(line[0].equals("s")){//Star
		   			Star star = new Star(Integer.parseInt(line[1])+x2,Integer.parseInt(line[2])+y,Integer.parseInt(line[3]));	
		   			sList.add(star);
		   			rList.add(new Rectangle(star.getX(),star.getY(),star.getWidth(),star.getHeight()));
		   		}
		   		else if(line[0].equals("rs")){//Random Star
		   			int rand = (int)(Math.random()*4);
		   			Star star = new Star(Integer.parseInt(line[1])+x2,Integer.parseInt(line[2])+y,rand);
		   			sList.add(star);
		   			rList.add(new Rectangle(star.getX(),star.getY(),star.getWidth(),star.getHeight()));
		   		}
		   		if(line[0].equals("b")){//Box
		   			Box box = new Box(Integer.parseInt(line[1])+x2,Integer.parseInt(line[2])+y,by);
		   			bList.add(box);
		   			rList.add(new Rectangle(box.getX(),box.getY(),box.getWidth(),box.getHeight()));
		   		}
		   		else if(line[0].equals("j")){//Jumper
		   			Jumper jumper = new Jumper(Integer.parseInt(line[1])+x2,Integer.parseInt(line[2])+y,by);
		   			jList.add(jumper);
		   			rList.add(new Rectangle(jumper.getX(),jumper.getY(),jumper.getWidth(),jumper.getHeight()));
		   		}
		   		else if(line[0].equals("e")){//Enemy
		   			Enemy enemy = new Enemy(Integer.parseInt(line[1])+x2,Integer.parseInt(line[2])+y,Integer.parseInt(line[3]));	
		   			eList.add(enemy);
		   			rList.add(new Rectangle(enemy.getX(),enemy.getY(),enemy.getWidth(),enemy.getHeight()));
		   		}
		   		else if(line[0].equals("sp")){//Spike
		   			Spikes spikes = new Spikes(Integer.parseInt(line[1])+x2,Integer.parseInt(line[2])+y,by);
		   			spList.add(spikes);
		   			rList.add(new Rectangle(spikes.getX(),spikes.getY(),spikes.getWidth(),spikes.getHeight()));
		   		}
		   		else if(line[0].equals("rp")){//Random powerup
		   			int rand = (int)(Math.random()*8);
		   			Powerup powerup = new Powerup(Integer.parseInt(line[1])+x2,Integer.parseInt(line[2])+y,rand);
		   			pList.add(powerup);
		   			rList.add(new Rectangle(powerup.getX(),powerup.getY(),powerup.getWidth(),powerup.getHeight()));
		   		}
		   	}
	   	}
	   	catch(NullPointerException npe){}
	}
	public ArrayList<Coin> getCoins(){
		//Return the array of coins
		return cList;
	}
	public ArrayList<Star> getStars(){
		//Return the array of stars
		return sList;
	}
	public ArrayList<Box> getBoxes(){
		//Return the array of boxes
		return bList;
	}
	public ArrayList<Enemy> getEnemies(){
		//Return the array of enemies
		return eList;
	}
	public ArrayList<Spikes> getSpikes(){
		//Return the array of spikes
		return spList;
	}
	public ArrayList<Jumper> getJumpers(){
		//Return the array of jumpers
		return jList;
	}
	public ArrayList<Powerup> getPowerUps(){
		//Return the array of powerups
		return pList;
	}
	public ArrayList<Rectangle> getRects(){
		//Return the array of rectangles
		return rList;
	}
	public int getX(){
		//Return the x-value
		return x;
	}
	public int getY(){
		//Return the y-value
		return y;
	}
	public int getWidth(){
		//Return the width of the pattern
		return w;
	}
	public int getHeight(){
		//Return the height of the pattern
		return h;
	}
	public void setX(int x){
		//Takes an integer as a parameter and that becomes the new x-value
		this.x = x;
	}
	public void setY(int y){
		//Takes an integer as a parameter and that becomes the new y-value
		this.y = y;
	}
}