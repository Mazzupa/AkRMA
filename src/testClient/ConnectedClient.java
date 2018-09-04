/**
 * Classe per gestire la comunicazione multiclient del server
 * ogni oggetto di questa classe rappresenta ogni singolo client connesso e gestisce la comuniczione con esso
 * 
 * @author Mazzucchetti Patrick
 */

package testClient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ConnectedClient extends Thread {

	private Socket _server;
	private MuThDBH _muThDBH;
	@SuppressWarnings(value = { "unused" })
	private String _username;

	public ConnectedClient(String username, Socket server, MuThDBH MuTrDBMaH) {
		_username = username;
		_server = server;
		_muThDBH = MuTrDBMaH;
		this.start();
	}

	public void run() {

		Scanner inStream = null;
		ObjectOutputStream oos = null;

		try {
			inStream = new Scanner(new InputStreamReader(_server.getInputStream()));
			oos = new ObjectOutputStream(_server.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		ResultSet rs = null;
		try {
			// ricezione query
			rs = _muThDBH.query(inStream.nextLine());
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		// invio risultati

		MyResulSet mrs = new MyResulSet();
		try {
			mrs.create(rs);
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		try {
			oos.writeObject(mrs);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		inStream.close();
		try {
			_server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
