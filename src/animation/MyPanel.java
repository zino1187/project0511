package animation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/*
 * 기존에 이미지를 얻기 위한 방법은 우리가 Toolkit 을 이용하여 
 * 하드디스크상에 있는 자원의 풀경로를 다 적어야 했다..
 * 하지만, 이 방법은 os 의존적이다!! D:/~~~
 * 시스템에 의존적이지 않는 경로를 사용하려면? 
 * - 고정관념 버리자!! 패키지안에는 반드시 클래스만 둘수 있는게 아니다
 * 즉 클래스패스가 잡혀있기 때문에 일반 이미지, 파일등도 패키지경로로
 * 접근할 수 있다!!
 * */
public class MyPanel extends JPanel {
	BufferedImage img;// is Image Type
	Thread thread; //repaint() 루프로 호출할 쓰레드!!
	int x,y;
	int velX, velY;
	
	public MyPanel() {
		setPreferredSize(new Dimension(600, 500));
		// setBackground(Color.YELLOW);

		// 클래스에 대한 정보를 가진 객체!!
		Class myClass = this.getClass();
		System.out.println("현재 클래스이름은 :" + myClass.getName());

		// 이미지가 패키지 안에 있을때, 슬래스에 사용한다.
		// 즉 오직 클래스만이 .점을 이용한다!!
		URL url = this.getClass().getResource("/res/rockman.gif");
		try {
			img = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		thread = new Thread() {
			//독립수행할 코드를 기재한다!! 우리의 경우  repaint()호출
			public void run() {
				while(true) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					tick();
					repaint();
				}
			}
		};
		thread.start();
	}
	
	public void tick() {
		x+=velX;
		y+=velY;
	}
	
	// 이미지 그리기!!
	public void paint(Graphics g) {
		//기존에 그려진 그림이 있다면 , 아래의 커다란 사각형도형으로 밀어버린다!
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 600, 500);
		g.drawImage(img, x, y, 100, 100, this);
	}
}















