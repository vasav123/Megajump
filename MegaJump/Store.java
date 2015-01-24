/**
 * @(#)Store.java
 *
 *
 * @author 
 * @version 1.00 2014/6/10
 */
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Store {
	private int money,count,count2;
	private ArrayList<String> chars = new ArrayList<String>();
	private ArrayList<String> powerUps = new ArrayList<String>();
	private ArrayList <String> stats = new ArrayList<String>();
	private ArrayList<String> allPup= new ArrayList<String>(Arrays.asList("Boost","Balloon","Magnet","Lucky","Sheild","Umbrella"));
	private ArrayList<String> allChars= new ArrayList<String>((Arrays.asList("android","bluto","dizzy","redford","redfridge","sheldon")));
	private int [] charPrices = {400,200,500,1000,200,100};//this are just random values
	private int [] powerPrices={400,300,200,100,200,400};
	private ArrayList <Image> charPics= new ArrayList<Image>();
	private ArrayList <Image> powerPics = new ArrayList <Image>();
	PrintWriter outFile;
	
	private Image displayPanel = new ImageIcon("Store/displayPanel.png").getImage();
    public Store(String type){
    	readFile("Stats");
    	manageStuff();
    	for (int i=0; i<allChars.size();i++){
    		charPics.add(new ImageIcon("Store/"+allChars.get(i)+"Display.png").getImage());
    	}
    	for (int i=0; i<allChars.size();i++){
    		powerPics.add(new ImageIcon("Store/"+allPup.get(i)+"Display.png").getImage());
    	}
    	System.out.println(charPics.size());
    	count=0;
    	count2=0;
    	System.out.println(money);
    	System.out.println(chars);
    	System.out.println(powerUps);
    	System.out.println(stats);
    }
    public void updateFile(){
		//writes the highscore
		try{
			outFile=new PrintWriter(new BufferedWriter(new FileWriter("stats.txt")));
			outFile.println(""+money);
			outFile.println(""+powerUps.size());
			for(int i=0;i<powerUps.size();i++){
				outFile.println(""+powerUps.get(i));		//writes the highscore in the file
			}
			outFile.println(""+chars.size());
			for(int i=0;i<chars.size();i++){
				outFile.println(""+chars.get(i));
			}
			outFile.println(stats.get(stats.size()-3));
			outFile.println(stats.get(stats.size()-2));
			outFile.println(stats.get(stats.size()-1));
			outFile.close();
		}
		catch(IOException ex){
			System.out.println("yooo stop noobing out");
		}

    }
	public void readFile(String name){			//reads the file and puts everything in the highscore list
		Scanner inFile=null;
    	try{
    		inFile=new Scanner (new BufferedReader (new FileReader(name+".txt")));   	
	    	while (inFile.hasNextLine()){
	    			stats.add(inFile.nextLine());
	    	}
    	}
    	catch(IOException ex){
    		System.out.println("Did you forget to make the"+name+".txt file?");
    	}
	}
	public void manageStuff(){
		int c=0;
		money= Integer.parseInt(stats.get(c));
		c++;
		for (int i = 0; i<Integer.parseInt(stats.get(1));i++){
			powerUps.add(stats.get(i+2));
			c++;
		}
		c++;
		System.out.println(Integer.parseInt(stats.get(1))+1);
		System.out.println(c);
		for(int i = c+1;i<c+Integer.parseInt(stats.get(c))+1;i++){
			chars.add(stats.get(i));
		}
	}
	public void buy(String type){
		if (type.equals("char")){
			if(getCharLocked() && money > charPrices[count]){
				chars.add(allChars.get(count));
				money-=charPrices[count];
			}
		}else if (type.equals("powerup")){
			if(getPowerLocked() && money > powerPrices[count2]){
				powerUps.add(allPup.get(count2));
				money-=powerPrices[count2];
			}
		}
	}
	public Image getCharPic(){
		return charPics.get(count);
	}
	public Image getPowerPic(){
		return powerPics.get(count2);
	}
	public void goRight(){
		count++;
		count2++;
		if (count>=charPics.size()){
			count=0;
		}
		if (count2>=powerPics.size()){
			count2=0;
		}
	}
	public void goLeft(){
		count--;
		count2--;
		if (count<0){
			count=charPics.size()-1;
		}
		if (count2<0){
			count2=powerPics.size()-1;
		}
	}
	public boolean getCharLocked(){
		if(chars.contains(allChars.get(count))){
			return false;
		}
		else{
			return true;
		}
	}
	public boolean getPowerLocked(){
		if(powerUps.contains(allPup.get(count2))){
			return false;
		}
		else{
			return true;
		}
	}
	public int getMoney(){
		return money;
	}
	public int getCharPrice(){
		return charPrices[count];
	}
	public int getPowerUpPrice(){
		return powerPrices[count2];
	}
}