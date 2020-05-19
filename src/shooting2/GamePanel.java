package shooting2;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/*게임의 그래픽처리를 담당하는 스크린!!*/
public class GamePanel extends JPanel{
	int[][] screenSize=new int[][] {
		{600,400},{800,600},{1024,768}
	};
	int ratio;	
	Hero hero;
	Image[] backImg=new Image[2];//패널에 그려질 배경 이미지
	int bgX;
	int bgY;
	int speedX=-1;
	
	public GamePanel() {
		setScreenSize(0);//탄생과 동시에 600*400 으로...
		backImg[0]=createImage("/resource/game_bg.jpg");
		backImg[1]=createImage("/resource/game_bg.jpg");
		
		createHero();
		
		//패널에 마우스 리스너 연결 
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				
				switch(key) {
					case KeyEvent.VK_LEFT:hero.velX=-5;break;
					case KeyEvent.VK_RIGHT:hero.velX=5;break;
					case KeyEvent.VK_UP:hero.velY=-5;break;
					case KeyEvent.VK_DOWN:hero.velY=5;break;
					case KeyEvent.VK_SPACE:hero.fire();break;
				}
			}
			//키에서 손을떼면, 즉 눌렀다 뗄때!!
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				
				switch(key) {
					case KeyEvent.VK_LEFT:hero.velX=0;break;
					case KeyEvent.VK_RIGHT:hero.velX=0;break;
					case KeyEvent.VK_UP:hero.velY=0;break;
					case KeyEvent.VK_DOWN:hero.velY=0;break;
				}
			}
		});
		
	}
	
	public void setScreenSize(int ratio) {
		this.ratio=ratio;
		setPreferredSize(new Dimension(screenSize[ratio][0], screenSize[ratio][1]));
	}
	
	//주인공 생성 
	public void createHero() {
		hero =new Hero(50, 100, 80, 60, 0, 0);
		hero.img=createImage("/resource/plane.png");		
	}
	
	public Image createImage(String path) {
		URL url=this.getClass().getResource(path);
		Image img=null;
		try {
			img=ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	//주인공, 블락, 적군등 모든!~!~게임의 오브젝트는 바로 이 패널에
	//그려짐!! 따라서 아래의 paint 메서드의  g가 그 역할을 한다!!
	public void paint(Graphics g) {
		Graphics2D g2=(Graphics2D)g;
		
		//채워진 사각형으로 배경색을 처리하자!!
		bgX+=speedX;
		//g.setColor(Color.YELLOW);
		//g.fillRect(0, 0, screenSize[ratio][0], screenSize[ratio][1]);//화면만한 사각형으로 화면을 덮음
		g2.drawImage(backImg[0], 0, bgY, screenSize[ratio][0], screenSize[ratio][1], null);
		g2.drawImage(backImg[1], screenSize[ratio][0], bgY, screenSize[ratio][0], screenSize[ratio][1], null);
		
		g.translate(bgX, bgY);
		
		hero.tick();
		hero.render(g2);
	}
}









