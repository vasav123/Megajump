//Box.java
//Vasav Shah, Kevin Shi
//Creates a box object with a type, x-value, y-value, p-count. It takes the x-value, y-value, and backy in the constructor. 
//The x and y values determine the position of the box and the backy determines what type the box should be. 
//The p-count tracks the number of times the box can be hit again, and also indicates which image should be drawn. 
//IT also contains a rectangle object used to keep track of collisions.

import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Box {
	private int pcount,xPos,yPos;
	private String boxType;	//the type of the box - determines which image should be loaded and drawn
	private Rectangle boxRect;	//A box drawn around the box object that is used for collision
	private ArrayList <Image> boxImages = new ArrayList<Image>();	//a list containing all the images
	public boolean onScreen;		//keeps track of if the box is on the screen or not
    public Box(int x,int y,int by){
    	xPos=x;
    	yPos=y;
    	onScreen=true;//keeps track of if the box can be seen on the jpanel or not
    	pcount=2;//keeps track of how many hits the box has left
    	
    	//The type of box is chosen based on the position of the box. The type of box is determined by the backy values.
  		//by is equal to the backy of the game.
    	if (by<=3796 && by>=0){	//This range is grassy levels
    		boxType="wood";
    	}
    	else if(by>3796 && by<=7613){	//this range is desert levels
    		boxType="stone";
    	}
    	else if(by>7613 && by<=18406){ //this range is sky levels
    		boxType="green";
    	}
    	else{	//the rest are space levels
    		boxType="steel";    		
    	}
    	loadImages();
    	
    }
	public boolean checkCollision(Image ePic,Player p){
		//checks to see if the player collides with this object
		if (getImage()!=null){
			boxRect= new Rectangle (xPos,yPos,ePic.getWidth(null),1);
			if (boxRect.intersects(p.getRect()) && p.getVelocity()<0){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
    public void loadImages(){
    	///loads the pictures based on the type of the box
    	for (int i = 1; i<3;i++){
    		boxImages.add(new ImageIcon("gamelayerstuff/boxes/"+boxType+i+".png").getImage());
    	}
    }
//------------------------------------------------------------------------------------------------------------------------------------
//Getters
	public int getX(){
		//Returns x-value of box
    	return xPos;
    }
    
    public int getY(){
    	//Returns the y-value of box
    	return yPos;
    }
    
    public Image getImage(){
    	//returns the image of the box
    	if (pcount==0){//if pcount is 0, then that means the box has been hit twice already and then no image should be drawn
    		return null;
    	}
    	return boxImages.get(pcount-1);
    }
    
    public int getHeight(){
    	//returns the height of the box
    	if (pcount==0){
    		return 0;
    	}
    	return boxImages.get(pcount-1).getHeight(null);
    }
    
    public int getWidth(){
    	//returns the width of the box by getting the width of the image that is drawn
    	if (pcount==0){
    		return 0;
    	}
    	return boxImages.get(pcount-1).getWidth(null);
    }
    
    public int getPcount(){
    	//returns the position of the image that should be drawn
    	return pcount;
    }
    public boolean getOnScreen(){
    	//returns if the object fits on the panel
    	return onScreen;
    }
//------------------------------------------------------------------------------------------------------------------------------------    
    public void setX(int x){
    	//takes an integer as a parameter and sets that as the x-value
    	xPos=x;
    }
    public void setY(int y){
    	//takes an integer as a parameter and sets that as the y-value
    	yPos=y;
    	if (y>1000){	//if the new value set is over 1000, then its not on the screeen anymore and will be removed
    		onScreen=false;
    	}
    }
    public void setPcount(int p){
    	//tracks which picture is being blitted
    	pcount=p;
    	if (pcount<=0){
    		pcount=0;
    	}
    }
}