package diary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MyCal extends JFrame {
	JPanel p_content;
	JLabel la_title;//���
	JButton bt_next;
	String[] week= {"Sun","Mon","Tue","Wed","Thur","Fri","Sat"};
	Calendar cal;
	int year;
	int month;
	int startDay;// �ش� ���� ���� ����
	int lastDate; //�� ���� ���ϱ��� �ִ���
	
	public MyCal() {
		cal = Calendar.getInstance();
		p_content = new JPanel();
		
		getCurrent();// ���糯¥ ���ϱ� 
		
		
		la_title = new JLabel(year+"-"+(month+1), SwingConstants.CENTER);
		bt_next = new JButton("����");
		
		//��Ÿ�� ���� 
		la_title.setPreferredSize(new Dimension(420, 50));
		la_title.setFont(new Font("����", Font.BOLD, 30));
		la_title.setBackground(Color.BLACK);
		la_title.setForeground(Color.BLACK);
		
		add(la_title, BorderLayout.NORTH);
		add(p_content);
		add(bt_next, BorderLayout.SOUTH);
		createCard();
		
		setSize(420,560);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		bt_next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//���� ������ 
				cal.set(year, month+1, 1);
				getCurrent(); 
				deleteCard();//������ ī�� ����� 
				createCard();//ī�� ����
			}
		});
		
	}
	public void getCurrent() {
		year=cal.get(Calendar.YEAR);
		month=cal.get(Calendar.MONTH);
		
		getStartDay(month);
	}
	
	//�ش� ���� ���� ����
	//�� �޼��� ȣ���ڴ�, �ڽ��� ���ϰ��� �ϴ� ���� �ѱ�� �ȴ�!!
	public void getStartDay(int mm) {
		Calendar calendar =  Calendar.getInstance();
		calendar.set(year, mm, 1);//1�Ϸ� �����غ���!!
		startDay=calendar.get(Calendar.DAY_OF_WEEK);
		System.out.println("����� �ñ����ϴ� "+(mm+1)+"���� ���� ������ "+week[startDay-1]);
		
		//������ ������ ��¥ ���ϱ� �������� -1�Ϸ� ����
		
		//calendar.add(Calendar.DAY_OF_MONTH,4);
		calendar.set(year, mm , -1);//1�Ϸ� �����غ���!!
		//calendar.set(Calendar.DATE,-1);
		
		lastDate=cal.getActualMaximum(Calendar.DATE);
		
		System.out.println("����� ������ "+(mm+1)+"���� ��������¥��"+lastDate);
	}
	
	
	public void deleteCard() {
		Component[] comp=p_content.getComponents();
		for(int i=0;i<comp.length;i++){
			p_content.remove(comp[i]);
		}
		p_content.updateUI();
	}
	
	
	public void createCard() {
		for(int i=1;i<=week.length;i++) {
			DateCard card = new DateCard(week[i-1],Color.CYAN);
			p_content.add(card);
		}
		
		
		int d=0;//��¿� ��¥
		String str=null;
		System.out.println("lastDate"+lastDate);
		
		for(int i=1;i<=42;i++) {
			if(i>=startDay && d<=lastDate-1) {
				d++;
				str=d+"��";
			}else {
				str="";
			}
			//System.out.println(d);
			DateCard card = new DateCard(str,Color.YELLOW);
			p_content.add(card);
		}
	}
	public static void main(String[] args) {
		new MyCal();

	}
}


