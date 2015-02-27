package gui;

import java.io.IOException;

import core.Person;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class CreateUserController {
	//Det er her brukeren registrerer seg første gang, 
	//her man skriver inn passord, email, telefonnr osvosv. 
	@FXML private TextField name;
	@FXML private TextField username;
	@FXML private TextField email;
	@FXML private TextField phoneNumber;
	@FXML private TextField password;
	
	CreateUserMain createUserMain = new CreateUserMain();
	
	protected void handle(Event evt){
		if (!name.isDisable()){
			name.setStyle("-fx-border-color: null");
		}
	}
	
	@FXML protected void handleSubmitButtonAction(ActionEvent event){
		boolean check = true;
		final ContextMenu nameValidator = new ContextMenu();
        nameValidator.setAutoHide(false);
        final ContextMenu usernameValidator = new ContextMenu();
        usernameValidator.setAutoHide(false);
        final ContextMenu passwordValidator = new ContextMenu();
        passwordValidator.setAutoHide(false);
        final ContextMenu emailValidator = new ContextMenu();
        emailValidator.setAutoHide(false);
        final ContextMenu phonenumberValidator = new ContextMenu();
        phonenumberValidator.setAutoHide(false);
		
		//Sjekker Navn   
		if (name.getText().matches("[A-Z][a-z]+ [A-Z][a-z]+")){
			name.setStyle("-fx-border-color:green");
		}else{
			check = false;
			name.setStyle("-fx-border-color: red");
			//name.setPromptText("Fornavn og Etternavn");
			 nameValidator.getItems().clear();
             nameValidator.getItems().add(
                     new MenuItem("Fyll inn navn"));
             nameValidator.show(name, Side.RIGHT, 5, 0);
		}
		
		//Sjekker brukernavn
		if (username.getText().matches("[a-z]+[a-z,0-9]{2,}")){
			username.setStyle("-fx-border-color: green");
		}else{
			check = false;
			username.setStyle("-fx-border-color: red");
			
			usernameValidator.getItems().clear();
            usernameValidator.getItems().add(
                    new MenuItem("Fyll inn brukernavn"));
            usernameValidator.show(username, Side.RIGHT, 5, 0);
		}
		
		//Sjekker epostadresse
		if(isValidEmailAddress(email.getText())){
			email.setStyle("-fx-border-color: green");
		}else{
			check = false;
			email.setStyle("-fx-border-color: red");
			emailValidator.getItems().clear();
            emailValidator.getItems().add(
                    new MenuItem("Fyll inn emailadresse"));
            emailValidator.show(email, Side.RIGHT, 5, 0);
		}
		
		//Sjekker telefonnummer
		if(phoneNumber.getText().matches("[0-9]{8,8}")){
			phoneNumber.setStyle("-fx-border-color: green");
		}else{
			check = false;
			phoneNumber.setStyle("-fx-border-color: red");
			phonenumberValidator.getItems().clear();
            phonenumberValidator.getItems().add(
                    new MenuItem("Fyll inn telefonnummeret"));
            phonenumberValidator.show(phoneNumber, Side.RIGHT, 5, 0);
		}
		
		if (password.getText().matches("[A-Z,a-z,0-9]{6,}")){
			password.setStyle("-fx-border-color: green");
		}else{
			check = false;
			password.setStyle("-fx-border-color: red");
			passwordValidator.getItems().clear();
            passwordValidator.getItems().add(
                    new MenuItem("Passordet er ugyldig"));
            passwordValidator.show(password, Side.RIGHT, 5, 0);
		}
		
		if (check){
			Person p = new Person(name.getText(), username.getText(), email.getText(), phoneNumber.getText(), password.getText());
		}
	
		
	}
	
	@FXML protected void avbryt(ActionEvent event) throws IOException{
		createUserMain.changeView();
	}
	
	
	//Sjekker emailadresse
	private boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}
	
}
