package game;

import java.awt.Color;
import java.awt.Graphics2D;

public class Hero extends GameObject{
	boolean jump;
	boolean onBlock;
	
	public Hero(ObjectManager objectManager,ObjectId objectId,int x, int y, int width, int height,int velX, int velY) {
		super(objectManager,objectId, x,y,width,height,velX,velY);
	}
	
	public void tick() {
		
		this.velY+=this.g;
		
		//충돌검사 
		for(int i=0;i<super.objectManger.objectList.size();i++) {
			GameObject gameObject=super.objectManger.objectList.get(i);
			
			if(gameObject.objectId==ObjectId.Block) {
				Block block = (Block)gameObject;
				if(this.rect.intersects(block.rect) && this.jump==false) {
					onBlock=true;
					this.velY=0;
					this.y=(block.y-this.height+1);
				}
			}
		}
		
		this.x+=this.velX;
		this.y+=this.velY;		
		
		rect.setBounds(x, y, width, height);
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.RED);
		g.drawRect(rect.x, rect.y, rect.width, rect.height);
		g.drawImage(createImage("/resource/food4.jpg", 50, 60), 100, 50, 50, 60, null);
	}
	 
}
