package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CreateUserController {
	//Det er her brukeren registrerer seg første gang, 
	//her man skriver inn passord, email, telefonnr osvosv. 
	@FXML private TextField name;
	
	
	@FXML protected void handleSubmitButtonAction(ActionEvent event){
		if (name.getText().trim().length() == 0){
			System.out.println("feil");
		}
	}
	
	
}
