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
	JPanel p_info;//텍스트 영역 처리 
	JPanel p_canvas;//큰 이미지 나올곳 
	JLabel la_title;//영화제목
	JLabel la_release;//배급연도
	JPanel p_thumb;//남쪽 썸네일 영역
	Image bigImg;// 상세보기에 사용할 객체
	ArrayList<Thumbnail> thumbList=new ArrayList<Thumbnail>();
	
	public GalleryMain() {
		p_center = new JPanel();
		p_info = new JPanel();
		
		p_canvas = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(bigImg, 0, 0, 300,300,p_canvas);
			}
		};
		
		la_title = new JLabel("영화제목:터미네이터");
		la_release = new JLabel("배급연도:2020년");
		p_thumb = new JPanel();
		
		//스타일 적용 
		p_canvas.setPreferredSize(new Dimension(300, 300));
		p_canvas.setBackground(Color.YELLOW);
		
		la_title.setFont(new Font("돋움체",Font.BOLD,20));
		la_release.setFont(new Font("돋움체",Font.BOLD,20));
		la_title.setPreferredSize(new Dimension(250, 50));
		la_release.setPreferredSize(new Dimension(250, 50));
		
		//조립 
		p_center.setLayout(new GridLayout(1,2));
		
		p_info.add(la_title);
		p_info.add(la_release);
		
		p_center.add(p_canvas);//그리드0에 켄버스부착 
		p_center.add(p_info);//그리드1에 켄버스부착 
		
		add(p_center);
		
		
		add(p_thumb, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600,400);
		setVisible(true);
		setLocationRelativeTo(null);
		
		createThumb();
	}
	//아이콘 생성 및 반환메서드 
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
		//json데이터 파싱하기!!
		JSONParser jsonParser =new JSONParser();
		FileReader reader = null;
		
		try {
			reader=new FileReader("D:/web_app/java_workspace/Project0511/src/json/data.json");
			JSONObject jsonObject=(JSONObject)jsonParser.parse(reader);//해석 시작!!
			
			JSONArray jsonArray=(JSONArray)jsonObject.get("marvel");
			
			//영화객체들..
			for(int i=0;i<jsonArray.size();i++) {
				JSONObject json=(JSONObject)jsonArray.get(i);
				String title=(String)json.get("title");
				long yy=(Long)json.get("release_year");
				String url=(String)json.get("url");
				
				System.out.println(title+","+yy);
				//json 데이터로 부터 얻어진 결과를 통해 라벨을 생성!!!
				Thumbnail thumb = new Thumbnail(i,getIcon(url, 50, 40), 50,40);
				p_thumb.add(thumb);//윈도우 등장 이후에 붙인것이므로, 
												//updateUI() 호출하자!
				
				thumbList.add(thumb);//멤버변수인 thumbList에 보관!!
				
				//라벨과 마우스 리스너 연결
				thumb.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						Thumbnail obj=(Thumbnail)e.getSource();//이벤트를 일으킨 컴포넌트 반환
						System.out.println(obj.index+" 눌렀어?");
						
						//상세이미지 처리
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
			//DB, Stream은 사용후 반드시 닫아야 한다!!
			//안닫으면 메모리에 계속 쌓인다..메모리 누수...memory leak
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









