package game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

abstract public class GameObject {
	ObjectId objectId;
	ObjectManager objectManger;
	int x;
	int y;
	int width;
	int height;
	float velX;
	float velY;
	float g=0.3f;
	Rectangle rect;
	
	public GameObject(ObjectManager objectManger,ObjectId objectId,int x, int y, int width, int height, float velX, float velY) {
		this.objectManger=objectManger;
		this.objectId=objectId;
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.velX=velX;
		this.velY=velY;
		
		rect = new Rectangle(x, y, width, height);
	}
	abstract public void tick();
	abstract public void render(Graphics2D g);
	
	public Image createImage(String url, int width, int height) {
		BufferedImage img = null;
		try {
			img= ImageIO.read(this.getClass().getResource(url));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}
	
}
