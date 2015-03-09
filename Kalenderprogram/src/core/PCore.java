package core;

import gui.Gui;

public class PCore {
	private Gui gui;
	public SConnector sc;
	
	public PCore(Gui gui){
		this.gui = gui;
		sc = new SConnector(this);
	}
	
}
