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
	private static String host = "127.0.0.1";
	private BufferedReader fromServer;
	private PrintWriter toServer;
	private Gson gson = new Gson();
	Scanner consoleInput = new Scanner(System.in);
	public Auctioneer[] openAuctions;
	public ObservableList<Bid> bidhistory;
	public ObservableList<AuctionItem> purchaseHistory;
	
	
	String name;
	String username;
	String password;

	public Client() {
		try {
			this.setUpNetworking();
			this.name = null;
			this.username = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Client(String name, String username) {
		try {
			this.setUpNetworking();
			this.name = name;
			this.username = username;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setUpNetworking() throws Exception {
		@SuppressWarnings("resource")
		Socket socket = new Socket(host, 4242);
		System.out.println("Connecting to... " + socket);
		fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		toServer = new PrintWriter(socket.getOutputStream());
		Thread readerThread = new Thread(new Runnable() {
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

	protected void processRequest(String input) {
		if(input.charAt(0) == '[') {
		openAuctions = gson.fromJson(input, Auctioneer[].class);
		}
	}
//		Message response = gson.fromJson(input, Message.class);
//		
//		switch(response.command) {
//			case "allow":
//				ClientController.clientList.add(this);
//				Auctioneer[] openAuctions = gson.fromJson(response.input, Auctioneer[].class);
//				break;
//			
//			default:
//				System.out.println(response.input);
//				break;
//		}
//	}
	
	public void run() {
		while(true) {
			
		}
	}

	protected void sendToServer(String string) {
		System.out.println("Sending to server: " + string);
		toServer.println(string);
		toServer.flush();
	}
	
	protected void sendToServer(Message message) {
		System.out.println("Sending to server: " + message.input);
		toServer.println(gson.toJson(message, Message.class));
		toServer.flush();
	}
}
