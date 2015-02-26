package gui;

import java.awt.Component;
import java.awt.Frame;

import javax.swing.JOptionPane;

import core.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.shape.Rectangle;

public class CreateUserController {
	//Det er her brukeren registrerer seg første gang, 
	//her man skriver inn passord, email, telefonnr osvosv. 
	@FXML private TextField name;
	@FXML private TextField username;
	@FXML private TextField email;
	@FXML private TextField phoneNumber;
	@FXML private TextField password;
	
	
	@FXML protected void handleSubmitButtonAction(ActionEvent event){
		boolean check = true;
		
		//Sjekker Navn
		if (name.getText().matches("[A-Z][a-z]+ [A-Z][a-z]+")){
			name.setStyle("-fx-border-color:green");
		}else{
			check = false;
			name.setStyle("-fx-border-color: red");
			Tooltip tooltip = new Tooltip();
			tooltip.setText(
			    "\nYour password must be\n" +
			    "at least 8 characters in length\n"
			);
			name.setTooltip(tooltip);
		}
		
		//Sjekker brukernavn
		if (username.getText().matches("[a-z]+[a-z,0-9]{2,}")){
			username.setStyle("-fx-border-color: green");
		}else{
			check = false;
			username.setStyle("-fx-border-color: red");
		}
		
		//Sjekker epostadresse
		if(isValidEmailAddress(email.getText())){
			email.setStyle("-fx-border-color: green");
		}else{
			check = false;
			email.setStyle("-fx-border-color: red");
		}
		
		//Sjekker telefonnummer
		if(phoneNumber.getText().matches("[0-9]{8,8}")){
			phoneNumber.setStyle("-fx-border-color: green");
		}else{
			check = false;
			phoneNumber.setStyle("-fx-border-color: red");
		}
		
		if (password.getText().matches("[A-Z,a-z,0-9]{6,}")){
			password.setStyle("-fx-border-color: green");
		}else{
			check = false;
			password.setStyle("-fx-border-color: red");
		}
		
		if (check){
			Person p = new Person(name.getText(), username.getText(), email.getText(), phoneNumber.getText(), password.getText());
		}
	
		
	}
	
	//Sjekker emailadresse
	private boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}
	
}
