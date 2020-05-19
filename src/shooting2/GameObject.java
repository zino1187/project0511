package shooting2;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/*게임상의 모든 객체의 어버이 클래스 정의 */
abstract public class GameObject {
	int x;
	int y;
	int width;
	int height;
	int velX;
	int velY;
	Image img;
	
	public GameObject(int x, int y,int width, int height, int velX, int velY) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.velX=velX;
		this.velY=velY;
	}
	
	//모든 객체들의 어버이 이므로, 동작 내용을 확정지을수도 없고, 
	//확정지어서도 안된다!! 따라서 불완전한 메서드로 정의한다!!
	abstract public void tick();
	abstract public void render(Graphics g);
	
	//이미지 얻기 메서드!!
	//이 메서드 호출자는, 자신이 원하는 이미지 파일경로를 인수로 넘겨야함
}











