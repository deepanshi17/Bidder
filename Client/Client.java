package assignment7Client;

import java.io.*;
import java.net.Socket;
import java.util.*;

import com.google.gson.*;

import javafx.collections.*;

/*
 * Author: Vallath Nandakumar and EE 422C instructors
 * Date: April 20, 2020
 * This starter code is from the MultiThreadChat example from the lecture, and is on Canvas. 
 * It doesn't compile.
 */

public class Client implements Runnable {
	ObjectInputStream reader;
	ObjectOutputStream writer;
	private static String host = "127.0.0.1";		// Host: 127.0.0.1 hard coded
	private BufferedReader fromServer;
	private PrintWriter toServer;
	private Gson gson = new Gson();
	Scanner consoleInput = new Scanner(System.in);
	public Auctioneer[] openAuctions;				// Current auction information
	public ArrayList<Bid> bidhistory;				// Individual client history
	public ObservableList<AuctionItem> purchaseHistory;
	ClientController controller;

	private String name;
	protected String username;
	String password;
	User user;

	public Client() {		// Default constructor
		try {
			this.setUpNetworking();
			this.name = null;
			this.username = null;
			this.user = new User();
			this.bidhistory = new ArrayList<>();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Client(String name, String username, ClientController ctrl) {	// Called by ClientController
		try {
			this.setUpNetworking();
			this.name = name;
			this.username = username;
			this.controller = ctrl;
			this.user = new User(name, username);
			this.bidhistory = new ArrayList<>();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Client(Client c) {				// Copy constructor
		try {
			this.setUpNetworking();
			this.name = c.name;
			this.username = c.username;
			this.controller = c.controller;
			this.user = new User(name, username);
			this.bidhistory = new ArrayList<>();
			this.bidhistory = c.bidhistory;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setUpNetworking() throws Exception {
		@SuppressWarnings("resource")
		Socket socket = new Socket(host, 4242);				// Port: 4242
		System.out.println("Connecting to... " + socket);
		fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		toServer = new PrintWriter(socket.getOutputStream());
		Thread readerThread = new Thread(new Runnable() {		// Threads constantly polling for server messages
			@Override
			public void run() {
				String input;
				try {
					while ((input = fromServer.readLine()) != null) {
						System.out.println("From server: " + input);
						processRequest(input);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		Thread writerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					String input = consoleInput.nextLine();
					String[] variables = input.split(",");
					Message request = new Message(variables[0], variables[1], Integer.valueOf(variables[2]));
					GsonBuilder builder = new GsonBuilder();
					Gson gson = builder.create();
					sendToServer(gson.toJson(request));
				}
			}
		});
		readerThread.start();
		writerThread.start();
	}

	protected void processRequest(String input) throws IOException {	// Can be Json message or regular string
		if(input.charAt(0) == '[') {			// Auctioneer list
		openAuctions = gson.fromJson(input, Auctioneer[].class);
		controller.updateUI(this);
		}
		
		if(input.startsWith("Sold")) {			// Item sold
			controller.createPopUp(input);
			sendToServer(new Message("browse"));
		}
		
		if(input.startsWith("{")) {		// Regular Message
			Message response = gson.fromJson(input, Message.class);	
			if(response.command.equals("login")) {		// Login request
				if(response.input.startsWith("error")){
					controller.isConfirmed = false;
				} else {
					controller.savedName = response.input;
					controller.isConfirmed = true;
				}
			}
			else if(response.command.equals("error")) {		// Error message
				controller.createPopUp(response.input);
			}
		}
		
		if(input.equals("new bid")) {		// Browse to update Auction data
			sendToServer(new Message("browse"));
		}
		
	}

	public void run() {
		while (true) {
		}
	}
	
	protected String getName() {
		return this.name;
	}

	protected void sendToServer(String string) {
		System.out.println("Sending to server: " + string);
		toServer.println(string);
		toServer.flush();
	}

	protected void sendToServer(Message message) {
		System.out.println("Sending to server: " + message.command);
		toServer.println(gson.toJson(message, Message.class));
		toServer.flush();
	}

	protected Auctioneer[] getAuctions() {		// Controller instance gets current auction list
		return openAuctions;
	}
}
