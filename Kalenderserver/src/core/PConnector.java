package core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class PConnector {
	
	public void runServer(){
		String request;
	    String response;
	    try {
			ServerSocket welcomeSocket = new ServerSocket(1200);

			while(true){
				Socket connectionSocket = welcomeSocket.accept();
			    BufferedReader inFromClient =
			    new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			    DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			    request = inFromClient.readLine();
			    System.out.println("Received: " + request);
			    response = response(request);
			    outToClient.writeBytes(response);
			}
		} catch (IOException e) {
			System.out.println("Noe gikk galt");
		}
		
		
	}
	
	private String response(String request){
		String[] requestList = request.split(":");
		
		
		
		return null;
		
	}
	
	
	public static void main(String argv[]) throws Exception{
       
    }
	
	
	
}
