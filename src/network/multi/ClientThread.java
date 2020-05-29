package network.multi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/*
 * �����ɽ��ð��� �޸�, �������� ���ƿ� �ٸ� ����� ���� �޽�����
 * ���� ����ڰ� ��� �׼��� ���� �ʾƵ� (��-Ű���� ����) ������ 
 * û��Ǿ�� �Ѵ�..
 * ���� ���ѷ����� û������� �����ؾ��ϴµ�, �̶� ���ν���θ� 
 * ���ѷ����� ������ �ϸ� GUIó��,  Event ó���� �Ҽ� ���� �ǹǷ� 
 * listen�� ���� ó���� ������ ������ ���� ������� ó������!!
 * ��?? ������� �ϳ��� ���μ��������� ���������� ����ɼ��ִ� �����̹Ƿ�
 * ���ξ�����ʹ� ������ ���ѷ������� ó���� �����ϴ�...
 * */
public class ClientThread extends Thread{
	//�޼��� �ְ�ޱ� ���� ����� ��Ʈ�� 
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	EchoClient echoClient;
	
	public ClientThread(EchoClient echoClient,Socket socket) {
		this.echoClient=echoClient;
		this.socket=socket;
		//���Ӱ� ���ÿ� ��ȭ�� �������κ��� ��Ʈ�� �̱�!!
		try {
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//������ �޽��� ������!!
	public void send(String msg) {
		try {
			buffw.write(msg+"\n");
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//�޽��� �ޱ� 
	public void listen() {
		String msg=null;
		try {
			while(true) {//�ٸ� ����� �޽����� ������ û��!!(�ǽð� û��)
				msg=buffr.readLine();//û��!!
				echoClient.area.append(msg+"\n");//log
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	public void run() {
		listen();
	}
}





