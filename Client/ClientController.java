package assignment7Client;

import javafx.application.Application; 
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;

import javafx.geometry.*;

import com.google.gson.*;

public class ClientController extends Application implements Initializable {
	
	ClientController controller;
	public Stage primaryStage = new Stage();
	public static ArrayList<Client> clientList = new ArrayList<>();
	private ArrayList<Thread> threads = new ArrayList<>();
	
	Client myClient;
	Gson gson = new Gson();
	
	@FXML
    // The reference of text fields will be injected by the FXML loader
    private TextField name;
     
    @FXML
    private TextField username;
    
    @FXML
    private TextField password;
    
    @FXML
    // The reference of text fields will be injected by the FXML loader
    private Hyperlink info;
     
    @FXML
    private Hyperlink bidhistory;
    
    @FXML
    private Hyperlink purchasehist;
    
    @FXML
    public GridPane grid;
    
    @FXML
    public VBox vbox1;
    
    @FXML
    private VBox vbox2;
    
    @FXML
    private VBox vbox3;
    
    @FXML
    private VBox vbox4;
    
    @FXML
    private VBox vbox5;
    
    @FXML
    private VBox vbox6;
    
    @FXML
    private VBox vbox7;
    
    @FXML
    private VBox vbox8;
    
    @FXML
    private VBox vbox9;
    
    @FXML
    private VBox vbox10;
    
    @FXML
    private VBox vbox11;
    
    @FXML
    private VBox vbox12;
    
    @FXML
    private TextField search;
    
     
    // location and resources will be automatically injected by the FXML loader 
    @FXML
    private URL location;
     
    @FXML
    private ResourceBundle resources;
     
    // Public no-args constructor
    public ClientController() 
    {
		name = null;
		username = null;
		password = null;
    }
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		threads = new ArrayList<>();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("client.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		controller = fxmlLoader.getController();
		primaryStage.setTitle("Customer");
		primaryStage.setScene(new Scene(root, 700, 600));
		primaryStage.show();
		this.primaryStage = primaryStage;
	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
		for(Thread t : threads) {
			t.interrupt();
		}
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}
	
	@FXML
	private void handleButtonAction(ActionEvent e) throws JsonSyntaxException, IOException {
		System.out.println("Pressed");
		myClient = new Client(name.getText(), username.getText());
		Thread clientThread = new Thread(myClient);
		clientThread.setDaemon(true);
//		clientThread.start();
		threads.add(clientThread);
		Message message = new Message("register", name.getText(), username.getText(), password.getText());
		myClient.sendToServer(gson.toJson(message, Message.class));
		primaryStage.close();
		primaryStage.setScene(makeUI(myClient));
		primaryStage.show();
	}

	public Scene makeUI(Client client) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("interface.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		Auctioneer[] auctions = client.openAuctions;
		ClientController ctrl = new ClientController();
		ctrl = fxmlLoader.getController();
		for(Integer i = 1 ; i <= auctions.length; i++) {
			String current = "vbox" + i.toString();
			VBox vb = (VBox) ctrl.grid.lookup("#" + current);
			
			Label name = new Label(auctions[i - 1].Item.name);
			Label description = new Label(auctions[i - 1].Item.description);
			Label buyNow = new Label("Buy Now: ");
			Label buyNowPrice = new Label(Double.toString(auctions[i - 1].Item.buyItNow));
			Label timeRemaining = new Label(Long.toString(System.nanoTime() - auctions[i - 1].Item.startTime));
			Label highestBid = new Label("Highest bid: ");
			Label highestBidValue = new Label(Double.toString(auctions[i - 1].bestBid.amount));
			Label highestBidder = new Label("Highest bidder: ");
			Label highestBidderName = new Label(auctions[i - 1].bestBid.username);
			Button bid = new Button("bid");
			Button buyNowb = new Button("buyNow");
			
			GridPane gp = new GridPane();
			

			gp.add(name, 0, 5);
			gp.add(description, 1, 5);
			gp.add(buyNow, 5, 5);
			gp.add(buyNowPrice, 5, 5);
			gp.add(timeRemaining, 5, 5);
			gp.add(highestBid, 5, 5);
			gp.add(highestBidValue, 5, 5);
			gp.add(highestBidder, 5, 5);
			gp.add(highestBidderName, 5, 5);
			gp.add(bid, 5, 5);
			gp.add(buyNowb, 5, 5);
			
			gp.setHgap(2); //horizontal gap in pixels
			gp.setVgap(2); //vertical gap in pixels
			gp.setPadding(new Insets (2, 2, 2, 2));

			vb.getChildren().add(gp);
			
		}
		
		return new Scene(root, 700, 600);
	}
	
	



}
