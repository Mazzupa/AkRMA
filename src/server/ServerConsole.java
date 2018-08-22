package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class ServerConsole extends Thread{

	private ServerSocket serverSocket;

	public ServerConsole(ServerSocket _serverSocket) {
		this.serverSocket = _serverSocket;
		this.start();
	}

	@Override
	public void run() {
		while (true) {
			Scanner in = new Scanner(System.in);
			String msg = in.nextLine();

			if (msg.equals("/close")) {
				for (ConnectedClient i : Server.connectedClient) {
					try {
						i.clientSocket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			if (msg.equals("/end")) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				in.close();
				return;
			}
			if (msg.equals("/getUtenti")) {
				for (ConnectedClient i : Server.connectedClient) {
					System.out.println(i.username);
				}
			}
		}

	}

}
