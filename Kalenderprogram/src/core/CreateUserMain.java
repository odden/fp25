package core;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class CreateUserMain extends Application {

	@Override
	public void start(Stage primaryStage) {
		Parent root;
		Scene scene;
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gui.fxml"));
			fxmlLoader.setController(this);
			root = (Parent) fxmlLoader.load();
			
			scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
