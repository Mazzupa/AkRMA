/**
 * client di test
 */

package testClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

public class Client {

	public static final int PORT = 1518;

	public static void main(String[] args)
			throws UnknownHostException, IOException, ClassNotFoundException, SQLException {

		System.out.println("client started");
		Socket client = new Socket(InetAddress.getLocalHost(), PORT);
		System.out.println("Client connected to server");

		PrintWriter outStream = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
		ObjectInputStream ois = new ObjectInputStream(client.getInputStream());

		outStream.println("SELECT * FROM utenti");
		MyResulSet mrs = (MyResulSet) ois.readObject();

		for (String i : mrs.getCoulumnName()) {
			System.out.print(i + " ");
		}

		System.out.println();

		for (int i = 0; i < mrs.getNumRows(); i++) {
			for (String j : mrs.getData(i)) {
				System.out.print(j + " ");
			}
			System.out.println();
		}

		outStream.close();
		client.close();
	}
}