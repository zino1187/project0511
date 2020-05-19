package shooting;

import java.awt.Graphics;

public class Bullet extends GameObject{
	Hero hero;
	
	public Bullet(Hero hero, int x, int y, int width, int height, int velX, int velY) {
		super(x, y, width, height, velX, velY);
		this.hero=hero;
	}

	@Override
	public void tick() {
		this.x+=this.velX;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(img, x, y, 20, 20, null);
	}
	
}






