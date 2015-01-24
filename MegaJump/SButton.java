//SButton.java
//Vasav Shah, Kevin Shi
//Creates an object that serves as our buttons, because JButtons are ugly.
//It works by loading images of buttons and drawing them. It looks to see if the mouse is
//hovering over the buttons or not or if the mouse is being clicked while hovering over the
//button.


import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.Rectangle;


public class SButton {
	
	private Image pic,pic2;
	private int x,y;
	String t,name,screen;
	
    public SButton(int x, int y, String name, String screen) {
    	//The x and y determine the position of the button
    	//The name determines which image should be loaded
    	//The screen is used to decide whiich screen the button leads to
    	this.x = x;
    	this.y = y;
    	this.t = name;
    	this.name = name;
    	this.screen = screen;
    	
    	pic = new ImageIcon("buttons/"+name+".png").getImage();	//<-Image when not hovered over
    	pic2= new ImageIcon("buttons/"+name+"2.png").getImage();//<-Image when hovered over
    }
    public Image getPic(int mx, int my){
    	//Takes the x and y values of the mouse and checks to see if the mouse is hovering over the button.
    	//If the button is hovering over the button, return the second image (hovered button) and if not, return
    	//the regular image.
    	Rectangle rect =new Rectangle(x,y,pic.getWidth(null),pic.getHeight(null));
    	if (rect.contains(mx,my)){
    		return pic2;
    	} 
    	else{
    		return pic;
    	}	
    }
    public boolean clicked(int mx,int my, boolean clicked){
    	//Takes the x and y values of the mouse as well as a boolean. The boolean clicked keeps track of if the
    	//mouse has been clicked or not (true if clicked and false if not). Clicked is altered in the gamePanel and
    	//mainMenu classes. If the mx and my are within the boudaries of the rectangle created by the button and clicked,
    	//then return true. This method is used to check if the button has been clicked or not.
    	Rectangle r=new Rectangle(x,y,pic.getWidth(null),pic.getHeight(null));//<hovered over and clicked
    	if (r.contains(mx,my) && clicked){
    		return true;
    	}
    	else if (r.contains(mx,my)){//<Hovered over
    		return false; 
    		
    	}
    	else{
    		return false;//this is normal
    	}	    	
    }
    public boolean checkScreen(){
    	//Checks to see if the button leads to a screen. Return false if it doesn't
    	if(screen.equals("")){
    		return false;
    	}
    	else{
    		return true;
    	}
    }
//Getters
    public String getScreen(){
    	return screen;
    }
    public int getX(){
    	return x;
    }
    public int getY(){
    	return y;
    }
    public Rectangle getRect(){
    	//Creates a rectangle object at the dimenstions of the button and returns it
    	return new Rectangle(x,y,pic.getWidth(null),pic.getHeight(null));
    }
    public String getType(){
    	return t;
    } 
    
    
}