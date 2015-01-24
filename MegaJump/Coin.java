//Coin.java
//Vasav Shah, Kevin Shi
//Creates coin objects with an x-value,y-value,points value, a coin value, a list of images, and a rectangle object.
//The the x and y values, and the num are specified in the constructor.

import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Coin {
	private int num,xPos,yPos,xMax,yMax,x,y,direction,value,points;
	private double picCounter;
	//xPos,yPos are the original starting position of the coin
	//x, y track the position of the coin
	//xMax, yMax track the farthest distance the coin moves
	//value holds how much the coin is worth
	//points holds how many points each coin is worth
	//picCounter keeps track of the position in the list of pictures (which picture is being drawn)
	private String type;	//keeps track of which enemy this is
	private Rectangle coinRect;
	private ArrayList<Image> picList = new ArrayList<Image>();	//list holding all the sprites for the enemy type
	private boolean onScreen;
    public Coin(int x,int y,int num) {
    	//Object takes an x and y values which are the positions the enemy starts at
    	//randomly decides which enemy the object should be and then picks the sprites and movement patters for that enemy
    	this.xPos = x;
    	this.yPos = y;
    	this.x = x;
    	this.y = y;
    	this.num = num;
    	pickType();
    	picCounter = 0;
    	onScreen= true;
    }
    public void pickType(){
    	//randomly picks the type of enemy. Enemies are chosen by random integers. The type, value, and points are also decided based on the type of the 
    	//enemy
		if(num == 0){
			//The small blue coin moves up and down
			type = "bluecoin";
			value = 2;
			points = 20;
			direction = 1;
			xMax = 0;
			yMax = yPos+100;
		}
		else if(num == 1){
			type = "brcoin";
			value = 5;
			points = 50;
			xMax = 0;
			yMax = 0;
		}
		else if(num == 2){
			type = "bbcoin";
			value = 2;
			points = 20;
			xMax = 0;
			yMax = 0;
		}
		else if(num == 3){
			type = "byellowcoin";
			value = 1;
			points = 10;
			xMax = 0;
			yMax = 0;
		}
		else if(num == 4){
			//The red coin moves left and right
			type = "redcoin";
			value =5;
			points = 50;
			direction = 1;
			xMax = xPos+100;
			yMax = 0;
		}
		else if(num == 5){
			type = "syellowcoin";
			value = 1;
			points = 10;
			xMax = 0;
			yMax = 0;
		}
		loadImage();
    }
    public void loadImage(){
    	//loads the sprites for the type of enemy and then adds them to the arraylist
    	for(int i=1;i<7;i++){
    		picList.add(new ImageIcon("gamelayerstuff/coins/"+type+i+".png").getImage());
    	}
    }
    public void count(){
    	//increases the counter and mods it so that it doesn't go out of range
    	picCounter+=0.2;
    	picCounter = picCounter%picList.size();
    }
    public void moveTowards(Player p){
    	//When the magnet powerup is picked up, coins start moving towards the player.
    	//This is done by comparing the x and y values of the coin relative to the 
    	//x and y values of the player. The coin starts moving towards the player until
    	//it reaches the same x and y-positions as the player
    	if (p.getX()>x+3){
    		System.out.println(1);
    		x+=3;
    	}
    	else if (p.getX()<x-3){
    		System.out.println(2);
    		x-=3;
    	}
    	if (p.getY()>y+3){
    		System.out.println(3);
    		y+=3;
    	}
    	else if (p.getY()<y-3){
    		System.out.println(4);
    		y-=3;
    	}
    }
	public boolean checkCollision(Image ePic,Player p){
		//Draws a rectangle around the object and checks to see if the player-rectangle intersents
		//with the rectangle around the coin. If they are intersecting, return true. Otherwise, 
		//return false.
		coinRect= new Rectangle (x,y,ePic.getWidth(null),ePic.getHeight(null));
		if (coinRect.intersects(p.getRect())){
			return true;
		}else{
			return false;
		}
	}
	public void setDirection(){
		//The red coin and the small blue coin move. When they reach their maximum range, they
		//change direction.
		if(type.equals("redcoin")){
			if (x == xMax){	//checks to see if the redcoin has reached it's maximum range
				direction = -1;//goes backwards
			}
			else if (x == xPos){//checks to see if the coin has reached it's minumum range
				direction = 1;//goes normally
			}
		}
		else if(type.equals("bluecoin")){
			if (y == yMax){
				direction = -1;
			}
			else if (y == yPos){
				direction = 1;
			}
		}
	}
	public void move(){
		//The red coin and blue coins move.
		if(type.equals("redcoin")){
			x += direction;	//changes the position of the coin based on the direction
			y = yPos;//red coin doesn't move up or down
		}
		else if(type.equals("bluecoin")){
			y -= direction;	
			x = xPos;//blue coin only moves vertically
			if (Math.abs(yPos-y)>50){ //If the max ranges have been reached, change the direction
				direction*=-1;
			}else if (Math.abs(yPos-y)==0){
				direction*=-1;
			}
		}
	}
//-------------------------------------------------------------------------------------------------------------------------------------------------------
//Getters
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
    public boolean getOnScreen(){
    	//Returns true if the coin is on the screen and false if not
    	return onScreen;
    }
    public int getXPos(){
    	//Returns the x-value of the starting position
    	return xPos;
    }
    public int getPoints(){
    	//returns the point value of the coin
    	return points;
    }
    public int getYPos(){
    	//Returns the y-value of the starting position
    	return yPos;
    }
    public int getXMax(){
    	//Returns the x-value of the maximum position
    	return xMax;
    }
    public int getYMax(){
    	//Returns the y-value of the maximum position
    	return yMax;
    }
    public int getWidth(){
    	//returns the width of the image
		return picList.get((int)(picCounter)).getWidth(null);
	}
	public int getHeight(){
		//Returns the height of the image
		return picList.get((int)(picCounter)).getHeight(null);
	}
    public int getValue(){
    	//Returns the value of the coin
    	return value;
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
    public void setXPos(int x){
    	//takes an integer and that becomes the new x-pos
    	this.xPos = x;
    }
    public void setYPos(int y){
    	//tales an integer and that becomes the new y-pos
    	this.yPos = y;
    }
    public void setXMax(int x){
    	//takes an integer and that becomes the new x-max
    	this.xMax = x;
    }
    public void setYMax(int y){
    	//tales an integer and that becomes the new y-max
    	this.yMax = y;
    }
}