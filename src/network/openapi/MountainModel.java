package network.openapi;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class MountainModel extends AbstractTableModel{
	ArrayList<Mountain> list=new ArrayList<Mountain>();
	String[] column= {"고유번호","산이름","산소재지","산높이"};
	
	public int getRowCount() {
		return list.size();
	}

	public int getColumnCount() {
		return column.length;
	}
	
	public String getColumnName(int col) {
		return column[col];
	}
	
	public Object getValueAt(int row, int col) {
		Mountain mt = list.get(row);
		String data=null;
		
		if(col==0) {
			data=Integer.toString(mt.getMntnid());
		}else if(col==1) {
			data=mt.getMntnnm();
		}else if(col==2) {
			data=mt.getMntninfopoflc();
		}else if(col==3) {
			data=Integer.toString(mt.getMntninfohght());
		}
		return data;
	}
	
}
