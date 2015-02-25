package core;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class SCore {
	DBConnector dbc;
	public void init(){
		try {
			dbc = new DBConnector(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date d = null;
		try {
			d = new java.sql.Date(sdf.parse("2015/02/25").getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(d.toString());
		System.out.println(getRoom(8,d,java.sql.Time.valueOf("11:00:00"),java.sql.Time.valueOf("13:00:00")));
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
			rs2= dbc.executeSQL("SELECT romNr FROM reservasjon WHERE romNr in ("+rooms+") AND dato BETWEEN '"+dato+" 00:00:00' AND '"+dato+" 00:00:00' AND start BETWEEN '"+start+"' AND '"+slutt+"' AND slutt BETWEEN '"+start+"' AND '"+slutt+"'");
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
			System.out.println(potentialRooms);
			return potentialRooms;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
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
