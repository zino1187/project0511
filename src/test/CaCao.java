package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CaCao extends JFrame{
	JPanel content;
	JScrollPane scroll;
	JTextField t_input;
	int count=1;
	
	public CaCao() {
		content = new JPanel();
		scroll = new JScrollPane(content);
		t_input = new JTextField();
		
		//스타일 적용 
		content.setPreferredSize(new Dimension(250, 5000));
		content.setBackground(Color.YELLOW);
		
		add(scroll);
		add(t_input, BorderLayout.SOUTH);
		
		setSize(290,400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//리스너연결
		t_input.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				if(key==KeyEvent.VK_ENTER) {
					enter();
				}
			}
		});
	}
	
	public void enter() {
		count++;
		int align=(count%2==0)? SwingConstants.LEFT:SwingConstants.RIGHT;
		Message msg = new Message(t_input.getText(),align);
		content.add(msg);
		content.updateUI();
		
		t_input.setText("");
	}
	
	public static void main(String[] args) {
		new CaCao();
	}

}
