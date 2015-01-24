//Spikes.java
//Vasav Shah, Kevin Shi
//
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Spikes {
	private int xPos,yPos;
	private String type;
	private Rectangle spikeRect;
	private Image spikeImage;
	public boolean onScreen;
    public Spikes(int x,int y,int by){
    	xPos=x;
    	yPos=y;
    	onScreen=true;
    	if (by<=3796 && by>=0){
    		type="wood";
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
    	loadImages();
    }
	public boolean checkCollision(Image ePic,Player p){
		if (getImage()!=null){
			spikeRect= new Rectangle (xPos,yPos,getWidth(),getHeight());
			if (spikeRect.intersects(p.getRect())){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
    public void loadImages(){
    	spikeImage= new ImageIcon("gamelayerstuff/spikes/"+type+".png").getImage();
    }
    public Image getImage(){
    	return spikeImage;
    }
    public int getHeight(){
    	return spikeImage.getHeight(null);
    }
    public int getWidth(){
    	return spikeImage.getWidth(null);
    }
    public int getX(){
    	return xPos;
    }
    public int getY(){
    	return yPos;
    }
    public boolean getOnScreen(){
    	return onScreen;
    }
    public void setX(int x){
    	xPos=x;
    }
    public void setY(int y){
    	yPos=y;
    	if (y>1000){
    		onScreen=false;
    	}
    }
}