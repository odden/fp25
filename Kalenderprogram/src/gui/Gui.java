package gui;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import core.PCore;
import core.Person;

public class Gui extends Application {
	PCore core;
	private static Stage stage; 
	Parent root;
	private LoggInnController logIn;
	private String brukernavn;
	private ArrayList<Person> users;
	public void init(){
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
			else if (fxml.equals("LoggInn.fxml")){
				logIn = loader.getController();
			    logIn.initData(stage,this);
			}
			else if(fxml.equals("CalenderView.fxml")) {
				final CalendarViewController controller = loader.getController();
				controller.initData(stage,this);
			}
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String tryLogIn(String brukernavn,String passord){
			String response = core.sc.logIn(brukernavn,passord);
			System.out.println(response);
			this.users = new ArrayList<Person>();
			if (response == null){
				return "aua";
			}
			else{
				switchSceneContent("CalenderView.fxml");
				this.brukernavn = response.split(":")[0];
				ArrayList<String> users = core.sc.getUsers();
				for (String s: users){
					if (s.equals(":")){}
					else{
						String[] user = s.split(":");
						Person p = new Person(user[0], user[1], user[2], user[3]);
						this.users.add(p);
					}
				}
				
				System.out.println(this.users);
				
				return "Ok";
			}
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
