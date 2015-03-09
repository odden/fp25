package gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoggInnController{
	@SuppressWarnings("unused")
	private static Stage stage; 
	Parent root;
	@FXML private TextField brukernavn;
	@FXML private Button loggInn;
	@FXML private PasswordField passord;
	@FXML private Text commentUsername;
	@FXML private Text commentPassword;
	private LoggInn innlogger = new LoggInn();
	Gui gui;
	

	@FXML
	public void validateUser(ActionEvent event) {

        boolean check = true;
		
		if (brukernavn.getText().trim().length() == 0 && !brukernavn.getText().matches("[a-z]+[a-z,0-9]{2,}")){
			check = false;
			brukernavn.setStyle("-fx-border-color: red");
			commentUsername.setVisible(true);
		
		}else {
			brukernavn.setStyle("-fx-border-color: green");
		}


		
		if (passord.getText().trim().length() == 0){
			check = false;
			passord.setStyle("-fx-border-color: red");
			commentPassword.setVisible(true);
		}
		else{
			passord.setStyle("-fx-border-color: green");
		}
		
		if (check){
			@SuppressWarnings("unused")
			int svaretPaaLivet = 42; 
			String login = gui.tryLogIn(brukernavn.getText(),passord.getText());
			System.out.println(login);
			//hvis  brukernavn og passord samsvarer => logg inn
		}
		
	}
	
	//Knappen man trykker paa for aa komme til lagbrukervinduet
	@FXML
	public void lagbruker(ActionEvent Event){
		try {
			gui.switchSceneContent("CreateUser.fxml");
		} catch (Exception e) {
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

	@SuppressWarnings("static-access")
	public void initData(Stage stage, Gui gui) {
		this.stage = stage;
		this.gui = gui;
		
		
		ChangeListener<Boolean> SjekkUsername = new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
	        	brukernavn.setStyle("-fx-border-color:transparent");
	        	commentUsername.setVisible(false);
	        }
		};
		
		ChangeListener<Boolean> SjekkPassword = new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
	        	passord.setStyle("-fx-border-color:transparent");
	        	commentPassword.setVisible(false);
	        }
		};
		
		brukernavn.focusedProperty().addListener(SjekkUsername);
		passord.focusedProperty().addListener(SjekkPassword);
		
	}
}
