package testClient;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyResulSet implements Serializable {

	private static final long serialVersionUID = -4925035860042745104L;
	private ArrayList<String> _coulumnName;
	private int _numCoulumns;
	private int _numRows = 0;

	private Map<Integer, ArrayList<String>> _data;
	
	public MyResulSet() {
		_coulumnName = new ArrayList<>();
		_data = new HashMap<Integer, ArrayList<String>>();
	}

	public void create(ResultSet rs) throws SQLException {

		java.sql.ResultSetMetaData rsm = rs.getMetaData();

		_numCoulumns = rsm.getColumnCount();
		
		for (int i = 1; i <= _numCoulumns; i++) {
			_coulumnName.add(rsm.getColumnLabel(i));
		}
		
		ArrayList<String> data = null;
		rs.next();
		while (true) {
			data = new ArrayList<>();
			for (int i = 1; i <= _numCoulumns; i++) {

				data.add(rs.getString(i));
			}
			_data.put(_numRows, data);
			_numRows++;
			
			if(rs.next() == false)
				break;
			
		}
	}
	
	
	public ArrayList<String> getData(int c){
		return this._data.get(c);
	}
	
	public ArrayList<String> getCoulumnName(){
		return this._coulumnName;
	}
	
	public int getNumRows() {
		return this._numRows;
	}
}
