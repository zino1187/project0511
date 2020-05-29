package diary;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

//달력을 이루는 하나 하나의 셀
public class DateCard extends JPanel{
	JLabel la;
	LineBorder border;
	
	public DateCard(String n, Color color) {
		la = new JLabel(n);
		border = new LineBorder(Color.RED, 1, false);
		
		add(la);
		setPreferredSize(new Dimension(50, 50));
		setBackground(color);
		setBorder(border);
	}
}
