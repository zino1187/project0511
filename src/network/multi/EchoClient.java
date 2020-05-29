package network.multi;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
public class EchoClient extends JFrame{
	JPanel p_north;
	Choice choice;
	JButton bt;
	JTextArea area;
	JScrollPane scroll;
	JTextField t_input;
	Socket client;
	String ip="";//따라치지 말기,나의 ip 적기
	int port=7777;//
	ClientThread clientThread;
	
	public EchoClient() {
		p_north = new JPanel();
		choice = new Choice();
		
		for(int i=80;i<=236;i++){
			choice.add("192.168.0."+i);
		}
		bt=new JButton("접속");
		area = new JTextArea();
		scroll = new JScrollPane(area);
		t_input = new JTextField();
		
		//스타일 적용
		area.setBackground(Color.YELLOW);
		area.setFont(new Font("굴림", Font.BOLD, 16));
		
		p_north.add(choice);
		p_north.add(bt);
		add(p_north, BorderLayout.NORTH);
		
		add(scroll);
		add(t_input, BorderLayout.SOUTH);
		
		setBounds(100, 100, 300, 400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//버튼에 리스너 연결 
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();//접속
			}
		});
		
		//텍스트필드에 리스너 연결 
		t_input.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				//엔터치면 메시지 보내기!!
				int key = e.getKeyCode();
				
				if(key == KeyEvent.VK_ENTER) {
					
					//텍스트필드에서 쳐넣은 메시지 전달!!
					String msg=t_input.getText();
					clientThread.send(msg);//보내고
					t_input.setText("");//입력값 비우기!!
					
				}
			}
		});
	}
	
	public void connect() {
		try {
			client = new Socket(choice.getSelectedItem() , port);
			
			//접속을 성공했으니, 쓰레드인 메시지 객체를 생성
			clientThread = new ClientThread(this, client);
			clientThread.start();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	
	public static void main(String[] args) {
		new EchoClient();
	}
}

















