package network.uni;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JTextArea;

//�� ��ü�� Socket, BufferedReader, BufferedWriter�� 
//������ ���� �뵵�� �����ߴ�
public class MessageObj {
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	JTextArea area;
	
	//�����ڰ� �߻�������, ����� ������ ���޹���!!
	public MessageObj(JTextArea area, Socket socket) {
		this.area=area;
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
				area.append(msg+"\n");
				//������ 
				send(msg);
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
}


















