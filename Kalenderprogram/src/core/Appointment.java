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
	private String host;
	private String beskrivelse;
	
	
	public Appointment(String start, String slutt, String sted, String title, String beskrivelse, LocalDate date, String host, ArrayList<Person> participants){
		this.start=start;
		this.slutt=slutt;
		this.sted=sted;
		this.title=title;
		this.beskrivelse=beskrivelse;
		this.date =date;
		this.host=host;
		this.participants.addAll(participants);
		
	}
	
	public Appointment(String start, String slutt, int rom, String title, String beskrivelse, LocalDate date, String host, ArrayList<Person> participants){
		this.start=start;
		this.slutt=slutt;
		this.rom=rom;
		this.title=title;
		this.beskrivelse=beskrivelse;
		this.date =date;
		this.host=host;
		this.participants.addAll(participants);
		
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

	public void init(LocalDate date, int rom, String host){
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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
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
	
	public String getWhen(){
		return date.toString()+" "+start;
	}
	
	
	
}
