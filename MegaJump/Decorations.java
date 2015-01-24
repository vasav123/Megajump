//Decorations.java
//Kevin Shi, Vasav Shah
//Creates decoration objects. The are a part of the 2nd layer. They are not meant to be collided with. It loads all
//possible decoration images and selects a set of images (based on level, which is determined by backy), and randomly
//picks one from there.

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Decorations {
	private ArrayList<Image> grasslvl= new ArrayList<Image>();;	//Arraylists containing images based on level
	private ArrayList<Image> desertlvl= new ArrayList<Image>();;
	private ArrayList<Image> skylvl= new ArrayList<Image>();;
	private ArrayList<Image> spacelvl= new ArrayList<Image>();;
	boolean flip;
	Image pic;
	private int ytop,ybot;
	private String lvl;
    public Decorations(int y, int by, boolean flip) {
    	//The parameters are the y-position, the background y-value, and if it should be flipped or not.
    	lvl=getLvl(by); 
    	loadImages();
    	//Radomly selects an image and then the image is stored as the picture
    	pic = pickImage();
    	this.flip = flip;
    	setY(y);		
    }
    public String getLvl(int by){
    	//The level of the game is determined by where the decoration is created. This is 
    	//determined by the background-y. When the by is within a range of numbers, then it changes
    	//the type of decoration created.
    	//There are 4 levels
    	by = by + 600;
    	if (by>=0 && by<=3840){
    		return "grass";
    	}else if (by>3840 && by<=8520){
    		return "desert";
    	}else if (by>8520 && by<=14664){
    		return "sky";
    	}else if (by>14664 && by<=22344){
    		return "space";
    	}else{
    		return null;//<--so game doesnt crash
    	}
    }
    public boolean isFlipped(){
    	//Checks to see if the image is flipped or not
    	//This is used in the gamepanel class to flip the image
    	return flip;
    }
    
    public ArrayList<Image> getLvl(){
    	//Based on the type of decoration, a specific arraylist is returned
    	if (lvl.equals("grass")){
    		return grasslvl;
    	}else if (lvl.equals("desert")){
    		return desertlvl;
    	}else if (lvl.equals("sky")){
    		return skylvl;
    	}else if (lvl.equals("space")){
    		return spacelvl;
    	}else{
    		return null;
    	}
    }
    public void setY(int y){
    	//Sets the bottom value of the decoration as well as the top value.
    	if (lvl!=null){
	    	ytop=y;
	    	ybot=ytop-pic.getHeight(null);    		
    	}
    }
    public Image pickImage(){
    	//Goes through the arraylist selected by "getLvl()" and randomly returns an image from the
    	//arraylist.
    	if (lvl!=null){
	    	int pos= (int) (Math.random()*getLvl().size());
	    	return getLvl().get(pos);
       	}
		else{
			return null;
		}
    }
    public Image getImage(){
    	return pic;
    }
    public int getYBot(){	//gets bottom height of picture
    	return ybot;
    }
    public int getYTop(){	//gets top height of picture
    	return ytop;
    }
    public void loadImages(){
    	//Loads all the decoration images, regardless of level, and adds them to the arraylists of
    	//pictures
    	for (int i=1;i<4;i++){	//Loops to call pictures
    		desertlvl.add(new ImageIcon("Deco/desert"+i+".png").getImage());
    	}
    	for (int i=1;i<6;i++){
    		grasslvl.add(new ImageIcon("Deco/grass"+i+".png").getImage());
    	}
    	for (int i=1;i<6;i++){
    		skylvl.add(new ImageIcon("Deco/sky"+i+".png").getImage());
    	}
    	for (int i=1;i<7;i++){
    		spacelvl.add(new ImageIcon("Deco/space"+i+".png").getImage());
    	}
    }
    public String toString(){
    	return ytop+" "+ybot;
    }
}