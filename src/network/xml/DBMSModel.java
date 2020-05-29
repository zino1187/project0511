package network.xml;

import javax.swing.table.AbstractTableModel;

public class DBMSModel extends AbstractTableModel{

	public int getRowCount() {
		return 20;
	}

	public int getColumnCount() {
		return 10;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return "dbms";
	}	
}





