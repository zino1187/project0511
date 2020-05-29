package network.xml;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class XMLModel extends AbstractTableModel{
	ArrayList<Pet> list=new ArrayList<Pet>();
	
	String[] column= {"이름","나이","성별"};
	
	public int getRowCount() {
		return list.size();
	}

	public int getColumnCount() {
		return 3;
	}

	public String getColumnName(int col) {
		return column[col];
	}
	public Object getValueAt(int row, int col) {
		Pet pet=list.get(row);
		String data=null;
		
		if(col==0) {
			data=pet.getName();
		}else if(col==1) {
			data=Integer.toString(pet.getAge());
		}else if(col==2) {
			data=pet.getGender();
		}
		return data;

	}	
}





