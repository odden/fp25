package core;

import java.util.ArrayList;

public class Person {
	
	private String username;
	private String name;
	private String email;
	private String phoneNumber;
	private ArrayList<Invitation> invitations = new ArrayList<Invitation>();
	private ArrayList<Appointment> myAppointments = new ArrayList<Appointment>();
	private ArrayList<String> varsler = new ArrayList<String>();
	
	//Constructor
	public Person(String username, String name, String email, String phoneNumber){
		setUsername(username);
		setName(name);
		setEmail(email);
		setPhoneNumber(phoneNumber);
	}
	//avtaler

	
	public void addInvitation(Invitation i){
		invitations.add(i);
	}
	
	public boolean hasAvtale() {
		if(myAppointments.isEmpty()){ 
			return false;
		} else {
			return true;
		}
	}

	//Navn
	public String getName(){
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	//Brukernavn
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	//Epostadresse
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	//Telefonnr
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	//Passord
	
	@Override
	public String toString() {
		return username;
	}
	public void runAlarm(Appointment appointment) {
		System.out.println("ALARMALARMALARMALARMALARMALARM");
		System.out.println("ALARMALARMALARMALARMALARMALARM");
		System.out.println("ALARMALARMALARMALARMALARMALARM");
		System.out.println("ALARMALARMALARMALARMALARMALARM");
	}
	public ArrayList<Appointment> getMyAppointments() {
		return myAppointments;
	}
	public void setAllAppointments(ArrayList<Appointment> myAppointments) {
		this.myAppointments = myAppointments;
	}
	public void removeAppointment(Appointment a){
		this.myAppointments.remove(a);
	}
	public void addAppointment(Appointment a){
		if (! this.myAppointments.contains(a)) {
			this.myAppointments.add(a);
		}
	}
	public ArrayList<String> getVarsler() {
		return varsler;
	}
	public void setVarsler(ArrayList<String> varsler) {
		this.varsler = varsler;
	}


	public ArrayList<Invitation> getInvitations() {
		return invitations;
	}


	public void setInvitations(ArrayList<Invitation> invitations) {
		this.invitations = invitations;
	}
	
}
