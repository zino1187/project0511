package util;

public class StringApp {
	public static void main(String[] args) {
		/*신입개발자가 욕먹는 코드의 예*/
		/*
		for(int i=0;i<10000;i++) {
			new String("korea");
		}
		*/
		
		//스트링은 불변이다(=immutable)
		//한번 생성된 스트링은 상수취급되기 때문에 그 데이터를 절대로
		//변경할 수 없다!
		//메모리 효율성을 위해 수정불변을, 수정가능하게 해야한다!!
		StringBuilder sb= new StringBuilder();
		
		sb.append("");
		
		for(int  i=1;i<=10000;i++) {
			//스트링은 = 연산자로 변경하려하지말고, 아래처럼 append
			//메서드를 이용한다
			sb.append("ko"+i); //10000개의 객체 생성
		}
		
		String result=sb.toString();
		
	}
}





