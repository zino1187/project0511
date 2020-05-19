/*
  자바스크립트에서 자동호출을 구현하기 위해  setInterval, setTimeout
  을 지원하듯, 자바에서도 자동호출을 구현할 수 있다!!
*/
package thread;
public class ThreadApp {
	/*Process 프로세스란? 
	 * 메모리에 올려진 실행중인 프로그램 한단위
	 * 
	 * Thread란?
	 * 하나의 프로세스내에 독립적으로 수행되는 세부 실행단위!!
	 * */
	Thread thread;
	int count=0;
	
	public ThreadApp() {
		
		//분신하나 생성!!
		thread = new Thread() {
			//쓰레드가 실행시키는 수행영역
			public void run() {
				while(true) {
					try {
						Thread.sleep(100);//non-runnable
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("d");
				}
			}
		};
		
		//생성된 분신인 쓰레드를 가동!!
		thread.start();
	}
	public static void main(String[] args) {
		new ThreadApp();
	}

}





