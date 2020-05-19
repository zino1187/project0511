package homework.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

/*
 * 이 클래슬르 정의해야 하는 이유는? 
 * 만일 이 클래스가 없다면, 데이터베이스와의 연동 관련 코드가 
 * TableSelectionApp라는 디자인을 담당하게 되는 코드와 섞여 버린다..
 * 따라서 데이터만 교체하려고 해도 해당 디자인 코드를 함께 수정해야 되는 상황..
 * 또한 디자인코드를 더이상 사용하지 않고 폐기시킬때, 데이터베이스 연동 코드또한
 * 함께 딸려서 버려진다..결국 코드를 합쳐놓은 행위는 전산분야에서 피해야할 행동임
 * 
 * 아래의 클래스는 MVC 패턴을 기준으로 한다면 어떤 역할을 위해 정의하는가?
 * MVC패턴을 전산분야에서 선배들의 경험이 녹아진, 개발 방법론을 의미한다
 * 그냥 개념일 뿐임
 * Model : 데이터와 그 데이터를 제어하는 로직
 * View :  디자인 영역 
 * Controller :  Model, View를 분리시켜주는 중재자!!
 * */
public class EmpTableModel extends AbstractTableModel{
	ConManager conManager;
	String[][] data=null;
	String[] title= {"empno","ename","job","msg","hiredate","sal","comm","deptno"};
	
	public EmpTableModel(String ip) {
		conManager = new ConManager(ip);
		
		//접속객체를 얻어온다!! 왜?? 쿼리문 담당 객체들을 생성하기 위해
		Connection con=conManager.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql="select * from emp";
		
		try {
			pstmt=con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs=pstmt.executeQuery();
			
			rs.last();//커서를 레코드의 마지막행으로 이동!!
			
			data=new String[rs.getRow()][8];
			
			//배열에  rs데이터를 옮겨심기 전에 커서를 원상복귀 
			rs.beforeFirst();
			
			int index=0;
			while(rs.next()){
				String empno=Integer.toString(rs.getInt("empno"));
				String ename=rs.getString("ename");
				String job=rs.getString("job");
				String mgr=Integer.toString(rs.getInt("mgr"));
				String hiredate=rs.getString("hiredate");
				String sal=Integer.toString(rs.getInt("sal"));
				String comm="";
				if(rs.getInt("comm")!=0) {// 데이터가 null 인경우
					comm=Integer.toString(rs.getInt("comm"));
				}
				String deptno=Integer.toString(rs.getInt("deptno"));
				
				//받아진 변수들일 이용하여 배열에 채우자!!
				data[index][0]=empno;
				data[index][1]=ename;
				data[index][2]=job;
				data[index][3]=mgr;
				data[index][4]=hiredate;
				data[index][5]=sal;
				data[index][6]=comm;
				data[index][7]=deptno;
				
				index++;
			}
			System.out.println("최종 ");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			//db 관련 자원 반납
			 conManager.closeDB(con, pstmt, rs);
		}
		
	}
	
	//행의 갯수반환
	public int getRowCount() {
		return data.length; //층수 반환
	}
	
	//열의 갯수반환
	public int getColumnCount() {
		return 8;
	}
	
	//제목처리
	public String getColumnName(int col) {
		return title[col];
	}
	
	//각셀에 들어갈 데이터 반환
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
	
}









