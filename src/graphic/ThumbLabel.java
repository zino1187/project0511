package graphic;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

//이 클래스를 기존의  JLabel 을 상속받아, 
//내가 원하는 데이터를 넣기 위해 변수를 별도로 정의
public class ThumbLabel extends JLabel{
	int id;//이 객체를 식별할 번호!!
	
	//누군가가 생성자 호출시 식별번호를 넘기면 된다!!
	public ThumbLabel(int id, ImageIcon icon) {
		//이 라벨이 사용할 이미지 아이콘!!
		super(icon);
		this.id=id;
		setPreferredSize(new Dimension(70,60));
	}
}









