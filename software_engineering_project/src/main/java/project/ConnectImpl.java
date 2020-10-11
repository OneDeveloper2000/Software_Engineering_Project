package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectImpl implements Connect {
	
	
	private static final String db = "jdbc:mysql://localhost:3306/classicmodels?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String username="root";
	private static final String password="";
	private Connection connection=null;
	private Statement statement=null;
	
	public ConnectImpl() {
		this.connection = null;
		this.statement = null;
		this.openConnection();
	}
	
	/**
	 * Open the connection to the database
	 */
	@Override
	public void openConnection() {
		try {
			// recreate the connection if needed
			if (this.connection == null || this.connection.isClosed()) {
				// change the DB Path
				
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver ());
				this.connection = DriverManager.getConnection( db, username, password);
			}
			//recreate the statement if needed
			if (this.statement == null || this.statement.isClosed()) {
				this.statement = this.connection.createStatement();
			}

		} catch (SQLException e) {
			System.out
					.println("ERRRO - Failed to create a connection to the database");
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Execute the base query and return a result set
	 */
	@Override
	public ResultSet useTable(String tableName){
		String query = "select * from " + tableName; 
		ResultSet rs=null;
		try {
			rs = this.statement.executeQuery(query);
		}	
		catch (SQLException e) {
			System.out.println("SQLException happened while retrieving records- abort programmme");
			throw new RuntimeException(e);
		}
		return rs;
	}
	
	/**
	 * close the connection
	 */
	@Override
	public void closeConnection() {

		try {

			if (this.statement != null) {
				this.statement.close();
			}
			if (this.connection!=null) {
				this.connection.close();
			}
		}
		catch (Exception e) {
			System.out.print("ERROR-Failed to close the connection to the database");
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Execute a query and return a result set.
	 */
	@Override
	public ResultSet excecuteQuery(String query){
		ResultSet rs=null;
		try {
			rs = this.statement.executeQuery(query);
		} 
		catch (Exception e) {
			System.out.print("ERROR-Failed to close the connection to the database");
			throw new RuntimeException(e);
		}
		return rs;
	}

}
