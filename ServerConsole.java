import java.util.Scanner;

import client.ChatClient;
import common.ChatIF;

import java.io.*;

public class ServerConsole implements ChatIF{
	
	//Class variables *************************************************
	  
	  /**
	   * The default port to connect on.
	   */
	  final public static int DEFAULT_PORT = 5555;
	  
	  //Instance variables **********************************************
	  
	  /**
	   * The instance of the client that created this ConsoleChat.
	   */
	  EchoServer clientOnServer;
	  
	  
	  
	  /**
	   * Scanner to read from the console
	   */
	  Scanner fromServer; 
	
	public ServerConsole(int port) {
		
		clientOnServer = new EchoServer(port);
        

        try {
        	clientOnServer.listen(); 

        } catch (Exception ex) {
            System.out.println("ERROR - Could not listen for clients!");
        }
		
	}
	

	public void accept() {
		String message;
        fromServer = new Scanner(System.in);

        try {
            while (true) {
                message = fromServer.nextLine();
                clientOnServer.handleMessageFromClientOnServer(message);
            }
        } catch (Exception ex) {
            System.out.println("Unexpected error!");
        }
	}
	/**
	   * This method overrides the method in the ChatIF interface.  It
	   * displays a message onto the screen.
	   *
	   * @param message The string to be displayed.
	   */
	  public void display(String message) 
	  {
		  System.out.println("SERVER MSG> " + message);
	  }
	  
	  public static void main(String[] args) 
	  {
	    int port = 0; //Port to listen on

	    try
	    {
	      port = Integer.parseInt(args[0]); //Get port from command line
	    }
	    catch(Throwable t)
	    {
	      port = DEFAULT_PORT; //Set port to 5555
	    }
	    ServerConsole chat = new ServerConsole(port);
        chat.accept();
	   
	  }
}
