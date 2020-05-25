package network.uni;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JTextArea;

//이 객체는 Socket, BufferedReader, BufferedWriter를 
//보관해 놓을 용도로 정의했다
public class MessageObj {
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	JTextArea area;
	
	//접속자가 발생했을때, 얻어진 소켓을 전달받자!!
	public MessageObj(JTextArea area, Socket socket) {
		this.area=area;
		this.socket = socket; 
		//소켓으로부터 스트림 뽑기!!
		try {
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	//소켓 및 스트림이 이 클래스로 이전햇으므로 , 여기서 메시지 처리를한다
	
	//메시지 청취 
	public void listen() {
		String msg=null;
		
		try {
			while(true) {
				msg=buffr.readLine();
				//log
				area.append(msg+"\n");
				//보내기 
				send(msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//메시지 전송 
	public void send(String msg){
		try {
			buffw.write(msg+"\n");
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}


















