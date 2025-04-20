package pc2t.Query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDB {
	private static volatile Connection dbConnection;
	private static final String DB_FILE_PATH = "jdbc:sqlite:db/StudentDatabase.db";

	public static Connection connect() {
		if (dbConnection == null) {
			synchronized (Connection.class) {
				if (dbConnection == null) {
					try {
						Class.forName("org.sqlite.JDBC");
						dbConnection = DriverManager.getConnection(ConnectDB.DB_FILE_PATH);
					} 
					catch (SQLException | ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return dbConnection;
	}

	public static void disconnect() {
		try {
			dbConnection.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

