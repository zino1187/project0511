package homework.shop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import homework.lib.FileManager;
import homework.lib.Formatter;

//�ϳ��� ȭ���� ������ �гη� ó������!~!
public class ProductManage extends Page{
	//--------------------------���� ����
	JPanel p_west;//���ʿ� �ٿ��� �����̳�
	JTextField t_name;
	JTextField t_price;
	JTextField t_brand;
	JButton bt_find;//�̹��� ã�� ��ư
	JPanel p_thumb;//��Ͻ� ������ �̸����� ����� 
	JButton bt_regist;// ��Ϲ�ư
	JButton bt_list;// ��Ϲ�ư
	JButton bt_del;// ������ư
	
	//-------------------------���Ϳ���
	JPanel p_content;//������ �� ��� ������Ʈ�� �����!!
	JTable table;
	JScrollPane scroll;
	JPanel p_detailBox;//�󼼺��� �����̳�
	JPanel p_detail;//����� ������ �󼼺��� 
	JPanel p_info;//�̸�,����,�귻�� ���� ���� 
	JLabel la_name;
	JLabel la_price;
	JLabel la_brand;
	
	JFileChooser chooser;
	Toolkit kit=Toolkit.getDefaultToolkit();
	Image thumbImg;//����� �̹���
	Image detailImg;//�� �̹���
	
	FileInputStream fis;//������ ������� �� �Է½�Ʈ�� 
	FileOutputStream fos;//������ ������� �� ��½�Ʈ�� 
	File thumbFile;//����� ó����  ���� ���ϰ�ü
	String copyName;//���Ӱ� �ο��� ���ϸ�!!
	ProductModel productModel;
	int product_id;//���� ����ڰ� �����ִ� ���ڵ��� �ĺ���
						//�̰��� 0�̸�, ������ ��ǰ�� ���������� ���°Ŵ�!!
	String delFile;
	
	public ProductManage(ShopApp shopApp, String title,Color color, int width,int height,boolean showFlag) {
		super(shopApp,title,color,width,height,showFlag);
		
		p_west=new JPanel();
		t_name = new JTextField(10);
		t_price = new JTextField(10);
		t_brand = new JTextField(10);
		
		bt_find = new JButton("�̹���");
		
		p_thumb = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(thumbImg, 0, 0, 120,120, ProductManage.this);
			}
		};
		
		bt_regist = new JButton("���");
		bt_list = new JButton("���");
		bt_del = new JButton("����");
		
		p_content=new JPanel();
		table = new JTable();
		scroll = new JScrollPane(table);
		
		p_detailBox= new JPanel();
		p_detail = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(detailImg, 0, 0, ProductManage.this);
			}
		};
		p_info = new JPanel();
		la_name = new JLabel("��ǰ��:Ƽ����");
		la_price = new JLabel("����:80,000��");
		la_brand = new JLabel("�귣��:POLHAM");
		
		chooser = new JFileChooser("D:/web_app/js_workspace/images");
		table.setModel(productModel=new ProductModel());
		
		//��Ÿ�� ���� 
		p_content.setBackground(Color.PINK);
		p_west.setPreferredSize(new Dimension(120, height));
		
		p_thumb.setBackground(Color.WHITE);
		p_thumb.setPreferredSize(new Dimension(120, 120));
		p_detail.setBackground(Color.BLACK);
		p_detail.setPreferredSize(new Dimension(180, 180));
		
		la_name.setPreferredSize(new Dimension(350, 50));
		la_price.setPreferredSize(new Dimension(350, 50));
		la_brand.setPreferredSize(new Dimension(350, 50));
		
		la_name.setFont(new Font("����",Font.BOLD,14));
		la_price.setFont(new Font("����",Font.BOLD,14));
		la_brand.setFont(new Font("����",Font.BOLD,14));
		
		//���� ���̾ƿ� ���� 
		this.setLayout(new BorderLayout());
		p_content.setLayout(new BorderLayout());
		p_detailBox.setLayout(new BorderLayout());
		
		//���� 
		add(p_content, BorderLayout.CENTER);
		p_west.add(t_name);
		p_west.add(t_price);
		p_west.add(t_brand);
		p_west.add(bt_find);
		p_west.add(p_thumb);
		p_west.add(bt_regist);
		p_west.add(bt_list);
		p_west.add(bt_del);
		
		add(p_west, BorderLayout.WEST);
		p_content.add(scroll);
		
		//�󼼺��� ���� ����
		p_info.add(la_name);
		p_info.add(la_price);
		p_info.add(la_brand);
		
		p_detailBox.add(p_detail, BorderLayout.WEST);
		p_detailBox.add(p_info);
		
		p_content.add(p_detailBox, BorderLayout.SOUTH);
		
		//��ư�� ������ ���� 
		bt_find.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findImg();
			}
		});
		
		bt_regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				regist();
			}
		});
		
		bt_list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getList();
			}
		});
		
		bt_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int result=JOptionPane.showConfirmDialog(ProductManage.this, "�����Ͻðڽ��ϱ�?");
				
				if(result == JOptionPane.OK_OPTION) {
					if(deleteFile()) {//������ ������ �Ǹ�.. 
						delete();//db����
					}
				}
			}
		});
		
		//���̺�� ������ ���� 
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//�󼼺���!!
				select();
			}
		});
		
	}
	
	//�̹��� Ž���� ����, ������ �̹��� �׸���!!
	public void findImg() {
		int result=chooser.showOpenDialog(shopApp);
		
		// ������ ���ϼ��� �� ok�� ������..
		if(result==JFileChooser.APPROVE_OPTION) {
			thumbFile=chooser.getSelectedFile();
			//System.out.println(file.getAbsolutePath());
			
			//������ �̹��� ��θ� �̿��� �׸� �׸���!!
			thumbImg=kit.getImage(thumbFile.getAbsolutePath());
			p_thumb.repaint();
		}
	}
	
	//�����ͺ��̽��� �ֱ� �� �̹��� ����!!
	public void regist() {
		//������� �õ��ϰ�, �����ϸ�  db�� ����!!!
		if(copy()) {//���簡 �����ϸ�  db�� ����!!
			
			String name=t_name.getText();
			int price = Integer.parseInt(t_price.getText());
			String brand = t_brand.getText();
			
			
			String sql="insert into product(product_id, name, price,brand,img)";
			sql+=" values(seq_product.nextval,'"+name+"', "+price+" ,'"+brand+"','"+copyName+"')";
			//������ ���ఴü��  PreparedStatement  ����!!
			//�������̽��� new �Ͽ� ���� �Ұ�!! �ذ�å? 
			//������ �Ǿ�� ������ �����Ƿ�, Connection���� ���� ����������
			//�ν��Ͻ��� ������ �ȴ�!!
			PreparedStatement pstmt=null;
			try {
				pstmt=shopApp.con.prepareStatement(sql);
				//���� ����޼��� ȣ��!! DML executeUpdate() ȣ�� 
				int result=pstmt.executeUpdate();//������ �� �������� ���� ������
				//���� ���ڵ� ������ ��ȯ����!!
				if(result==0) {//������ �ƴ϶� ���� insert�� �ȵȰ���
					JOptionPane.showMessageDialog(this, "��Ͻ���");
				}else {
					JOptionPane.showMessageDialog(this, "��ϼ���");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				shopApp.connectionManager.closeDB(pstmt);
			}
			
			
			
		}
	}
	
	//���� �������θ� ��ȯ�ϴ� �޼���!!
	public boolean copy() {
		boolean result=false;
		
		try {
			fis = new FileInputStream(thumbFile);
			//����� �̹����� ����ڰ� �ø� �̹������� ������� �ʰ� ��������
			//��Ģ�� �����Ͽ� ���Ӱ� �����ؾ� ��!!
			long time=System.currentTimeMillis();
			//System.out.println(time);
			
			//Ȯ���� ���ϱ� 
			String ext=FileManager.getExt(thumbFile.getAbsolutePath());
			System.out.println(time+"."+ext);
			
			copyName=time+"."+ext;
			
			fos = new FileOutputStream("D:/web_app/java_workspace/Project0511/data/"+copyName);
			//�������!!
			byte[] buff=new byte[1024];//�۸��� �׸��뷮 1kbyte
			int data=-1;//�������� ���� �˷��ִ� ���� 
			
			while(true) {
				try {
					data=fis.read(buff);//�������Է�!! read()�Ҷ����� 1024��
					if(data==-1)break;//������ ���� ������ �ݺ��� Ż��!!
					fos.write(buff);//���, write()�Ҷ����� 1024����!!
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			JOptionPane.showMessageDialog(this, "����Ϸ�");
			result=true;//���缺��
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			result=true;//�������
		}finally {
			if(fos!=null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	//��� �������� 
	public void getList() {
		String sql="select * from product order by product_id desc";
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=shopApp.con.prepareStatement(sql);//�غ�
			rs=pstmt.executeQuery();
			
			//Ŀ���� �������鼭  VO �� ���ڵ带 ����!
			//List�� �����Ͽ� �Ʒ��� VO���� �������� �־����!!
			//�׷��� TableModel ���� �迭ó�� ����ϴϱ�!!
			ArrayList productList=new ArrayList();
			
			while(rs.next()){//Ŀ�� ��ĭ �̵�!!
				Product product = new Product();
				//�迭�� �ε����� �ƴ϶�, �������� �̿��ϹǷ� �ξ� ������!!
				product.setProduct_id(rs.getInt("product_id"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getInt("price"));
				product.setBrand(rs.getString("brand"));
				product.setImg(rs.getString("img"));
				
				productList.add(product);//�ϼ��� VO�� ����Ʈ�� ����!
			}
			System.out.println("����� �� ��ǰ�� ���� "+productList.size());
			
			//������ ����Ʈ�� TableModel ���� ��������!!
			productModel.list=productList;
			table.updateUI();//JTable ���ΰ�ħ
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			shopApp.connectionManager.closeDB(pstmt, rs);
		}
	}
	
	//���ڵ� �Ѱ� ��������!!
	public void select() {
		int row=table.getSelectedRow();
		
		//�ڹٿ����� �⺻�ڷ����� �� �⺻�ڷ����� ��ü�� WrapperŬ����
		//�� �ڵ�����ȯ�� �������ִµ�, �̷��� ���� ������ 
		//Boxing :  �⺻�ڷ��� ���μ� ��üȭ��Ų�� 
		//UnBoxing : ��ü�ڷ����� �⺻�ڷ������� ��ȯ
		//Integer x = new Integer(3);
		//int y=x;
		
		product_id=Integer.parseInt((String)table.getValueAt(row, 0));//(x,0)��°�� �ִ�.. 
		
		String sql="select * from product where product_id="+product_id;
		System.out.println(sql);
		
		
		PreparedStatement pstmt=null;//�������� ��ü
		ResultSet rs=null;//���ڵ带 ���� ��ü
		
		try {
			pstmt=shopApp.con.prepareStatement(sql);
			rs=pstmt.executeQuery();//select���� ��� executeQuery()
			
			//���ڵ� �Ѱ��� , �ڹٺо߿����� �ϳ��� �ν��Ͻ��� ������ �ȴ�!!
			Product product = new Product();
			
			//rs�� �����͸� �ڹ��ν��Ͻ��� �ֱ�!!
			if(rs.next()) {//���ڵ尡 ��������...
				product.setProduct_id(rs.getInt("product_id"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getInt("price"));
				product.setBrand(rs.getString("brand"));
				product.setImg(rs.getString("img"));				
			}	
			//��� ������Ƿ�, Product VO �̿��Ͽ� ȭ�鿡 ���!!
			//���̹���  repaint(), ��ǰ��,����,�귣�� �󺧿� �� ä���!!
			detailImg=kit.getImage("D:/web_app/java_workspace/Project0511/data/"+product.getImg());
			detailImg=detailImg.getScaledInstance(180, 180, java.awt.Image.SCALE_SMOOTH);
			p_detail.repaint();
			
			//��ǰ��,����,�귣�� ��
			la_name.setText("��ǰ�� : "+product.getName());
			la_price.setText("����: "+Formatter.getCurrency(product.getPrice()));
			la_brand.setText("�귣���: "+product.getBrand());
			
			//���ý� ���� �����ִ� �̹��������� ��������� ����!!
			delFile = product.getImg();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			shopApp.connectionManager.closeDB(pstmt, rs);
		}
	}
	
	public void delete() {
		//������ ��ǰ�� �ִ��� ���� üũ(��ȿ�� üũ)
		if(product_id==0) {
			JOptionPane.showMessageDialog(this, "�����Ͻ� ��ǰ�� �����ϼ���");
			return;//������� ������ ����!!, �Ʒ��ڵ�� ����������..
		}
		
		int result=JOptionPane.showConfirmDialog(this, "�����Ͻðڽ��ϱ�?");
		if(result == JOptionPane.OK_OPTION) {
			String sql="delete from product where product_id="+product_id;
			System.out.println(sql);
			
			PreparedStatement pstmt=null;
			try {
				pstmt=shopApp.con.prepareStatement(sql);
				
				//row��ȯ���� �� ������ ���� ������ ���� ���ڵ� ���� ��ȯ!!
				int row=pstmt.executeUpdate();
				if(row==0) {
					JOptionPane.showMessageDialog(this, "��������");
				}else {
					JOptionPane.showMessageDialog(this, "��������");
					getList();//�ٽ� ���̺� ����!!
				}
				product_id=0;
				//�̹����� ����!!
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				shopApp.connectionManager.closeDB(pstmt);
			}
		}
	}
	
	//�̹��� ���� 
	public boolean deleteFile() {
		//�����ϰ���� ���Ͽ� ���� ���ϰ�ü�� �����ؾ� �Ѵ�!!
		File file=new File("D:/web_app/java_workspace/Project0511/data/"+delFile);
		return file.delete();
	}
}





















