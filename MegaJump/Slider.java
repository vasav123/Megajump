/**
 *Kevin Shi,Vasav Shah
 *This class helps with scroll movement of any image.
 *This is class is practically made so that it can be used anywhere
 */


public class Slider {
	private int x,y;
    public Slider(int x, int y){
    	this.x=x;
    	this.y=y;
    }
    public void moveDown(int yammount){	
    	y+=yammount;					//just moves down
    	if (y>=545){				//everything is scaled to 545 because thats what the scroll bar is that big. 
    		y=545;					//if its the max distance you can't move down anymore
    	}
    }
    public void moveUp(int yammount){
    	y-=yammount;				//just moves up
    	if (y<=0){					//if its the mid distance you can't move up anymore
    		y=0;
    	}
    }
    public int move(int height){
		return (int)(y*(height-600)/582);			//just moves the whole image
    }
//getters and reseters
    public int getX(){
    	return x;
    }
    public int getY(){
    	return y;
    }
    public void reset(){
    	y=0;
    }
}