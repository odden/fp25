package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class LoggInnController {
	
	@FXML private TextField brukernavn;
	@FXML private Button loggInn;
	@FXML private PasswordField passord;
	private LoggInn innlogger = new LoggInn();
	
	
	@FXML
	public void validateUser(ActionEvent event) {
		System.out.println("hei");
		/*
		If passord og bruker stemmer overens med database
			åpne kalenderen til denne brukeren og lukk logg inn vindu 
		else gi tilbakemelding om en feil
		*/
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
