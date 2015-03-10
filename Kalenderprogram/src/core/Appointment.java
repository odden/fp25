package core;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Appointment {
	
	private LocalDate date;
	private String start;
	private String slutt;
	private int rom;
	private String sted;
	private String title;
	private ArrayList<Person> participants = new ArrayList<Person>();
	private Person host;
	private String beskrivelse;
	
	
	public Appointment(){
		
	}
	
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

	public void init(LocalDate date, int rom, Person host){
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

	public int getRom() {
		return rom;
	}

	public void setRom(int rom) {
		this.rom = rom;
	}
	
	public String toString() {
		return title;
	}

	public String getSted() {
		return sted;
	}

	public void setSted(String sted) {
		this.sted = sted;
	}
	
	
	
	
}
