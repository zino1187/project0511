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

//Runnable �� ������ ��ü�� �ƴ�, ���� run �޼��带 ������ �������̽�
//�̴�
public class GUIServer extends JFrame implements Runnable {
	JPanel p_north;
	JTextField t_port;
	JButton bt;
	JTextArea area;
	JScrollPane scroll;

	// ��ȭ�� ������ �ƴ�, ������ ������ ������ ����!!
	// ��ȭ��� �������ڸ�, ��ȭ�� ��ȭ�Ⱑ �ƴ϶�, ���� �����ϴ� ���
	ServerSocket server;
	Thread thread;
	
	//����� ������ �����Ҽ����� ������ �������� �޽�����ü�� �����Ϸ���
	//�迭���ٴ� �÷��� �����ӿ��� ��ü �� List �迭�� ó������!!
	//Vector �� ����Ʈ�迭�� ��ü�̰�,  ArrayList�� ���� �����ϳ� 
	//�� ����ȭ�� �����ϹǷ�, ���� ������ȯ�濡�� �������̴�..�� ����ȭ ó����
	//���� ArrayList ���� �ӵ��� ����
	Vector<MessageObj> clientList=new Vector<MessageObj>();
	
	public GUIServer() {
		p_north = new JPanel();
		t_port = new JTextField("7777", 10);
		bt = new JButton("��������");
		area = new JTextArea();
		scroll = new JScrollPane(area);

		p_north.add(t_port);
		p_north.add(bt);

		add(p_north, BorderLayout.NORTH);
		add(scroll);
		setBounds(400, 100, 300, 400);
		setVisible(true);

		// ���� ���� �ݱ� ó���� �Ʒ��� �޼���� ������ ����!!
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// ��������
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ������ ���� �� runnable ���·� ����
				// ������ ������ Runnable�� ������ �ڸ� �Ű������� ����
				// �� �ִ�!!
				thread = new Thread(GUIServer.this);
				thread.start();
			}
		});
		// �޼����� �����ɶ�, Ŀ�� ��Ŀ���� ��ũ���� ���� �ϴܿ� ���� ó��
		DefaultCaret caret = (DefaultCaret) area.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}

	public void startServer() {
		int port = Integer.parseInt(t_port.getText());

		try {
			server = new ServerSocket(port);
			// ���� ����
			area.append("�������� �� ������ ��ٸ��� ��...\n");

			while (true) {
				Socket socket = server.accept();// �����ڰ� �߰ߵɶ����� ���Ѵ��!!
				// ���� �� �ڵ带 ���ξ������� ����ΰ� �����Ұ��, ��� ������
				// �̺�Ʈ �������� �Ҽ����� �ȴ�..���� ����� ���ξ����尡
				// �������³�, ���ѷ����� ������ �ؼ��� �ȵȴ�. �ȵ���̵�,
				// ������������ �̷� ����� �ݱ�� �Ѵ�!!
				String ip = socket.getInetAddress().getHostAddress();
				area.append(ip + " �� ������\n");
				
				//�����ڸ���  MessageObj�� �����Ͽ� ���ϵ��� ����!!
				//�������� ������ while ���� ���������� ������Ƿ�...
				MessageObj messageObj=new MessageObj(this, socket);
				//������ �޽��� ������Ʈ�� ���Ϳ� ��������!!!
				//��?? ���ʹ� ��������̱� ������ �� ���α׷��� ����ɶ�����
				//�װ��� �����ȴ�. ������� �ν��Ͻ��� �Բ���..
				
				messageObj.start();//�޽��� ��ü ����!!
				
				clientList.add(messageObj);
				area.append("������� ������ Ŭ���̾�Ʈ ���� "+clientList.size()+"\n");				
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// �����ڴ� ������� ���������� �ڵ带 run�� �������ϸ� �ȴ�...
	// �����带 �����ϴ� ����� ���������� �ִµ�, ���� Runnable ����
	// ���̽��� �̿��Ͽ� �����ϴ� ����� �̿��غ�����..
	public void run() {
		startServer();
	}

	public static void main(String[] args) {
		new GUIServer();
	}

}
