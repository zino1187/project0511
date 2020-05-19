package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class SuperMario extends JFrame{
	JMenuBar bar;
	JMenu m_control;
	JMenuItem item_start,item_pause,item_exit;
	
	GamePanel gamePanel;
	Thread thread;
	boolean gameFlag;
	
	public SuperMario() {
		bar = new JMenuBar();
		m_control = new JMenu("게임설정");
		item_start = new JMenuItem("게임시작");
		item_pause = new JMenuItem("Pause");
		item_exit = new JMenuItem("게임종료");
		
		bar.add(m_control);
		m_control.add(item_start);
		m_control.add(item_pause);
		m_control.add(item_exit);
		
		setJMenuBar(bar);
		
		gamePanel=new GamePanel();
		
		thread = new Thread() {
			public void run() {
				try {
					while(true){
						Thread.sleep(10);
						gameLoop();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		};
		
		add(gamePanel);
		
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		/*리스너 연결 */
		gamePanel.setFocusable(true);
		gamePanel.requestFocus();
		
		item_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startGame();
			}
		});
		item_pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pauseGame();
			}
		});
		item_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitGame();
			}
		});
		
		thread.start();
		
		gamePanel.createHero();
	}
	public void startGame() {
		gameFlag=true;
	}
	public void pauseGame() {
		gameFlag=false;
	}
	public void exitGame() {
		pauseGame();
		System.exit(0);
	}
	
	public void gameLoop() {
		if(gameFlag) {
			gamePanel.repaint();
		}
	}
	
	public static void main(String[] args) {
		new SuperMario();
	}
}
