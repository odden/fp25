package gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoggInnController {
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

		if (brukernavn.getText().trim().length() != 0) {
			brukernavn.setStyle("-fx-border-color:transparent");
			check = true;
		} else {
			check = false;
		}

		if (passord.getText().trim().length() != 0) {
			passord.setStyle("-fx-border-color:transparent");
			check = true;
		} else {
			check = false;
		}
		

		if (check) {
			String login = gui
					.tryLogIn(brukernavn.getText(), passord.getText());
			System.out.println(login);
		}else{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Feilmelding");
			alert.setHeaderText("Ugyldig brukernavn og/eller passord");
			alert.setContentText("Vennligst pr\u00F8v igjen");

			alert.showAndWait();
		}

	}

	// Knappen man trykker paa for aa komme til lagbrukervinduet
	@FXML
	public void lagbruker(ActionEvent Event) {
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
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean oldPropertyValue, Boolean newPropertyValue) {
				brukernavn.setStyle("-fx-border-color:transparent");
				commentUsername.setVisible(false);
			}
		};

		ChangeListener<Boolean> SjekkPassword = new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean oldPropertyValue, Boolean newPropertyValue) {
				passord.setStyle("-fx-border-color:transparent");
				commentPassword.setVisible(false);
			}
		};

		brukernavn.focusedProperty().addListener(SjekkUsername);
		passord.focusedProperty().addListener(SjekkPassword);

	}
}
