package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class LoggInnController extends Application{
	private static Stage stage; 
	Parent root;
	@FXML private TextField brukernavn;
	@FXML private Button loggInn;
	@FXML private PasswordField passord;
	private LoggInn innlogger = new LoggInn();
	private Gui gui;
	
	public LoggInnController(Gui gui){
		this.gui = gui;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		this.stage = primaryStage;
		try {
			root = FXMLLoader.load(getClass().getResource("LoggInn.fxml"));
			Scene scene = new Scene(root);
			TextField brukernavn = (TextField) root.lookup("#brukernavn");
			primaryStage.setTitle("Logg Inn");
			primaryStage.setScene(scene);
			primaryStage.show();
			brukernavn.requestFocus();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	public void start(){
		launch();
	}
	
	@FXML
	public void validateUser(ActionEvent event) {
		final ContextMenu usernameValidator = new ContextMenu();
        usernameValidator.setAutoHide(false);
        final ContextMenu passwordValidator = new ContextMenu();
        passwordValidator.setAutoHide(false);
		
        boolean check = true;
		
		if (brukernavn.getText().trim().length() == 0){
			check = false;
			brukernavn.setStyle("-fx-border-color: red");
			usernameValidator.getItems().clear();
            usernameValidator.getItems().add(
                    new MenuItem("Dette feltet kan ikke stå tomt"));
            usernameValidator.show(brukernavn, Side.RIGHT, 5, 0);
		}else if (!brukernavn.getText().matches("[a-z]+[a-z,0-9]{2,}")){
			check = false;
			brukernavn.setStyle("-fx-border-color: red");
			usernameValidator.getItems().clear();
            usernameValidator.getItems().add(
                    new MenuItem("Brukernavnet kan ikke inneholde mellomrom"));
            usernameValidator.show(brukernavn, Side.RIGHT, 5, 0);
		}
//		else {
//			brukernavn.setStyle("-fx-border-color: green");
//		}
		
		if (passord.getText().trim().length() == 0){
			check = false;
			passord.setStyle("-fx-border-color: red");
			passwordValidator.getItems().clear();
            passwordValidator.getItems().add(
                    new MenuItem("Dette feltet kan ikke stå tomt"));
            passwordValidator.show(passord, Side.RIGHT, 5, 0);
		}
//		else{
//			passord.setStyle("-fx-border-color: green");
//			//passwordValidator.hide(); funker ikke
//		}
		
		if (check){
			@SuppressWarnings("unused")
			int svaretPaaLivet = 42; 
			String login = gui.logIn(brukernavn.getText(),passord.getText());
			//hvis  brukernavn og passord samsvarer => logg inn
		}
		
	}
	
	//Knappen man trykker paa for aa komme til lagbrukervinduet
	@FXML
	public void lagbruker(ActionEvent Event){
		try {
			 final FXMLLoader loader = new FXMLLoader(
				      getClass().getResource(
				        "CreateUser.fxml"
				      )
				    );

			root = loader.load();
			final CreateUserController controller = loader.getController();
		    controller.initData(stage);
			Scene scene = new Scene(root);
			stage.setTitle("CreateUser");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@FXML
	public void updateBrukernavn(KeyEvent event) {
		String navn = brukernavn.getText();
		innlogger.setBrukernavn(navn);
	}
	
	@FXML
	public void updatePassord(KeyEvent event) {
		String str = passord.getText();
		innlogger.setPassord(str);
	}
	
	
}
