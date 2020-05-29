package network.echo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * 전기 분야에서 소켓의 역할은 일반인들로 하여금, 전기에 대한 전문지식이
 * 없이도, 전구를 끼어넣으면 동작하듯, 프로그래밍 분야에서도 이 소켓의
 * 개념이 적용된다..
 * 따라서 개발자는 소켓을 다룰줄 알면 네트워크에 대한 전문지식없이도 
 * 네트워크 프로그래밍이 가능하다!!
 * 
 * 참고) 네트워크 프로그래밍은 전화를 연상하면 너무 쉽다!! 
 * */

//클라이언트의 접속을 받는 서버를 정의한다
public class EchoServer {
	//소켓을 이용하여 대화를 나누기 전, 클라이언트의 접속을 처리하는
	//객체
	
	//포트번호는 네트워크 프로그램을 구분해주는 고유번호이다.
	//1~1024번까지는 이미 시스템내부에서 점유하고 있기 때문에
	//사용을 피한다..그리고 이미유명한 상용 프로그램이 사용하는
	//포트도 피해야 한다..
	int port=7777;
	
	ServerSocket serverSocket;
	
	public EchoServer() {
		try {
			//소켓 서버 생성!!
			serverSocket = new ServerSocket(port);
			System.out.println("소켓 서버 생성 성공");
			
			//접속클라이언트를 감지하고, 접속이 감지되면 대화용 소켓을 반환
			//accept()메서드는 클라이언트가 접속할때까지 무한대기에 빠짐
			//접속이 감지되면, 그 시점부터는 클라이언트와 통신을 시도할수
			//있는 소켓이 반환된다!!
				Socket socket=serverSocket.accept();
				InetAddress net=socket.getInetAddress();
				String ip=net.getHostAddress();
				
				System.out.println(ip+"님 접속");
				
				//소켓은 대화를 나누는데 사용되는객체이며 대화는 
				//클라이언트의 메시지 듣기!!! 즉 입력스트림을 이용한다!!
				
				//소켓이 이미 연결된 스트림을 보유하고 있으므로, 그 스트림을
				//얻으면 된다!! 대화에서 핵심 객체는 결국 소켓이다!!
				
				/*스트림의 유형을 처리방법에 따른 기준
				 * 바이트기반 스트림 : 1byte씩 처리하는 , 근본 스트림 
				 * 문자기반 : 2byte를 묶어 문자로 이해할 수 있는 스트림
				 * 버퍼기반: 바이트를 버퍼에 모아 입출력을 처리하는
				 *             스트림 
				 * */ 
				InputStream is=socket.getInputStream();
				InputStreamReader reader=new InputStreamReader(is);
				BufferedReader buffr=new BufferedReader(reader);
				
				//서버의 역할은 클라이언트의 메시지를 받아, 다시 클라이언트에게
				//보내야 하므로, 출력을 처리한다!!
				OutputStream os=socket.getOutputStream();
				OutputStreamWriter writer=new OutputStreamWriter(os);
				BufferedWriter buffw=new BufferedWriter(writer);
				
				
				
				String data=null;
				while(true) {
					data=buffr.readLine(); //클라이언트와 연결된 스트림으로부터
					//데이터를 1 byte 읽는다!!
					//클라이언트에 출력!!
					//버퍼의 끝을 알려주지 않으면, 클라이언트는 무한대기에 빠진
					//다, 왜? 입력이 완료되지 않았으니깐..ex) cml창에 입력할
					//때 엔터쳐서 입력의 완료를 표시하듯이..
					buffw.write(data+"\n");//맨뒤에 엔터친 효과내기..
					buffw.flush();
					
					System.out.println(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new EchoServer();
	}
}





















