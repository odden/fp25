package gui;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
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
import core.Invitation;
import core.Person;
import core.Appointment;
import core.SConnector;

public class Gui extends Application {
	private static Stage stage;
	Parent root;
	private LoggInnController logIn;
	private CalendarViewController cw;
	private Person user;
	private ArrayList<Person> users;
	private ArrayList<Appointment> thisAppointments = new ArrayList<Appointment>();
	public SConnector sc;

	public void init() {
		sc = new SConnector(this);
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
				cw = loader
						.getController();
				cw.initData(stage, this,users , user);
			}
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String tryCreateUser(String brukernavn, String passord, String navn, String email, int telefon){
		String response = sc.createUser(brukernavn, passord, navn, email, telefon);
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
		String response = sc.logIn(brukernavn, passord);
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
			this.user = new Person(response.split("::")[0],response.split("::")[1],response.split("::")[2],response.split("::")[3]);
			
			this.user.setVarsler(new ArrayList<String>(Arrays.asList(response.split("::")[4].split(";"))));
			ArrayList<String> users = sc.getUsers();
			this.users.add(user);
			for (String s : users) {
				if (s.equals("::")) {
				} else {
					String[] user = s.split("::");
					if (!user[0].equals(this.user.getUsername())) {
						Person p = new Person(user[0], user[1], user[2], user[3]);
						this.users.add(p);
						
					}
				}
			}
			ArrayList<String> appointments = sc.getAllAppointments();
			
			if(appointments !=null && !appointments.get(0).equals("")){
				
				for (String appointment : appointments) {
					String [] appointmentSplit = appointment.split("::");
					String appId = appointmentSplit[0];
					ArrayList<String> participants = new ArrayList<String>();
					participants = sc.getInvited(appId);
					HashMap<Person,Boolean>persons = new HashMap<Person,Boolean>();
					for (String user : participants) {
						String[] p = user.split("::");
						for (Person person : this.users) {
							if (person.getUsername().equals(p[0])) {
								persons.put(person,p[1].equals("NULL") ? null : Boolean.valueOf(p[1]));
							}
						}
					}
					Person host = null;
					for (Person person : this.users) {
						if (appointmentSplit[1].equals(person.getUsername())) {
							host = person;
						}
					}
					Appointment avtale = new Appointment(Integer.parseInt(appId), host, appointmentSplit[2], appointmentSplit[3],appointmentSplit[4].equals("NULL") ? 0 : Integer.parseInt(appointmentSplit[4]), appointmentSplit[5],appointmentSplit[6].substring(0,appointmentSplit[6].length()-3),appointmentSplit[7].substring(0,appointmentSplit[7].length()-3), persons);
					thisAppointments.add(avtale);
					host.addAppointment(avtale);
					for (Person person : new ArrayList<Person>(persons.keySet())) {
						person.addAppointment(avtale);
					}
				}
			}
			
			
			ArrayList<String> invitations = sc.getInvitations(user.getUsername());
			if (invitations != null && !invitations.get(0).equals("")){
				for (String s: invitations){
					String[] invitation = s.split("::");
					for (Appointment  a: thisAppointments) {
						if (a.getId()==Integer.parseInt(invitation[1])) {
							Invitation i = new Invitation(this.user,a,Boolean.valueOf(invitation[2]),Integer.parseInt(invitation[3]),Boolean.valueOf(invitation[4]),this);
							user.addInvitation(i);
						}
					}
				}
			}
			
			switchSceneContent("CalenderView.fxml");
			return "Ok";
			}
	}

	public ArrayList<String> getRoom(String date, String start, String slutt, int size){
		ArrayList<String> rooms = sc.getRoom(size, date, start, slutt);
		ArrayList<String> roomList = new ArrayList<String>();
		for (String room : rooms) {
			roomList.add(room.split("::")[0]);
		}
		return roomList;
	}
	
	public int tryCreateAppointment( Person host, String title, String sted, String rom, String date, String start,String slutt, ArrayList<Person> participants) {
		ArrayList<String> invited = new ArrayList<String>();
		for (Person p : participants) {
			invited.add(p.getUsername());
		}
		int id = 0;
		id = Integer.parseInt(sc.createAppointment(host.getUsername(), title, sted,rom, date, start, slutt, invited));
		return id;
	}
	
	public boolean tryEditAppointment(Appointment avtale, int id, Person host, String title, String sted, String rom, String date, String start,String slutt, HashMap<Person,Boolean> participants) {
		HashMap<String,Boolean> usernames = new HashMap<String,Boolean>();
		System.out.println(participants+"-participants i gui");
		System.out.println(avtale.getParticipants()+"-allerede inviterte gui");
		for (Person person : new ArrayList<Person>(participants.keySet())) {
			if (!avtale.getParticipants().contains(person)) {
				usernames.put(person.getUsername(),participants.get(person));
			}
		}
		System.out.println(usernames+"-usernames i gui");
		boolean result1 = Boolean.valueOf(sc.invite(usernames, Integer.toString(id)));
		boolean result2 = Boolean.valueOf(sc.editAppointment(id, host.getUsername(), title, sted, rom, date, start, slutt, "endring"));
		return result1 && result2;
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
	public void runAlarm(Appointment appointment, int timer) {
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				cw.runAlarm(appointment, timer);
				
			}
			
		});
	}
}
