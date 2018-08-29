package testClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import databaseUtility.DatabaseManager;

public class Server {

	public static final int PORT = 1518;
	public static ArrayList<ConnectedClient> clients = new ArrayList<>();

	public static void main(String[] args) throws IOException {

		//TODO da migliorare (sopratutto gestione psw)
		//Indirizzo db, porta db, nome db, username, password
		DatabaseManager DBH = new DatabaseManager("localhost", "3306", "PROVA", "root", "");
		MuThDBH muThDBH = new MuThDBH(DBH);
		
		ServerSocket serverSocket = new ServerSocket(PORT);
		Socket server = null;
		while (true) {
			System.out.println("Server started on " + serverSocket.getInetAddress());
			server = serverSocket.accept();
			System.out.println("Conection accepted from " + server.getInetAddress());

			clients.add(new ConnectedClient("user " + clients.size(), server, muThDBH));
			
			if(server.isClosed()) {
				break;
			}
		}
		
		serverSocket.close();
		server.close();
	}
}
