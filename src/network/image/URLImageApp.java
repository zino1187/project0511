package network.image;

import java.awt.FlowLayout;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 * �̹����� �����ö� ��������� �ִ�..
 * 1) ���� �ϵ��η� �����ö� : Toolkit 
 * 2) Ŭ�����н�/���ͳݻ��� �̹����� ���� �����ö� : 
 *     ImageIO.read(URL);
 */
public class URLImageApp extends JFrame{
	JLabel la;//�̹��� ������ ����!!
	
	public URLImageApp() {
		setLayout(new FlowLayout());
		
		//�󺧿� �������� �ֵ�, ���û��� �ڿ��� �ƴ�  URL�ּҷ� ����!!
		URL url=null;
		
		try {
			url=new URL("https://image.yes24.com/momo/TopCate2199/MidCate003/219823351.jpg");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		ImageIcon icon=new ImageIcon(url);
		
		//�̹��� ũ�� �����ϱ�!!
		//ũ�⸦ �����ϴ� ����� �������� �ƴ�  Image ��ü�� �����Ƿ�
		//�������� �̹����� ��ȯ����!!
		Image img=icon.getImage();
		img=img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		
		la = new JLabel(icon=new ImageIcon(img));
		
		add(la);
		
		setSize(300,400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		new URLImageApp();
	}
}






















