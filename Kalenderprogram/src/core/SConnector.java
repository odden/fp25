package core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SConnector {

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
	
	public static void main(String argv[]) throws Exception{
		SConnector sco =  new SConnector();
		sco.logIn("eirirog", "1234");
	}
}

