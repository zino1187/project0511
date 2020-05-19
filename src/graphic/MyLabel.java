package graphic;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class MyLabel extends JLabel{
	int id;
	ImageIcon icon;
	
	public MyLabel(int id, ImageIcon icon) {
		super(icon);
		this.id=id;
		this.icon=icon;
	}
	
}
