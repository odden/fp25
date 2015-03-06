package gui;

import core.PCore;

public class Gui {
	PCore core;
	LoggInn logIn;
	public Gui(PCore core){
		this.core = core;
	}
	
	public void init(){
		logIn = new LoggInn();
	}
}
