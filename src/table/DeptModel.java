package table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

//View 인 JTable을 데이터와 분리시켜주는 객체인 TableModel인터페이스를
//구현한 AbstractTableModel을 재정의해보자!!
public class DeptModel extends AbstractTableModel{
	/*아래의 재정의 메서드들을 필요로 하고, 
	 * 호출하는 주체는 바로  JTable 이다
	*/
	//우리대신 디비 접속을 대행할 클래스 보유한다!!
	ConnectionManager connectionManager;
	String[][] data=new String[][] {
		{"사과","딸기","포도"},
		{"자바","오라클","Mysql"},
	};
	
	String[] column= {"deptno","dname","loc"};
	
	
	public DeptModel() {
		connectionManager = new ConnectionManager();
		
		//접속 후 커넥션 객체를 반환받자!! 왜? 아래의  pstmt의 인스턴스를
		//얻기 위해...
		Connection con=connectionManager.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		//접속이 된 이후부터 쿼리문 수행이 가능하다~
		//접속객체로부터 인스턴스를 얻는다!!
		String sql="select * from dept";
		
		try {
			pstmt=con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=pstmt.executeQuery();
			
			//rs를 last 보내자!!
			rs.last();
			int total = rs.getRow();//마지막 행의 번호를 반환!!
			
			//rs의 가진 표정보를 위의 data이차원배열로 옮겨담고, 
			//rs, psmt 는 더이상 필요없으므로 닫아버리자!!
			data = new String[total][3];
			
			//rs의 커서위치 원상복귀!!
			rs.beforeFirst();
			
			int index=0;//층수를 접근하는 변수!!
			while(rs.next()) {
				String deptno = Integer.toString(rs.getInt("deptno"));
				String dname=rs.getString("dname");
				String loc=rs.getString("loc");
				
				data[index][0]=deptno;
				data[index][1]=dname;
				data[index][2]=loc;
				
				index++;
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			//접속 객체 이외에 객체들을 반납!!
			connectionManager.closeDB(con,pstmt, rs);
		}
	}
	
	/*행의 갯수를 반환해주는 메서드*/
	public int getRowCount() {
		//System.out.println("JTable에 의해 getRowCount() 호출됨");
		return data.length; //위의 이차원 배열의 층수
	}

	/*열의 갯수를 반환해주는 메서드*/
	public int getColumnCount() {
		//System.out.println("JTable에 의해 getColumnCount() 호출됨");
		return data[0].length;
	}
	
	@Override
	public String getColumnName(int col) {
		return column[col];
	}
	/*해당 행과 열에 들어있는 데이터를 반환하는 메서드*/
	public Object getValueAt(int row, int col) {
		System.out.println("JTable에 의해 getValueAt("+row+","+col+") 호출됨");
		return data[row][col];
	}
	
}





