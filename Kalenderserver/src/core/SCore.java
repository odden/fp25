package core;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
public class SCore {
	DBConnector dbc;
	PConnector pc;
	public void init(){
		pc = new PConnector(this);
		try {
			dbc = new DBConnector(this);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date d = null;
		try {
			d = new java.sql.Date(sdf.parse("2015/02/26").getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal=Calendar.getInstance(); 
		cal.setTime(d); 
		//System.out.println(getRoom(8,d,java.sql.Time.valueOf("11:00:00"),java.sql.Time.valueOf("13:00:00")));
		//System.out.println(logIn("stefanborg","stefanPW"));
		//createUser("herman","hpw","HP PLLLL","emmmm",423423);
		//createAppointment("stefanborg","Kake","322",cal,"13:00:00","15:00:00",Arrays.asList("stefanborg"));
		//setStatus("herman", "stefanborg", "2015-02-26", "13:00:00",false);
		//System.out.println(getAppointments("herman"));
		//editAppointment(4,"kakemann","Kakefest for noen","322","2015-02-28","16:45:00","19:00:00");
	}
	public List<Object> logIn(String username, String password){
		//Sjekker om oppgit info er korrekt, hvis ja send bruker info og OK
		ResultSet login;
		try {
			login = dbc.executeSQL("SELECT brukernavn,navn,epost,tlfnr FROM bruker WHERE brukernavn = '"+username+"' AND passord = '"+password+"'");
			List<Object> user = resToList(login).get(0);
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Boolean createUser(String username,String password,String name,String email,int tlf){
		try {
			dbc.insertRow("bruker", username,password,name,email,tlf);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Boolean createAppointment(String vert,String title,int room, Calendar dato,String start, String slutt, List<String> invited){
		//Avtale med reservert rom
		try {
			dbc.insertRow("avtale", null,vert,title,null,room,dato,start,slutt,null);
			ResultSet rs = dbc.executeSQL("SELECT MAX( idavtale ) AS idavtale FROM avtale");
			Object max = resToList(rs).get(0).get(0);
			if (invited != null){
				for (String bn:invited){
					dbc.insertRow("bruker_has_avtale", bn,max,false);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public Boolean createAppointment(String vert,String title,String sted, Calendar dato,String start, String slutt, List<String> invited){
		//Avtale med bare stedsnavn	
			try {
				dbc.insertRow("avtale", null,vert,title,sted,null,dato,start,slutt,null);
				ResultSet rs = dbc.executeSQL("SELECT MAX( idavtale ) AS idavtale FROM avtale");
				Object max = resToList(rs).get(0).get(0);
				if (invited != null){
					for (String bn:invited){
						dbc.insertRow("bruker_has_avtale", bn,max,false);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
	
	public boolean invite(List<String>usernames,String vert,String dato,String start){
		//Inviterer en eller flere brukere til et arrangement
		int id = getAppointmentID(vert, dato, start);
		try {
			if (id != 0){
				for (String n:usernames){
						dbc.insertRow("bruker_has_avtale",n,id,false);
				}
				return true;
			}
			else{
				return false;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void setStatus(String bruker, String vert,String dato,String start, Boolean status){
		//Setter invitasjon status til 'bruker' til 'status'
		int id = getAppointmentID(vert, dato, start);
		try {
			dbc.executeSQL("UPDATE bruker_has_avtale SET bruker_svar = "+status+" WHERE avtale_idavtale = '"+id+"' AND bruker_brukernavn = '"+bruker+"'");
			if (status == false){
				ResultSet rs = dbc.getQueryCondition("bruker_has_avtale", "avtale_idavtale", id);
				ArrayList<List<Object>> ids = resToList(rs);
				String appIds = ",";
				for (List<Object> o:ids){
					appIds+= o.get(0).toString()+",";
				}
				String tittel = (String) resToList(dbc.getQueryCondition("avtale", "idavtale", id, "tittel")).get(0).get(0);
				String endring = " - " + bruker + " har avslått invitasjonen til "+tittel+" - ";
				dbc.executeSQL("UPDATE bruker_has_avtale SET varsel_endring = varsel_endring + "+endring+" WHERE avtale_idavtale IN ("+appIds+")");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getAppointmentID(String vert,String dato,String start){
		ResultSet rs;
		try {
			rs = dbc.executeSQL("SELECT idavtale FROM avtale WHERE vert_brukernavn = '"+vert+"' AND dato = '"+dato+" 00:00:00' AND start = '"+start+"'");
			return (int) resToList(rs).get(0).get(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public ArrayList<List<Object>> getRoom(int size,Date dato,Time start, Time slutt){
		//Gir tilbake en liste med lister over rom som er ledig til den tid
		
		ResultSet rs1;
		ResultSet rs2;
		try {
			String statement = "SELECT 'romnr' FROM 'rom' WHERE 'plass' >= "+size;
			System.out.println(statement);
			rs1 = dbc.executeSQL("SELECT romnr,plass FROM rom WHERE plass >= "+size);
			ArrayList<List<Object>> potentialRooms = resToList(rs1);
			String rooms = ",";
			for (List<Object> o:potentialRooms){
				rooms+= o.get(0).toString()+",";
			}
			rooms = rooms.substring(1,rooms.length()-1);
			System.out.println(rooms);
			rs2= dbc.executeSQL("SELECT romNr FROM avtale WHERE romNr in ("+rooms+") AND dato BETWEEN '"+dato+" 00:00:00' AND '"+dato+" 00:00:00' AND start BETWEEN '"+start+"' AND '"+slutt+"' AND slutt BETWEEN '"+start+"' AND '"+slutt+"'");
			ArrayList<List<Object>> invalidRooms = resToList(rs2);
			System.out.println(potentialRooms);
			ArrayList<List<Object>> tobeRemoved = new ArrayList<List<Object>>();
			
			for (List<Object> o:potentialRooms){
				for (List<Object> i:invalidRooms){
					if (o.get(0).toString().equals(i.get(0).toString())){
						tobeRemoved.add(o);
					}
				}
			}
			potentialRooms.removeAll(tobeRemoved);
			return potentialRooms;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<List<Object>> getUsers(){
		ResultSet rs;
		try {
			rs = dbc.getQuery("bruker");
			return resToList(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public ArrayList<List<Object>> getAppointments(){
		ResultSet rs;
		try {
			rs = dbc.getQuery("avtale");
			return resToList(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<List<Object>> getAppointments(String bruker){
		ResultSet rs;
		
		try {
			rs = dbc.getQueryCondition("bruker_has_avtale", "bruker_brukernavn", bruker, "avtale_idavtale");
			ArrayList<List<Object>> ids = resToList(rs);
			String appIds = ",";
			for (List<Object> o:ids){
				appIds+= o.get(0).toString()+",";
			}
			appIds = appIds.substring(1,appIds.length()-1);
			System.out.println(appIds);
			rs = dbc.executeSQL("SELECT * FROM avtale WHERE idavtale in ("+appIds+")");
			ids = resToList(rs);
			rs = dbc.getQueryCondition("avtale", "vert_brukernavn", bruker);
			ids.addAll(resToList(rs));
			return ids;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean editAppointment(int id, String vert, String title, String sted, String room,String dato,String start, String slutt, String endring){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(dato));
			dbc.editRow("avtale", id,null,vert,title,sted, room,cal,start,slutt,endring);
			ResultSet rs = dbc.getQueryCondition("bruker_has_avtale", "avtale_idavtale", id);
			ArrayList<List<Object>> ids = resToList(rs);
			String appIds = ",";
			for (List<Object> o:ids){
				appIds+= o.get(0).toString()+",";
			}
			dbc.executeSQL("UPDATE bruker_has_avtale SET varsel_endring = varsel_endring + "+endring+" WHERE avtale_idavtale IN ("+appIds+")");
			return true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public boolean setAlarm(String bruker,String id, String tid){
		try {
			dbc.executeSQL("UPDATE bruker_has_avtale SET alarm = "+tid+" WHERE avtale_idavtale = '"+id+"' AND bruker_brukernavn = '"+bruker+"'");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	public boolean setVisible(String brukernavn, String avtaleid, boolean svar){
		try {
			dbc.executeSQL("UPDATE bruker_has_avtale SET synlig = "+svar+" WHERE avtale_idavtale = '"+avtaleid+"' AND bruker_brukernavn = '"+brukernavn+"'");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static ArrayList<List<Object>> resToList(ResultSet resultSet) {
		
		ArrayList<List<Object>> results = new ArrayList<List<Object>>();
		try {
			int columnCount = resultSet.getMetaData().getColumnCount();
			
			while (resultSet.next()) {
				ArrayList<Object> row = new ArrayList<Object>(columnCount);
				
				int i = 1;
				while (i <= columnCount) {
					row.add(resultSet.getObject(i++));
				}
				results.add(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
		
	}
	public static void main(String[] args) {
		SCore core = new SCore();
		core.init();
	}

}
