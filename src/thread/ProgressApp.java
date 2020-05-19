package thread;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

/*진행상황을 표현하는 컴포넌트인  JProgressBar 를 사용해본다!!*/
public class ProgressApp extends JFrame implements ActionListener{
	JProgressBar bar;
	JProgressBar bar2;
	
	JButton bt_manual,bt_auto;
	int n,n2; //누적될 변수
	
	public ProgressApp() {
		setLayout(new FlowLayout());
		bar = new JProgressBar();
		bar2 = new JProgressBar();
		
		bt_manual = new JButton("수동");
		bt_auto = new JButton("자동");
		
		//스타일 
		bar.setPreferredSize(new Dimension(350, 45));
		bar.setBackground(Color.YELLOW);
		bar.setForeground(Color.RED);
		bar2.setPreferredSize(new Dimension(350, 45));
		bar2.setBackground(Color.YELLOW);
		bar2.setForeground(Color.RED);
		
		add(bar);
		add(bar2);
		
		add(bt_manual);
		add(bt_auto);
		
		setSize(400,200);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		//버튼과 리스너 연결 
		bt_manual.addActionListener(this);
		bt_auto.addActionListener(this);
	}
	
	//프로그래스바에 색상 채우기!!
	public void fillColor() {
		n++;
		bar.setValue(n);
	}
	public void fillColor2() {
		n2++;
		bar2.setValue(n2);
	}
	
	public void manual() {
		fillColor();
	}
	
	public void auto() {
		//개발자는 쓰레드로 수행시키고 싶은 로직을  run 에작성한다!!
		Thread thread=new Thread() {
			
			//아래의 메서드는 JVM이 호출하는 것이다.
			//왜?? 그래야 시스템에 의해 랜덤하게 골고루 호출하기 때문에...
			//또한 이렇게 해야 여러개의 쓰레드를 대상으로 jvm이 멀티태스킹을
			//해주기 때문에..os 가 프로세스들을 대상으로 골고루 멀티태스킹하는것
			//과 동일한 원리...
			public void run() {
				//독립수행할 코드를 작성!!
				while(true){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					fillColor();
				}
			}
		};
		
		thread.start(); //Runnable 영역으로 밀어넣자!!
		//왜  Runnable 이라고 하나?
		//start() 메서드에 Runnable로 진입한 쓰레드는 jvm이 실행여부를
		//결정하는 후보 중 하나이므로 running 이라 하지 않고 '수행할수있는후보'
		//라고 하여 Runnable 상태에 잇다고 한다, 이때 순간적으로 JVM에 의해
		//선택한 쓰레드는 running 이라 하고 그 쓰레드가 보유한  run()메서드를
		//호출하게 된다..따라서 jvm이 호출할 run을 개발자가 호출하지 말자
		
		Thread thread2 = new Thread() {
			public void run() {
				while(true) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					fillColor2();
				}
			}
		};
		
		thread2.start();
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();//어차피 버튼의 주소를갖고 있음
		//버튼클래스만이 보유한 속성, 메서드를 호출하고 싶다면, 그땐 형변환..
		
		if(obj.equals(bt_manual)) {
			manual();
		}else if(obj.equals(bt_auto)) {
			auto();
		}
	}
	
	public static void main(String[] args) {
		new ProgressApp();
	}
	
	
	
	
	
	
	
	
	

}
