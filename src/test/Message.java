package test;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Message extends JLabel{
	String msg;
	
	public Message(String msg, int align) {
		super(msg,align);
		this.msg=msg;
		this.setBackground(Color.YELLOW);
		this.setPreferredSize(new Dimension(200, 50));
		
		this.setText(this.msg);
		
	}
}
