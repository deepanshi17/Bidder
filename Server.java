package assignment7Server;

import java.io.*; 
import java.net.*;
import java.util.*;
import com.google.gson.Gson;

/*
 * Author: Vallath Nandakumar and the EE 422C instructors.
 * Data: April 20, 2020
 * This starter code assumes that you are using an Observer Design Pattern and the appropriate Java library
 * classes.  Also using Message objects instead of Strings for socket communication.
 * See the starter code for the Chat Program on Canvas.  
 * This code does not compile.
 */
public class Server extends Observable {

    static Server server;
    private static Security security;
    private static String filename;
	private static Gson auctionItemList;
	private static AuctionItem[] auctionList;
	private static HashMap<String, Map<String, String>> userDB;
	private static HashMap<String, User> users;
	private static HashMap<String, User> activeUsers;
	private static HashMap<String, Auctioneer> auctioneers;

    public static void main (String [] args) throws Exception {
        server = new Server();
        filename = args[0];
        server.CreateAuction();
        try {
        	server.SetupNetworking();
        } catch (Exception e){
        	e.printStackTrace();
        	return;
        }
    }

    private void SetupNetworking() throws Exception {
    	@SuppressWarnings("resource")
        ServerSocket serverSock = new ServerSocket(4242);
        while (true) {
          Socket clientSocket = serverSock.accept();
          System.out.println("Connecting to... " + clientSocket);

          ClientHandler handler = new ClientHandler(this, clientSocket);
          this.addObserver(handler);

          Thread t = new Thread(handler);
          t.start();
        }
    }
    
    // Initialize auction items
    
    private void CreateAuction() throws Exception {
    	server.populateItems();
    	userDB = new HashMap<String, Map<String, String>>();
    	for(AuctionItem item : auctionList) {	
    		Auctioneer auctioneer = new Auctioneer(item);
    		auctioneers.put(item.name, auctioneer);
    	}
    	while(true) {
    		
    	}
    	
    }
    
    private void populateItems() throws Exception {
    	Scanner sc = new Scanner(new File(filename));
    	Gson g = new Gson();
    	String jsonString = sc.nextLine();
    	auctionList = g.fromJson(jsonString, AuctionItem[].class);
    	sc.close();
    }
    
    protected void processRequest(ClientHandler handler, String input) {
        String output = "Error";
        Gson gson = new Gson();
        Message message = gson.fromJson(input, Message.class);
        try {
          String temp = "";
          switch (message.type) {
            case "upper":
              temp = message.input.toUpperCase();
              break;
            case "lower":
              temp = message.input.toLowerCase();
              break;
            case "strip":
              temp = message.input.replace(" ", "");
              break;
          }
          
          output = "";
          for (int i = 0; i < message.number; i++) {
            output += temp;
            output += " ";
          }
          
          String[] inputs = input.split(" ");
          
          switch(message.command) {

			case "register":
				String salt = security.getSalt(inputs[3].length());
				String name = inputs[1];
				String userName = inputs[2];
				if(userDB.containsKey(userName)) {
					Message error = new Message("error", "error: username is taken.", 1);
					handler.sendToClient(error);
					break;
				}
				String password = inputs[3];
				String securePassword = security.generateSecurePassword(password, salt);
				User newUser = new User(name, userName, securePassword);
		    	Map<String, String> encrypted = new HashMap<String, String>();
		    	encrypted.put(securePassword, salt);
				userDB.put(name, encrypted);
				users.put(userName, newUser);
				activeUsers.put(userName, newUser);
				Message allow = new Message("allow", "verified", 1);
				handler.sendToClient(allow);
				break;
				
			case "login":
				String username = inputs[1];
				String checkPassword = inputs[2];
				if(userDB.containsKey(username)) {
					Map encryption = userDB.get(username);
					String saltVal = (String)encryption.values().toArray()[0];
					String passVal = (String)encryption.keySet().toArray()[0];
					boolean isMatch = security.verifyPassword(checkPassword, passVal, saltVal);
					if(isMatch) {
						Message allowed = new Message("allow", "password verified", 1);
						handler.sendToClient(allowed);
						User thisUser = users.get(username);
						activeUsers.put(username, thisUser);
						break;
					} else {
						Message error = new Message("error", "error: password does not match.", 1);
						handler.sendToClient(error);
						break;
					}
					
				} else {
					Message error = new Message("error", "error: user does not exist.", 1);
					handler.sendToClient(error);
					break;
				}
			
			case "bid":
				String client = inputs[1];	// username of bidder
				String item = inputs[2];	// name of item
				Double amount = Double.valueOf(inputs[3]);
				Bid bid = new Bid(amount, System.nanoTime(), client);
				auctioneers.get(item).JoinAuction(users.get(client), bid);
				break;
				
				
				
				
			}
          
          
          this.setChanged();
          this.notifyObservers(output);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
   
    
//    public class Main{
//    	public static main(String[] args) {
//    		Gson g = new Gson();
//    		AuctionItem iPad = new AuctionItem(500.0, 100.0, "iPad");
//    		String iPadString = g.toJson(iPad);
//    		
//    		String jsonString = "{\"currPrice\":500.0,\"name\":\"iPad\",\"minPrice\":100.0}";
//    		AuctionItem iPad_from_server = g.fromJson(jsonString, AuctionItem.class);
//    		
//    	}
//    }
    
    
}
