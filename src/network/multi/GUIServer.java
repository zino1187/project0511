package network.multi;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

//Runnable 은 쓰레드 자체는 아닌, 단지 run 메서드를 보유한 인터페이스
//이다
public class GUIServer extends JFrame implements Runnable {
	JPanel p_north;
	JTextField t_port;
	JButton bt;
	JTextArea area;
	JScrollPane scroll;

	// 대화용 소켓이 아닌, 접속자 감지용 소켓을 선언!!
	// 전화기와 비유하자면, 대화용 수화기가 아니라, 벨을 전담하는 기능
	ServerSocket server;
	Thread thread;
	
	//몇명이 들어올지 예측할수없기 때문에 여러개의 메시지객체를 보유하려면
	//배열보다는 컬렉션 프레임웍의 객체 중 List 계열로 처리하자!!
	//Vector 는 리스트계열의 객체이고,  ArrayList와 거의 동일하나 
	//단 동기화를 지원하므로, 다중 쓰레드환경에서 안정적이다..단 동기화 처리에
	//의해 ArrayList 보다 속도가 느림
	Vector<MessageObj> clientList=new Vector<MessageObj>();
	
	public GUIServer() {
		p_north = new JPanel();
		t_port = new JTextField("7777", 10);
		bt = new JButton("서버가동");
		area = new JTextArea();
		scroll = new JScrollPane(area);

		p_north.add(t_port);
		p_north.add(bt);

		add(p_north, BorderLayout.NORTH);
		add(scroll);
		setBounds(400, 100, 300, 400);
		setVisible(true);

		// 추후 소켓 닫기 처리시 아래의 메서드는 사용안할 거임!!
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 서버가동
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 쓰레드 생성 및 runnable 상태로 진입
				// 쓰레드 생성시 Runnable을 구현한 자를 매개변수로 넣을
				// 수 있다!!
				thread = new Thread(GUIServer.this);
				thread.start();
			}
		});
		// 메세지가 누적될때, 커서 포커스가 스크롤의 제일 하단에 오게 처리
		DefaultCaret caret = (DefaultCaret) area.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}

	public void startServer() {
		int port = Integer.parseInt(t_port.getText());

		try {
			server = new ServerSocket(port);
			// 서버 가동
			area.append("서버생성 및 접속자 기다리는 중...\n");

			while (true) {
				Socket socket = server.accept();// 접속자가 발견될때까지 무한대기!!
				// 만일 이 코드를 메인쓰레드인 실행부가 수행할경우, 모든 디자인
				// 이벤트 감지등을 할수없게 된다..따라서 절대로 메인쓰레드가
				// 지연상태나, 무한루프에 빠지게 해서는 안된다. 안드로이드,
				// 아이폰에서는 이런 방식을 금기시 한다!!
				String ip = socket.getInetAddress().getHostAddress();
				area.append(ip + " 님 접속함\n");
				
				//접속자마다  MessageObj를 생성하여 소켓등을 보관!!
				//보관하지 않으면 while 문의 지역변수로 사라지므로...
				MessageObj messageObj=new MessageObj(this, socket);
				//생성된 메시지 오브젝트를 백터에 보관하자!!!
				//왜?? 백터는 멤버변수이기 때문에 이 프로그램이 종료될때까지
				//그값이 유지된다. 생명력이 인스턴스와 함께함..
				
				messageObj.start();//메시지 객체 동작!!
				
				clientList.add(messageObj);
				area.append("현재까지 접속한 클라이언트 수는 "+clientList.size()+"\n");				
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 개발자는 쓰레드로 독립수행할 코드를 run에 재정의하면 된다...
	// 쓰레드를 구현하는 방법은 여러가지가 있는데, 그중 Runnable 인터
	// 페이스를 이용하여 구현하는 방법을 이용해본것임..
	public void run() {
		startServer();
	}

	public static void main(String[] args) {
		new GUIServer();
	}

}
