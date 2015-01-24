
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class CoinPattern{
	String name;
	int x, y;
	int cnum;
	ArrayList<Coin> cList = new ArrayList<Coin>();
	public CoinPattern(int x, int y, int pattern){
		this.x = x;
		this.y = y;
		this.name = "pattern"+pattern;
		readPattern();
	}
	public void readPattern(){
		Scanner inFile=null;
	   	try{
	   		inFile=new Scanner (new BufferedReader (new FileReader("coin texts/"+name+".txt")));
	   	}
	   	catch(IOException ex){}
	   	int numCoin = Integer.parseInt(inFile.nextLine());
	   	cnum = numCoin;
	   	
	   	for(int i=1; i<numCoin+1; i++){
	   		String[] line = inFile.nextLine().split(",");
	   		Coin coin = new Coin(Integer.parseInt(line[0])+x,Integer.parseInt(line[1])+y,Integer.parseInt(line[2]));
	   		cList.add(coin);
	   	}
	}
	public ArrayList<Coin> getCoins(){
		return cList;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
}