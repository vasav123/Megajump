/**
 *Kevin Shi, Vasav Shah
 *This class is used to keep track of the jumper objects.
 *This class loads diffrent types of jumpers according to the level the palyer is on
 */

import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Jumper {
	private int x,y,dist;			//x,y positions
	private double picCounter;		//helps with sprites
	private String type;	//keeps track of which type of jumper to load
	private Rectangle jumperRect;				//used for collision
	private ArrayList<Image> picList = new ArrayList<Image>();	//list holding all the sprites for the enemy type
	private boolean onScreen,collision;			//onScreen keeps track of when to delete the jumper
	
    public Jumper(int x, int y, int by) {
    	this.x = x;
    	this.y = y;
		if (by<=3796 && by>=0){
    		type="wood";					//depedning on the background the jumpers spawing image changes
    	}
    	else if(by>3796 && by<=7613){
    		type="stone";
    	}
    	else if(by>7613 && by<=18406){
    		type="green";
    	}
    	else{
    		type="steel";    		
    	}
    	loadImage();
    	picCounter = 0;
    	onScreen= true;
    	collision=false;
    }
    public void loadImage(){
    	//loads the sprites for the type of enemy and then adds them to the arraylist
    	for(int i=1;i<4;i++){
    		picList.add(new ImageIcon("gamelayerstuff/jumpers/"+type+i+".png").getImage());
    	}
    	for(int i=3;i>0;i--){
    		picList.add(new ImageIcon("gamelayerstuff/jumpers/"+type+i+".png").getImage());
    	}
    }
	public boolean checkCollision(Image ePic,Player p){
		//Keeps track of the Collsion  if the player clloides while going down meaning its on the top you return true
		jumperRect= new Rectangle (x,y,ePic.getWidth(null),1);
		if (jumperRect.intersects(p.getRect()) && p.getVelocity()<0){
			collision=true;
			return true;
		}else{
			return false;
		}
	}
//--------------------------------------------------------------------------------------------------------
//getters
	public Image getImage(){
		if (collision){
			picCounter+=0.03;
		}
		if (picCounter>=picList.size()){
			collision=false;
			picCounter=0;
		}
		return picList.get((int)(picCounter));
	}
    public int getHeight(){
    	return getImage().getHeight(null);
    }
    public int getWidth(){
    	return getImage().getWidth(null);
    }
    public int getX(){
    	return x;
    }
    public int getY(){
    	return y;
    }
    public int getPcount(){
    	return (int)picCounter;
    }
    public boolean getOnScreen(){
    	return onScreen;
    }
//--------------------------------------------------------------------------------------------------------
//setters
    public void setX(int x){
    	this.x=x;
    }
    public void setY(int y){
    	this.y=y;
    	if (y>1000){
    		onScreen=false;
    	}
    }
}