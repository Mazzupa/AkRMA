package databaseUtility;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class DatabaseManager {
	
	private String _driver;
	private Connection _connection;
	private String _connectionString;
	private String _user;
	private String _psw;
	
	public DatabaseManager(String addressDB, String port, String dbName, String user, String psw) {
		try {
			_driver = "com.mysql.jdbc.Driver";
			this._connectionString="jdbc:mysql://"+addressDB+":"+port+"/"+dbName;
			Class.forName(_driver);
			
			this._user = user;
			this._psw = psw;
			
			_connection = DriverManager.getConnection(_connectionString, this._user, this._psw);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public void closeConnection(){
		try {
			_connection.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public ResultSet Query(String query){
		
		Statement stmt = null;
		try {
			stmt = _connection.createStatement();

			ResultSet res = stmt.executeQuery(query);
			
			return res;
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			try {
				stmt.close();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
			return null;
		}
	}
	
	public boolean ExecuteInsert(String instruction){
		
		Statement stmt = null;
		boolean res = false;
		try {
			stmt = _connection.createStatement();

			res = stmt.execute(instruction);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			try {
				stmt.close();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
				return false;
			}
		}
		return res;
	}
	
	public int ExecuteUpdate(String instruction){
		
		Statement stmt = null;
		int count = 0;
		try {
			stmt = _connection.createStatement();

			stmt.execute(instruction);
			
			count = stmt.getUpdateCount();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			try {
				stmt.close();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
				return -1;
			}
		}
		return count;
	}
	
	public static String toXML(ResultSet rs, String root, String elementName) throws Exception
	{
		Vector<Vector<Object>> matrix = new Vector<Vector<Object>>();
		matrix = _toMatrix(rs);
		_PrintMatrix(matrix);
		Vector<Object> tmp = new Vector<Object>();
		for (int i = 1 ; i <= rs.getMetaData().getColumnCount(); i++)
			tmp.add(rs.getMetaData().getColumnName(i));
		matrix.add(0, tmp);
		rs.beforeFirst();
		return toXML(matrix, root, elementName);
	}
	
	public static String toXML(Vector<Vector<Object>> m, String root, String elementName) throws Exception
	{
		DocumentBuilderFactory docBuilderFact = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFact.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		
		Element rootElement = doc.createElement(root);
		doc.appendChild(rootElement);
		
		for (int i = 1; i < m.size(); i++)
		{
			Element e = doc.createElement(elementName);
			for (int j = 0; j < m.get(i).size(); j++)
			{
				Element e1 = doc.createElement(m.get(0).get(j)+"");
				e1.appendChild(doc.createTextNode(m.get(i).get(j)+""));
				e.appendChild(e1);
			}
			rootElement.appendChild(e);
		}
		
	    FileWriter fileWriter = new FileWriter("queryResult.xml");

	    BufferedWriter bufferWriter = new BufferedWriter (fileWriter);

	    bufferWriter.write(_toString(doc));
	    
	    fileWriter.flush();
	    bufferWriter.flush();
	    
	    fileWriter.close();
	    bufferWriter.close();
		
		return _toString(doc);
	}
	
	public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException
	{
		ResultSetMetaData meta = rs.getMetaData();
		Vector<Vector<Object>> matrix = new Vector<Vector<Object>>();
		Vector<Object> metaData = new Vector<Object>();
		for (int i = 1; i <= meta.getColumnCount(); i++)
			metaData.add(meta.getColumnName(i));
		matrix = _toMatrix(rs);
		rs.beforeFirst();
		return new DefaultTableModel(matrix, metaData);
	}
	
	
	private static Vector<Vector<Object>> _toMatrix(ResultSet rs) throws SQLException
	{
		Vector<Vector<Object>> matrix = new Vector<Vector<Object>>();
		while(rs.next())
		{
			Vector<Object> tmp = new Vector<Object>();
			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
				tmp.add(rs.getObject(i));
			matrix.addElement(tmp);
		}
		rs.beforeFirst();
		return matrix;
	}
	
	private static String _toString (Document doc) throws Exception
	{
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		transformer.transform(source, new StreamResult(writer));
		//System.out.println(writer.toString());
		return writer.toString();
	}
	
	private static void _PrintMatrix(Vector<Vector<Object>> m)
	{
		for (Vector<Object> v: m)
		{
			System.out.println("_________________");
			for (Object o : v)
				System.out.println(o);
		}
	}
	
	public static int GetNumRows(ResultSet _rs){
		int cont = 0;
		try {
			while(_rs.next())
				cont++;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return cont;
	}
}
