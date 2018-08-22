package server;
import java.sql.ResultSet;
import java.util.Scanner;
import databaseUtility.*;

public class Main {
	
	public static void main(String[] args){
		
		System.out.println("Tentativo di connessione...");
		//indirizzo server, porta, nome database, username, password
		DatabaseManager dbh = new DatabaseManager("mazzupa.ddns.net", "3306", "UTENTI", "Admin", "Admin");
		System.out.println("Connessione riuscita");
		
		/** PROCEDURA PER EVENTUALE LOGIN *****
		Scanner input = new Scanner(System.in);
		System.out.print("Username: ");
		String username = input.nextLine();
		System.out.print("Password: ");
		//Console console = System.console();
		//String password = new String(console.readPassword());
		String password = input.nextLine();
		
		password = Password.Hash(password);
		
		ResultSet rs = dbh.Query("SELECT * FROM user WHERE USERNAME='" + username + "' AND PASSWORD='" + password + "'");
		
		if(DatabaseManager.GetNumRows(rs) > 0)
			System.out.println("Login effettuato");
		else {
			dbh.ExecuteInsert("INSERT INTO user(username, password) VALUES ('" + username + "', '" + password + "')");
			System.out.println("Utente registrato");
		}
		input.close();
		*/
		
		
		
		dbh.closeConnection();

	}

}
