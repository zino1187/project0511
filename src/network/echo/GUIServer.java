package network.echo;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

//콘솔기반의 서버가 관리하기가 불편하므로, GUI기반으로 업그레이한다!!
public class GUIServer extends JFrame{
	JPanel p_north;
	JTextField t_port;
	JButton bt;
	JTextArea area;
	JScrollPane scroll;
	
	//서버구축 관련
	ServerSocket serverSocket;
	int port=7777;
	Thread thread;// accept()시 무한대기에 빠질수 있기 때문에 
					//메인실행부로 accept() 호출을 하지 않기 위한 용도!!
	
	public GUIServer() {
		p_north = new JPanel();
		t_port = new JTextField("7777",10);
		bt=new JButton("서버가동");
		area = new JTextArea();
		scroll = new JScrollPane(area);
		
		p_north.add(t_port);
		p_north.add(bt);
		
		add(p_north, BorderLayout.NORTH);
		add(scroll);
		setBounds(400, 100, 300, 400);
		setVisible(true);
		
		//추후 소켓 닫기 처리시 아래의 메서드는 사용안할 거임!!
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//서버가동
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//가동버튼 누름 중복방지
				bt.setEnabled(false);//비활성화!!
				
				//하나의 프로세스안에서 독립적 수행능력이 잇는 쓰레드 생성!!
				thread=new Thread() {
					public void run() {
						startServer();
					}
				};
				thread.start();//Runnable 상태로 진입!!
			}
		});
		//메세지가 누적될때, 커서 포커스가 스크롤의 제일 하단에 오게 처리 
		DefaultCaret caret = (DefaultCaret)area.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}
	
	public void startServer() {
		try {
			//서버 생성!! ( 가동은 아니다)
			serverSocket = new ServerSocket(port);
			area.append("서버 생성\n");
			
			area.append("접속자 기다리는 중...\n");
			//서버가동 
			//메인 실행부가 appept()메서드를 만나면, 무한대기에 빠진다!!
			//메인실행부의 역할? 
			//프로그램을 운영해주는 주쓰레드임, 특히 GUI 나 Event처리를 담당
			//하기 때문에 메인실행부를 절대로 루프나, 대기상태에 빠지게 해서는 
			//안된다..프로그래밍 언어에 따라서는 아예 금지사항이다!!
			
			//accept() 메서드가 접속자를 발견하면, 이 시점부터 접속한
			//클라이언트와 대화를 나눌 수 있는 소켓이 반환된다!!
			Socket socket=serverSocket.accept();
			String ip=socket.getInetAddress().getHostAddress();
			area.append(ip+"님 접속!!\n");
			
			InputStream is=socket.getInputStream();
			InputStreamReader reader=new InputStreamReader(is);
			BufferedReader buffr=new BufferedReader(reader);
			
			OutputStream os=socket.getOutputStream();
			OutputStreamWriter writer=new OutputStreamWriter(os);
			BufferedWriter buffw=new BufferedWriter(writer);
			
			//위에서 뽑아놓은 입력,출력 스트림을 이용하여 클라이언트의
			//메세지를 받고, 보내보자!!
			while(true){
				String msg=buffr.readLine();//입력:듣기
				area.append(msg+"\n");//로그 남김
				
				buffw.write(msg+"\n");//출력:말하기
				buffw.flush();//버퍼 비우기!!
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new GUIServer();
	}
}










