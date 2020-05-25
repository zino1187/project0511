package homework.shop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

//�ϳ��� ȭ���� ������ �гη� ó������!~!
public class Login extends Page{
	JPanel loginBox;
	JPanel form;//�α��� ��
	JLabel la_id;
	JLabel la_pw;
	JTextField t_id;
	JPasswordField t_pw;
	JButton bt_regist;
	
	public Login(ShopApp shopApp, String title,Color color, int width,int height,boolean showFlag) {
		super(shopApp,title,color,width,height,showFlag);	
		
		loginBox = new JPanel();
		form = new JPanel();
		la_id = new JLabel("ID");
		la_pw = new JLabel("Password");
		t_id = new JTextField();
		t_pw = new JPasswordField();
		bt_regist = new JButton("Login");
		
		//��Ÿ�� ����
		loginBox.setBackground(Color.WHITE);
		loginBox.setPreferredSize(new Dimension(250, 170));
		loginBox.setPreferredSize(new Dimension(250, 100));
		
		//���̾ƿ� ���� 
		form.setLayout(new GridLayout(2,2));
		
		//����
		form.add(la_id);
		form.add(t_id);
		form.add(la_pw);
		form.add(t_pw);
		
		loginBox.add(form);
		loginBox.add(bt_regist);
		
		add(loginBox);
		
		//��ư�� ������ ���� 
		bt_regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginCheck();
			}
		});
	}
	public void loginCheck() {
		//?����ǥ ǥ����� ����: ���ε� �������Ѵ�...
		//�����ͺ��̽� ��������� ���� ���..
		String sql="select * from admin where id=? and pass=?";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			pstmt=shopApp.con.prepareStatement(sql);
			pstmt.setString(1, t_id.getText());//id
			pstmt.setString(2, new String(t_pw.getPassword()));
			
			//System.out.println(t_pw.getText());
			rs=pstmt.executeQuery();//��������!!
			if(rs.next()) {//��ġ�ϴ� ������ ������
				shopApp.hasAuth=true;
				JOptionPane.showMessageDialog(this, "�����Ǿ����ϴ�.");
				shopApp.m_login.setText("Logout");
			}else {
				//��ġ�ϴ� ������ ����
				shopApp.hasAuth=false;
				JOptionPane.showMessageDialog(this, "���� ���Ф̤�");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			shopApp.connectionManager.closeDB(pstmt, rs);
		}
		
	}
	
}

















