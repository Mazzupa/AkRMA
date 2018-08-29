package testClient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.Scanner;

public class ConnectedClient extends Thread{

	private Socket _server;
	private MuThDBH _muThDBH;
	private String _username;
	
	public ConnectedClient(String username, Socket server, MuThDBH MuTrDBMaH) {
		_username = username;
		_server = server;
		_muThDBH = MuTrDBMaH;
		this.start();
	}
	
	public void run() {
		
		Scanner inStream = null;
		PrintWriter outStream = null;
		
		try {
			inStream = new Scanner(new InputStreamReader(_server.getInputStream()));
			outStream = new PrintWriter(new OutputStreamWriter(_server.getOutputStream()), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ResultSet rs = null;
		try {
			//ricezione query
			rs = _muThDBH.query(inStream.nextLine());
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		//invio risultati
		outStream.println(rs.toString());
		
		
		
		inStream.close();
		outStream.close();
		try {
			_server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
