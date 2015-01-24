//Player.java
//Vasav Shah,Kevin Shi
//Creates a player object

import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Player{
	private int x,y,middle,counter;	
	private String name,power;
	private ArrayList<ArrayList<Image>>picList = new ArrayList<ArrayList<Image>>();
	private ArrayList<Image> balloonPics,downPics,fallPics,flyLeftPics,flyRightPics,upPics;	//Arraylists of images as the player 
																							//performs certain actions
	private Image nowPic,standing;
	private boolean doneAni;
	private int coutner,max,min;
	private double accel,velocity,deathcounter;
	private double piccount=0;
	private boolean ground,down,invisible;
	
    public Player(int x, int y, double v, String n) {
    	//Takes a x-value, a y-value, a velocity, and a name in the constructor.
    	this.x=x;
    	this.y=y;
    	name=n;
    	balloonPics = new ArrayList<Image>();
    	downPics = new ArrayList<Image>();
    	fallPics = new ArrayList<Image>();
    	flyLeftPics = new ArrayList<Image>();
    	flyRightPics = new ArrayList<Image>();
    	upPics = new ArrayList<Image>();
    	loadSprites();
    	nowPic = new ImageIcon("characters/"+name+"/"+name+"35.png").getImage();
    	ground = true;
    	counter=0;
    	deathcounter=0;   	
    	velocity= v;
    	accel = -0.01; 
    	down=false;
    	max=50;
    	min=-20;
    	power="";
    }
    public void resetPicCounter(){piccount=0;}//Changes the counter to 0
    public void loadSprites(){
    	//Loads the sprites for the character based on character name. The sprites are added into seperate arrays
    	//based on the actions the user can perform.
    	//These arrays are then added into a single array.
    	for(int i=1; i<5; i++){
    		balloonPics.add(new ImageIcon("Characters/"+name+"/balloon/"+name+i+".png").getImage());	
    	}
    	for(int i=12; i<16; i++){
    		downPics.add(new ImageIcon("Characters/"+name+"/down/"+name+i+".png").getImage());
    	}
    	for(int i=1; i<9; i++){
    		fallPics.add( new ImageIcon("Characters/"+name+"/fall/"+name+i+".png").getImage());
    	}
    	for(int i=1; i<3; i++){
    		flyLeftPics.add(new ImageIcon("Characters/"+name+"/flyleft/"+name+i+".png").getImage());
    	}
    	for(int i=1; i<3; i++){
    		flyRightPics.add(new ImageIcon("Characters/"+name+"/flyright/"+name+i+".png").getImage());
    	}
    	for (int i=1;i<8;i++){
    		upPics.add(new ImageIcon("Characters/"+name+"/down/"+name+i+".png").getImage());
    	}
    	standing=new ImageIcon("Characters/"+name+"/"+name+"35.png").getImage();
    	picList.add(balloonPics);
    	picList.add(downPics);
    	picList.add(fallPics);
    	picList.add(flyLeftPics);
    	picList.add(flyRightPics);
    	picList.add(upPics);
    	
    }
    public void resetCounter(){
    	//helps with accelerating and deaccelrating the side movement
    	counter=0;
    }
    public Image getImage(ArrayList<Image> moveList, int c){
    	// if you reached the end of the animation you just return the last or else you return the the pics within the animation
		if (c < moveList.size()){
			doneAni=false;
			return moveList.get(c);
		}else{
			doneAni=true;
			return moveList.get(moveList.size()-1);
		}
    }
    public void managePow(){
    	//keeps track of the powerups and its effect on the player
    	if(power.equals("Umbrella")){
    		accel=-0.25;			//you becmoe lighter
    	}else if(power.equals("Ball")){
    		accel=-2;				//you become heavier
    	}else if(power.equals("Boost")){
    		velocity=150;			//you go faster
    		accel=-1;
    		invisible=true;
    	}else if(power.equals("Cloud")){
    		velocity=0;				//removes all powerups
    		accel=-1;
    		power="";
    		invisible=false;
    	}else if (power.equals("Balloon")){
    		accel=0;				//you become lighter and bigger in size so its easier to collect coins
    		velocity=50;
    		invisible=true;
    	}else if (power.equals("Sheild")){	
    		invisible=true;			//makes you invisble
    	}
    	else{
    		accel=-1;			//removes all powerups (no powerup effect is active)
    		invisible=false;
    	}
    }
    public Image managePic(String imageType){
		if (imageType.equals("balloon")){			// just manages the picture the current animation the player is in
			nowPic = getImage(picList.get(0),(int)piccount);
		}else if (imageType.equals("down")){
			nowPic = getImage(picList.get(1),(int)piccount);
		}else if (imageType.equals("fall")){
			nowPic = getImage(picList.get(2),(int)deathcounter);
		}else if (imageType.equals("fleft")){
			nowPic = getImage(picList.get(3),(int)piccount);
		}else if (imageType.equals("fright")){
			nowPic = getImage(picList.get(4),(int)piccount);
		}else if (imageType.equals("up")){
			nowPic = getImage(picList.get(5),(int)piccount);
		}else {
			nowPic=standing;
		}return nowPic;
    }
    public Image move(int side){
    	if (velocity==0){		// ones the star has lost its speead then iten you're not invisble anymore
    		invisible=false;
    	}
    	if (side == 1){
    		counter+=3;				//counter is practically the acceleration
    		if (counter>=300){
    			counter=300;		//max acceleration
    		}
    		x+=(int)(counter*0.05);		//moves the player right 
    		piccount+=0.1;
    		if (power.equals("Balloon")){		//if you're in the balloon powerup no need to use diffrent animation
    			return managePic("balloon");
    		}
    		return managePic("fright");
    		
    	}
    	else if (side == -1){			//same thing as above but just for left side
    		counter-=3;
    		if (counter<=-300){
    			counter=-300;
    		}
    		x+=(int)(counter*0.05);
    		piccount+=0.1;
    		if (power.equals("Balloon")){
    			return managePic("balloon");
    		}
    		return managePic("fleft");
    	}
    	else if(side==2){				//once the player is dead the death animation should be played
    		deathcounter+=0.1;
    		return managePic("fall");
    	}
    	else if(velocity<0){			//if velocity is negative it means you're going down
    		piccount+=0.1;
    		return managePic("down");
    	}
    	else if (velocity>=10){		//just means you're going up and the up animation needs to be played
     		piccount+=0.1;
    		if (power.equals("Balloon")){///no need for up animation if the player is in a balloon powerup
    			return managePic("balloon");
    		}
    		return managePic("up");	
    	}
    	else{
    		return managePic("still");
    	}
    }
	public Image getPic(){
		return nowPic;
	}

//----------------------------------------------------------------------------------------------------------------------------------------
//getters
	public Rectangle getRect(){
		return new Rectangle(x,y,nowPic.getWidth(null),nowPic.getHeight(null));
	}
	public String getPower(){
		if (power.equals("")){
			return "";
		}
		return power;
	}
	public double getVelocity(){
		return velocity;
	}
	public boolean getInvi(){
		return invisible;
	}
    public String getName(){
    	return name;
    }
    public int getX(){x=(x>479)? -getWidth():x; x=(x+getWidth()<0)? 479:x; return x;}
    public int getY(){
    	return y;
    }
    public int getWidth(){
    	return nowPic.getWidth(null);
    }
    public int getHeight(){
    	return nowPic.getHeight(null);
    }
    public int getMid(){
    	return x+(nowPic.getWidth(null)/2);
    }
    public void onGround(){
    	ground = true;
    }
    public void NonGround(){
    	ground = false;
    }
    public boolean getGround(){
    	return ground;
    }
    public boolean getDown(){
    	return down;
    }
    public boolean animationComplete(){
    	return doneAni;
    }
//----------------------------------------------------------------------------------------------------------------------------------------
//setters
	public void setAccel(double a){
		accel = -1;
	}
	public void setPower(String pow){
		power=pow;
		managePow();
	}
	public void setInvi(boolean i){
		invisible=i;
	}
	public void changeVelocity(){
		velocity+=accel;
		if (velocity<min){
			velocity = min;
		}
	//	if (velocity>max){
	//		velocity=max;
	//		down=true;
	//	}
	}
	public void setX(int x){
    	this.x = x;
    }
    public void setY(int y){
    	this.y = y;
    }
    public void setDown(boolean d){
    	down=d;
    }
    public void setVelo(double v){
    	if (v>velocity){
    		velocity=v;
    	}
    }
    public void setSpikeVelo(){
    	velocity=-10;
    }
    public void setMax(int m){
    	velocity=m;
    }
    public void setMin(int m){
    	min=m;
    }
}
