package server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server extends Thread {

	public static final int PORT = 1518;
	public static ArrayList<ConnectedClient> connectedClient = new ArrayList<>();

	public static void main(String[] args) {
		new Server();
	}

	public Server() {
		this.start();
	}

	@Override
	public void run() {
		Scanner inStream = null;
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			System.out.println("[SERVER CONSOLE]");
			System.out.println("Server " + serverSocket.getInetAddress() + ":" + PORT + ": ONLINE...");
			//ServerConsole serverConsole = new ServerConsole(serverSocket);

			while (true) {
				Socket clientSocket = serverSocket.accept();
				inStream = new Scanner(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter outStream = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

				System.out.println("Server> Connection request from: " + clientSocket.getInetAddress() + ":"
						+ clientSocket.getPort());
				System.out.println(
						"Server> Connection accepted: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

				ConnectedClient client = new ConnectedClient(serverSocket, clientSocket, "username" + connectedClient.size());
				connectedClient.add(client);
			}

		} catch (IOException e) {
			System.err.println(e.getMessage());
		} finally {
			
			inStream.close();
		}
	}

}
