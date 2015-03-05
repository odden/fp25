package gui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Main extends Application {
	Stage stage; 
	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		try {
			Parent root = FXMLLoader.load(getClass().getResource("LoggInn.fxml"));
			Scene scene = new Scene(root);
			TextField brukernavn = (TextField) root.lookup("#brukernavn");

			primaryStage.setTitle("Logg Inn");
			primaryStage.setScene(scene);
			primaryStage.show();

			brukernavn.requestFocus();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	

	
	public static void main(String[] args) {
		launch(args);
	}
}
