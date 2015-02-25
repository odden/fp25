package core;

import java.util.ArrayList;
import java.util.Date;

public class Appointment {
	
	private Date date;
	private String start;
	private String slutt;
	private String rom;
	private String title;
	private ArrayList<Person> participants;
	private Person host;
	
	public void init(Date date, String rom, Person host){
		this.rom = rom;
		this.date = date;
		this.host = host;
	}

	public ArrayList<Person> getParticipants() {
		return participants;
	}

	public void addParticipant(Person participant) {
		this.participants.add(participant);
	}
	
	public void removeParticipant(Person participant){
		this.participants.remove(participant);
	}

	public Person getHost() {
		return host;
	}

	public void setHost(Person host) {
		this.host = host;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getRom() {
		return rom;
	}

	public void setRom(String rom) {
		this.rom = rom;
	}
	
	
	
	
}
