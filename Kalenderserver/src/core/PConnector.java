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
			    outToClient.writeBytes(responseStr);
			}
		} catch (IOException e) {
			System.out.println("Noe gikk galt");
		}
	}
	
	private ArrayList<String> response(String request){
		String[] requestList = request.split(":");
		if(requestList[0].equals("logIn")){
			return logIn(requestList);
		}
		else if(requestList[0].equals("createUser")){
			ArrayList<String> response = new ArrayList<String>();
			response.add(String.valueOf(createUser(requestList)));
			return response;
		}
		return null;
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

	public static void main(String argv[]) throws Exception{
       SCore sc = new SCore();
       PConnector pco = new PConnector(sc);
       sc.init();
       pco.runServer();
    }
	
	
	
}