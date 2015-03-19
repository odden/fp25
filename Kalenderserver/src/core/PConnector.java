package core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class PConnector {
	
	private SCore sc;
	
	public PConnector(SCore sc){
		this.sc = sc;
	}
	
	public void runServer(){
		String request;
	    ArrayList<String> response;
	    try {
			@SuppressWarnings("resource")
			ServerSocket welcomeSocket = new ServerSocket(1200);

			while(true){
				Socket connectionSocket = welcomeSocket.accept();
			    BufferedReader inFromClient =
			    new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			    DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			    request = inFromClient.readLine();
			    System.out.println("Received: " + request);
			    response = response(request);
			    String responseStr = "";
			    if (response != null){
				    for (String object : response) {
				    	responseStr += object;
				    }
			    }
			    outToClient.writeBytes(responseStr + "\n");
			}
		} catch (IOException e) {
			System.out.println("Noe gikk galt");
		}
	}
	
	private ArrayList<String> response(String request){
		String[] requestList = request.split("::");
		ArrayList<String> response = new ArrayList<String>();
		if(requestList[0].equals("logIn")){
			return logIn(requestList);
		}
		else if(requestList[0].equals("createUser")){
			response.add(String.valueOf(createUser(requestList)));
			return response;
		}else if (requestList[0].equals("createAppointment")) {
			response.add(String.valueOf(createAppointment(requestList)));
			return response;
		}else if(requestList[0].equals("getAppointments")){
			response = getAppointments(requestList[1]);
			return response;
		}else if(requestList[0].equals("getInvitations")){
			response = getInvitations(requestList[1]);
			return response;
		}else if (requestList[0].equals("getUsers")){
			return getUsers();
		}else if(requestList[0].equals("invite")){
			response.add(String.valueOf(invite(requestList)));
			return response;
		}else if(requestList[0].equals("setStatus")){
			sc.setStatus(requestList[1],Integer.parseInt(requestList[2]), Boolean.valueOf(requestList[3]));
			return null;
		}else if(requestList[0].equals("getRoom")){
			response = getRoom(requestList[1], requestList[2], requestList[3], requestList[4]);
			return response;
		}else if(requestList[0].equals("editAppointment")){
			response.add(String.valueOf(editAppointment( requestList[1], requestList[2], requestList[3], requestList[4],requestList[5], requestList[6], requestList[7], requestList[8], requestList[9])));
			return response;
		}else if(requestList[0].equals("getInvited")){
			response = getInvited(requestList[1]);
			return response;
		}else if(requestList[0].equals("getGroups")){
			response = getGroups();
			return response;
		}else if(requestList[0].equals("getAllAppointments")){
			response = getAllAppointments();
			return response;
		}else if(requestList[0].equals("deleteAppointment")){
			response.add(String.valueOf(deleteAppointment(requestList[1])));
			return response;
		}else if(requestList[0].equals("getStatus")){
			response = getStatus(requestList[1]);
			return response;
		}else if(requestList[0].equals("deleteAppointment")){
			response.add(String.valueOf(sc.deleteInvitation(Integer.parseInt(requestList[1]), requestList[2])));
			return response;
		}
		return null;
	}
	
	
	private ArrayList<String> getStatus(String appId) {
		ArrayList<List<Object>> statuses = sc.getStatus(appId);
		return convertToList(statuses);
	}

	private ArrayList<String> getAllAppointments() {
		ArrayList<List<Object>> appointments = sc.getAppointments();
		return convertToList(appointments);
	}

	private ArrayList<String> getGroups() {
		ArrayList<List<Object>> groups = sc.getGroups();
		return convertToList(groups);
	}

	//kan v�re int i for l�kke er en for mye/lite
	private int createAppointment(String[] request) {
		List<String> invited = new ArrayList<String>();
		for (int i = 8; i < request.length; i++) {
			invited.add(request[i]);
		}
		return sc.createAppointment(request[1], request[2], request[3], request[4], request[5],request[6],request[7], invited);
	}
	
	private ArrayList<String> getUsers(){
		ArrayList<List<Object>> users = sc.getUsers();
		return convertToList(users);
	}

	private ArrayList<String> logIn(String[] request) {
		List<Object> responseList = sc.logIn(request[1], request[2]);
		ArrayList<String> response = new ArrayList<String>();
		if (responseList != null){
			for (Object object : responseList) {
				if (object != null){
					response.add(object.toString() + "::");
				}
				else 
					response.add("NULL::");
			}
		}
		return response;
	}
	
	private boolean createUser(String[] request){
		return sc.createUser(request[1], request[2], request[3], request[4], Integer.parseInt(request[5]));
	}
	
	private ArrayList<String> getAppointments(String user){
		ArrayList<List<Object>> appointments = sc.getAppointments(user);
		return convertToList(appointments);
	}
	private ArrayList<String> getInvitations(String user){
		ArrayList<List<Object>> invitations = sc.getInvitations(user);
		return convertToList(invitations);
	}
	
	private boolean invite(String[] request){
		List<String> invited = new ArrayList<String>();
		for (int i = 2; i < request.length; i++) {
			invited.add(request[i]);
		}
		System.out.println(invited);
		return sc.invite(invited, request[1]);
	}
	
	private ArrayList<String> getRoom(String size,String date, String start, String slutt){
		int sizeInt = Integer.parseInt(size);
		ArrayList<List<Object>> rooms = sc.getRoom(sizeInt, date, start, slutt);
		return convertToList(rooms);
	}
	
	private boolean editAppointment(String id, String vert, String title, String sted, String room,String dato,String start, String slutt, String endring){
		return sc.editAppointment(Integer.parseInt(id), vert, title, sted, room, dato, start, slutt, endring);
	}
	
	private boolean deleteAppointment(String id){
		return sc.deleteAppointment(Integer.parseInt(id));
	}
	
	private ArrayList<String> getInvited(String appId){
		ArrayList<List<Object>> invited = sc.getInvited(appId);
		return convertToList(invited);
	}
	
	private ArrayList<String> convertToList(ArrayList<List<Object>> list){
		if (list == null) {
			return null;
		}
		ArrayList<String> arrayList = new ArrayList<String>();
		for (List<Object> listList : list) {
			String str = "";
			for (Object object : listList) {
				if (object != null){
					str += object.toString() + "::";
				}
				else{
					str += "NULL::";
				}
			}
			arrayList.add(str.substring(0, str.length() - 2 ) + ";");
		}
		return arrayList;
	}
	
}
