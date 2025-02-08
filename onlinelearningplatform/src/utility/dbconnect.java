package utility;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconnect{
     
    	private static final String url="jdbc:mysql://localhost:3306/olp";
    	private static final  String username="root";
    	private static  final String password="kvms$123%";
    	 
    	 
    	 public static Connection getConnection() throws SQLException{
    		 return DriverManager.getConnection(url,username,password);
    	 }

    	 public static void closeConnection(Connection con) {
    		 if(con!=null) {
    			 try {
					con.close();
				} catch (SQLException e) {
					System.out.println("Error Closing Connection"+e.getMessage());
     				e.printStackTrace();
				}
    		 }
    	 }
    	 

	
    
}
     
