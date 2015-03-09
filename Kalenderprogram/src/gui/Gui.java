package gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import core.PCore;

public class Gui extends Application {
	PCore core;
	private static Stage stage; 
	Parent root;
	private LoggInnController logIn;
	
	public void initialize(){
		this.core = new PCore(this);
	}
	
	public void switchSceneContent(String fxml){
		try {
			final FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
			
			root = loader.load();
			stage.setScene(new Scene(root));
			if (fxml.equals("CreateUser.fxml")){
				final CreateUserController controller = loader.getController();
				controller.initData(stage,this);
			}
			else{
				logIn = loader.getController();
			    logIn.initData(stage,this);
			}
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String tryLogIn(String brukernavn,String passord){
		return core.sc.logIn(brukernavn,passord);
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.stage = primaryStage;
		try {
			final FXMLLoader loader = new FXMLLoader(getClass().getResource("LoggInn.fxml"));
			
			root = loader.load();
			Scene scene = new Scene(root);
			logIn = loader.getController();
		    logIn.initData(stage,this);
			TextField brukernavn = (TextField) root.lookup("#brukernavn");
			primaryStage.setTitle("Logg Inn");
			primaryStage.setScene(scene);
			primaryStage.show();
			brukernavn.requestFocus();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void main(String args[]){
		launch();
	}
}
