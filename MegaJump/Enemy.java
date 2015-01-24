//Kevin Shi,Vasav Shah
/*This class is used for keeping track of the enemies. Diffrent enemies have diffrent type of movement and sprite animation. 
 *There fore using this as a class makes our code easier to keep track of.
 */
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Enemy{
	private String type;		//keeps track of the type of enemy it is
	private int num;			//will help with keepintrack of the type of enemy
	private int xPos,yPos,x,y,xMax,yMax,direction;			//variables for movement
	private boolean flip,onScreen;							// fliping the image and onSCreen helps with keepin track if its on the game screen
	private double picCounter;								//counter for sprite animation
	private ArrayList<Image> picList= new ArrayList<Image>();		//Holds all the images
	private Image pic;											//keeps track of the current image
	private Rectangle enemyRect; 								//for collision
	public Enemy(int x,int y, int num){
		this.xPos = x;
		this.yPos = y;
		this.x = x;
		this.y = y;
		this.num = num;
		
		pickType();
		picCounter = 0;
		onScreen=true;
		pic = picList.get(0);
		if (x>240){
			flip= true;
		}else{
			flip= false;
		}
	}
	public void pickType(){
		//int num = (int)(Math.random()*3);
		/*In this method using the number we load all the images and determine its movement
		*/	
		if (num == 0){
			type = "blue";
			for (int i=1; i<21; i++){
	    		picList.add(new ImageIcon("Badguys/bluemonster/blue"+i+".png").getImage());	
	    	}
	    	direction = 1;
			xMax = xPos+200;										//move right and left
			yMax = yPos;
		}
		else if (num == 1){
			type = "purple";
			for (int i=1; i<21; i++){
	    		picList.add(new ImageIcon("Badguys/purplemonster/purp"+i+".png").getImage());	
	    	}
	    	direction = 1;
			xMax = xPos;				
			yMax = yPos+200;											//move up and down
		}
		else if (num == 2){
			type = "red";
	    	for (int i=1; i<11; i++){
	    		picList.add(new ImageIcon("Badguys/redmonster/red"+i+".png").getImage());
	    	}
	    	direction = 1;												//don't move at all
			xMax = xPos;
			yMax = yPos;
		}
	}
	public void setDirection(){
		//using the direction movement, we set a variable about flip and then in the gAmePanel the images are flipped
		if(type.equals("blue")){
			if (x == xMax){
				direction = -1;
				flip=true;
			}
			else if (x == xPos){
				direction = 1;
				flip=false;
			}
		}
		else if(type.equals("purple")){
			if (y == yMax){
				direction = -1;
			}
			else if (y == yPos){
				direction = 1;
			}
		}
	}
	public void move(){
		/*This method keeps track of the direction the enemy moves in
		 *If the enemy reaches its max distance away from the original point, it should start moving back towards there
		 */
		if(type.equals("blue")){
			x += direction;
			y = yPos;
			if (Math.abs(xPos-x)>200){									//Max distance away from original
				direction*=-1;											//keeps track of the direction of movement
				flip = true;	
			}else if (Math.abs(xPos-x)==0){								//Min distance away from the original
				direction*=-1;
				flip = false;
			}
		}
		else if(type.equals("purple")){								//Max distance away from original
			y -= direction;
			x = xPos;
			if (Math.abs(yPos-y)>200){								//keeps track of the direction of movement
				direction*=-1;
			}else if (Math.abs(yPos-y)==0){							//Min distance away from the original
				direction*=-1;
			}
		}
		else if(type.equals("red")){
			x = xPos;
			y = yPos;
		}

	}
	public ArrayList<Image> getPics(){
		return picList;
	}
	public void count(){
		picCounter+=0.5;
		picCounter= picCounter%picList.size();
	}
//-------------------------------------------------------------------------------------------------------------------------------------------------------
//collision checking
	public boolean checkCollision(Image ePic,Player p){				
		/*Our collision is based upon Rect intersection
		 *If the player Rectangle intersects with the enemy rectangle then it returns true and depending on where it collided
		 *The Charcter loses coins or not
		 */
		enemyRect= new Rectangle(x,y,ePic.getWidth(null),ePic.getHeight(null));
		if (enemyRect.intersects(p.getRect())){
			return true;
		}else{
			return false;
		}
	}
//-------------------------------------------------------------------------------------------------------------------------------------------------------
//Getters
	public String getType(){
		return type;
	}
	public int getX(){
		return x;
	}
	public boolean getFlip(){
		return flip;
	}
	public int getY(){
		return y;
	}
	public int getXPos(){
		return xPos;
	}
	public int getYPos(){
		return yPos;
	}
	public int getXMax(){
		return xMax;
	}
	public int getYMax(){
		return yMax;
	}
	public int getWidth(){
		return picList.get((int)(picCounter)).getWidth(null);
	}
	public int getHeight(){
		return picList.get((int)(picCounter)).getHeight(null);
	}
	public int getCounter(){
		return (int)(picCounter);
	}
	public boolean getOnScreen(){
		return onScreen;
	}
	
//-------------------------------------------------------------------------------------------------------------------------------------------------------
//Setters
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
		if(y>2000){
			onScreen=false;	
		}
	}
	public void setYPos(int y){
		yPos = y;
	}
	public void setXPos(int x){
		xPos = x;
	}
	public void setYMax(int y){
		yMax = y;
	}
	public void setXMax(int x){
		xMax = x;
	}
//-------------------------------------------------------------------------------------------------------------------------------------------------------

}