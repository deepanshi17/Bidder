package assignment7Client;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.TextField;
import javafx.stage.*;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.*;

public class ClientController extends Application implements Initializable {
	ClientController controller;
	Client myClient;
	Gson gson = new Gson();
	
	@FXML
    // The reference of text fields will be injected by the FXML loader
    private TextField name;
     
    @FXML
    private TextField username;
    
    @FXML
    private TextField password;
     
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
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("client.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		controller = fxmlLoader.getController();
		primaryStage.setTitle("Customer");
		primaryStage.setScene(new Scene(root, 700, 600));
		primaryStage.show();
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}
	
	@FXML
	
	private void handleButtonAction() {
		System.out.println("Pressed");
		myClient = new Client();
		
		Message message = new Message("register", name.getText(), username.getText(), password.getText());
		myClient.sendToServer(gson.toJson(message, Message.class));
		
	}



}
