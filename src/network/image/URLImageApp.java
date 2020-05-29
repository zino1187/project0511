package network.image;

import java.awt.FlowLayout;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 * 이미지를 가져올때 여러방법이 있다..
 * 1) 로컬 하드경로로 가져올때 : Toolkit 
 * 2) 클래스패스/인터넷상의 이미지를 통해 가져올때 : 
 *     ImageIO.read(URL);
 */
public class URLImageApp extends JFrame{
	JLabel la;//이미지 아이콘 들어갈곳!!
	
	public URLImageApp() {
		setLayout(new FlowLayout());
		
		//라벨에 아이콘을 넣되, 로컬상의 자원이 아닌  URL주소로 부터!!
		URL url=null;
		
		try {
			url=new URL("https://image.yes24.com/momo/TopCate2199/MidCate003/219823351.jpg");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		ImageIcon icon=new ImageIcon(url);
		
		//이미지 크기 조정하기!!
		//크기를 수정하는 기능은 아이콘이 아닌  Image 객체에 있으므로
		//아이콘을 이미지로 변환하자!!
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






















