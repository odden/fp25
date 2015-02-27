package gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateUserMain extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("CreateUser.fxml"));
		Scene scene = new Scene(root);
		TextField name = (TextField) root.lookup("#name");
		
		primaryStage.setTitle("Create User");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		name.requestFocus();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}