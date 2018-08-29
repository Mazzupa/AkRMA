package testClient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client{
	
	public static final int PORT = 1518;
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		System.out.println("client started");
		Socket client = new Socket(InetAddress.getLocalHost(), PORT);
		System.out.println("Client connected to server");
		
		Scanner inStream = new Scanner(new InputStreamReader(client.getInputStream()));
		PrintWriter outStream = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
		
		outStream.println("SELECT * FROM Utenti");
		System.out.println(inStream.nextLine().toString());
		
		
		inStream.close();
		outStream.close();
		client.close();
	}
}