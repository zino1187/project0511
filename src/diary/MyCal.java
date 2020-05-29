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
	JLabel la_title;//년월
	JButton bt_next;
	String[] week= {"Sun","Mon","Tue","Wed","Thur","Fri","Sat"};
	Calendar cal;
	int year;
	int month;
	int startDay;// 해당 월의 시작 요일
	int lastDate; //각 월이 몇일까지 있는지
	
	public MyCal() {
		cal = Calendar.getInstance();
		p_content = new JPanel();
		
		getCurrent();// 현재날짜 구하기 
		
		
		la_title = new JLabel(year+"-"+(month+1), SwingConstants.CENTER);
		bt_next = new JButton("다음");
		
		//스타일 적용 
		la_title.setPreferredSize(new Dimension(420, 50));
		la_title.setFont(new Font("돋움", Font.BOLD, 30));
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
				//다음 월보기 
				cal.set(year, month+1, 1);
				getCurrent(); 
				deleteCard();//기존의 카드 지우고 
				createCard();//카드 생성
			}
		});
		
	}
	public void getCurrent() {
		year=cal.get(Calendar.YEAR);
		month=cal.get(Calendar.MONTH);
		
		getStartDay(month);
	}
	
	//해당 월의 시작 요일
	//이 메서드 호출자는, 자신이 구하고자 하는 월을 넘기면 된다!!
	public void getStartDay(int mm) {
		Calendar calendar =  Calendar.getInstance();
		calendar.set(year, mm, 1);//1일로 조작해본다!!
		startDay=calendar.get(Calendar.DAY_OF_WEEK);
		System.out.println("당신이 궁금해하는 "+(mm+1)+"월의 시작 요일은 "+week[startDay-1]);
		
		//각월의 마지막 날짜 구하기 다음달의 -1일로 조작
		
		//calendar.add(Calendar.DAY_OF_MONTH,4);
		calendar.set(year, mm , -1);//1일로 조작해본다!!
		//calendar.set(Calendar.DATE,-1);
		
		lastDate=cal.getActualMaximum(Calendar.DATE);
		
		System.out.println("당신이 선택한 "+(mm+1)+"월의 마지막날짜는"+lastDate);
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
		
		
		int d=0;//출력용 날짜
		String str=null;
		System.out.println("lastDate"+lastDate);
		
		for(int i=1;i<=42;i++) {
			if(i>=startDay && d<=lastDate-1) {
				d++;
				str=d+"일";
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


