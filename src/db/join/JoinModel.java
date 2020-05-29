package db.join;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

/*3���� ���̺��� ������ �����͸� ��Ʈ���ϴ� ��ü*/
public class JoinModel extends AbstractTableModel{
	ArrayList<Goods> goodsList=new ArrayList<Goods>();
	
	//�÷����� �迭 
	String[] column= {
		"topcategory_id"
		,"����ī�װ���"
		,"subcategory_id"
		,"����ī�װ���"
		,"goods_id"
		,"��ǰ��"
		,"����"
		,"�귣��"		
	};
	
	@Override
	public int getRowCount() {
		return goodsList.size();
	}

	@Override
	public int getColumnCount() {
		return column.length;
	}
	@Override
	public String getColumnName(int col) {
		return column[col];
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		//ArrayList ����Ʈ�� ����ִ� �����͸� ���!!
		Goods goods=goodsList.get(row);
		
		String data=null;
		
		if(col==0) {//topcategory_id
			data=Integer.toString(goods.getSubCategory().getTopCategory().getTopcategory_id());
		}else if(col==1) {//top name
			data=goods.getSubCategory().getTopCategory().getName();
		}else if(col==2) {//subcategory_id
			data=Integer.toString(goods.getSubCategory().getSubcategory_id());
		}else if(col==3) {//sub name
			data=goods.getSubCategory().getName();
		}else if(col==4) {//goods_id
			data=Integer.toString(goods.getGoods_id());
		}else if(col==5) {//��ǰ��
			data=goods.getName();
		}else if(col==6) {//����
			data=Integer.toString(goods.getPrice());
		}else if(col==7) {//�귣��..
			data=goods.getBrand();
		}
		return data;
	}
	

}






