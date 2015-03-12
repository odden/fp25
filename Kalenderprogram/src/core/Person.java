package core;

import java.util.ArrayList;

public class Person {
	
	private String username;
	private String name;
	private String email;
	private String phoneNumber;
	private ArrayList<Invitation> avtaler = new ArrayList<Invitation>();
	
	//Constructor
	public Person(String username, String name, String email, String phoneNumber){
		setUsername(username);
		setName(name);
		setEmail(email);
		setPhoneNumber(phoneNumber);
	}
	//avtaler
	public ArrayList<Appointment> getAvtaler() {
		ArrayList<Appointment> a=new ArrayList<Appointment>();
		for (Invitation i:avtaler){
			a.add(i.getAppointment());
		}
		return a;
	}
	
	public void addAvtale(Invitation avtale) {
		avtaler.add(avtale);
	}
	
	public boolean hasAvtale() {
		if(avtaler.isEmpty()){ 
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
		// TODO Auto-generated method stub
		
	}
}
