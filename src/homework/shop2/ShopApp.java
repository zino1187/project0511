package homework.shop2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ShopApp extends JFrame implements ActionListener{
	JPanel p_north;
	JPanel p_content;//�������� �ٰԵ� ������ ����!!
	JButton m_product;//��ǰ����
	JButton m_main;//����ڰ� ���Ե� ���θ� ����ȭ��
	JButton m_login;//������ ���� ȭ��
	JButton m_chat;//�����ڿ� 1:1 ä��ȭ��
	
	Page[] pages=new Page[4];//������ �� ��ŭ �迭Ȯ��
	ConnectionManager connectionManager;
	Connection con;//�гε� �� ���������� ��� ������ �� �ֵ��� 
							//�θ� �����̳��� �����쿡 �����س���!!
	int currentPage=1;
	boolean hasAuth=true;
	
	public ShopApp() {
		p_north 		= new JPanel();
		p_content 		= new JPanel();
		m_product 	= new JButton("��ǰ����");
		m_main 		= new JButton("���θ�����");
		m_login 		= new JButton("�α���");
		m_chat 			= new JButton("1:1ä��");
		
		//������ �����ϱ� 
		pages[0]=new ProductManage(this,"��ǰ����",Color.YELLOW,700,500,false);
		pages[1]=new ShoppingMain(this,"���θ�����",Color.RED,700,500,true);
		pages[2]=new Login(this,"�α���",Color.BLUE,700,500,false);
		pages[3]=new Chatting(this,"1:1ä��",Color.GREEN,700,500,false);
		
		//���Ӱ����� ���� 
		connectionManager = new ConnectionManager();
		
		//��Ÿ������
		p_north.setBackground(Color.BLACK);
		
		//����
		p_north.add(m_product);
		p_north.add(m_main);
		p_north.add(m_login);
		p_north.add(m_chat);
		
		add(p_north, BorderLayout.NORTH);
		
		//��� �������� �����ӿ� �ٴ°� �ƴ϶�, ������������ �پ�� 
		//�ϹǷ�..
		add(p_content);
		
		p_content.add(pages[0]);
		p_content.add(pages[1]);
		p_content.add(pages[2]);
		p_content.add(pages[3]);
		
		pack();//������ ���빰���� �ɱ׶���!!
		//setSize(700,600);
		setVisible(true);
		
		//�ӽ������� ������
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		//��ư�� ������ ���� 
		m_product.addActionListener(this);
		m_main.addActionListener(this);
		m_login.addActionListener(this);
		m_chat.addActionListener(this);
		
		//����Ŭ ����!!
		con=connectionManager.getConnection();
		if(con==null) {
			JOptionPane.showMessageDialog(this, "�����ͺ��� ���� ����");
		}else {
			//JOptionPane.showMessageDialog(this, "�����ͺ��� ���� ����");
		}
		
		//�������� 
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				connectionManager.closeDB(con);//DB��������
				System.exit(0);//���μ��� ����
			}
		});
		
		//������ ���ԵǴ� ����ȭ��!!!
		showPage(1);
		ShoppingMain main=(ShoppingMain)pages[1];
		main.removeChild();// ����������Ʈ ����
		main.selectAll();//���� ����
		main.updateUI();
	}
	
	
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj==m_product) {
			showPage(0);
		}else if(obj==m_main) {
			showPage(1);
			//ShoppingMain Ŭ������  selectAll() �޼��� ȣ������!!
			//�� ������ �̹� �����ͺ��̽����� ������ �Ϸ�� ���Ķ� ����!!
			ShoppingMain main=(ShoppingMain)pages[1];
			main.removeChild();// ����������Ʈ ����
			main.selectAll();//���� ����
		}else if(obj==m_login) {
			showPage(2);			
		}else if(obj==m_chat) {
			showPage(3);
		}
	}
	
	//ȭ����ȯ �޼��� 
	//������ �������� �μ��� �ѱ�� ��!!
	public void showPage(int page) {
		//���� ���� �����ִ� �������� �����ڻ�ǰ����������̰�, 
		//���� ������ �������� ������ ..
		if(page==0 && !hasAuth) {
			JOptionPane.showMessageDialog(this, "�α����� �ʿ��� �����Դϴ�");
			return;
		}
		currentPage=page;
		
		//�������� ������ �г��� �������� ��ü!!
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











