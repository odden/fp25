package gui;

import core.PCore;

public class Gui {
	PCore core;
	private LoggInnController logIn;
	public Gui(PCore core){
		this.core = core;
		logIn = new LoggInnController(this);
		logIn.start();
	}
	public String logIn(String brukernavn,String passord){
		return core.sc.logIn(brukernavn,passord);
	}
}
