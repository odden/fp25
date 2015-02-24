package core;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class SCore {
	DBConnector dbc;
	public void init(){
		try {
			dbc = new DBConnector(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//System.out.println(getRoom(19,new Date(0)));
	}
	
	public String getRoom(int size,Date start,Date slutt){
		ResultSet rs1;
		ResultSet rs2;
		ArrayList<ResultSet> rooms;
		try {
			String statement = "SELECT 'romnr' FROM 'rom' WHERE 'plass' >= "+size;
			System.out.println(statement);
			rs1 = dbc.executeSQL("SELECT romnr FROM rom WHERE plass >= "+size);
			ArrayList<List<Object>> potentialRooms = resToList(rs1);
			for (List<Object> o:potentialRooms){
				
			}
			//rs2 = dbc.getQueryCondition("reservasjon", "romNr",, "romnr", "start","slutt");
			return resToList(rs1).toString();
		} catch (SQLException e) {
			e.printStackTrace();
			return "Ingen ledig da.";
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
