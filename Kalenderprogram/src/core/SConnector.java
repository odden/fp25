package core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class SConnector {

	public String getUserData(String userName){
		String message = "GetUser:" + userName;
		String response = connectToServer(message);
		return response;
	}
	
	private String connectToServer(String message){
		try {
			String sentence = message;
			String response;
			BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
			Socket clientSocket = new Socket("localhost", 1200);
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			sentence = inFromUser.readLine();
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
	  
	 }
}

