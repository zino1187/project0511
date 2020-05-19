package thread;

//쓰레드를 상속받아서 구현해본다!!
public class MyThread extends Thread{
	@Override
	public void run() {
		System.out.println("쓰레드 동작");
	}
}
