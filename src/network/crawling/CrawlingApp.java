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
 * Http 프로토콜을 사용하는 Web상에 공유되는 모든 데이터는 
 * 공개되어 있으므로, 수집이 가능하다!!
 * 이미지,  js, css, html  , 영상 파일이던 상관없이 수집해보자!!
 * 1) 자원에연결 
 * 2) 스트림으로 입력처리
 * 3) 로컬 하드에 출력처리 
 * 네트워크상의 자원을 복사해보자!! 
 * */
public class CrawlingApp extends JFrame{
	JTextField t_url;
	JButton bt;
	JPanel p_thumb; //썸네일 이미지를 출력할 패널
	
	//웹상의 자원에 연결을 지원해주는 객체, 이 객체로부터 스트림을 연결
	//하여 원하는 작업을 수행할 수 있다..
	HttpURLConnection con;//추상클래스라서, 직접 new불가하며
	//URL객체를 이용하여 인스턴스를 얻어야 한다~~
	
	URL url;
	String saveDir="D:/web_app/java_workspace/Project0511/data/";
	FileOutputStream fos;//로컬 하드경로에 생성할 출력스트림
	//FileOutputStream 의 특징은 생성자에서 빈(empty)파일을 생성
	int index; //썸네일 식별번호
	Thread thread; //프로그레스 바 처리용 쓰레드
	JProgressBar bar;
	
	public CrawlingApp() {
		t_url = new JTextField("http://",30);
		bt = new JButton("수집하기");
		p_thumb = new JPanel();
		bar = new JProgressBar();
		
		//스타일 적용
		bar.setPreferredSize(new Dimension(480, 30));
		bar.setBackground(Color.ORANGE);
		bar.setForeground(Color.RED);
		p_thumb.setPreferredSize(new Dimension(500, 150));
		p_thumb.setBackground(Color.YELLOW);
		
		//조립
		setLayout(new FlowLayout());//레이아웃 변경!!
		add(t_url);
		add(bt);
		add(bar);
		add(p_thumb);
		
		setSize(500,250);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		//버튼과 리스너 연결 
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
	
	//인터넷 주소상의 리소스를 나의 하드디스크로 복사뜨기!!
	public void collectData() {
		
		String urlString = t_url.getText();
		InputStream is=null;//finally에서 닫기위함!!
		try {
			url = new URL(urlString);
			con=(HttpURLConnection)url.openConnection();
			//con.setRequestMethod("HEAD");
			int total = con.getContentLength();//total byte
			float kb=(float)total/1024;
			
			System.out.println("총 용량은 "+kb+" kbytes");
			
			//연결이 성공되었으면, 스트림을 얻어와 원하는 입출력 작업을 시도
			is=con.getInputStream();
			
			//비어있는 파일이 생성되며, 출력스트림이 연결!!(쓰기중..)
			//임의의 파일명 생성하기!!
			long time=System.currentTimeMillis();//밀리세컨드까지의 시간,
			//즉 유일하다!!
			String ext=FileManager.getExt(t_url.getText());
			
			String filename=time+"."+ext;
			System.out.println("생성된 경로 : "+saveDir+filename);
			
			fos=new FileOutputStream(saveDir+filename);
			
			//텍스트파일, 이미지, 동영상 등 모든 종류의 데이터를 섭렵하려면
			//byte[] buff=new byte[1024]; //1 kbyte
			
			int data=-1;
			int read=0;
			while(true) {
				data=is.read();//실제 데이터는 배열에 들어가고, 
				//data변수는 읽어들일 자원이 있는지 여부를 판단하는 용도로사용
				read+=data;
				
				
				bar.setValue(100-(int)getPercent(total, read));
				
				System.out.println(100-(int)getPercent(total, read));
				
				if(data==-1)break;
				//원하는 경로에 입력데이터 출력~~(복사)
				fos.write(data);//1byte 출력!!
			}
			JOptionPane.showMessageDialog(this, "수집완료");
			
			//이미지인 경우와 아닌 경우에 대한 판단 메시지!!
			//JPG --> jpg 모든 대문자는 강제로 소문자로 변경!!
			ext=ext.toLowerCase();//소문자로 변환!!
			
			if(ext.equals("jpg") || ext.equals("gif") || ext.equals("png")) {
				JOptionPane.showMessageDialog(this, "이미지를 가져왔군요\n아래의 패널에 표시할께요");
				createThumb(saveDir+filename);//썸네일 출력
			}else {
				JOptionPane.showMessageDialog(this, "이미지가 아니군요\n패널에 표시 안할께요");
			}
			
		} catch (MalformedURLException e) {
			JOptionPane.showMessageDialog(this, "주소를 확인해주세요");
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "입출력 에러");
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
		
		//homework.gallery에 들어있는 클래스 재활용!!
		try {
			Image img=ImageIO.read(new File(dest));
			img=img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(img);//아이콘 생성
			Thumbnail thumb = new Thumbnail(index, icon, 40, 40);
			p_thumb.add(thumb);//라벨을 패널에 부착!!
			p_thumb.updateUI();//새로고침!!
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
























