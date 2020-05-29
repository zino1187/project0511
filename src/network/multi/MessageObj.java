package network.multi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

//이 객체는 Socket, BufferedReader, BufferedWriter를 
//보관해 놓을 용도로 정의했다
//각 메시지 객체는, 서로 영향을 받지 않고 독립적으로 동작해야 한다.
//쓰레드란? 하나의 프로세스내에서 독립적으로 실행될 수 있는 세부실행단위
//이때 개발자는 독립수행할 코드를 run메서드에 작성한다!!
public class MessageObj extends Thread{
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	GUIServer server;
	
	//접속자가 발생했을때, 얻어진 소켓을 전달받자!!
	public MessageObj(GUIServer server, Socket socket) {
		this.server=server;
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
				server.area.append(msg+"\n");
				//보내기
				
				//내꺼를 포함해서, 지금 서버에 생성된 다른  MessageObj
				//의 send()도 호출하자!!
				//clientList만큼 반복문 돌리면서  send() 호출하면 됨!!
				//이때 여러사람에게 메세지가 날아가므로, Multi Casting
				for(int i=0;i<server.clientList.size();i++) {
					MessageObj obj=server.clientList.get(i);
					obj.send(msg);					
				}
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
	
	//독립수행할 코드를 run에 작성!!
	@Override
	public void run() {
		listen();
	}
}



























