package core;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Appointment {
	
	private LocalDate date;
	private String start;
	private String slutt;
	private String rom;
	private String title;
	private ArrayList<Person> participants = new ArrayList<Person>();
	private Person host;
	private String beskrivelse;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBeskrivelse() {
		return beskrivelse;
	}

	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse = beskrivelse;
	}

	public void init(LocalDate date, String rom, Person host){
		this.rom = rom;
		this.date = date;
		this.host = host;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getSlutt() {
		return slutt;
	}

	public void setSlutt(String slutt) {
		this.slutt = slutt;
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getRom() {
		return rom;
	}

	public void setRom(String rom) {
		this.rom = rom;
	}
	
	public String toString() {
		return title;
	}
	
	
	
	
}
