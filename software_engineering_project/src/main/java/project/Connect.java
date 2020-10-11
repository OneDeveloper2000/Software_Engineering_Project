package project;

import java.sql.ResultSet;

public interface Connect {
	

	public void openConnection();
	
	public ResultSet useTable(String tableName);
			
	public void closeConnection();
		
	public ResultSet excecuteQuery(String query);
}

