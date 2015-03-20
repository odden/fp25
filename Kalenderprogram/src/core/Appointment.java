package core;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Appointment {
	
	private int id;
	private LocalDate date;
	private String start;
	private String slutt;
	private Integer rom;
	private String sted;
	private String title;
	private HashMap<Person,Boolean> participants = new HashMap<Person,Boolean>();
	private Person host;
	
	public Appointment(){
		
	}
	
	public Appointment(int id, Person host, String title, String sted, int rom, String date, String start,String slutt, HashMap<Person,Boolean> participants){
		this.id = id;
		this.start=start;
		this.slutt=slutt;
		this.sted=sted;
		this.rom = rom;
		this.title=title;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		this.date = LocalDate.parse(date, formatter);
		this.host=host;
		this.participants = participants;
		
	}

	
	
	public int getId(){
		return id;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
		return new ArrayList<Person>(participants.keySet());
	}
	
	public HashMap<Person,Boolean> getInvited(){
		return participants;
	}
	public void addParticipant(Person participant) {
		if (!participants.containsKey(participant)) {
			this.participants.put(participant, null);
		}
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
		return rom == null ? 0:rom;
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
		return date.toString()+" "+start+":00";
	}
	
}
