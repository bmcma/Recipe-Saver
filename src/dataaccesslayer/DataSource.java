package dataaccesslayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
	private Connection connection = null;
	private final String connectionString = "jdbc:mysql://localhost:3306/recipes";
    private final String username = "user1";
	private final String password = "password";
	
	public DataSource(){}

	public Connection createConnection(){
		try{
			if(connection != null){
				System.out.println("Cannot create new connection, one exists already");
			}
			else{
				connection = DriverManager.getConnection(connectionString, username, password);
			}
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
		return connection;
	}
}
