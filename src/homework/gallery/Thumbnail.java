package homework.gallery;

import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JLabel;

//썸네일 이미지를 표현하는 객체
public class Thumbnail extends JLabel{
	int index;//썸네일을 구분해주는 일종의 아이디, 숫자!!
	
	//생성시 경로만 넘겨주면 알아서 이미지 처리!!
	public Thumbnail(int index,Icon icon,int width,int height) {
		super(icon);
		this.index=index;
		setPreferredSize(new Dimension(width, height));
	}
}


















