package network.echo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

/*
 * 에코서버는 완성했으나, 클라이언트 접속툴이  Telnet 이다 보니
 * 대화를 나누기엔 무리가 있다.. Telnet은 대화용이 아니라, 원격지 컴퓨터에게
 * 명령어를 입력 및 출력을 처리하는 콘솔기반의 프로그램이다..
 * 개선) GUI기반의 클라이언트로 전환!!
 * */
public class EchoClient2 extends JFrame{
	JButton bt;
	JTextArea area;
	JScrollPane scroll;
	JTextField t_input;
	Socket client;
	BufferedReader buffr;
	BufferedWriter buffw;
	
	public EchoClient2() {
		bt=new JButton("접속");
		area = new JTextArea();
		scroll = new JScrollPane(area);
		t_input = new JTextField();
		
		//스타일 적용
		area.setBackground(Color.YELLOW);
		area.setFont(new Font("굴림", Font.BOLD, 16));
		
		add(bt, BorderLayout.NORTH);
		add(scroll);
		add(t_input, BorderLayout.SOUTH);
		
		setSize(300,400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});
		
		DefaultCaret caret = (DefaultCaret)area.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		t_input.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int code = e.getKeyCode();
				
				if(code==KeyEvent.VK_ENTER) {
					String outMsg = t_input.getText();
					
					try {
						buffw.write(outMsg+"\n");
						buffw.flush();
						
						String inMsg=buffr.readLine();
						area.append(inMsg+"\n");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				
				if(client.isConnected()) {
					try {
						client.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
	}
	
	public void connect() {
		try {
			client = new Socket("192.168.0.236",7777);
			buffr = new BufferedReader(new InputStreamReader(client.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new EchoClient2();
	}
}













