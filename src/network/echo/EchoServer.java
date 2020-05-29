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
 * ���� �о߿��� ������ ������ �Ϲ��ε�� �Ͽ���, ���⿡ ���� ����������
 * ���̵�, ������ ��������� �����ϵ�, ���α׷��� �о߿����� �� ������
 * ������ ����ȴ�..
 * ���� �����ڴ� ������ �ٷ��� �˸� ��Ʈ��ũ�� ���� �������ľ��̵� 
 * ��Ʈ��ũ ���α׷����� �����ϴ�!!
 * 
 * ����) ��Ʈ��ũ ���α׷����� ��ȭ�� �����ϸ� �ʹ� ����!! 
 * */

//Ŭ���̾�Ʈ�� ������ �޴� ������ �����Ѵ�
public class EchoServer {
	//������ �̿��Ͽ� ��ȭ�� ������ ��, Ŭ���̾�Ʈ�� ������ ó���ϴ�
	//��ü
	
	//��Ʈ��ȣ�� ��Ʈ��ũ ���α׷��� �������ִ� ������ȣ�̴�.
	//1~1024�������� �̹� �ý��۳��ο��� �����ϰ� �ֱ� ������
	//����� ���Ѵ�..�׸��� �̹������� ��� ���α׷��� ����ϴ�
	//��Ʈ�� ���ؾ� �Ѵ�..
	int port=7777;
	
	ServerSocket serverSocket;
	
	public EchoServer() {
		try {
			//���� ���� ����!!
			serverSocket = new ServerSocket(port);
			System.out.println("���� ���� ���� ����");
			
			//����Ŭ���̾�Ʈ�� �����ϰ�, ������ �����Ǹ� ��ȭ�� ������ ��ȯ
			//accept()�޼���� Ŭ���̾�Ʈ�� �����Ҷ����� ���Ѵ�⿡ ����
			//������ �����Ǹ�, �� �������ʹ� Ŭ���̾�Ʈ�� ����� �õ��Ҽ�
			//�ִ� ������ ��ȯ�ȴ�!!
				Socket socket=serverSocket.accept();
				InetAddress net=socket.getInetAddress();
				String ip=net.getHostAddress();
				
				System.out.println(ip+"�� ����");
				
				//������ ��ȭ�� �����µ� ���Ǵ°�ü�̸� ��ȭ�� 
				//Ŭ���̾�Ʈ�� �޽��� ���!!! �� �Է½�Ʈ���� �̿��Ѵ�!!
				
				//������ �̹� ����� ��Ʈ���� �����ϰ� �����Ƿ�, �� ��Ʈ����
				//������ �ȴ�!! ��ȭ���� �ٽ� ��ü�� �ᱹ �����̴�!!
				
				/*��Ʈ���� ������ ó������� ���� ����
				 * ����Ʈ��� ��Ʈ�� : 1byte�� ó���ϴ� , �ٺ� ��Ʈ�� 
				 * ���ڱ�� : 2byte�� ���� ���ڷ� ������ �� �ִ� ��Ʈ��
				 * ���۱��: ����Ʈ�� ���ۿ� ��� ������� ó���ϴ�
				 *             ��Ʈ�� 
				 * */ 
				InputStream is=socket.getInputStream();
				InputStreamReader reader=new InputStreamReader(is);
				BufferedReader buffr=new BufferedReader(reader);
				
				//������ ������ Ŭ���̾�Ʈ�� �޽����� �޾�, �ٽ� Ŭ���̾�Ʈ����
				//������ �ϹǷ�, ����� ó���Ѵ�!!
				OutputStream os=socket.getOutputStream();
				OutputStreamWriter writer=new OutputStreamWriter(os);
				BufferedWriter buffw=new BufferedWriter(writer);
				
				
				
				String data=null;
				while(true) {
					data=buffr.readLine(); //Ŭ���̾�Ʈ�� ����� ��Ʈ�����κ���
					//�����͸� 1 byte �д´�!!
					//Ŭ���̾�Ʈ�� ���!!
					//������ ���� �˷����� ������, Ŭ���̾�Ʈ�� ���Ѵ�⿡ ����
					//��, ��? �Է��� �Ϸ���� �ʾ����ϱ�..ex) cmlâ�� �Է���
					//�� �����ļ� �Է��� �ϷḦ ǥ���ϵ���..
					buffw.write(data+"\n");//�ǵڿ� ����ģ ȿ������..
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





















