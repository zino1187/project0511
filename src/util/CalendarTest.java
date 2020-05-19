package util;

import java.util.Calendar;

public class CalendarTest {
	/*자바에서 날짜를 다루려면  Calendar 이용하면 된다*/
	public static void main(String[] args) {
		//추상클래스인지라,  static 메서드로 인스턴스를 얻는다!!
		Calendar cal=Calendar.getInstance();
		System.out.println(cal.get(Calendar.YEAR));
		System.out.println(cal.get(Calendar.MONTH)+1);
		System.out.println(cal.get(Calendar.DATE));
	}

}
