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
			    for (String object : response) {
			    	responseStr += object + ":";
			    }
			    outToClient.writeBytes(responseStr + "\n");
			}
		} catch (IOException e) {
			System.out.println("Noe gikk galt");
		}
	}
	
	private ArrayList<String> response(String request){
		String[] requestList = request.split(":");
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
		}else if (requestList[0].equals("getUsers")){
			ArrayList<List<Object>> responseList = getUsers();
			for (List<Object> list : responseList) {
				String userdata = "";
				
				for (Object object : list) {
					if (object != null){
						userdata += object.toString();
					}
					else
						userdata += "NULL";
					userdata += ":";
				}
				userdata += ";";
				response.add(userdata);
			}
			return response;
		}
		return null;
	}
	
	
	//kan være int i for løkke er en for mye/lite
	private boolean createAppointment(String[] request) {
		List<String> invited = new ArrayList<String>();
		for (int i = 7; i < request.length; i++) {
			invited.add(request[i]);
		}
		return sc.createAppointment(request[1], request[2], request[3], request[4], request[5],request[6], invited);
	}
	
	private ArrayList<List<Object>> getUsers(){
		ArrayList<List<Object>> users = new ArrayList<List<Object>>();
		users = sc.getUsers();
		return users;
	}

	private ArrayList<String> logIn(String[] request) {
		List<Object> responseList = sc.logIn(request[1], request[2]);
		ArrayList<String> response = new ArrayList<String>();
		for (Object object : responseList) {
			response.add(object.toString());
		}
		return response;
	}
	
	private Boolean createUser(String[] request){
		return sc.createUser(request[1], request[2], request[3], request[4], Integer.parseInt(request[5]));
	}
	
	private ArrayList<String> getAppointments(String user){
		ArrayList<List<Object>> appointments = new ArrayList<List<Object>>();
		appointments = sc.getAppointments(user);
		ArrayList<String> response = new ArrayList<String>();

		for (List<Object> list : appointments) {
			String appointment = "";
			for (Object object : list) {
				appointment += object.toString() + "_";
			}
			appointment += ":";
			response.add(appointment);
		}
		return response;
	}

	public static void main(String argv[]) throws Exception{
       SCore sc = new SCore();
       PConnector pco = new PConnector(sc);
       sc.init();
       pco.runServer();
    }
	
	
	
}
