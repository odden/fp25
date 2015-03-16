package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import core.PCore;
import core.Person;
import core.Appointment;

public class Gui extends Application {
	PCore core;
	private static Stage stage;
	Parent root;
	private LoggInnController logIn;
	private Person user;
	private ArrayList<Person> users;

	public void init() {
		this.core = new PCore(this);
	}

	public ArrayList<Person> getUsers() {
		return users;
	}

	public void switchSceneContent(String fxml) {
		try {
			final FXMLLoader loader = new FXMLLoader(getClass().getResource(
					fxml));

			root = loader.load();
			stage.setScene(new Scene(root));
			if (fxml.equals("CreateUser.fxml")) {
				final CreateUserController controller = loader.getController();
				controller.initData(stage, this);
			} else if (fxml.equals("LoggInn.fxml")) {
				logIn = loader.getController();
				logIn.initData(stage, this);
			} else if (fxml.equals("CalenderView.fxml")) {
				final CalendarViewController controller = loader
						.getController();
				controller.initData(stage, this,users , user);
			}
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String tryCreateUser(String brukernavn, String passord, String navn, String email, int telefon){
		String response = core.sc.createUser(brukernavn, passord, navn, email, telefon);
		System.out.println(response);
		if (response == null){
			return "yipyip";
		}
		else if (response.equals("false:")){
			return "tatt";
		}
		else{
			switchSceneContent("LoggInn.fxml");
		}
		
		return "oki"; 
	}

	public String tryLogIn(String brukernavn, String passord) {
		String response = core.sc.logIn(brukernavn, passord);
		System.out.println(response + "----");
		this.users = new ArrayList<Person>();
		if (response == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Feilmelding");
			alert.setHeaderText("Ugyldig brukernavn og/eller passord");
			alert.setContentText("Vennligst pr\u00F8v igjen");

			alert.showAndWait();
            
			return "aua";
		} else if (response.equals("\n") || response.equals("")){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Feilmelding");
			alert.setHeaderText("Ugyldig brukernavn og/eller passord");
			alert.setContentText("Vennligst pr\u00F8v igjen");

			alert.showAndWait();
            
			return "aua";
		}
		else{
			this.user = new Person(response.split(":")[0],response.split(":")[1],response.split(":")[2],response.split(":")[3]);
			ArrayList<String> users = core.sc.getUsers();
			this.users.add(user);
			for (String s : users) {
				if (s.equals(":")) {
				} else {
					String[] user = s.split(":");
					if (!user[0].equals(this.user.getUsername())) {
						Person p = new Person(user[0], user[1], user[2], user[3]);
						this.users.add(p);
						
					}
				}
			}
			for (Person person : this.users) {
				
				ArrayList<Appointment> myAppointments = new ArrayList<Appointment>();
				ArrayList<String> myApps = core.sc
						.getAppointments(person.getUsername());
				if (myApps != null){
					if (!myApps.get(0).equals("")){
						for (String s : myApps) {
							if (s.equals(":")) {
							} else {
								String[] appointments = s.split(":");
								ArrayList<String> invited = core.sc.getInvited(appointments[0]);
								System.out.println(invited);
								ArrayList<Person> participants = new ArrayList<Person>();
								Person host = null;
								for (Person p: this.users){
									if (invited != null && invited.contains(p.getUsername())){
										participants.add(p);
									}
									if (p.getUsername().equals(appointments[1])) {
										host = p;
									}
								}
								int room;
								try{
									room = Integer.parseInt(appointments[4]);
								} catch(NumberFormatException e){
									room = 0;
								}
								Appointment a = new Appointment(Integer.parseInt(appointments[0]),host,appointments[2],appointments[3],room,appointments[5],appointments[6],appointments[7],participants);
								myAppointments.add(a);
							}
						}
					}
				}
				person.setAllAppointments(myAppointments);
			}
			System.out.println(this.users);
			switchSceneContent("CalenderView.fxml");
			return "Ok";
			}
	}

	public ArrayList<String> getRoom(String date, String start, String slutt, int size){
		return core.sc.getRoom(size, date, start, slutt);
	}
	
	
	
	
	@SuppressWarnings("static-access")
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.stage = primaryStage;
		try {
			final FXMLLoader loader = new FXMLLoader(getClass().getResource(
					"LoggInn.fxml"));

			root = loader.load();
			Scene scene = new Scene(root);
			logIn = loader.getController();
			logIn.initData(stage, this);
			TextField brukernavn = (TextField) root.lookup("#brukernavn");
			primaryStage.setTitle("Logg Inn");
			primaryStage.setScene(scene);
			primaryStage.show();
			brukernavn.requestFocus();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {
		launch();
	}
}
