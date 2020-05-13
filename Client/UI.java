package assignment7Client;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;

public class UI extends Application implements Initializable  {

	UI controller;
	Client myClient;
	
	@FXML
    // The reference of text fields will be injected by the FXML loader
    private Hyperlink info;
     
    @FXML
    private Hyperlink bidhistory;
    
    @FXML
    private TextField purchasehist;
    
    
     
    // location and resources will be automatically injected by the FXML loader 
    @FXML
    private URL location;
     
    @FXML
    private ResourceBundle resources;
     
    // Public no-args constructor
    public UI(Client myClient) {
    	this.myClient = myClient;
    }
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("interface.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		controller = fxmlLoader.getController();
		primaryStage.setTitle("Auction");
		primaryStage.setScene(new Scene(root, 700, 600));
		primaryStage.show();
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}
	
}
