package homework.gallery;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GalleryMain extends JFrame{
	JPanel p_center;
	JPanel p_info;//�ؽ�Ʈ ���� ó�� 
	JPanel p_canvas;//ū �̹��� ���ð� 
	JLabel la_title;//��ȭ����
	JLabel la_release;//��޿���
	JPanel p_thumb;//���� ����� ����
	Image bigImg;// �󼼺��⿡ ����� ��ü
	ArrayList<Thumbnail> thumbList=new ArrayList<Thumbnail>();
	
	public GalleryMain() {
		p_center = new JPanel();
		p_info = new JPanel();
		
		p_canvas = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(bigImg, 0, 0, 300,300,p_canvas);
			}
		};
		
		la_title = new JLabel("��ȭ����:�͹̳�����");
		la_release = new JLabel("��޿���:2020��");
		p_thumb = new JPanel();
		
		//��Ÿ�� ���� 
		p_canvas.setPreferredSize(new Dimension(300, 300));
		p_canvas.setBackground(Color.YELLOW);
		
		la_title.setFont(new Font("����ü",Font.BOLD,20));
		la_release.setFont(new Font("����ü",Font.BOLD,20));
		la_title.setPreferredSize(new Dimension(250, 50));
		la_release.setPreferredSize(new Dimension(250, 50));
		
		//���� 
		p_center.setLayout(new GridLayout(1,2));
		
		p_info.add(la_title);
		p_info.add(la_release);
		
		p_center.add(p_canvas);//�׸���0�� �˹������� 
		p_center.add(p_info);//�׸���1�� �˹������� 
		
		add(p_center);
		
		
		add(p_thumb, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600,400);
		setVisible(true);
		setLocationRelativeTo(null);
		
		createThumb();
	}
	//������ ���� �� ��ȯ�޼��� 
	public Icon getIcon(String path,int width,int height) {
		URL url=null;
		Image image=null;
		try {
			url=new URL(path);
			image=ImageIO.read(url);
			image=image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ImageIcon(image);
	}
	
	
	public void createThumb() {
		//json������ �Ľ��ϱ�!!
		JSONParser jsonParser =new JSONParser();
		FileReader reader = null;
		
		try {
			reader=new FileReader("D:/web_app/java_workspace/Project0511/src/json/data.json");
			JSONObject jsonObject=(JSONObject)jsonParser.parse(reader);//�ؼ� ����!!
			
			JSONArray jsonArray=(JSONArray)jsonObject.get("marvel");
			
			//��ȭ��ü��..
			for(int i=0;i<jsonArray.size();i++) {
				JSONObject json=(JSONObject)jsonArray.get(i);
				String title=(String)json.get("title");
				long yy=(Long)json.get("release_year");
				String url=(String)json.get("url");
				
				System.out.println(title+","+yy);
				//json �����ͷ� ���� ����� ����� ���� ���� ����!!!
				Thumbnail thumb = new Thumbnail(i,getIcon(url, 50, 40), 50,40);
				p_thumb.add(thumb);//������ ���� ���Ŀ� ���ΰ��̹Ƿ�, 
												//updateUI() ȣ������!
				
				thumbList.add(thumb);//��������� thumbList�� ����!!
				
				//�󺧰� ���콺 ������ ����
				thumb.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						Thumbnail obj=(Thumbnail)e.getSource();//�̺�Ʈ�� ����Ų ������Ʈ ��ȯ
						System.out.println(obj.index+" ������?");
						
						//���̹��� ó��
						ImageIcon icon=(ImageIcon)obj.getIcon();
						bigImg=icon.getImage();
						p_canvas.repaint();
					}
				});
				
			}
			p_thumb.updateUI();
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}finally {
			//DB, Stream�� ����� �ݵ�� �ݾƾ� �Ѵ�!!
			//�ȴ����� �޸𸮿� ��� ���δ�..�޸� ����...memory leak
			if(reader!=null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
	public static void main(String[] args) {
		new GalleryMain();
	}
	
}









