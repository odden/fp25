package gui;

import core.Person;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CreateUserController {
	//Det er her brukeren registrerer seg første gang, 
	//her man skriver inn passord, email, telefonnr osvosv. 
	@FXML
	private static Stage stage;
	private Gui gui;
	@FXML private TextField name;
	@FXML private TextField username;
	@FXML private TextField email;
	@FXML private TextField phoneNumber;
	@FXML private TextField password;
	@FXML private Text commentName;
	@FXML private Text commentUsername;
	@FXML private Text commentPassword;
	@FXML private Text commentEmail;
	@FXML private Text commentPhone;


	
	@FXML protected void handleSubmitButtonAction(ActionEvent event){
		boolean check = true;

		
		//Sjekker Navn   
		if (name.getText().matches("[A-Z,\u00C6\u00D8\u00D5][a-z,\u00E6\u00F8\u00E5]+ [A-Z,\u00C6\u00D8\u00D5][a-z,\u00E6\u00F8\u00E5]+(-)*( )*([A-Z,\u00C6\u00D8\u00D5][a-z,\u00E6\u00F8\u00E5]+)*")){
			name.setStyle("-fx-border-color:green");
			commentName.setVisible(false);
		}else{
			check = false;
			name.setStyle("-fx-border-color: red");
			commentName.setVisible(true);
		}
		
		//Sjekker brukernavn
		if (username.getText().matches("[a-z]+[a-z,0-9]{2,}")){
			username.setStyle("-fx-border-color: green");
			commentUsername.setVisible(false);
		}else{
			check = false;
			username.setStyle("-fx-border-color: red");
			commentUsername.setVisible(true);
		}
		
		//Sjekker epostadresse
		if(isValidEmailAddress(email.getText())){
			email.setStyle("-fx-border-color: green");
			commentEmail.setVisible(false);
		}else{
			check = false;
			email.setStyle("-fx-border-color: red");
			commentEmail.setVisible(true);
		}
		
		//Sjekker telefonnummer
		if(phoneNumber.getText().matches("[0-9]{8,8}")){
			phoneNumber.setStyle("-fx-border-color: green");
			commentPhone.setVisible(false);
		}else{
			check = false;
			phoneNumber.setStyle("-fx-border-color: red");
			commentPhone.setVisible(true);
		}
		
		if (password.getText().matches("[A-Z,a-z,0-9]{6,}")){
			password.setStyle("-fx-border-color: green");
			commentPassword.setVisible(false);
		}else{
			check = false;
			password.setStyle("-fx-border-color: red");
			commentPassword.setVisible(true);
		}
		
		if (check){
			if (gui.tryCreateUser(username.getText(), password.getText(), name.getText(), email.getText(), Integer.parseInt(phoneNumber.getText())).equals("tatt")){
				username.setStyle("-fx-border-color: red");
			}
		}
	}
	
	
	//Skifte scene fra CreateUser til Main
	@FXML protected void avbryt(ActionEvent event) throws Exception{
		gui.switchSceneContent("LoggInn.fxml");
	}
		
	
	//Sjekker emailadresse
	private boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}
	@SuppressWarnings("static-access")
	public void initData(Stage stage, Gui gui) {
		this.stage = stage;
		this.gui = gui;
		
		ChangeListener<Boolean> SjekkName = new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
	        	name.setStyle("-fx-border-color: transparent");
	        	commentName.setVisible(false);
	        }
		};
		
		ChangeListener<Boolean> SjekkUsername = new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
	        	username.setStyle("-fx-border-color:transparent");
	        	commentUsername.setVisible(false);
	        }
		};
		
		ChangeListener<Boolean> SjekkPassword = new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
	        	password.setStyle("-fx-border-color:transparent");
	        	commentPassword.setVisible(false);
	        }
		};
		
		ChangeListener<Boolean> SjekkEmail = new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
	        	email.setStyle("-fx-border-color:transparent");
	        	commentEmail.setVisible(false);
	        }
		};
		
		ChangeListener<Boolean> SjekkPhone = new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
	        	phoneNumber.setStyle("-fx-border-color:transparent");
	        	commentPhone.setVisible(false);
	        }
		};
		
		name.focusedProperty().addListener(SjekkName);
		username.focusedProperty().addListener(SjekkUsername);
		password.focusedProperty().addListener(SjekkPassword);
		email.focusedProperty().addListener(SjekkEmail);
		phoneNumber.focusedProperty().addListener(SjekkPhone);
	}
	
}
