package core;

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
	
	boolean svar;
	boolean visibility;
	private Date alarm;
	public Invitation(Person p,Appointment a, int timer, boolean svar, boolean visibility){
		me = p;
		appointment = a;
		this.svar = svar;
		this.visibility = visibility;
		
		
		try {
			if (timer != 0){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				alarm = (df.parse(a.getWhen()));
				alarm.setTime(alarm.getTime()-(timer*60*1000));
				Timer appAlarm = new Timer();
				appAlarm.schedule(this, alarm);
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
		me.runAlarm(appointment);
	}
	
}
