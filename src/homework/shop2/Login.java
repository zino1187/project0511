package homework.shop2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

//하나의 화면은 앞으로 패널로 처리하자!~!
public class Login extends Page{
	JPanel loginBox;
	JPanel form;//로그인 폼
	JLabel la_id;
	JLabel la_pw;
	JTextField t_id;
	JTextField t_pw;
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
		
		//스타일 적용
		loginBox.setBackground(Color.WHITE);
		loginBox.setPreferredSize(new Dimension(250, 170));
		loginBox.setPreferredSize(new Dimension(250, 100));
		
		//레이아웃 변경 
		form.setLayout(new GridLayout(2,2));
		
		//조립
		form.add(la_id);
		form.add(t_id);
		form.add(la_pw);
		form.add(t_pw);
		
		loginBox.add(form);
		loginBox.add(bt_regist);
		
		add(loginBox);
	}

	
}










