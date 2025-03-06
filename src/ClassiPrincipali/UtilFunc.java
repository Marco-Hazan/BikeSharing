package ClassiPrincipali;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UtilFunc {
	public static Connection connectdb() {
		String user = "test";
		String password = "password123";
		String db = "bikesharing";
		Connection c = null;
		String url = "jdbc:postgresql://localhost:5432/"+db+"?user="+user+"&password="+password;
			try {
				c = DriverManager.getConnection(url);
				c.createStatement().execute("SET SEARCH_PATH TO bikesharing");
			}catch (SQLException e) {
				e.printStackTrace();
			}
			return c;
	}
}
