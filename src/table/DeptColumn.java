package table;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class DeptColumn extends DefaultTableColumnModel{
	
	String[] column= {"deptno","dname","loc"};
	
	
	public TableColumn getColumn(int col) {
		return column[col];
	}
	
}



