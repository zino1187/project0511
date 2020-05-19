package db.oracle;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*
 * JDBC란? Java DataBase Connectivity의 약자로 자바언어로 데이터
 * 베이스를 다루는 기술을 의미하며, 관련된 패키지는 java.sql 이다!! 
 */
//select 문 수행하여 레코드 가져오기!
public class SelectApp extends JFrame implements ActionListener{
	JButton bt_connect, bt_insert, bt_select;
	JTextField t_insert;
	JTextArea area;
	JScrollPane scroll;
	
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="c##java";
	String password="1234";
	
	//다른 언어에서는 (예)MS) Connection객체가 접속을 담당하지만, 
	//자바언어에서는 Connection 는 객체는 접속을 시도하는 객체가 아니라
	//접속이 성공되어야 메모리에 올라오는 인터페이스이다!!
	//그렇다면 접속은 누가 시도하나 ? DriverManager 객체임!!
	//결론)  Connection 객체는 접속이 성공한 이후, 그 정보를 가진 객체이기
	//		때문에 만일 이 객체가 null 이라면 접속은 되지 않은 것이다!!
	Connection con;//접속된 이후 그 정보를 가진 객체 
	PreparedStatement pstmt;//쿼리 수행 객체 
	ResultSet rs; //쿼리문이  select 인 경우 그 결과집합을 보유한 객체
	
	public SelectApp() {
		bt_connect = new JButton("접속");
		bt_insert = new JButton("insert");
		bt_select = new JButton("select");
		t_insert = new JTextField(40);
		area  = new JTextArea(15,40);
		scroll = new JScrollPane(area);
		
		setLayout(new FlowLayout());
		add(bt_connect);	
		add(bt_insert);	
		add(bt_select);
		add(t_insert);
		
		add(scroll);
		
		setSize(500, 400);
		setVisible(true);
		
		//윈도우와 리스너 연결 
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//데이터베이스와 관련된 자원 해제!!
				closeDB();
				System.exit(0);
			
			}
		});
		
		//버튼과 리스너 연결 
		bt_connect.addActionListener(this);
		bt_insert.addActionListener(this);
		bt_select.addActionListener(this);
	}
	
	public void closeDB() {
		if(con!=null) {//객체가 널이 아니면 닫는다~!
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}
	}
	
	public void connect() {
		/*
		 [ 데이터베이스 프로그래밍 순서 ]
		 1) 드라이버 로드!! (벤더사에서 제공하는 java 언어로 만든 핸들러!!) 
		 		mysql :  mysql 제작사에서 다운로드 ==오라클사
		 		oracle: oracle사에서 다운로드 ==오라클사
		 		mssql: ms에서 다운로드 
		 		기타  db: 
		 2) 접속 
		 3) 쿼리문 실행 
		 4) 데이터베이스 관련 자원 해제 
		*/
		
		//드라이버 로드 
		try {
			//string 형으로 넣은 클래스명을 로드시켜줌!!
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로드 성공");
			
			//접속
			con=DriverManager.getConnection(url, user, password);
			
			if(con!=null) {
				System.out.println("접속 성공");
			}else {
				System.out.println("접속 실패");
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	//EMP 테이블 레코드 가져오기!!
	public void select() {
		String sql="select * from emp";
		
		try {
			pstmt=con.prepareStatement(sql);//수행할 쿼리문 준비!!
			
			//준비된 쿼리문을 실행해본다!!
			rs=pstmt.executeQuery();//select문을 수행함!!
			//insert, update, delete 경우엔 그 결과여부가 0이 아니면될
			//뿐 쿼리 수행후 가져올 데이터가 없다..하지만 select 문은 수행후
			//출력결과를 현재 어플리케이션이 수행되고 있는 pc로 가져와야 한다!!
			//sun에서는 데이터베이스의 조회 결과를 ResultSet 이라는 인터페
			//이스로 제공해준다!!
			
			//ResultSet 객체를 이용하려면 , 개발자가 커서를 제어해야 한다!
			//그리고 이커서의 디폴트 위치는 첫 레코드보다 위에 있다!!
			
			//area에 출력~!!~
			area.append("ENAME\t"+"SAL\t"+"JOB\t"+"HIREDATE\n");
			area.append("-----------------------------------------------------------------------------------------------\n");
			
			while(rs.next()) {//커서를 한칸 전진!!
				String ename=rs.getString("ename");//컬럼명으로 데이터를 가져오기!!
				String sal=Integer.toString(rs.getInt("sal"));//급여
				String job=rs.getString("job");
				String hiredate=rs.getString("hiredate");
				area.append(ename+"\t"+sal+"\t"+job+"\t"+hiredate+"\n");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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

	public void select2() {
		String sql="select * from student";
		
		try {
			pstmt=con.prepareStatement(sql);//수행할 쿼리문 준비!!
			
			//준비된 쿼리문을 실행해본다!!
			rs=pstmt.executeQuery();//select문을 수행함!!
			//insert, update, delete 경우엔 그 결과여부가 0이 아니면될
			//뿐 쿼리 수행후 가져올 데이터가 없다..하지만 select 문은 수행후
			//출력결과를 현재 어플리케이션이 수행되고 있는 pc로 가져와야 한다!!
			//sun에서는 데이터베이스의 조회 결과를 ResultSet 이라는 인터페
			//이스로 제공해준다!!
			
			//ResultSet 객체를 이용하려면 , 개발자가 커서를 제어해야 한다!
			//그리고 이커서의 디폴트 위치는 첫 레코드보다 위에 있다!!
			
			//area에 출력전, 기존 데이터 지우기!!
			area.setText("");
			
			//area에 출력~!!~
			area.append("STUDENT_ID\t"+"NAME\t"+"PHONE\n");
			area.append("-----------------------------------------------------------------------------------------------\n");
			
			while(rs.next()) {//커서를 한칸 전진!!
				String student_id=Integer.toString(rs.getInt("student_id"));
				String name=rs.getString("name");
				String phone=rs.getString("phone");
				
				area.append(student_id+"\t"+name+"\t"+phone+"\n");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
	public void insert() {
		//insert 쿼리 수행을 위해 또  PreparedStatement 얻어야 함
		//즉 쿼리문 하나마다 1:1 대응하는 PreparedStatement 필요함
		//String sql="insert into student(student_id, name,phone)";
		//sql+=" values(seq_student.nextval, 'test','1234')";
		String sql=t_insert.getText();
		
		try {
			pstmt=con.prepareStatement(sql);//준비에 불과함 
			
			//준비된 쿼리문 실행!!
			//DML (insert, update, delete) 인 경우엔 executeUpdate()
			//select 인 경우엔 결과를 가져와야 하므로 executeQuery() 이용
			
			//반환값이 숫자인 이유는 DML에 의해 영향을 받은 레코드수를 반환함
			//따라서 insert 의 경우엔 언제나 1건만 들어가므로 성공인 경우 1반환
			//update, delete인 경우엔 반영된 레코드수를 알수없으므로 1이상의
			//수가 나온다...단 실패의 경우엔 모두 0이 반환된다!! 따라서 DML
			//성공여부를 0을 반환하는지 여부로 판단하면 된다!!
			
			//0이 나왓다고 하여 에러는 아니다. 단지 조건에 맞는 DML이 수행되지 않았
			//을뿐이다..에러의 경우엔 Exception이 발생하게 된다!!
			int result=pstmt.executeUpdate();//DML 수행!!
			if(result==0) {
				JOptionPane.showMessageDialog(this, "등록되지 않음");
			
			}else {
				JOptionPane.showMessageDialog(this, "등록성공");
				select2();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}			
		}
		
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj==bt_connect) {//접속 버튼을 누르면
			connect();
		}else if(obj==bt_select){// selct 버튼을 누르면..
			select();
		}else if(obj==bt_insert){// insert 버튼을 누르면..
			insert();
		}			
	}
	
	public static void main(String[] args) {
		new SelectApp();
	}
}







