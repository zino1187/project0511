package homework.shop;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShopApp extends JFrame implements ActionListener{
	JPanel p_north;
	JPanel p_content;//페이지가 붙게될 컨텐츠 영역!!
	JButton m_product;//상품관리
	JButton m_main;//사용자가 보게될 쇼핑몰 메인화면
	JButton m_login;//관리자 인증 화면
	JButton m_chat;//관리자와 1:1 채팅화면
	
	Page[] pages=new Page[4];//페이지 수 만큼 배열확보
	
	public ShopApp() {
		p_north 		= new JPanel();
		p_content 		= new JPanel();
		m_product 	= new JButton("상품관리");
		m_main 		= new JButton("쇼핑몰메인");
		m_login 		= new JButton("로그인");
		m_chat 			= new JButton("1:1채팅");
		
		//페이지 구성하기 
		pages[0]=new ProductManage(this,"상품관리",Color.YELLOW,700,500,true);
		pages[1]=new ShoppingMain(this,"쇼핑몰메인",Color.RED,700,500,false);
		pages[2]=new Login(this,"로그인",Color.BLUE,700,500,false);
		pages[3]=new Chatting(this,"1:1채팅",Color.GREEN,700,500,false);
		
		//스타일적용
		p_north.setBackground(Color.BLACK);
		
		//조립
		p_north.add(m_product);
		p_north.add(m_main);
		p_north.add(m_login);
		p_north.add(m_chat);
		
		add(p_north, BorderLayout.NORTH);
		
		//모든 페이지가 프레임에 붙는게 아니라, 컨텐츠영역에 붙어야 
		//하므로..
		add(p_content);
		
		p_content.add(pages[0]);
		p_content.add(pages[1]);
		p_content.add(pages[2]);
		p_content.add(pages[3]);
		
		pack();//보유한 내용물까지 쪼그라든다!!
		//setSize(700,600);
		setVisible(true);
		
		//임시적으로 쓸거임
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		//버튼과 리스너 연결 
		m_product.addActionListener(this);
		m_main.addActionListener(this);
		m_login.addActionListener(this);
		m_chat.addActionListener(this);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj==m_product) {
			showPage(0);
		}else if(obj==m_main) {
			showPage(1);
		}else if(obj==m_login) {
			showPage(2);			
		}else if(obj==m_chat) {
			showPage(3);
		}
	}
	
	//화면전환 메서드 
	//보여질 페이지를 인수로 넘기면 됨!!
	public void showPage(int page) {
		//윈도우의 제목을 패널의 제목으로 교체!!
		this.setTitle(pages[page].title);
		
		for(int i=0;i<pages.length;i++) {
			if(i==page) {
				pages[i].setVisible(true);
			}else {
				pages[i].setVisible(false);
			}
		}
	}
	
	public static void main(String[] args) {
		new ShopApp();
	}
}











