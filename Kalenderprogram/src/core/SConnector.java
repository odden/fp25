package core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
	
	public String createAppointment(String host,String title,String room, Calendar date,String start, String end, List<String> invited){
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
		
		return null;
	}
	
	public ArrayList<String> getUsers(){
		String message = "getUsers";
		String response = connectToServer(message);
		String[] responseSplit = response.split("|");
		ArrayList<String> responseList = new ArrayList<String>(Arrays.asList(responseSplit));
		return responseList;
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
	
}

