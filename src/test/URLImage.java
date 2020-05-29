package test;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class URLImage extends JFrame{
	JLabel la;
	Image thumb;
	
	public URLImage() {
		URL url=null;
		try {
			url=new URL("https://i.pinimg.com/236x/55/5d/5f/555d5fffd635584ac97818c1abb47ee6.jpg");
			BufferedImage img=ImageIO.read(url);
			//크기를 조절하고 싶은 경우 
			thumb=img.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Icon icon=new ImageIcon(thumb);
		la = new JLabel(icon);
		
		setLayout(new FlowLayout());
		add(la);
		setSize(300,300);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new URLImage();
	}
}
