package assignment7Client;

import java.io.*;
import java.net.Socket;
import java.util.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import com.google.gson.*;

/*
 * Author: Vallath Nandakumar and EE 422C instructors
 * Date: April 20, 2020
 * This starter code is from the MultiThreadChat example from the lecture, and is on Canvas. 
 * It doesn't compile.
 */

public class Client extends Application {
	ClientController controller;
	ObjectInputStream reader;
	ObjectOutputStream writer;

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader();
		Parent root = fxmlLoader.load(getClass().getResource("client.fxml").openStream());
		controller = fxmlLoader.getController();
		primaryStage.setTitle("Customer");
		primaryStage.setScene(new Scene(root, 700, 600));
		primaryStage.show();
		controller.myClient = this;
		customer = new User();

		connectToServer();
	}

	private static String host = "127.0.0.1";
	private BufferedReader fromServer;
	private PrintWriter toServer;
	private Scanner consoleInput = new Scanner(System.in);

	public static void main(String[] args) {
		try {
			new Client().setUpNetworking();
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
		return;
	}

	protected void sendToServer(String string) {
		System.out.println("Sending to server: " + string);
		toServer.println(string);
		toServer.flush();
	}
}
