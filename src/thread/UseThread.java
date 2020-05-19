package thread;


public class UseThread {
	public static void main(String[] args) {
		//쓰레드를 생성하여 동작시켜보자
		MyThread t=new MyThread();//여기까지가 개발자가
		//할일이다.!!
		//t.run();// 가상머신에게 맡기게 되지 않는다!!
		t.start();//jvm에게 맡기게 됨
	}
}
