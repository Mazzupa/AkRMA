package testClient;

import java.sql.ResultSet;
import java.util.concurrent.Semaphore;

import databaseUtility.DatabaseManager;

/**
 * involucro per il multiThreading 
 * 
 * MultiThreadingDataBaseHandler
 * 
 */
public class MuThDBH {
	
	private DatabaseManager _DBH;
	private Semaphore _mutex;
	
	public MuThDBH(DatabaseManager DBH) {
		_DBH = DBH;
	}
	
	public synchronized ResultSet query(String query) throws InterruptedException {
		
		_mutex.acquire();
		
		ResultSet rs = this._DBH.Query(query);
		
		_mutex.release();
		
		return rs;
		
	}
}
