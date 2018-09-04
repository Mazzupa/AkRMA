/**
 * Classe involucro per gestire il multiThreading e l'accesso cocorrente alla risorsa condivisa DatabaseManager
 * Che si occupa della comunicazione tra il server e il database MySQL
 * MultiThreadingDataBaseHandler
 * 
 * @author Mazzucchetti Patrick
 */

package testClient;

import java.sql.ResultSet;
import java.util.concurrent.Semaphore;

import databaseUtility.DatabaseManager;

public class MuThDBH {

	private DatabaseManager _DBH;
	private Semaphore _mutex;

	public MuThDBH(DatabaseManager DBH) {
		_DBH = DBH;
		_mutex = new Semaphore(1);
	}

	public synchronized ResultSet query(String query) throws InterruptedException {

		_mutex.acquire();

		ResultSet rs = this._DBH.Query(query);

		_mutex.release();

		return rs;

	}
}
