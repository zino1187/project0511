package homework.lib;

import java.text.DecimalFormat;

public class Formatter {
	
	//���ϴ� ���ڸ� �Ѱܹ�����, ��ǥó���� ��ȭ�� ��ȯ�ϴ� �޼��� 
	public static String getCurrency(int price) {
		DecimalFormat formatter=new DecimalFormat("###,###,###");
		return formatter.format(price);
	}
}






