package homework.table;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * 데이터베이스와 관련된 접속, 해제의 중복된 코드를
 * 이 객체로 하여금 담당하게 하여 재사용성을 높여보자!! 
 */
public class ConManager {
	String url;
	String user="c##java";
	String password="1234";
	
	public ConManager(String ip) {
		url="jdbc:oracle:thin:@"+ip+":1521:XE";
		//드라이버 로드!!
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로드 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로드 실패");
		}
	}
	//접속
	public Connection getConnection() {
		Connection con=null;
		
		try {
			con=DriverManager.getConnection(url, user, password);
			if(con!=null) {
				System.out.println("접속 성공");
			}else {
				System.out.println("접속 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	//해제 : DML(insert, update, delete)
	public void closeDB(Connection con, PreparedStatement pstmt) {
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//해제 : Select 
	public void closeDB(Connection con, PreparedStatement pstmt , ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	}
	
}

















