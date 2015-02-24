package core;

import java.util.Date;

public class Appointment {
	
	private Date date;
	private String rom;
	
	public void init(Date date, String rom){
		this.rom = rom;
		this.date = date;
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
