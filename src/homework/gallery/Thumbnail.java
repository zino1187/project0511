package homework.gallery;

import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JLabel;

//����� �̹����� ǥ���ϴ� ��ü
public class Thumbnail extends JLabel{
	int index;//������� �������ִ� ������ ���̵�, ����!!
	
	//������ ��θ� �Ѱ��ָ� �˾Ƽ� �̹��� ó��!!
	public Thumbnail(int index,Icon icon,int width,int height) {
		super(icon);
		this.index=index;
		setPreferredSize(new Dimension(width, height));
	}
}


















