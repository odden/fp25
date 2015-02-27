package gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoggInnMain extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("LoggInn.fxml"));
		Scene scene = new Scene(root);
		TextField brukernavn = (TextField) root.lookup("#brukernavn");
		
		primaryStage.setTitle("Lag Bruker");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		brukernavn.requestFocus();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
