package core;

import java.util.ArrayList;

public class Person {
	
	private String name;
	private String username;
	private String email;
	private String phoneNumber;
	private String password;
	private ArrayList<Appointment> avtaler = new ArrayList<Appointment>();
	
	//Constructor
	public Person(String name, String username, String email, String phoneNumber, String password){
		setName(name);
		setUsername(username);
		setEmail(email);
		setPhoneNumber(phoneNumber);
		setPassword(password);
	}
	//avtaler
	public ArrayList<Appointment> getAvtaler() {
		return avtaler;
	}
	
	public void addAvtale(Appointment avtale) {
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
	public String getPassword(){
		return password;
	}
	public void setPassword(String password){
		this.password = password;
	}
	
	@Override
	public String toString() {
		return username;
	}
}
