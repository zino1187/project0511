package homework.lib;

import java.text.DecimalFormat;

public class Formatter {
	
	//원하는 숫자를 넘겨받으면, 쉼표처리된 통화로 반환하는 메서드 
	public static String getCurrency(int price) {
		DecimalFormat formatter=new DecimalFormat("###,###,###");
		return formatter.format(price);
	}
}






