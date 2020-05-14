package assignment7Client;

import javafx.application.Application;   
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;

import javafx.geometry.*;

import com.google.gson.*;

public class ClientController extends Application implements Initializable {
	
	private static ClientController controller;
	private Stage primaryStage = new Stage();
	private static HashMap<String, Client> clientList = new HashMap<>();
	private static ArrayList<Thread> threads = new ArrayList<>();
	private Client myClient = new Client();
	public String savedName;
	
	Gson gson = new Gson();
	
	Boolean isReady = false;
	
	Boolean isConfirmed = null;
	
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
    public GridPane grid;
    
    @FXML
    public GridPane gp;
    
    @FXML
    public VBox display;
    
    @FXML
    public Label historyTitle;
    
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
    public ClientController() {
		this.name = null;
		this.username = null;
		this.password = null;
    }
    
  
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
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
		Client client = new Client(name.getText(), username.getText(), this);
		this.myClient = client;
		clientList.put(myClient.username, myClient);
		Message message = new Message("register", name.getText(), username.getText(), password.getText());
		myClient.sendToServer(gson.toJson(message, Message.class));
		primaryStage.close();
		primaryStage.setScene(makeUI(myClient));
		primaryStage.show();
	}
	
	@FXML
	private void handleLogin(ActionEvent e) throws IOException {
		String user = username.getText();
		Client tempClient = new Client(null, username.getText(), controller);
		Message message = new Message("login", null, username.getText(), password.getText());
		tempClient.sendToServer(message);

		while (this.isConfirmed == null) {
			System.out.println();
		}
		if (this.isConfirmed == false) {
			createPopUp("Sorry, user does not exist.");
		} else {
			Client client = new Client(savedName, user, this);
			client.controller = controller;
			this.myClient = client;
			Message mess = new Message("browse");
			myClient.sendToServer(gson.toJson(mess, Message.class));
			primaryStage.close();
			primaryStage.setScene(makeUI(myClient));
			primaryStage.show();
		}

	}
	
	@FXML
	private void handleLogout(ActionEvent e) throws Exception {
		Client client  = controller.myClient;
		client.sendToServer(new Message("logout", client.username));
		primaryStage.close();
		Platform.exit();
		stop();
		System.exit(0);
	}
	
	@FXML
	private void returntoRegister(ActionEvent e) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("client.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		controller = fxmlLoader.getController();
		primaryStage.setTitle("Customer");
		primaryStage.setScene(new Scene(root, 700, 600));
		primaryStage.show();
	}
	
	@FXML
	private void handleLoginUI(ActionEvent e) throws IOException {
		primaryStage.close();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		ClientController ctrl = new ClientController();
		ctrl = fxmlLoader.getController();
		controller = ctrl;
		Scene newScene = new Scene(root, 700, 600);
		primaryStage.setScene(newScene);
		primaryStage.show();
	}
	
	@SuppressWarnings("unchecked")
	@FXML
	private void showClientHistory(ActionEvent e) {
		
		display.getChildren().clear();
		
		TableView<ItemPair> table = new TableView<>();
		table.setEditable(true);
		
		historyTitle.setText(controller.myClient.getName() + "'s History");
		historyTitle.setFont(new Font("Helvetica", 10));
		
		TableColumn<ItemPair, String> items = new TableColumn<>("Item ID");
		items.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<ItemPair, String> amounts = new TableColumn<>("Amount");		
		amounts.setCellValueFactory(new PropertyValueFactory<>("amount"));
		
		table.getColumns().addAll(items, amounts);
		
//		ObservableList<ItemPair> data = FXCollections.observableArrayList();
		
		for(Bid b : controller.myClient.bidhistory) {
			
			
			table.getItems().add(new ItemPair(Integer.toString(b.item), String.valueOf(b.amount)));
		}
		
		display.getChildren().add(table);
	}
	
	@FXML
	private void searchHandle(ActionEvent e) {
		String searched = search.getText();
		String current = "vbox" + searched;
		VBox vb = (VBox) this.grid.lookup("#" + current);
		vb.setBorder(new Border(new BorderStroke(Color.RED,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
	}
	
	public void updateUI(Client client) throws IOException {
		Platform.runLater(new Runnable() {
			public void run() {
				primaryStage.close();
				try {
					primaryStage.setScene(makeUI(client));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				primaryStage.show();
			}});
	}
	
	
	public void createPopUp(String message) throws IOException {
		Platform.runLater(new Runnable() {
			public void run() {
				Stage newStage = new Stage();
				VBox comp = new VBox();
				Popup pop = new Popup();
				Label label = new Label(message);
				label.setStyle(" -fx-background-color: white;");
				pop.getContent().add(label);
				label.autosize();
				pop.setAutoHide(false);

				Scene stageScene = new Scene(comp, 300, 300);
				newStage.setScene(stageScene);
				newStage.show();
				pop.show(newStage);
			}});
	}

	public Scene makeUI(Client client) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("interface.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		controller = fxmlLoader.getController();
		controller.myClient = client;
		Auctioneer[] auctions;
		while(client.getAuctions() == null) {
			System.out.println();
		}
		auctions = client.getAuctions();
		for(Integer i = 1 ; i <= auctions.length; i++) {
			Auctioneer thisAuction = auctions[i-1];
			String current = "vbox" + i.toString();
			VBox vb = (VBox) controller.grid.lookup("#" + current);
			
			Label name = new Label(auctions[i - 1].Item.name);
			Label description = new Label(auctions[i - 1].Item.description);
			Label buyNow = new Label("Buy Now: ");
			Label buyNowPrice = new Label(Double.toString(auctions[i - 1].Item.buyItNow));
			Label highestBid = new Label("Highest bid: ");
			Label highestBidValue = new Label(Double.toString(auctions[i - 1].bestBid.amount));
			Label highestBidder = new Label("Highest bidder: ");
			Label highestBidderName = new Label(auctions[i - 1].bestBid.username);
			TextField bidVal = new TextField();
			Button bid = new Button("bid");
			
			bid.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
				public synchronized void handle(ActionEvent event) {
					if (thisAuction.status) {
						Double value = Double.parseDouble(bidVal.getText());
						if (value <= Double.parseDouble(highestBidValue.getText())) {
							try {
								createPopUp("Sorry, bid amount is not high enough.");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.out.println("Sorry, bid amount is not high enough.");
						} else {
							Bid b = new Bid(value, System.nanoTime(), client.username, thisAuction.Item.id);
							thisAuction.nextBestBid = thisAuction.bestBid;
							thisAuction.nextBestUser = thisAuction.bestBidUser;
							thisAuction.bestBid = b;
							thisAuction.bestBidUser = client.user;
							thisAuction.status = (value < Double.parseDouble(buyNowPrice.getText())) ? true : false;
							Message placeBid = new Message("bid", client.username, b.amount, b.timestamp, b.item);
							client.sendToServer(placeBid);
							client.bidhistory.add(b);
							client.openAuctions = null;
						}
					} else {
						try {
							createPopUp("Invalid bid: item is sold.");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
	            }
	        });
			Button buyNowb = new Button("buyNow");
			
			GridPane gp = new GridPane();
			gp.setId("gp");

			gp.addRow(0, name);
			gp.addRow(1, description);
			HBox bn = new HBox(5, buyNow, buyNowPrice);
			gp.addRow(2, bn);
			HBox bidbox = new HBox(5, highestBid, highestBidValue);
			gp.addRow(4, bidbox);
			HBox bidderbox = new HBox(5, highestBidder, highestBidderName);
			gp.addRow(5,  bidderbox);
			
			HBox placebid = new HBox(5, bidVal, bid);
			gp.addRow(6, placebid);
			gp.addRow(7, buyNowb);
			
			gp.setHgap(2); //horizontal gap in pixels
			gp.setVgap(2); //vertical gap in pixels
			gp.setPadding(new Insets (2, 2, 2, 2));
			
			if(thisAuction.status == false) {
				Label sold = new Label("SOLD");
				gp.add(sold, 5, 5);
				placebid.setDisable(true);
				buyNowb.setDisable(true);
			}

			vb.getChildren().add(gp);
			
		}
		return new Scene(root, 700, 600);
	}
	
	



}
