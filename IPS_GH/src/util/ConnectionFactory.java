package util;
import java.sql.Connection;
import java.sql.DriverManager;



public class ConnectionFactory {
	
	public static Connection getOracleConnection() throws Exception {

	    String directory = (System.getProperty("user.dir")+"\\wallet").replace('\\', '/');
	    String DB_URL="jdbc:oracle:thin:@ips5jl6rhzy4qxy1y_medium?TNS_ADMIN="
	    		+ directory;
	    String DB_USER = "admin";
	    String DB_PASSWORD = "LyQmZ7HwG4edJ2";//Encontrar manera de esconder contraseña
	    return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	    
	}

}
