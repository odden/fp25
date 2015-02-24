package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import gui.LoggInnController;

public class LoggInn extends Application {
	
	private String brukernavn;
	private String passord;

	
	public String getBrukernavn() {
		return brukernavn;
	}

	public void setBrukernavn(String brukernavn) {
		this.brukernavn = brukernavn;
	}

	public String getPassord() {
		return passord;
	}

	public void setPassord(String passord) {
		this.passord = passord;
	}

	@Override
    public void start(Stage stage) throws Exception {
    	FXMLLoader fxmlLoader = new FXMLLoader();
    	fxmlLoader.setController(new LoggInnController());
        Parent root = FXMLLoader.load(getClass().getResource("LoggInn.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("LogInWindow");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
