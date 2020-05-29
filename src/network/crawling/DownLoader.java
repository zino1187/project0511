package network.crawling;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import homework.gallery.Thumbnail;
import homework.lib.FileManager;

public class DownLoader extends JFrame implements Runnable{
	JButton bt;
	JLabel la_name;//������ ���ϸ�
	JLabel la_total;//������ ������ �뷮 
	JProgressBar bar;
	JPanel p_thumb;//����� ���� �г�
	String jsonPath="D:/web_app/java_workspace/Project0511/src/json/data.json";
	String saveDir="D:/web_app/java_workspace/Project0511/data/";//���Ӱ� ������ ������ ��ġ
	
	URL url;
	HttpURLConnection con;
	Thread thread;//�б� �������� ������ �����ϱ� ������ ���ξ����带
	//�� ������ ����ּ��� �ȵǱ⿡, ������ �����尡 �ʿ��ϴ�!!
	int index;//���� �Ϸù�ȣ
	
	public DownLoader() {
		bt = new JButton("����");
		la_name = new JLabel("���ϸ�:aven.jpg");
		la_total = new JLabel("����ũ��:34 kbyte");
		bar = new JProgressBar();
		p_thumb = new JPanel();
		
		//��Ÿ�� ����
		bar.setBackground(Color.YELLOW);
		bar.setForeground(Color.RED);
		bar.setPreferredSize(new Dimension(450,30));
		bar.setStringPainted(true);//����� ����� �ؽ�Ʈ ���!!
		
		la_name.setPreferredSize(new Dimension(450,30));
		la_total.setPreferredSize(new Dimension(450,30));
		la_name.setFont(new Font("����",Font.BOLD,20));
		la_total.setFont(new Font("����",Font.BOLD,20));
		
		p_thumb.setPreferredSize(new Dimension(450,150));
		p_thumb.setBackground(Color.YELLOW);
		
		//���̾ƿ�
		this.setLayout(new FlowLayout());
		
		//����
		add(bt);
		add(la_name);
		add(la_total);
		add(bar);
		add(p_thumb);
		
		setSize(500,350);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thread = new Thread(DownLoader.this);
				thread.start();
			}
		});
	}
	public void run() {
		//���̽� �Ľ�
		parseJSON();
	}
	public void parseJSON() {
		JSONParser parser = new JSONParser();
		FileReader reader=null;
		try {
			reader = new FileReader(jsonPath);
			
			//�Ľ��� ���ĺ��ʹ� ���ڿ��� �Ұ��ߴ� ���̽� ǥ������� 
			//��üȭ�Ǿ� ��ȯ�ȴ�..���� �� �������ʹ� �����ڴ� json ǥ�����
			//��üó�� ����� �� �ִ�..
			JSONObject jsonObject=(JSONObject)parser.parse(reader);//json �ļ��� �ؼ��� �����Ѵ�!!
			
			//marvel ������ ����Ű�� ��ü�� �迭�̹Ƿ�...
			JSONArray jsonArray=(JSONArray)jsonObject.get("marvel");
			
			for(int i=0;i<jsonArray.size();i++) {
				//���̽��� ���������� �����Ͽ� url�� ������ �̿��� ����߱�!!
				JSONObject obj=(JSONObject)jsonArray.get(i);
				String path=(String)obj.get("url");
				
				System.out.println(path);
				collectData(path);
				index++;
			}
			JOptionPane.showMessageDialog(this, jsonArray.size()+"���� �̹����� �ٿ�ε� �Ϸ�!!");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}finally {
			if(reader !=null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//�̹��� �����ϱ�!!
	public void collectData(String path) {
		InputStream is=null;
		FileOutputStream fos=null;
		
		try {
			url = new URL(path);
			con=(HttpURLConnection)url.openConnection();
			//������ �ڿ��� ���� ����!!
			int total=con.getContentLength(); //byte ��ȯ!!
			System.out.println("total : "+(total/1204)+"kbyte");
			
			//���Ͽ� ���� ���� ��� 
			showInfo(path , total);
			
			//��Ʈ���� �̾Ƽ� ���Ϻ��� ����!!
			//����Ǿ��� ���ϸ��� �����ڰ� ���ϴ� ���̴�!!
			String filename=saveDir+System.currentTimeMillis()+"."+FileManager.getExt(path);
			fos=new FileOutputStream(filename);
			
			is=con.getInputStream();
			
			int data=-1;
			int count=0;
			
			while(true) {
				data=is.read();//�б�
				count++;
				if(data==-1)break;
				System.out.println(getPercent(total, count)+"% ����");
				
				//������ �ۼ�Ʈ�� �ٿ� ����ϱ�~~
				float value=getPercent(total, count);
				bar.setValue((int)value);
				
				fos.write(data);//����
			}
			System.out.println("����Ϸ�");
			
			//�гο� ������ ����!!
			createThumb(filename);
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(fos!=null) {
				try {
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(is!=null) {
				try {
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	//���� ���� ��� ó�� 
	public void showInfo(String path, int total) {
		la_name.setText(FileManager.getFilenameFromHttp(path));
		la_total.setText(Integer.toString(total/1024)+" kbyte");
	}
	
	//����� ���� �� ����~~!
	public void createThumb(String filepath) {
		Image oriImg=null;//ũ�⸦ ���̱� ������ �̹���
		Image thumbImg=null;//ũ�⸦ ���� �̹���
		
		try {
			oriImg=ImageIO.read(new File(filepath));
			thumbImg=oriImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			ImageIcon icon=new ImageIcon(thumbImg);
			Thumbnail thumb = new Thumbnail(index, icon, 40, 40);
			
			p_thumb.add(thumb);//�гο� ����!!
			p_thumb.updateUI();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//����� ����ϱ� �޼��� ���� 
	public float getPercent(float total , int read) {
		//�ѿ뷮/��������Ʈ��*100 = �����
		return (read/total)*100;
	}
	
	public static void main(String[] args) {
		new DownLoader();
	}
	
}








