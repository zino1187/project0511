package network.multi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

//�� ��ü�� Socket, BufferedReader, BufferedWriter�� 
//������ ���� �뵵�� �����ߴ�
//�� �޽��� ��ü��, ���� ������ ���� �ʰ� ���������� �����ؾ� �Ѵ�.
//�������? �ϳ��� ���μ��������� ���������� ����� �� �ִ� ���ν������
//�̶� �����ڴ� ���������� �ڵ带 run�޼��忡 �ۼ��Ѵ�!!
public class MessageObj extends Thread{
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	GUIServer server;
	
	//�����ڰ� �߻�������, ����� ������ ���޹���!!
	public MessageObj(GUIServer server, Socket socket) {
		this.server=server;
		this.socket = socket; 
		//�������κ��� ��Ʈ�� �̱�!!
		try {
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	//���� �� ��Ʈ���� �� Ŭ������ ���������Ƿ� , ���⼭ �޽��� ó�����Ѵ�
	
	//�޽��� û�� 
	public void listen() {
		String msg=null;
		
		try {
			while(true) {
				msg=buffr.readLine();
				//log
				server.area.append(msg+"\n");
				//������
				
				//������ �����ؼ�, ���� ������ ������ �ٸ�  MessageObj
				//�� send()�� ȣ������!!
				//clientList��ŭ �ݺ��� �����鼭  send() ȣ���ϸ� ��!!
				//�̶� ����������� �޼����� ���ư��Ƿ�, Multi Casting
				for(int i=0;i<server.clientList.size();i++) {
					MessageObj obj=server.clientList.get(i);
					obj.send(msg);					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//�޽��� ���� 
	public void send(String msg){
		try {
			buffw.write(msg+"\n");
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//���������� �ڵ带 run�� �ۼ�!!
	@Override
	public void run() {
		listen();
	}
}



























