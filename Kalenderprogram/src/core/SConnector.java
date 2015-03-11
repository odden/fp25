package core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class SConnector {
	PCore core;
	public SConnector(PCore core){
		this.core = core;
	}
	public String logIn(String userName, String password){
		String message = "logIn:" + userName + ":" + password;
		String response = connectToServer(message);
		return response;
	}
	
	public String createUser(String userName, String password, String name, String email, int tlf){
		String message = "createUser:" + userName + ":" + password + ":" + name + ":" + email + ":" + Integer.toString(tlf);
		String response = connectToServer(message);
		return response;
	}
	
	public String createAppointment(String host,String title,String room, String date,String start, String end, List<String> invited){
		String message = "createAppointment:" + host + ":" + title + ":" + room + ":" + date.toString() + ":" + start + ":" + end + ":";
		for (String string : invited) {
			message += string + ":";
		}
		String response = connectToServer(message);
		return response;
	}
	
	public ArrayList<String> getAppointments(String user){
		String message = "getAppointments:" + user;
		String response = connectToServer(message);
		return convert(response);
	}
	
	public ArrayList<String> getUsers(){
		String message = "getUsers";
		String response = connectToServer(message);
		return convert(response);
	}
	
	public String invite(List<String>usernames,String host,String date,String start){
		String message = "invite" + ":" + host + ":" + date + ":" + start + ":";
		for (String string : usernames) {
			message += string + ":";
		}
		String response = connectToServer(message);
		return response;
	}
	
	public void setStatus(String user, String host,String date,String start, Boolean status){
		String message = "setStatus" + ":" + user + ":" + host + ":" + date + ":" + start + ":" + status.toString();
		connectToServer(message);
	}
	
	public ArrayList<String> getRoom(int size,String date, String start, String slutt){
		String message = "getRoom:" + Integer.toString(size) + ":" + date + ":" + start + ":" + slutt;
		String response = connectToServer(message);
		return convert(response);
	}
	
	
	private String connectToServer(String message){
		try {
			String sentence = message;
			String response;
			Socket clientSocket = new Socket("localhost", 1200);
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			outToServer.writeBytes(sentence + '\n');
			response = inFromServer.readLine();
			System.out.println("FROM SERVER: " + response);
			clientSocket.close();
			return response;
		}  catch (IOException e) {
			System.out.println("Funk itj");
		}
		return null;
	}
	
	private ArrayList<String> convert(String response){
		if (response != null){
			String[] responseSplit = response.split(";");
			ArrayList<String> responseList = new ArrayList<String>();
			for (String string : responseSplit) {
				responseList.add(string);
			}
			return responseList;
		}
		return null;
	}
	
}

