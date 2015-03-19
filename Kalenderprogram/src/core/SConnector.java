package core;

import gui.Gui;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SConnector {
	Gui core;
	public SConnector(Gui core){
		this.core = core;
	}
	public String logIn(String userName, String password){
		String message = "logIn::" + userName + "::" + password;
		String response = connectToServer(message);
		return response;
	}
	
	public String createUser(String userName, String password, String name, String email, int tlf){
		String message = "createUser::" + userName + "::" + password + "::" + name + "::" + email + "::" + Integer.toString(tlf);
		String response = connectToServer(message);
		return response;
	}
	
	public String createAppointment(String host,String title,String sted, String room, String date,String start, String end, ArrayList<String> invited){
		String message = "createAppointment::" + host + "::" + title + "::"+sted+"::" + room + "::" + date + "::" + start + "::" + end + "::";
		for (String string : invited) {
			message += string + "::";
		}
		String response = connectToServer(message);
		return response;
	}
	
	public ArrayList<String> getAppointments(String user){
		String message = "getAppointments::" + user;
		String response = connectToServer(message);
		return convert(response);
	}
	public ArrayList<String> getInvitations(String user){
		String message = "getInvitations::" + user;
		String response = connectToServer(message);
		return convert(response);
	}
	
	public ArrayList<String> getAllAppointments(){
		String message = "getAllAppointments::";
		String response = connectToServer(message);
		return convert(response);
	}
	
	public ArrayList<String> getUsers(){
		String message = "getUsers";
		String response = connectToServer(message);
		return convert(response);
	}
	
	public String deleteAppointment(int id){
		String message = "deleteAppointment::"+id;
		String response = connectToServer(message);
		return response;
	}
	
	public String invite(List<String> usernames,String id){
		String message = "invite" + "::" + id+"::";
		for (String string : usernames) {
			message += string + "::";
		}
		String response = connectToServer(message.substring(0, message.length() - 2));
		return response;
	}
	
	public void setStatus(String user, int id, Boolean status){
		String message = "setStatus" + "::" + user + "::" +id+ "::" + status.toString();
		connectToServer(message);
	}
	
	public ArrayList<String> getRoom(int size,String date, String start, String slutt){
		String message = "getRoom::" + Integer.toString(size) + "::" + date + "::" + start + "::" + slutt;
		String response = connectToServer(message);
		return convert(response);
	}
	
	public String editAppointment(int id, String host, String title, String sted, String room,String dato,String start, String slutt, String endring){
		String message = "editAppointment" + "::" + Integer.toString(id) + "::" + host + "::" + title + "::" + sted + "::" + room + "::" + dato + "::" + start + "::" + slutt + "::" + endring;
		String response = connectToServer(message);
		return response;
	}
	
	public ArrayList<String> getInvited(String appId){
		String message = "getInvited::" + appId;
		String response = connectToServer(message);
		return convert(response);
	}
	
	public ArrayList<String> getGroups(){
		String message = "getGroups";
		String response = connectToServer(message);
		return convert(response);
	}
	
	public ArrayList<String> getStatus(String appId){
		String message = "getStatus::" + appId;
		String response = connectToServer(message);
		return convert(response);
	}
	
	public boolean deleteInvitation(int appId, String username){
		String message = "deleteInvitation::" + Integer.toString(appId) + "::" + username;
		String response = connectToServer(message);
		return Boolean.valueOf(response);
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

