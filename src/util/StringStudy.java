package util;

/*
 * String 클래스에 대해 알아본다
 * 
 * 1)문자가 아닌 문자열을 표현한 객체
 * 2)자바 API 패키지 중  import 한적이 없으므로 java.lang에서 지원
 * 3)정체는 ? 
 * */
public class StringStudy {
	public static void main(String[] args) {
		/*아래의 코드는 둘다 스트링 객체를 생성하는 방법
		 *어떤 방법이 메모리 효율상 더 좋을까? 
		 * */ 
		
		/*아래와 같이 String이 클래스임에도 불구하고 new 연산자로
		 * 생성하지 않고 객체의 인스턴스를 생성하는 방법을 가리켜 
		 * 묵시적,암묵적(implicit) 생성법이라 한다!!
		 * 왜 이런 방식을 지원하지??
		 * 스트링은 인간에게 가장 많이 쓰이는 형식이다!!따라서 객체임에도
		 * 불구하고 일반 데이터처럼 사용할수 있도록 간편법을 제공하는 것임
		 * 하지만 객체인지라, 내부적으로 인스턴스화 된다!! 
		 * */
		String str1="apple";
		String str2="apple";
		
		//스트링은 객체이므로 아래의 비교연산자를 내용비교가 아니라 
		//두변수간 주소값 비교이다!!
		System.out.println(str1==str2);
		/*
		 * 아래와 같이 String 클래스를 new 연산자에 의해 생성하는 방법을
		 * 가리켜  explicit(명시적) 생성법이라 한다. 
		 * 일반클래스 생성법과 동일하므로 메모리 효율성 1도 생각하지 않음
		 * */
		
		String s1=new String("banana");
		String s2=new String("banana");
		System.out.println(s1==s2);//주소값 비교이므로, 당연히 false
		
		//내용비교를 하고싶을때?
		//이때 사용하는 메서드가  Object 클래스로부터 물려받은 
		//equals() 메서드이다!! 객체의 내용을 비교함 
		System.out.println(str1.equals(str2));
		System.out.println(s1.equals(s2));
		
		//System.out.println("tomato".equals(new String("tomato")));
	}
	
}








