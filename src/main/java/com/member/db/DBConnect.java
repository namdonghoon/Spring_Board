package com.member.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**  http://www.h2database.com/html/tutorial.html#console_settings
 * import java.sql.*;
 * public class Test {
 *     public static void main(String[] a) throws Exception {
 *         Class.forName("org.h2.Driver");
 *         Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
 *         add application code here
 *         conn.close();
 *     }
 * }
**/

//mysql DataBase연결
public class DBConnect {
	private Connection conn;
	
	public Connection getConn(){
		return conn;
	}
	
	public DBConnect(){
		try{
			Class.forName("com.mysql.jdbc.Driver"); //드라이버 로딩
			String url = "jdbc:mysql://localhost:3306/donghoon?characterEncoding=UTF-8";
			//String url="jdbc:h2:~/test"; //접속할 DB url       jdbc:h2:tcp://localhost/~/test
			conn=DriverManager.getConnection(url, "localhost", "ehdgns22()");	

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
