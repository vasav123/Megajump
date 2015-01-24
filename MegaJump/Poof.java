//Poof.java
//Vasav Shah, Kevin Shi
//Creates a poof object. Poofs appear after a coin, enemy, etc. dissappear. They are just for visual effects. Each enemy, coin, etc. 
//creates a different poof object with different visuals.
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Poof {
	private ArrayList <Image> pics;
	private String [] types= {"coin","enemy","purple","blue","green","red","orange"};
	private int x,y,type;
	private double count;
    public Poof(int x, int y, int type){
    	//Takes an x-value, a y-value, and a type as parameters.
    	//The x and y-values determine the position of the poof.
    	//The type is an integer used as the position in the arraylist "types" and used to decide the
    	//type of image that is drawn
    	this.x=x;
    	this.y=y;
    	this.type=type;
    	count=0;
    	pics= new ArrayList<Image>();
    	loadImage(types[type]);
    }
    public void loadImage(String t){
    	//Once the type is decided, goes through all the images for the poof and adds the images to the arraylist
    	for(int i=1 ; i<4 ; i++){
    		pics.add(new ImageIcon("gamelayerstuff/poofs/"+t+"poof"+i+".png").getImage());
    	}
    }
    public void count(){
    	//counter that is needed to go through the sprites.
    	count+=0.2;
    }
    public Image getImage(){
    	//Returns the image of the poof
    	return pics.get((int)count);
    }
    public int getX(){
    	//Returns the x-value
    	return x;
    }
    public int getY(){
    	//Returns the y-value
    	return y;
    }
    public int getHeight(){
		//Returns the height of the poof
		return pics.get((int)count).getHeight(null);
	}
	public int getWidth(){
		//Returns the width of the poof
		return pics.get((int)count).getWidth(null);
	}
    public void setX(int x){
    	//Takes an integer as a parameter and sets that as the x-value
    	this.x=x;
    }
    public void setY(int y){
    	//Takes an integer as a parameter and sets that as the y-value
    	this.y=y;
    }
    public void setType(int type){
    	//Takes a int and sets that as the type
    	this.type= type;
    }
    public boolean isDone(){
    	//Checks to make sure the poof effect is done. If it is, return true and false if otherwise
    	if (count>=pics.size()){
    		return true;
    	}
    	return false;
    }
}