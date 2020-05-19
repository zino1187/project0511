package shooting2;

import java.awt.Color;
import java.awt.Graphics;

public class Hero extends GameObject{
	
	
	public Hero(int x, int y, int width, int height, int velX, int velY) {
		super(x, y, width, height, velX, velY);		
	}
	
	//총알 발사 
	public void fire() {
		
	}
	
	@Override
	public void tick() {
		this.x+=this.velX;
		this.y+=this.velY;
	}

	//주인공이건 누구건 간에 결국 패널에 그려져야 하므로 패널이 보유한 
	//Graphics 객체를 얻어와야 한다!!
	public void render(Graphics g) {
		//그래픽 처리가 되어야 함!!
		g.setColor(Color.RED);
		
		//g.drawRect(x, y, 70, 40);
		g.drawImage(img, x, y, 60, 40, null);
		
	}

}


