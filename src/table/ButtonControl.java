package table;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ButtonControl extends JFrame implements ActionListener{
	JButton bt_create, bt_bg;
	JPanel p_container;
	int n;
	//대부분의 프로그래밍 언어에서는 배열생성시 반드시 그 크기를 명시!!
	//크기를 결정짓는 것은 개발시 한계점도 있다..
	//but 자바스크립트의 배열은 크기가 유동적이다..자바언어에서는 유동적
	//배열 지원하지 않음..
	//자료구조!!
	//자바에서는 객체를 모아서 처리할때 유용한  api를 제공하며, 이 api
	//들은 java.util 에 모여 있다..이 라이브러리를 가리켜 자바에서는
	//Collection Framework(이미 만들어진 틀 )
	//주의 컬렉션 프레임웍이 대상으로 하는 데이터는 오직 객체이다!!
	/*
	 * 자바언어에서는 객체를 모아져 있는 모습을 크게 3가지로 본다..
	 * 1.순서없는 집합 : Set 
	 * 2.순서있는 집합: List :거의 배열이다!!
	 * 							단 차이점 
	 *                             1) 크기가 유동적이다!!
	 *                             2) 객체만을 다룬다 
	 * 3.key-value 쌍 : Map ex) json  {name:"호랑이"}
	 * */
	ArrayList<JButton> list=new ArrayList<JButton>();//크기는 0이지만,
												//고무줄처럼 늘어날수 있다!!		
	
	public ButtonControl() {
		bt_create = new JButton("생성");
		bt_bg = new JButton("색상");
		p_container = new JPanel();
		
		p_container.setPreferredSize(new Dimension(300, 300));
		p_container.setBackground(Color.BLUE);
		
		setLayout(new FlowLayout());
		add(bt_create);
		add(bt_bg);
		add(p_container);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300,400);
		setVisible(true);
		
		bt_create.addActionListener(this);
		bt_bg.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj ==bt_create) {
			create();
		}else if(obj ==bt_bg) {
			bg();
		}
	}
	public void create() {
		
		JButton bt=new JButton("버튼"+n++);
		p_container.add(bt);
		p_container.updateUI();
		
		list.add(bt);
		System.out.println("현재까지 담겨진 버튼의 수는 "+list.size());
	}
	
	public void bg() {
		for(int i=0;i<list.size();i++) {
			//ArrayList 에 넣을 수 있는 객체의 자료형은 모든 자료형이므로
			//Object 형으로 형변환되어 들어간다..따라서 끄집어 낼때 
			//Object 형이기 때문에, 다시 꺼낼때는 필요한 자료형으로 
			//down casting 해야한다!!
			
			//제너릭으로 선언된 컬렉션 프레임웍 객체는 형변환이 필요없다
			//왜? 어차피 지정한 자료형 전용이므로..(즉 섞여잇을 가능성이
			//0%이기 때문에...)
			JButton bt=list.get(i);
			bt.setBackground(Color.YELLOW);
		}
	}
	
	public static void main(String[] args) {
		new ButtonControl();
	}
}





