package graphic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

public class Gallery extends JFrame{
	String dir="D:/web_app/js_workspace/images/";	
	String[] path= {"t1.jpg","t2.jpg","t3.jpg","t4.jpg","t5.jpg","t6.jpg","t7.jpg","t8.jpg","t9.jpg","t10.jpg"};
	ImageIcon[] img;
	MyLabel[] la=new MyLabel[path.length];
	JPanel p_thumb; //썸네일 나올곳
	JButton bt_prev, bt_next;
	JPanel p_center;
	JPanel container;
	Toolkit kit=Toolkit.getDefaultToolkit();
	Image image;
	int cursor=0;
	JScrollPane scroll;
	
	public Gallery() {
		
		createImage();
		
		p_thumb = new JPanel();
		
		bt_prev = new JButton();
		bt_next = new JButton();
		
		bt_prev.setPreferredSize(new Dimension(80, 60));
		bt_next.setPreferredSize(new Dimension(80, 60));
		
		bt_prev.setIcon(getResizeIcon("smile.png"));
		bt_next.setIcon(getResizeIcon("smile.png"));
		
		image = kit.getImage(dir+path[0]);
		
		scroll = new JScrollPane(p_thumb);
		
		for(int i=0;i<la.length;i++) {
			la[i] = new MyLabel(i, img[i]);
			la[i].setPreferredSize(new Dimension(55, 55));
			
			la[i].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					MyLabel label=(MyLabel)e.getSource();
					System.out.println(label.getIcon().toString());
					image=kit.getImage(label.getIcon().toString());
					p_center.repaint();
					System.out.println(label.id);
					cursor=label.id;
				}
			});
			
			p_thumb.add(la[i]);
		}
		
		p_center = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(image, 0, 0,400,400, this);
			}
		};
		container = new JPanel();
		container.setBackground(Color.GREEN);
		
		p_thumb.setPreferredSize(new Dimension(100, 450));
		
		p_center.setBackground(Color.RED);
		p_center.setPreferredSize(new Dimension(400,400));
		
		container.add(bt_prev);
		container.add(bt_next);
		container.add(p_center);
		
		add(scroll, BorderLayout.WEST);
		add(container);
		
		setSize(500,450);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		bt_prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Border bd = BorderFactory.createLineBorder(Color.RED,5);
				
				la[cursor].setBorder(null);
				cursor--;
				image = kit.getImage("D:/web_app/js_workspace/images/"+path[cursor]);
				p_center.repaint();
				la[cursor].setBorder(bd);

			}
		});
		
		bt_next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Border bd = BorderFactory.createLineBorder(Color.RED,5);
				la[cursor].setBorder(null);
				cursor++;
				image = kit.getImage("D:/web_app/js_workspace/images/"+path[cursor]);
				p_center.repaint();
				la[cursor].setBorder(bd);
			}
		});
		
		
		
	}
	public void createImage() {
		kit =Toolkit.getDefaultToolkit();
		System.out.println(kit);
		
		img= new ImageIcon[path.length];//이미지 배열생성
		
		for(int i=0;i<path.length;i++) {
			img[i]=new ImageIcon("D:/web_app/js_workspace/images/"+path[i]);
			
		}
	}
	//이미지 크기 조정하기 
	public ImageIcon getResizeIcon(String name) {
		ImageIcon icon=new ImageIcon("D:/web_app/js_workspace/images/t1.jpg");
		Image scaledImage = icon.getImage().getScaledInstance(80, 60, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(scaledImage);
		return icon;
	}
	
	public static void main(String[] args) {
		new Gallery();
	}
}
