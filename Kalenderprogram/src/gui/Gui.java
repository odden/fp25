package gui;

import java.io.IOException;
import java.util.ArrayList;

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
	@SuppressWarnings("unused")
	private String brukernavn;
	private ArrayList<Person> users;
	private ArrayList<Appointment> myAppointments;

	public void init() {
		this.core = new PCore(this);
	}

	public ArrayList<Person> getUsers() {
		return users;
	}

	public ArrayList<Appointment> getAppointments() {
		return myAppointments;
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
				controller.initData(stage, this,users,myAppointments, brukernavn);
			}
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String tryCreateUser(String brukernavn, String passord, String email, String navn, int telefon){
		String response = core.sc.createUser(brukernavn, passord, navn, email, telefon);
		System.out.println(response);
		if (response == null){
			return "yipyip";
		}else{
			switchSceneContent("LoggInn.fxml");
		}
		
		return "oki"; 
	}

	public String tryLogIn(String brukernavn, String passord) {
		String response = core.sc.logIn(brukernavn, passord);
		System.out.println(response);
		this.users = new ArrayList<Person>();
		if (response == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Feilmelding");
			alert.setHeaderText("Ugyldig brukernavn og/eller passord");
			alert.setContentText("Vennligst prøv igjen");

			alert.showAndWait();
            
			return "aua";
		} else {
			this.brukernavn = response.split(":")[0];
			ArrayList<String> users = core.sc.getUsers();
			for (String s : users) {
				if (s.equals(":")) {
				} else {
					String[] user = s.split(":");
					Person p = new Person(user[0], user[1], user[2], user[3]);
					this.users.add(p);
				}
			}
			this.myAppointments = new ArrayList<Appointment>();
			ArrayList<String> myAppointments = core.sc
					.getAppointments(brukernavn);
			for (String s : myAppointments) {
				if (s.equals(":")) {
				} else {
					String[] appointments = s.split(":");
					Appointment a = new Appointment();
					this.myAppointments.add(a);
				}
			}
			System.out.println(this.users);

			switchSceneContent("CalenderView.fxml");
			return "Ok";
		}
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
