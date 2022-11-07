// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 


import java.io.IOException;

import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient (Object msg, ConnectionToClient client)
  {
	  String msgString = (String) msg;
      String[] messageArray =  msgString.split(" ");
      String loginId = null;


      //loginID has logged on
      if(messageArray.length == 4) {
    	  
          if((messageArray[2]).equals("logged")) {
              loginId = messageArray[0];
              
              client.setInfo("loginId", loginId);
              
              System.out.println("Message received: #login " + loginId + " from " + client.getInfo("loginId"));
              System.out.println(client.getInfo("loginId") + " has logged on.");
              this.sendToAllClients(msg);
          }
      }

      else if (msg.equals("Client Already logged in.")) {
          try { 
              client.close();
          } catch (IOException e) {
          }
      }

      else {
          System.out.println("Message received: " + msgString + " from " + client.getInfo("loginId"));
          
          this.sendToAllClients(client.getInfo("loginId") + "> " +  msgString);
      }
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  /**
   * Hook method called each time a new client connection is
   * accepted. The default implementation does nothing.
   * @param client the connection connected to the client.
   */
  protected void clientConnected(ConnectionToClient client) {
	  System.out.println("A new client has been connected");
  }

  /**
   * Hook method called each time a client disconnects.
   * The default implementation does nothing. The method
   * may be overridden by subclasses but should remains synchronized.
   *
   * @param client the connection with the client.
   */
  synchronized protected void clientDisconnected(
    ConnectionToClient client) {
	  System.out.println(client.getInfo("loginId") + " has disconnected.");
  }

  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */


public void handleMessageFromClientOnServer(String message) {
	// TODO Auto-generated method stub
	try {

        if (message.startsWith("#")) {
            handleTheCommand(message);
        }else {
            System.out.println("SERVER MSG> " + message);
            sendToAllClients("SERVER MSG> " + message);
        }

    } catch (IOException e) {
        System.out.println("Could not send message to server.  Terminating client.");
    }
	
	
	
}


private void handleTheCommand(String message) throws IOException{
	// TODO Auto-generated method stub
	String[] messageArray = message.toLowerCase().trim().split(" ");

    switch (messageArray[0]) {
    case "#start":
        if(!isListening()) {
            listen();
        }else {
            System.out.println("The server is already listening to connections.");
        }
        break; 


    case "#stop":
        stopListening();
        break;

    case "#close":
        close();
        break;

    case "#getport":
        getPort();
        break;

    case "#setport":
    	setPort(Integer.parseInt(messageArray[1]));
        break;
    case "#quit": 
        stopListening();
        close();
        break; 
    default:
        System.out.println("Command not valid");
    }
}
  
}
//End of EchoServer class
