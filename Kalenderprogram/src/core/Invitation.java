package core;

import gui.Gui;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Invitation extends TimerTask {
	private Person me;
	private Appointment appointment;
	private Gui gui;
	boolean svar;
	boolean visibility;
	private Date alarm;
	private Timer appAlarm = new Timer();
	int timer;
	public Invitation(Person p,Appointment a,boolean svar,int timer, boolean visibility, Gui gui){
		me = p;
		this.timer = timer;
		appointment = a;
		this.svar = svar;
		this.visibility = visibility;
		this.gui = gui;
		
		setAlarm(timer);
	}
	
	public Invitation(int timer, Gui g,Appointment a){
		this.timer = timer;
		this.gui = g;
		this.appointment = a;
	}
	public Appointment getAppointment() {
		return appointment;
	}

	@Override
	public void run() {
		gui.runAlarm(appointment,timer);
	}
	
	public void setAlarm(int timer){
		try {
			if (timer != 0){
				this.timer = timer;
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				alarm = (df.parse(appointment.getWhen()));
				alarm.setTime(alarm.getTime()-(timer*60*1000));
				if (alarm.after(new Date())){					
					appAlarm.cancel();
					this.cancel();
					appAlarm = new Timer();
					Invitation i = new Invitation(timer,gui,this.appointment);
					appAlarm.schedule(i, alarm);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
}
