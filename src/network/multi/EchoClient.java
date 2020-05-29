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
	String ip="";//����ġ�� ����,���� ip ����
	int port=7777;//
	ClientThread clientThread;
	
	public EchoClient() {
		p_north = new JPanel();
		choice = new Choice();
		
		for(int i=80;i<=236;i++){
			choice.add("192.168.0."+i);
		}
		bt=new JButton("����");
		area = new JTextArea();
		scroll = new JScrollPane(area);
		t_input = new JTextField();
		
		//��Ÿ�� ����
		area.setBackground(Color.YELLOW);
		area.setFont(new Font("����", Font.BOLD, 16));
		
		p_north.add(choice);
		p_north.add(bt);
		add(p_north, BorderLayout.NORTH);
		
		add(scroll);
		add(t_input, BorderLayout.SOUTH);
		
		setBounds(100, 100, 300, 400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//��ư�� ������ ���� 
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();//����
			}
		});
		
		//�ؽ�Ʈ�ʵ忡 ������ ���� 
		t_input.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				//����ġ�� �޽��� ������!!
				int key = e.getKeyCode();
				
				if(key == KeyEvent.VK_ENTER) {
					
					//�ؽ�Ʈ�ʵ忡�� �ĳ��� �޽��� ����!!
					String msg=t_input.getText();
					clientThread.send(msg);//������
					t_input.setText("");//�Է°� ����!!
					
				}
			}
		});
	}
	
	public void connect() {
		try {
			client = new Socket(choice.getSelectedItem() , port);
			
			//������ ����������, �������� �޽��� ��ü�� ����
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
















