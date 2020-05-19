package homework.db;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class OracleApp extends JFrame{
	JButton bt_exe, bt_con;
	JTextField t_query;
	JTextArea area;
	
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="c##java";
	String password="1234";
	
	Connection con;//접속 객체
	PreparedStatement pstmt;//쿼리문 수행 객체
	
	public OracleApp() {
		bt_exe = new JButton("쿼리수행");
		bt_con = new JButton("DB접속");
		
		t_query = new JTextField(30);
		area = new JTextArea(15,30);
		
		setLayout(new FlowLayout());
		
		add(bt_exe);
		add(bt_con);
		add(t_query);
		add(area);
		
		setSize(400,450);
		setVisible(true);
		
		//버튼과 리스너 연결 
		bt_exe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exeQuery();
			}
		});
		
		bt_con.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});
		
		//윈도우와 리스너 연결
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
		
	}

	public void connect() {
		try {
			//1단계:드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			area.append("드라이버 로드\n");
			
			//2단계:접속 
			con=DriverManager.getConnection(url, user, password);
			if(con!=null){
				area.append("접속성공\n");
			}else {
				area.append("접속실패\n");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			area.append("드라이버 로드 실패\n");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void exeQuery() {
		//텍스트 박스에 입력한 쿼리문을 오라클에 전달하여 수행하자!!
		//이 역할을 해주는 객체가 바로 PreparedStatement  이다!!
		String sql=t_query.getText();
		
		try {
			pstmt=con.prepareStatement(sql);
			int result=pstmt.executeUpdate();//쿼리수행!!
			if(result==0){
				area.append("등록실패\n");
			}else {
				area.append("등록성공\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//접속해제(db관련 모든 객체들을 닫음)
	public void close() {
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
	
	public static void main(String[] args) {
		new OracleApp();
	}
}








