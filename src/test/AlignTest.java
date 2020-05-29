package test;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AlignTest extends JFrame{
	JPanel p;
	JButton bt1,bt2;
	
	public AlignTest() {
		p=new JPanel();
		bt1=new JButton("bt1");
		bt2=new JButton("bt2");
		
		p.setPreferredSize(new Dimension(400, 100));
		p.setBackground(Color.YELLOW);
		
		p.setLayout(new BoxLayout(p, BoxLayout.LINE_AXIS));
		p.add(bt1);
		p.add(Box.createHorizontalGlue());
		p.add(bt2);
		
		add(p);
		
		setSize(400,200);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		new AlignTest();
	}
}
