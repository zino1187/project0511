package keyboard;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class KeyControl extends JFrame{
	JPanel panel;
	int x,y;
	int velX,velY;
	Thread thread;
	
	public KeyControl() {
		panel = new JPanel() {
			public void paint(Graphics g) {
				g.setColor(Color.YELLOW);//페인트 통의 색상만 변경
				g.fillRect(0, 0, 600,500);
				
				g.setColor(Color.RED);//페인트통 색상 변경
				g.drawRect(x, y, 50, 50);//노란색 사각형
			}
		};
		
		thread = new Thread() {
			public void run() {
				while(true){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					gameLoop();
				}
			}
		};
		
		thread.start();
		
		panel.setPreferredSize(new Dimension(600, 500));
		add(panel);
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		//패널과 리스너연결
		//포커스가 윈도우가 올라갈 경우, 아래의 패널에 대한 키보드 이벤트가
		//적용되지 않는다!! 
		//해결책? 패널에 포커스 올려놓으면 된다!!
		panel.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				System.out.println("키 눌렀어?");
				int key = e.getKeyCode();
				
				switch(key) {
					case KeyEvent.VK_LEFT:velX=-5 ;break; 
					case KeyEvent.VK_UP:velY=-5 ;break; 
					case KeyEvent.VK_RIGHT:velX=5 ;break; 
					case KeyEvent.VK_DOWN:velY=5 ;break; 
				}
			}
		});
			
		panel.setRequestFocusEnabled(true);
		panel.requestFocus();//패널에 포커스 올리기!!
		
	}
	
	public void gameLoop() {
		x+=velX;
		y+=velY;
		
		panel.repaint();
	}
	
	public static void main(String[] args) {
		new KeyControl();
	}

}




