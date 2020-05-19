package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel{
	int width=300;
	int height=200;
	int a=2;
	int WIDTH;
	int HEIGHT;
	ObjectManager objectManager;
	Hero hero;
	
	public GamePanel() {
		WIDTH=width*a;
		HEIGHT=height*a;
		
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		objectManager = new ObjectManager();
		
		createHero();
		createBlock();
		
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
						
				switch(key) {
					case KeyEvent.VK_LEFT:hero.velX=-5;break;
					case KeyEvent.VK_RIGHT:hero.velX=5;break;
				}
			}
		});				
	}
	
	public void paint(Graphics g) {
		Graphics2D g2=(Graphics2D)g;
		g2.setColor(Color.YELLOW);
		g2.fillRect(0, 0, WIDTH, HEIGHT);
		
		objectManager.tick();
		objectManager.render(g2);		
	}
	
	public void createHero() {
		hero = new Hero(objectManager, ObjectId.Hero, 100, 100, 40, 60, 0, 1);
		objectManager.addObject(hero);
	}
	
	public void createBlock() {
		for(int i=0;i<20;i++) {
			Block block = new Block(objectManager,ObjectId.Block, 10+(26*i), 300,25, 25, 0, 0);
			objectManager.addObject(block);
		}
	}
	
	
}







