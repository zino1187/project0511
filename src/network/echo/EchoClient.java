package network.echo;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*
 * 에코서버는 완성했으나, 클라이언트 접속툴이  Telnet 이다 보니
 * 대화를 나누기엔 무리가 있다.. Telnet은 대화용이 아니라, 원격지 컴퓨터에게
 * 명령어를 입력 및 출력을 처리하는 콘솔기반의 프로그램이다..
 * 개선) GUI기반의 클라이언트로 전환!!
 * */
public class EchoClient extends JFrame{
	JPanel p_north;
	Choice choice;
	JButton bt;
	JTextArea area;
	JScrollPane scroll;
	JTextField t_input;
	Socket client; //대화용 소켓 :전화기와 동일!!
	//서버에 접속하여 해당 네트워트 프로그램을 찾아가기 위해서는  ip, port 사용
	String ip="192.168.0.236";//따라치지 말기,나의 ip 적기
	int port=7777;//
	BufferedWriter buffw; //말하기용 스트림
	BufferedReader buffr;//듣기용 스트림
	
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
				connect();
			}
		});
		
		//텍스트필드에 리스너 연결 
		t_input.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				
				if(key == KeyEvent.VK_ENTER){//엔터치면..
					//1단계:서버에 메시지를 보내자!!
					String msg=t_input.getText();
					
					try {
						buffw.write(msg+"\n");//서버로 출력!!
						//버퍼처리된 출력스트림은 스트림에 채워진 데이터를
						//비우는 메서드를 호출해야 한다..
						buffw.flush();//버퍼에 존재하는 데이터 비워줌!!
						
						//서버가 보낸 메시지를 받아서  area 출력!!
						String inMsg=buffr.readLine();
						area.append(inMsg+"\n");
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			}
		});
	}
	
	public void connect() {
		try {
			client = new Socket(choice.getSelectedItem() , port);
			
			//서버에 메시지를 보낼 스트림 얻기 
			buffw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			//서버의 메시지를 받는 스트림 얻기
			buffr=new BufferedReader(new InputStreamReader(client.getInputStream()));
			
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

















