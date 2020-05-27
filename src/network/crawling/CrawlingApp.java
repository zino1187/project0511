package network.crawling;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import homework.gallery.Thumbnail;
import homework.lib.FileManager;
/*
 * Http ���������� ����ϴ� Web�� �����Ǵ� ��� �����ʹ� 
 * �����Ǿ� �����Ƿ�, ������ �����ϴ�!!
 * �̹���,  js, css, html  , ���� �����̴� ������� �����غ���!!
 * 1) �ڿ������� 
 * 2) ��Ʈ������ �Է�ó��
 * 3) ���� �ϵ忡 ���ó�� 
 * ��Ʈ��ũ���� �ڿ��� �����غ���!! 
 * */
public class CrawlingApp extends JFrame{
	JTextField t_url;
	JButton bt;
	JPanel p_thumb; //����� �̹����� ����� �г�
	
	//������ �ڿ��� ������ �������ִ� ��ü, �� ��ü�κ��� ��Ʈ���� ����
	//�Ͽ� ���ϴ� �۾��� ������ �� �ִ�..
	HttpURLConnection con;//�߻�Ŭ������, ���� new�Ұ��ϸ�
	//URL��ü�� �̿��Ͽ� �ν��Ͻ��� ���� �Ѵ�~~
	
	URL url;
	String saveDir="D:/web_app/java_workspace/Project0511/data/";
	FileOutputStream fos;//���� �ϵ��ο� ������ ��½�Ʈ��
	//FileOutputStream �� Ư¡�� �����ڿ��� ��(empty)������ ����
	int index; //����� �ĺ���ȣ
	Thread thread; //���α׷��� �� ó���� ������
	JProgressBar bar;
	
	public CrawlingApp() {
		t_url = new JTextField("http://",30);
		bt = new JButton("�����ϱ�");
		p_thumb = new JPanel();
		bar = new JProgressBar();
		
		//��Ÿ�� ����
		bar.setPreferredSize(new Dimension(480, 30));
		bar.setBackground(Color.ORANGE);
		bar.setForeground(Color.RED);
		p_thumb.setPreferredSize(new Dimension(500, 150));
		p_thumb.setBackground(Color.YELLOW);
		
		//����
		setLayout(new FlowLayout());//���̾ƿ� ����!!
		add(t_url);
		add(bt);
		add(bar);
		add(p_thumb);
		
		setSize(500,250);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		//��ư�� ������ ���� 
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thread = new Thread() {
					public void run() {
						collectData();
					}
				};
				thread.start();
			}
		});
	}
	
	//���ͳ� �ּһ��� ���ҽ��� ���� �ϵ��ũ�� ����߱�!!
	public void collectData() {
		
		String urlString = t_url.getText();
		InputStream is=null;//finally���� �ݱ�����!!
		try {
			url = new URL(urlString);
			con=(HttpURLConnection)url.openConnection();
			//con.setRequestMethod("HEAD");
			int total = con.getContentLength();//total byte
			float kb=(float)total/1024;
			
			System.out.println("�� �뷮�� "+kb+" kbytes");
			
			//������ �����Ǿ�����, ��Ʈ���� ���� ���ϴ� ����� �۾��� �õ�
			is=con.getInputStream();
			
			//����ִ� ������ �����Ǹ�, ��½�Ʈ���� ����!!(������..)
			//������ ���ϸ� �����ϱ�!!
			long time=System.currentTimeMillis();//�и������������ �ð�,
			//�� �����ϴ�!!
			String ext=FileManager.getExt(t_url.getText());
			
			String filename=time+"."+ext;
			System.out.println("������ ��� : "+saveDir+filename);
			
			fos=new FileOutputStream(saveDir+filename);
			
			//�ؽ�Ʈ����, �̹���, ������ �� ��� ������ �����͸� �����Ϸ���
			//byte[] buff=new byte[1024]; //1 kbyte
			
			int data=-1;
			int read=0;
			while(true) {
				data=is.read();//���� �����ʹ� �迭�� ����, 
				//data������ �о���� �ڿ��� �ִ��� ���θ� �Ǵ��ϴ� �뵵�λ��
				read+=data;
				
				
				bar.setValue(100-(int)getPercent(total, read));
				
				System.out.println(100-(int)getPercent(total, read));
				
				if(data==-1)break;
				//���ϴ� ��ο� �Էµ����� ���~~(����)
				fos.write(data);//1byte ���!!
			}
			JOptionPane.showMessageDialog(this, "�����Ϸ�");
			
			//�̹����� ���� �ƴ� ��쿡 ���� �Ǵ� �޽���!!
			//JPG --> jpg ��� �빮�ڴ� ������ �ҹ��ڷ� ����!!
			ext=ext.toLowerCase();//�ҹ��ڷ� ��ȯ!!
			
			if(ext.equals("jpg") || ext.equals("gif") || ext.equals("png")) {
				JOptionPane.showMessageDialog(this, "�̹����� �����Ա���\n�Ʒ��� �гο� ǥ���Ҳ���");
				createThumb(saveDir+filename);//����� ���
			}else {
				JOptionPane.showMessageDialog(this, "�̹����� �ƴϱ���\n�гο� ǥ�� ���Ҳ���");
			}
			
		} catch (MalformedURLException e) {
			JOptionPane.showMessageDialog(this, "�ּҸ� Ȯ�����ּ���");
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "����� ����");
			e.printStackTrace();
		}finally{
			if(fos!=null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(is!=null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void createThumb(String dest) {
		index++;
		
		//homework.gallery�� ����ִ� Ŭ���� ��Ȱ��!!
		try {
			Image img=ImageIO.read(new File(dest));
			img=img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(img);//������ ����
			Thumbnail thumb = new Thumbnail(index, icon, 40, 40);
			p_thumb.add(thumb);//���� �гο� ����!!
			p_thumb.updateUI();//���ΰ�ħ!!
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public float getPercent(float total, int read) {
		return (total/read)*100;
	}
	public static void main(String[] args) {
		new CrawlingApp();
	}
}
























