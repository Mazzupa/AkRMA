package server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.Scanner;

import databaseUtility.DatabaseManager;

public class ConnectedClient extends Thread {

	ServerSocket serverSocket;
	Socket clientSocket;
	String username;

	public ConnectedClient(ServerSocket _serverSocket, Socket _clientsocket, String _username) {

		this.serverSocket = _serverSocket;
		this.clientSocket = _clientsocket;
		this.username = _username;
		this.start();
	}

	@Override
	public void run() {
		Scanner inStream = null;
		try {
			inStream = new Scanner(new InputStreamReader(clientSocket.getInputStream()));
			

			String msg;
			while (true) {
				if(clientSocket.isClosed())
					return;
				else {
					msg = inStream.nextLine();
					
					System.out.println("Tentativo di connessione...");
					//indirizzo server, porta, nome database, username, password
					DatabaseManager dbh = new DatabaseManager("mazzupa.ddns.net", "3306", "UTENTI", "Admin", "Admin");
					System.out.println("Connessione riuscita");
					
					ResultSet rs = dbh.Query(msg);
					
					sendMsg(rs.toString(), this.clientSocket);
					
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			inStream.close();
		}
	}
	
	private void sendMsg(String msg, Socket dest) throws IOException {
		PrintWriter outStream = new PrintWriter(new OutputStreamWriter(dest.getOutputStream()), true);
		outStream.println(this.username + ">> " + msg);
	}

}
