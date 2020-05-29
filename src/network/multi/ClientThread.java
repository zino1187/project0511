package network.multi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/*
 * 유니케스팅과는 달리, 서버에서 날아온 다른 사람이 보낸 메시지는
 * 현재 사용자가 어떠한 액션을 하지 않아도 (예-키보드 엔터) 언제나 
 * 청취되어야 한다..
 * 따라서 무한루프로 청취업무를 수행해야하는데, 이때 메인실행부를 
 * 무한루프에 빠지게 하면 GUI처리,  Event 처리를 할수 없게 되므로 
 * listen에 대한 처리는 별도의 개발자 정의 쓰레드로 처리하자!!
 * 왜?? 쓰레드란 하나의 프로세스내에서 독립적으로 수행될수있는 단위이므로
 * 메인쓰레드와는 별도로 무한루프등의 처리에 적절하다...
 * */
public class ClientThread extends Thread{
	//메세지 주고받기 위한 입출력 스트림 
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	EchoClient echoClient;
	
	public ClientThread(EchoClient echoClient,Socket socket) {
		this.echoClient=echoClient;
		this.socket=socket;
		//접속과 동시에 대화용 소켓으로부터 스트림 뽑기!!
		try {
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//서버에 메시지 보내기!!
	public void send(String msg) {
		try {
			buffw.write(msg+"\n");
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//메시지 받기 
	public void listen() {
		String msg=null;
		try {
			while(true) {//다른 사람의 메시지를 언제나 청취!!(실시간 청취)
				msg=buffr.readLine();//청취!!
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





