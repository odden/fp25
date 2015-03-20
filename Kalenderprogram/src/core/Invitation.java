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
		
		try {
			if (timer != 0){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				alarm = (df.parse(a.getWhen()));
				alarm.setTime(alarm.getTime()-(timer*60*1000));
				if (alarm.after(new Date())){					
					appAlarm.cancel();
					this.cancel();
					appAlarm = new Timer();
					appAlarm.schedule(this, alarm);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
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
					appAlarm.schedule(this, alarm);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
}
