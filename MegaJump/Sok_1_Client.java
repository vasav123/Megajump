/**
 * (#)Sok_1_Client.java
* *
 * @author 
 * @version 1.00 2014/4/16
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class Sok_1_Client {

    public static void main(String [] args) throws Exception {
    	Socket sock = new Socket ("192.168.1.135",777);
    	PrintStream ps= new PrintStream (sock.getOutputStream());
    	InputStreamReader in;
    	BufferedReader bf;
    	String sendMessage="";
    	Scanner kb = new Scanner (System.in);
    	while (true){
    		if (kb.hasNextLine()){
    			sendMessage = kb.nextLine();
    			System.out.println(true);
    			ps.println(sendMessage);	
    		}
    		
		   	if (sendMessage.equals("0")){
    			break;
    		}
    		
			System.out.println(sock.getInputStream());
    	    in = new InputStreamReader (sock.getInputStream());
    	    System.out.println("stuch here1");
    		bf = new BufferedReader (in);
    		System.out.println("stuch here2");
    	   	String message = bf.readLine();
			if (!message.equals("")){
				System.out.println(message);
			}
    			
		
    	}


    }
    
    
}