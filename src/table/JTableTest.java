package table;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/*
 * 데이터베이스의 내용을 출력하기에 최적화된 
 * 컴포넌트인 JTable을 익혀보자
 * JTable MVC패턴을 적용한 컴포넌트 중 하나이다.. 
 * */
public class JTableTest extends JFrame{
	JTable table;
	JScrollPane scroll;
	
	String[] column= {
			"student_id","name","phone"
	};//테이블의 컬럼 제목은 일차원 배열로 채워넣음
	
	String[][] data= new String[0][0];
	
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="c##java";
	String password="1234";
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public JTableTest() {
		connect();
		
		//적어도 표라한다면, 행과 열이 존재해야 함
		table = new JTable(data, column);
		scroll = new JScrollPane(table);
		setLayout(new FlowLayout());
		
		add(scroll);
		
		setSize(500,300);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(con !=null) {
					try {
						con.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});		
		
		//JTable과 리스너 연결 
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.out.println("콕 찝었어?");
				
				//Table내의 선택한 행에 대한 정보를 출력해본다!!
				int row=table.getSelectedRow();
				int col = table.getSelectedColumn();
				//JOptionPane.showMessageDialog(JTableTest.this, "선택한 row:"+row+", 선택한 column:"+col);
				
				JOptionPane.showMessageDialog(JTableTest.this,data[row][col]);
				
			}
		});
		
	}
	
	//오라클에 접속 
	public void connect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			JOptionPane.showMessageDialog(this, "드라이버 로드 성공");
			
			con=DriverManager.getConnection(url,user,password);
			if(con!=null) {
				JOptionPane.showMessageDialog(this, "접속성공");
				getData();
			}else {
				JOptionPane.showMessageDialog(this, "접속실패");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "드라이버 로드 실패");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void getData() {
		String sql="select * from student order by student_id desc";
		
		//쿼리문 수행객체 생성 
		try {
			//ResultSet.TYPE_SCROLL_INSENSITIVE :
			//커서를 한칸씩이 아닌, 여러칸씩 전방향+후방향으로 
			//즉 자유자재로 움직일수 있는 속성
			pstmt=con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			//쿼리수행하자( DML, select  판단)
			rs=pstmt.executeQuery();
			//JTable이 의존하고 있는 이차원배열을 여기서 생성하여 
			//채워넣자!!
			
			//레코드의 총 개수를 알아맞추는 메서드는 따로 지원안함!!
			//따라서 커서를 레코드의 끝으로 보낸 후, 그 위치를 조사
			rs.last();//커서를 제일 마지막 row 보낸다!!
			//커서 한칸 전진!!
			//커서의 위치 알아보기!!
			int row=rs.getRow();//현재 커서의 위치에서의 행번호!!
			JOptionPane.showMessageDialog(this, "저 "+row+"에 와 있습니다");

			
			//이차원배열을 생성 
			data = new String[row][3];
			
			//이차원 배열에 데이터 채워넣기!!
			//모든 사람의 정보를 콘솔에 출력하기!!
			rs.beforeFirst();//커서의 탄생 시점인 제자리로 돌려놓음!!
			
			int index=0;//층수를 결정짓는 변수
			
			while(rs.next()) {
				String student_id=Integer.toString(rs.getInt("student_id"));
				String name=rs.getString("name");
				String phone=rs.getString("phone");
				System.out.println(student_id+","+name+","+phone);
				
				//한사람 한사람의 정보를 이차원배열로 옮기기
				data[index][0]=student_id;//student_id
				data[index][1]=name;//name
				data[index][2]=phone;//phone
				
				index++;
			}
			System.out.println("생성된 이차원배열의 길이는 "+data.length);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			//rs에 있는 모든 레코드가 이차원배열로 옮겨졌으므로, 더이상
			//사용할 일이 없다. 따라서 닫아버려도 된다!!
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
	
	public static void main(String[] args) {
		new JTableTest();
	}
}










