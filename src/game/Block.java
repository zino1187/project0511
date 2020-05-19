package game;

import java.awt.Color;
import java.awt.Graphics2D;

public class Block extends GameObject{
	public Block(ObjectManager objectManager,ObjectId objectId,int x, int y, int width, int height,int velX, int velY) {
		super(objectManager,objectId, x,y,width,height,velX,velY);
	}
	public void tick() {
		rect.setBounds(x, y, width, height);
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.BLUE);
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
	}
}
