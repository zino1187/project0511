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
 * ���ڼ����� �ϼ�������, Ŭ���̾�Ʈ ��������  Telnet �̴� ����
 * ��ȭ�� �����⿣ ������ �ִ�.. Telnet�� ��ȭ���� �ƴ϶�, ������ ��ǻ�Ϳ���
 * ��ɾ �Է� �� ����� ó���ϴ� �ֱܼ���� ���α׷��̴�..
 * ����) GUI����� Ŭ���̾�Ʈ�� ��ȯ!!
 * */
public class EchoClient extends JFrame{
	JPanel p_north;
	Choice choice;
	JButton bt;
	JTextArea area;
	JScrollPane scroll;
	JTextField t_input;
	Socket client; //��ȭ�� ���� :��ȭ��� ����!!
	//������ �����Ͽ� �ش� ��Ʈ��Ʈ ���α׷��� ã�ư��� ���ؼ���  ip, port ���
	String ip="192.168.0.236";//����ġ�� ����,���� ip ����
	int port=7777;//
	BufferedWriter buffw; //���ϱ�� ��Ʈ��
	BufferedReader buffr;//���� ��Ʈ��
	
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
				connect();
			}
		});
		
		//�ؽ�Ʈ�ʵ忡 ������ ���� 
		t_input.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				
				if(key == KeyEvent.VK_ENTER){//����ġ��..
					//1�ܰ�:������ �޽����� ������!!
					String msg=t_input.getText();
					
					try {
						buffw.write(msg+"\n");//������ ���!!
						//����ó���� ��½�Ʈ���� ��Ʈ���� ä���� �����͸�
						//���� �޼��带 ȣ���ؾ� �Ѵ�..
						buffw.flush();//���ۿ� �����ϴ� ������ �����!!
						
						//������ ���� �޽����� �޾Ƽ�  area ���!!
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
			
			//������ �޽����� ���� ��Ʈ�� ��� 
			buffw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			//������ �޽����� �޴� ��Ʈ�� ���
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

















