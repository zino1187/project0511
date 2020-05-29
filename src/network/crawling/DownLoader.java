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
	JLabel la_name;//수집할 파일명
	JLabel la_total;//수집할 파일의 용량 
	JProgressBar bar;
	JPanel p_thumb;//썸네일 붙을 패널
	String jsonPath="D:/web_app/java_workspace/Project0511/src/json/data.json";
	String saveDir="D:/web_app/java_workspace/Project0511/data/";//새롭게 생성될 파일의 위치
	
	URL url;
	HttpURLConnection con;
	Thread thread;//읽기 과정에서 루프가 존재하기 때문에 메인쓰레드를
	//이 루프에 묶어둬서는 안되기에, 별도의 쓰레드가 필요하다!!
	int index;//라벨의 일련번호
	
	public DownLoader() {
		bt = new JButton("수집");
		la_name = new JLabel("파일명:aven.jpg");
		la_total = new JLabel("파일크기:34 kbyte");
		bar = new JProgressBar();
		p_thumb = new JPanel();
		
		//스타일 적용
		bar.setBackground(Color.YELLOW);
		bar.setForeground(Color.RED);
		bar.setPreferredSize(new Dimension(450,30));
		bar.setStringPainted(true);//가운데에 진행률 텍스트 출력!!
		
		la_name.setPreferredSize(new Dimension(450,30));
		la_total.setPreferredSize(new Dimension(450,30));
		la_name.setFont(new Font("돋움",Font.BOLD,20));
		la_total.setFont(new Font("돋움",Font.BOLD,20));
		
		p_thumb.setPreferredSize(new Dimension(450,150));
		p_thumb.setBackground(Color.YELLOW);
		
		//레이아웃
		this.setLayout(new FlowLayout());
		
		//조립
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
		//제이슨 파싱
		parseJSON();
	}
	public void parseJSON() {
		JSONParser parser = new JSONParser();
		FileReader reader=null;
		try {
			reader = new FileReader(jsonPath);
			
			//파싱한 이후부터는 문자열에 불과했던 제이슨 표기법들이 
			//객체화되어 반환된다..따라서 이 시점부터는 개발자는 json 표기법을
			//객체처럼 사용할 수 있다..
			JSONObject jsonObject=(JSONObject)parser.parse(reader);//json 파서가 해석을 시작한다!!
			
			//marvel 변수가 가리키는 객체는 배열이므로...
			JSONArray jsonArray=(JSONArray)jsonObject.get("marvel");
			
			for(int i=0;i<jsonArray.size();i++) {
				//제이슨에 순차적으로 접근하여 url의 변수를 이용한 복사뜨기!!
				JSONObject obj=(JSONObject)jsonArray.get(i);
				String path=(String)obj.get("url");
				
				System.out.println(path);
				collectData(path);
				index++;
			}
			JOptionPane.showMessageDialog(this, jsonArray.size()+"개의 이미지를 다운로드 완료!!");
			
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
	
	//이미지 수집하기!!
	public void collectData(String path) {
		InputStream is=null;
		FileOutputStream fos=null;
		
		try {
			url = new URL(path);
			con=(HttpURLConnection)url.openConnection();
			//지정한 자원에 연결 성공!!
			int total=con.getContentLength(); //byte 반환!!
			System.out.println("total : "+(total/1204)+"kbyte");
			
			//파일에 대한 정보 출력 
			showInfo(path , total);
			
			//스트림을 뽑아서 파일복사 시작!!
			//복사되어질 파일명은 개발자가 정하는 것이다!!
			String filename=saveDir+System.currentTimeMillis()+"."+FileManager.getExt(path);
			fos=new FileOutputStream(filename);
			
			is=con.getInputStream();
			
			int data=-1;
			int count=0;
			
			while(true) {
				data=is.read();//읽기
				count++;
				if(data==-1)break;
				System.out.println(getPercent(total, count)+"% 읽음");
				
				//구해진 퍼센트를 바에 출력하기~~
				float value=getPercent(total, count);
				bar.setValue((int)value);
				
				fos.write(data);//쓰기
			}
			System.out.println("복사완료");
			
			//패널에 아이콘 부착!!
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
	
	//파일 정보 출력 처리 
	public void showInfo(String path, int total) {
		la_name.setText(FileManager.getFilenameFromHttp(path));
		la_total.setText(Integer.toString(total/1024)+" kbyte");
	}
	
	//썸네일 생성 및 부착~~!
	public void createThumb(String filepath) {
		Image oriImg=null;//크기를 줄이기 이전의 이미지
		Image thumbImg=null;//크기를 줄인 이미지
		
		try {
			oriImg=ImageIO.read(new File(filepath));
			thumbImg=oriImg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			ImageIcon icon=new ImageIcon(thumbImg);
			Thumbnail thumb = new Thumbnail(index, icon, 40, 40);
			
			p_thumb.add(thumb);//패널에 부착!!
			p_thumb.updateUI();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//백분율 계산하기 메서드 정의 
	public float getPercent(float total , int read) {
		//총용량/읽은바이트수*100 = 백분율
		return (read/total)*100;
	}
	
	public static void main(String[] args) {
		new DownLoader();
	}
	
}








