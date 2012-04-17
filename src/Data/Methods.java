package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/**
 * Class containing useful DB management methods 
 * @author mouhyi
 *
 */
public class Methods {
	
	/**
	 * Connects to db
	 * @param db
	 * @return
	 * @throws SQLException
	 */
	public static Connection connectToDB(String db) throws SQLException{
		Connection con= null;
		try{
			Properties connectionProps = new Properties();
			connectionProps.put("user", "ecse321");
			connectionProps.put("password", "ecse321");
			con = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/"+db, connectionProps);
			
		}catch(SQLException e){
			printSQLException(e);
		}finally{
			return con;
		}
	}
	
	/**
	 * Handles SQLEsceptions
	 * @param ex
	 */
	public static void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {

				e.printStackTrace(System.err);
				System.err.println("SQLState: "
						+ ((SQLException) e).getSQLState());
				System.err.println("Error Code: "
						+ ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}

		}
	}
	/**
	 * Add single quotes to string to use in a MySQL stmt
	 * @param str
	 * @return modified String
	 * @author mouhyi
	 */
	public static String addQuotes(String str){
		return "'"+str+"'";
	}
	
	public static String addQuotes(double d){
		return "'"+d+"'";
	}
	public static String addQuotes(boolean b){
		return "'"+b+"'";
	}
	
	
	
}
