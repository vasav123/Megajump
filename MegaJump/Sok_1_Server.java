/**
 * @(#)Sok_1_Server.java
 *
 *
 * @author 
 * @version 1.00 2014/4/16
 */
 import java.io.*;
 import java.net.*;
import java.util.*;

public class Sok_1_Server {

    public static void main(String[] args) throws Exception {
       ServerSocket servsock= new ServerSocket (777);
       Scanner kb= new Scanner (System.in);
       Socket sock = servsock.accept();
       String word="";
       while (true){
           InputStreamReader in = new InputStreamReader(sock.getInputStream());
	       BufferedReader bf = new BufferedReader (in);
	       String message = bf.readLine();
	       System.out.println(message);
	       if (kb.hasNextLine()){
	       		word = kb.nextLine();
	       }
	       
	       PrintStream ps = new PrintStream (sock.getOutputStream());
	       if (!word.equals("")){
	       		ps.println(word);
	       }else{
	       		ps.println("");
	       }
	       		
	       if (word.equals("0")){
	       	   ps.flush();
    		   ps.close();
	       	   servsock.close();
	       	   break;

	       }
       }

    }
}
