package table;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * 데이터베이스 정보를 클래스마다 각각 넣어둘 경우, 
 * 혹여나 db계정이 변경된다면? 모든 각각의 클래스마다 다 열어서
 * 수정해야 한다..즉 하드코딩은 피해야 한다!!
 * Hard Coding : 외부의 변화에 대응할 수 없는 고정된 코드작성 방식
 * */
public class ConnectionManager {
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="c##java";
	String password="1234";
	
	//접속을 전담하는 메서드!!
	public Connection getConnection() {
		Connection con=null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로드 성공");
			
			con=DriverManager.getConnection(url, user, password);
			
			if(con==null) {
				System.out.println("접속실패");
			}else {
				System.out.println("접속성공");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로드 실패");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con; // 지역변수를 반환한다!!
	}
	
	//접속을 해제하는 메서드!!
	public void closeDB(Connection con) {
		
	}
	
	//DML만 수행한 경우 
	public void closeDB(PreparedStatement pstmt) {
	}
	
	//select 문 수행한 경우
	public void closeDB(Connection con,PreparedStatement pstmt,ResultSet rs) {
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
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
	}
	
}














