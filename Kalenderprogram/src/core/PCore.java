package core;

import gui.Gui;

public class PCore {
	private Gui gui;
	private SConnector sc;
	public void init(){
		gui = new Gui(this);
		sc = new SConnector(this);
		
	}
	
	public static void main(String[] args) {
		PCore core = new PCore();
		core.init();
		
	}

}
