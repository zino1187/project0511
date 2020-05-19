package homework.table;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
/*
 * 데이터를 담는 용도를 배열로 하다보니, 처음부터 그 크기를 명시해야 하는 
 * 특징때문에 rs.last() 등의 커서까지 손대야 한다...
 * 개선) 조금더 객체지향적인 DB핸들링 패턴을 배워보자!!
 * 
 * JTable사용시 TableModel로 구현할 경우, 셀의 편집등은 
 * 개발자가 직접 메서드로  override 해야 한다..
 * */
public class DeptTableModel extends AbstractTableModel{
	ConManager conManager;
	
	//배열과 상당히 유사하지만, 크기를 명시하지 않아도 되며
	//크기가 유동적으로 변경될 수 있는 리스트 이용해본다!!
	ArrayList list;
	String ip;
	
	public DeptTableModel(String ip) {
		this.ip=ip;
		
		select();//아래에 정의한 메서드 호출!!
	}

	@Override
	public int getRowCount() {
		return list.size();//리스트의 수
	}

	@Override
	public int getColumnCount() {
		return 3;
	}
	
	//각셀을 편집할 수 있는지 여부를 반환하는 메서드 
	@Override
	public boolean isCellEditable(int row, int col) {
		//System.out.println("row:"+row+",col"+col+" editable="+true);
		
		boolean flag=true;
		
		if(col==0) {
			flag=false;
		}
		return flag;
		
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		//System.out.println("row="+row+",col="+col);
		Dept dept=(Dept)list.get(row);
		
		String data=null;
		
		if(col==0) {//deptno
			data=Integer.toString(dept.getDeptno());
		}else if(col==1) {//dname
			data=dept.getDname();
		}else if(col==2) {//loc
			data=dept.getLoc();
		}
		return data;
	}
	
	//셀에 값넣기!!
	/*
	 * value : 무엇을 넣을지 그 데이터 
	 * row : 층수, 
	 * col : 호수
	 * */
	public void setValueAt(Object value, int row, int col) {
		//리스트에 들어있는 데이터를 수정!!
		Dept dept=(Dept)list.get(row);//리스트에 들어있는 VO 를 가져오자!!
		
		if(col==1) {
			dept.setDname((String)value);
		}else if(col==2){
			dept.setLoc((String)value);
		}
	}
	
	//데이터를 가져오는 기능은 너무나 자주 하는 업무이므로, 메서드로 정의!!
	public void select() {
		list=new ArrayList();
		conManager = new ConManager(ip);
		
		//지역변수는 반드시 초기화해야 한다! 예외) 멤버변수는 개발자가 안하면
		//컴파일러가 초기화 해준다..결론) 변수는 사용전 누구에 의해서건 반드시
		//초기화 되어 잇어야 한다..
		Connection con=conManager.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
	
		String sql="select * from dept";
		
		try {
			pstmt=con.prepareStatement(sql);//쿼리준비, 아직 수행안함
			rs=pstmt.executeQuery();
			
			//배열에 담지 말고, 하나의 레코드는 하나의 자바인스턴스에
			//담아보자!! == 객체지향 적!!
			while(rs.next()) {
				//텅빈 empty 상태의 부서 인스턴스 생성
				Dept dept = new Dept();
				dept.setDeptno(rs.getInt("deptno"));
				dept.setDname(rs.getString("dname"));
				dept.setLoc(rs.getString("loc"));
				
				list.add(dept);//리스트에 부서 한개 담기!!
			}
			System.out.println("부서의 갯수는"+list.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conManager.closeDB(con, pstmt, rs);
		}		
	}
	
	//삭제쿼리 
	//삭제하고자 하는 자는 메서드호출시 부서번호를 넘겨야 한다!!
	public int deleteDeptById(int deptno) {
		int result=0;//성공, 실패를 판단할 수 있는 정수!!
		
		//실제 삭제처리!!
		Connection con=conManager.getConnection();
		
		String sql="delete from dept where deptno="+deptno;
		System.out.println(sql);
		
		//쿼리 수행 객체 얻기!!
		PreparedStatement pstmt=null;
		
		try {
			pstmt=con.prepareStatement(sql);
			result=pstmt.executeUpdate();//DML(insert, delete,update)
			//if(result==0) {
				//메시지 출력은 MVC 중 디자인 영역인 View 에서 처리하는 것이지
				//이 클래스와 같은 컨트롤러가 담당할 영역이 아니다!!
				//할수도 없다..왜?? 현재 클래스는 프레임이 아니니깐!!
				//JOptionPane.showMessageDialog(this, message);
			//}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conManager.closeDB(con, pstmt);//DML이므로... 
		}
		return result;//호출한 자가 결과 알수있도록 반환!!
	}
}












