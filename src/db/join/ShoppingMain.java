package db.join;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ShoppingMain extends JFrame {
	JPanel p_west;
	Choice ch_top;// top ī�װ�
	Choice ch_sub;// sub ī�װ�
	//����Ʈ�� �迭�� ���������, ����ó�� ũ�Ⱑ �������̸�, 
	//���� ��ü���� ���� �� �ִٴ� ���� Ʋ����!!
	ArrayList<TopCategory> topList=new ArrayList<TopCategory>();//ũ�� 0
	ArrayList<SubCategory> subList;//ũ�� 0
	
	JTextField t_name;
	JTextField t_price;
	JTextField t_brand;

	JButton bt_regist;// ��� ��ư
	JButton bt_list;// ��� ��ư

	JTable table;
	JScrollPane scroll;
	JoinModel joinModel;
	
	//�����ͺ��̽� ���� ���� 
	ConnectionManager connectionManager;
	Connection con;//���α׷��� ������ ���ÿ� �����ϰ�, ������â ������
							//����!!
	
	public ShoppingMain() {
		p_west = new JPanel();
		ch_top = new Choice();
		ch_sub = new Choice();
		
		t_name = new JTextField(10);
		t_price = new JTextField(10);
		t_brand = new JTextField(10);
		
		bt_regist = new JButton("���");
		bt_list = new JButton("���");
		
		table = new JTable(joinModel=new JoinModel());
		scroll = new JScrollPane(table);
		
		//���� �гο� ������Ʈ ���� 
		p_west.add(ch_top);
		p_west.add(ch_sub);
		p_west.add(t_name);
		p_west.add(t_price);
		p_west.add(t_brand);
		p_west.add(bt_regist);
		p_west.add(bt_list);
		
		//��Ÿ�� ���� 
		p_west.setPreferredSize(new Dimension(150, 500));
		p_west.setBackground(Color.CYAN);
		ch_top.setPreferredSize(new Dimension(140, 25));
		ch_sub.setPreferredSize(new Dimension(140, 25));
		
		//���ʿ� �г� ���� 
		add(p_west, BorderLayout.WEST);
		
		//���Ϳ� ��ũ�Ѻ��� 
		add(scroll);
		
		//�̸� �����ð� ��������!!
		connectionManager=new ConnectionManager();
		con=connectionManager.getConnection();
		
		getTopCategoryList();//�ֻ��� ī�װ� ��������!!
		TopCategory topCategory=topList.get(0);//ù��°
		getSubCategoryList(topCategory.getTopcategory_id());
		
		
		setVisible(true);
		setSize(700,500);
		setLocationRelativeTo(null); //ȭ�� ��� ��ġ 
		
		//�����Ӱ� ������ ����
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				connectionManager.closeDB(con);
				System.exit(0);
			}
		});
		
		//���� ī�װ� ���̽��� ������ ���� 
		ch_top.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				System.out.println("�� �ٲ��?");
				//������ ������ topcategory_id�� �����غ���!!
				//topList �÷��� �����ӿ�����....
				int index=ch_top.getSelectedIndex();
				System.out.println("����� ������ �ɼ��� "+index);
				
				TopCategory topCategory=topList.get(index); //VO ������ ����!!
				//�ֻ��� ī�װ��� pk�� �ѱ���!!
				getSubCategoryList(topCategory.getTopcategory_id());
			}
		});
		
		//��Ϲ�ư�� ������ ���� 
		bt_regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				regist();
			}
		});
		
		//��Ϲ�ư�� ������ ���� 
		bt_list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getList();
			}
		});
		
	}
	
	public void getTopCategoryList() {
		String sql="select * from topcategory order by topcategory_id asc";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			pstmt=con.prepareStatement(sql);//���� ��ü����
			rs=pstmt.executeQuery();//���� ����!!
			
			//rs�� ��� ���ڵ带 Choice �� �ݿ��ϱ�!!
			while(rs.next()) {
				//���ڵ� �Ѱ� ����, �ڹ� VO��ü�� �ν��Ͻ� 1���� �޴´�
				TopCategory topCategory=new TopCategory();
				topCategory.setTopcategory_id(rs.getInt("topcategory_id"));
				topCategory.setName(rs.getString("name"));
				
				topList.add(topCategory);//����Ʈ�� VO�ֱ�!!
				
				//�Ʒ��� �ڵ�� ������ ���Ե� ī�װ� ���� �� ������..
				ch_top.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectionManager.closeDB(pstmt, rs);
		}
	}
	
	public void getSubCategoryList(int topcategory_id) {
		String sql="select * from subcategory where topcategory_id=?";
		//System.out.println(sql);
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			pstmt=con.prepareStatement(sql);
			//������������ ���ε庯���� ���� ����!!
			pstmt.setInt(1, topcategory_id);
			rs=pstmt.executeQuery();//���� ����!!
			
			subList = new ArrayList<SubCategory>();
			
			while(rs.next()){
				//VO��  rs������ �Űܽɱ�!!
				SubCategory subCategory = new SubCategory();
				subCategory.setSubcategory_id(rs.getInt("subcategory_id"));
				subCategory.setName(rs.getString("name"));
				
				//subList �� ä���ֱ�!!
				subList.add(subCategory);
			}
			
			//������ �����۵��� ��� ����!!
			ch_sub.removeAll();
			
			//������ ����Ʈ�� �̿��Ͽ� ,ȭ�鿡 �ݿ�!! �� UI�� �ݿ�
			for(int i=0;i<subList.size();i++) {
				SubCategory subCategory=subList.get(i);
				ch_sub.add(subCategory.getName());//�������� �� ī�װ���
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectionManager.closeDB(pstmt, rs);
		}
		
	}
	
	//���
	public void regist() {
		
		//��ǰ���̺��� ����ī�װ��� �ܷ�Ű�� �����ϹǷ�, ���� ������ ����
		//�ִ� ����ī�װ��� pk�� ��������!!
		
		int index=ch_sub.getSelectedIndex();
		SubCategory subCategory=subList.get(index);
		
		String sql="insert into goods(goods_id, subcategory_id, name,price,brand)";
		sql+=" values(seq_goods.nextval,?,?,?,?)";
		
		PreparedStatement pstmt=null;
		try {
			pstmt=con.prepareStatement(sql);//��������
			//���ε庯�� �� ����
			pstmt.setInt(1, subCategory.getSubcategory_id());
			pstmt.setString(2, t_name.getText());//��ǰ�� 
			pstmt.setInt(3, Integer.parseInt(t_price.getText()));
			pstmt.setString(4, t_brand.getText());
			
			//���� ����!!!
			int result=pstmt.executeUpdate();
			
			if(result==0) {
				JOptionPane.showMessageDialog(this, "��Ͻ���");
			}else {
				JOptionPane.showMessageDialog(this, "��ϼ���");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectionManager.closeDB(pstmt);//�ڿ�����!!
		}
	}
	
	//��ϰ������� (3���� ���̺��� �����Ͽ� ������ ����)
	public void getList() {
		StringBuilder sql=new StringBuilder();
		
		sql.append("select t.topcategory_id as topcategory_id, t.name as top_name");
		sql.append(", s.subcategory_id as subcategory_id, s.name as sub_name ");
		sql.append(", goods_id, g.name as goods_name ,price,brand");
		sql.append(" from topcategory t, subcategory s, goods g");
		sql.append(" where t.topcategory_id=s.topcategory_id ");
		sql.append(" and");
		sql.append(" s.subcategory_id= g.subcategory_id");
		
		System.out.println(sql.toString());
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			pstmt=con.prepareStatement(sql.toString());
			rs=pstmt.executeQuery();
			
			//rs���� 3���� ���̺��� ������ ���ڵ尡 �ֱ� ������, �� �����͵���
			//�츮�� ������ VO �� ��� �־�� �Ѵ�!!
			List list=new ArrayList();
			
			while(rs.next()) {
				TopCategory topCategory=new TopCategory();
				SubCategory subCategory=new SubCategory();
				Goods goods = new Goods();
				
				//���ΰ��� ������� ��������!!
				subCategory.setTopCategory(topCategory);
				goods.setSubCategory(subCategory);
				
				//rs�� �����͸� VO �鿡 ������ �Űܽ���!!
				topCategory.setTopcategory_id(rs.getInt("topcategory_id"));
				topCategory.setName(rs.getString("top_name"));
				
				subCategory.setSubcategory_id(rs.getInt("subcategory_id"));
				subCategory.setName(rs.getString("sub_name"));
				
				goods.setGoods_id(rs.getInt("goods_id"));
				goods.setName(rs.getString("goods_name"));
				goods.setPrice(rs.getInt("price"));
				goods.setBrand(rs.getString("brand"));
				
				
				//�ϼ��� Goods VO�� ����Ʈ�� �߰�!!
				list.add(goods);
			}
			//�ϼ��� ����Ʈ��  JoinModel �� ������ ����Ʈ�� ����!!
			joinModel.goodsList=(ArrayList)list;
			table.updateUI();//���̺� ���� ��ħ!!
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectionManager.closeDB(pstmt, rs);
		}
		
	}
	
	public static void main(String[] args) {
		new ShoppingMain();
	}
}















