/**
 *Kevin Shi, Vasav Shah
 *This class keeps track of the powerup that shows up
 *Using file io our group keeps track of the powerups bought
 *However the Cloud and Ball power up are not helpful, therefore we make sure that its in the file all the time
 */


import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Powerup {
	private Image pic;
	private String [] name = {"Ball","Balloon","Boost","Cloud","Lucky","Magnet","Sheild","Umbrella"};			//instead of bashing it out, we put all the names in this list so that we can keep track of the type 
	private ArrayList <String> stats = new ArrayList<String>();						//hodls all  data from the textfile
	private ArrayList <String>boughtPower = new ArrayList<String>();				//holsd all the powerups that are bought
	private int x,y;																//x and y position
	private String type;															
	private Rectangle boxRect;														//Rectangle used for collision
    private boolean onScreen;														//if the thing is on the screen then remove it or else don't
    public Powerup(int x,int y,int num) {
    	this.x=x;
    	this.y=y;
    	readFile("stats");
    	type= name[num];
    	if (boughtPower.contains(type)){
    		pic= new ImageIcon("gamelayerstuff/powerups/"+type+".png").getImage();
    	}
    	onScreen=true;
    }
    public void readFile(String name){
    	//Reads the file and puts everything in the Stats
    	Scanner inFile=null;
    	try{
    		inFile=new Scanner (new BufferedReader (new FileReader(name+".txt")));   	
	    	while (inFile.hasNextLine()){
	    			stats.add(inFile.nextLine());
	    	}
	    	for (int i = 0; i<Integer.parseInt(stats.get(1));i++){
				boughtPower.add(stats.get(i+2));					//takes all the powerup Bought and puts them in the list
	    	}
    	}
    	catch(IOException ex){					//Catches the exception
    		System.out.println("Did you forget to make the"+name+".txt file?");
    	}
    }
//-------------------------------------------------------------------------------------------------
//getters
    public Image getImage(){
    	return pic;
    }
    public int getX(){
    	return x;
    }
    public int getY(){
    	return y;
    }
    public boolean getOnScreen(){
    	return onScreen;
    }
    public int getHeight(){
    	if (pic!=null){
    		return pic.getHeight(null);
    	}
    	else{
    		return 0;
    	}
    	
    }
    public int getWidth(){
    	if (pic!=null){
    		return pic.getWidth(null);
    	}
    	else{
    		return 0;
    	}
    }
    public String getType(){
    	return type;
    }
//---------------------------------------------------------------------------------------
//Setters
    public void setX(int x){
    	this.x=x;
    }
    public void setY(int y){
    	this.y=y;
    	if (y>1000){
    		onScreen=false;
    	}
    }
	public boolean checkCollision(Player p){//checks the collision with the player
		if (getImage()!=null){
			boxRect= new Rectangle (x,y,pic.getHeight(null),pic.getWidth(null));
			if (boxRect.intersects(p.getRect())){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
}