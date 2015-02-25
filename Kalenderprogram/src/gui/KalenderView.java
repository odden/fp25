package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class KalenderView extends Application {
	
	
	@Override
    public void start(Stage stage) throws Exception {
    	FXMLLoader fxmlLoader = new FXMLLoader();
    	fxmlLoader.setController(new LoggInnController());
        Parent root = FXMLLoader.load(getClass().getResource("KalenderView.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Kalender");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
