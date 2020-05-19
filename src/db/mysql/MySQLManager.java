package db.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * 자바 언어로 DBMS 제어해보기!! 
 * 예) DBeaver(클라이언트 프로그램)
*/
public class MySQLManager {

	public static void main(String[] args) {
		/*
		 * 데이터 베이스 연동 순서 
		 * 0.해당 DBMS제품을 핸들링 할 수 있는 라이브러리인 드라이브를 로드
		 * 1.접속
		 * 2.쿼리문 날리기!!(실행)
		 * 3.접속 해제!!
		 */
		String url="jdbc:mysql://localhost:3306/javadev";
		String user="root";
		String password="1234";
		
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			//0단계: 드라이버 로드~~ .class를  static 에 올려놓는것!!
			Class.forName("com.mysql.cj.jdbc.Driver");//로드할 클래스의 위치및 클래스명을 적는다
			System.out.println("드라이버 로드 성공!!");
			
			//1단계: 데이터베이스에 접속!!
			con=DriverManager.getConnection(url, user, password);
			if(con==null) {
				System.out.println("접속실패");
			}else {
				System.out.println("접속성공");
				
				//2단계: 쿼리문 전송 
				String sql="insert into member(name,phone,age)";
				sql=sql+" values('superman','010-7777-8888',35)";
				
				//쿼리문을 전송하기 위한 인터페이스인 PreparedStatement 를
				//생성하자, 이 객체는 접속이 되어야 얻어올수있으므로, Connection
				//객체로 부터 얻어와야 한다!!
				pstmt=con.prepareStatement(sql);
				int result=pstmt.executeUpdate();//쿼리문 실행!!
				if(result==0) {
					System.out.println("등록실패");
				}else {
					System.out.println("등록성공");
				}
			}
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버를 찾을 수 없습니다.");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			//데이터베이스 연동에 사용했던 모든 객체는 닫아야 한다!!
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

}




