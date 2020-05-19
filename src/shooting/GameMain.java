package shooting;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
/*이 게임제작시 프레임은 단지 윈도우만 제공하고 다른 역할은
 * 안할거임!!
 * 대부분의 그래픽처리는 JPanel 에서 처리할 거다!!
 * */
public class GameMain extends JFrame implements ActionListener{
	JMenuBar bar;
	JMenu menu_config;//환경설정
	JMenu menu_screen; //스크린의 크기조정
	
	//환경설정 관련
	JMenuItem item_start;
	JMenuItem item_pause;
	JMenuItem item_fps; //frame per second
	JMenuItem item_exit;
	
	
	//화면배율 관련
	JMenuItem item_1m;//1배 
	JMenuItem item_2m;//2배 
	JMenuItem item_3m;//3배 1024*768

	GamePanel gamePanel;
	
	Thread thread;//게임의 루프를 처리할 쓰레드!!(심장)
	boolean gameFlag=false;
	int fps=10; //게임의 속도를 결정짓는 변수
	
	public GameMain() {
		bar = new JMenuBar();
		menu_config = new JMenu("게임설정");
		menu_screen = new JMenu("화면배율");
		
		item_start=new JMenuItem("게임시작");
		item_pause=new JMenuItem("pause");
		item_fps=new JMenuItem("게임속도");
		item_exit=new JMenuItem("게임종료");
		
		item_1m = new JMenuItem("x1");
		item_2m = new JMenuItem("x2");
		item_3m = new JMenuItem("x3");
		
		gamePanel = new GamePanel();
		
		thread = new Thread() {
			//개발자는 쓰레드로 수행할 독립코드를 run에 짜두면, 
			//JVM이 알아서 호출!! 따라서 개발자는 생성 후  start()호출로
			//JVM에게 맡겨야 한다!! 이때 start()호출에 의해 쓰레드는
			//생명주기 중 Runnable 상태로 진입!!
			public void run() {
				while(true) {
					
					try {
						Thread.sleep(fps);//Non-Runnable  영역으로 
						//지정한 시간 만큼 나가있다가 다시 복귀!!
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					gameLoop();
				}
			}
		};
		
		thread.start();
		
		//스타일 부여 
		bar.setBackground(Color.BLACK);
		menu_config.setForeground(Color.WHITE);
		menu_screen.setForeground(Color.WHITE);
		
		
		//게임설정 메뉴 조립 
		menu_config.add(item_start);
		menu_config.add(item_pause);
		menu_config.add(item_fps);
		menu_config.add(item_exit);
		
		//게임화면 배율 메뉴 조립 
		menu_screen.add(item_1m);
		menu_screen.add(item_2m);
		menu_screen.add(item_3m);

		bar.add(menu_config);
		bar.add(menu_screen);
		
		//바를 윈도우에 부착 
		setJMenuBar(bar);
		
		add(gamePanel);
		
		setVisible(true);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exitProcess();
			}
		});
		
		setOptimize(0);
		
		//메뉴아이템들에 리스너 연결 
		item_start.addActionListener(this);
		item_pause.addActionListener(this);
		item_fps.addActionListener(this);
		item_exit.addActionListener(this);
		
		item_1m.addActionListener(this);
		item_2m.addActionListener(this);
		item_3m.addActionListener(this);
		
		//포커스를 패널에 올려놓아야, 키보드 이벤트가 적용된다!!
		gamePanel.setRequestFocusEnabled(true);
		gamePanel.requestFocus();
	}
	
	//게임 루프 
	public void gameLoop() {
		//쓰레드를 멈추려고 하지말고, 조건으로 처리하자!!
		//이유? 쓰레드의  run메서드가 종료되면 쓰레드는 생명주기에서 
		//Dead상태가 되어 죽어버린다!!
		if(gameFlag) {
			//System.out.println("gameLoop() called..");
			//화면에 등장하는 모든 객체를 대상으로  tick(), render() 호출할 것임
			gamePanel.repaint();
		}
		
	}
	
	//프로그램 종료 메서드
	public void exitProcess() {
		//프로세스란? 실행중인 프로그램 한 단위
		System.exit(0);//프로세스 종료
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();//이벤트 일으킨 컴포넌트
		
		if(obj==item_start) {
			gameFlag=true;
		}else if(obj==item_pause) {
			gameFlag=false;
		}else if(obj==item_fps) {
			//프롬프트 창 띄우기!!
			String msg=JOptionPane.showInputDialog("FPS 입력");
			/*
			 *자바의 객체자료형과 기본자료형간 형변환을 지원하는 클래스를
			 *Wrapper 클래스라 하고, 모든 기본 자료형마다 Wrapper
			 *클래스가 지원된다..  ex) int --> Integer, 
			 *							boolean -->Boolean
			 *특징) 형변환도 지원하고, 객체로 변환하면 메서드까지 지원되
			 *므로 훨씬 더 많은 기능을 구현할 수 있다..
			 * */
			fps = Integer.parseInt(msg);
		}else if(obj==item_exit) {
			exitProcess();
		}else if(obj==item_1m) {
			setOptimize(0);
			setLocationRelativeTo(null);
		}else if(obj==item_2m) {
			setOptimize(1);
		}else if(obj==item_3m) {
			setOptimize(2);
		}
	}
	
	public void setOptimize(int ratio) {
		gamePanel.setScreenSize(ratio);
		pack();
		setLocationRelativeTo(null);		
	}
	
	public static void main(String[] args) {
		new GameMain();
	}
	
}



