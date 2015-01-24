import java.util.*;
import java.io.*;

class TestCoinPattern extends JFrame{
	int x = 0;
	public FrameEg1(){
		super("Basic Frame");	//super(" ") calls the superclass constructor
								//the string will set the title
		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	public void paint(Graphics g){	//<-this replaces JFrame kinda like method overload
									//we replace the paint that we inherited from JFrame
		g.setColor(Color.WHITE);
		g.fillRect(0,0,getWidth(),getHeight());
		g.setColor(Color.RED);
		g.drawLine(100,50,300,100);
		x+=1;
		g.drawString(""+x,50,200);
	}
	public static void main(String[]args){
		Frame frame = new FrameEg1();
	}
	public void readPattern(){
		Scanner inFile=null;
	   	try{
	   		inFile=new Scanner (new BufferedReader (new FileReader("coin texts/pattern1.txt")));
	   	}
	   	catch(IOException ex){}
	   	int numCoin = Integer.parseInt(inFile.nextLine());
	   	
	   	for(int i=1; i<numCars+1; i++){
	}
}