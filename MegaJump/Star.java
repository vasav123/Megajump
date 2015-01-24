//Star.java
//Vasav Shah, Kevin Shi

//This class keeps track of the all the stars and start type
//diffrent types of star have diffrent values and diffren distance to travel
import java.util.*;
import javax.swing.*;
import java.awt.*;


public class Star {
	private int num,x,y,points,dist;
	private double picCounter;
	private String type;	//keeps track of which enemy this is
	private Rectangle coinRect;
	private ArrayList<Image> picList = new ArrayList<Image>();	//list holding all the sprites for the enemy type
	private boolean onScreen;
	
	//xPos,yPos are the original starting position of the coin
	//x, y track the position of the coin
	//xMax, yMax track the farthest distance the coin moves
	//value holds how much the coin is worth
	//points holds how many points each coin is worth
	//picCounter keeps track of the position in the list of pictures (which picture is being drawn)
    
    public Star(int x, int y, int type){
    	this.x = x;
    	this.y = y;
    	this.num = type;
    	pickType();
    	picCounter = 0;
    	onScreen= true;
    }
    public void pickType(){
    	//randomly picks the type of enemy. Enemies are chosen by random integers. The type, value, and points are also decided based on the type of the 
    	//enemy
		if(num == 0){
			type = "bluestar";
			points = 10;
			dist=80;
		}
		else if(num == 1){
			type = "greenstar";
			points = 10;
			dist = 70;
		}
		else if(num == 2){
			type = "redstar";
			points = 10;
			dist = 100;
		}
		else if(num == 3){
			type = "yellowstar";
			points = 10;
			dist = 60;
		}
		loadImage();
    }
    public void loadImage(){
    	//loads the sprites for the type of enemy and then adds them to the arraylist
    	for(int i=1;i<7;i++){
    		picList.add(new ImageIcon("gamelayerstuff/stars/"+type+i+".png").getImage());
    	}
    }
    public void count(){
    	//increases the counter and mods it so that it doesn't go out of range
    	picCounter+=0.2;
    	picCounter = picCounter%picList.size();
    }
	public boolean checkCollision(Image ePic,Player p){
		coinRect= new Rectangle (x,y,ePic.getWidth(null),ePic.getHeight(null));
		if (coinRect.intersects(p.getRect())){
			return true;
		}else{
			return false;
		}
	}
//-------------------------------------------------------------------------------------------------------------------------------------------------------
//Getters
	public int getNum(){
		return num;
	}
    public String getType(){
    	//Returns the type of enemy thge object is
    	return type;
    }
    public int getCounter(){
    	//Returns the position in the spritelist
    	return (int)(picCounter);
    }
    public int getX(){
    	//Returns the x-value of the current position
    	return x;
    }
    public int getY(){
    	//Returns the y-value of the current position
    	return y;
    }
    public int getDist(){
    	//Returns boost that the stars give the player
    	return dist;
    }
    public int getPoints(){
    	//returns the points the star is worth
    	return points;
    }
    public boolean getOnScreen(){
    	//returns if the star is on the screen or not
    	return onScreen;
    }
    public int getWidth(){
    	//returns the width of the image
		return picList.get((int)(picCounter)).getWidth(null);
	}
	public int getHeight(){
		//Returns the height of the image
		return picList.get((int)(picCounter)).getHeight(null);
	}
    public ArrayList<Image> getPics(){
    	//Returns the list of sprites
    	return picList;
    }
//-------------------------------------------------------------------------------------------------------------------------------------------------------
//Setters
    public void setX(int x){
    	//takes an integer and that becomes the new x-value
    	this.x = x;
    }
    public void setY(int y){
    	//takes an integer and that becomes the new y-value
    	this.y = y;
    	if (y>1000){
    		onScreen=false;
    	}
    }
}