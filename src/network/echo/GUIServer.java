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

//�ֱܼ���� ������ �����ϱⰡ �����ϹǷ�, GUI������� ���׷����Ѵ�!!
public class GUIServer extends JFrame{
	JPanel p_north;
	JTextField t_port;
	JButton bt;
	JTextArea area;
	JScrollPane scroll;
	
	//�������� ����
	ServerSocket serverSocket;
	int port=7777;
	Thread thread;// accept()�� ���Ѵ�⿡ ������ �ֱ� ������ 
					//���ν���η� accept() ȣ���� ���� �ʱ� ���� �뵵!!
	
	public GUIServer() {
		p_north = new JPanel();
		t_port = new JTextField("7777",10);
		bt=new JButton("��������");
		area = new JTextArea();
		scroll = new JScrollPane(area);
		
		p_north.add(t_port);
		p_north.add(bt);
		
		add(p_north, BorderLayout.NORTH);
		add(scroll);
		setBounds(400, 100, 300, 400);
		setVisible(true);
		
		//���� ���� �ݱ� ó���� �Ʒ��� �޼���� ������ ����!!
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//��������
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//������ư ���� �ߺ�����
				bt.setEnabled(false);//��Ȱ��ȭ!!
				
				//�ϳ��� ���μ����ȿ��� ������ ����ɷ��� �մ� ������ ����!!
				thread=new Thread() {
					public void run() {
						startServer();
					}
				};
				thread.start();//Runnable ���·� ����!!
			}
		});
		//�޼����� �����ɶ�, Ŀ�� ��Ŀ���� ��ũ���� ���� �ϴܿ� ���� ó�� 
		DefaultCaret caret = (DefaultCaret)area.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}
	
	public void startServer() {
		try {
			//���� ����!! ( ������ �ƴϴ�)
			serverSocket = new ServerSocket(port);
			area.append("���� ����\n");
			
			area.append("������ ��ٸ��� ��...\n");
			//�������� 
			//���� ����ΰ� appept()�޼��带 ������, ���Ѵ�⿡ ������!!
			//���ν������ ����? 
			//���α׷��� ����ִ� �־�������, Ư�� GUI �� Eventó���� ���
			//�ϱ� ������ ���ν���θ� ����� ������, �����¿� ������ �ؼ��� 
			//�ȵȴ�..���α׷��� �� ���󼭴� �ƿ� ���������̴�!!
			
			//accept() �޼��尡 �����ڸ� �߰��ϸ�, �� �������� ������
			//Ŭ���̾�Ʈ�� ��ȭ�� ���� �� �ִ� ������ ��ȯ�ȴ�!!
			Socket socket=serverSocket.accept();
			String ip=socket.getInetAddress().getHostAddress();
			area.append(ip+"�� ����!!\n");
			
			InputStream is=socket.getInputStream();
			InputStreamReader reader=new InputStreamReader(is);
			BufferedReader buffr=new BufferedReader(reader);
			
			OutputStream os=socket.getOutputStream();
			OutputStreamWriter writer=new OutputStreamWriter(os);
			BufferedWriter buffw=new BufferedWriter(writer);
			
			//������ �̾Ƴ��� �Է�,��� ��Ʈ���� �̿��Ͽ� Ŭ���̾�Ʈ��
			//�޼����� �ް�, ��������!!
			while(true){
				String msg=buffr.readLine();//�Է�:���
				area.append(msg+"\n");//�α� ����
				
				buffw.write(msg+"\n");//���:���ϱ�
				buffw.flush();//���� ����!!
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new GUIServer();
	}
}










