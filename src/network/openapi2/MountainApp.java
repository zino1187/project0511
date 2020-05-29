package network.openapi2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;



public class MountainApp extends JFrame{
	String serviceURL="http://openapi.forest.go.kr/openapi/service/trailInfoService/getforeststoryservice";
	String key="TPK6sq5VdCOFrijK99CmJHQCEVer9GwK4sxLvP6ED6dBExrBc6FO298QjQadJsw7C4sDZ8yBXJfsYZ%2FVT6LG0A%3D%3D";
	
	JButton bt;
	JTextArea area;
	JScrollPane scroll1;
	
	JTable table;
	JScrollPane scroll2;
	Thread thread; //Open API 요청시 지연이 발생하므로 쓰레드로 처리
						//메인쓰레드는 절대로 루프, 지연, 대기 등에 빠지게 해서는
						//안됨
	InputStream is;
	MountainHandler mountainHandler;
	BufferedReader rd;
	HttpURLConnection conn;
	
	public MountainApp() throws Exception{
		bt = new JButton("Load");
		area = new JTextArea();
		scroll1 = new JScrollPane(area);
		
		table = new JTable();
		scroll2 = new JScrollPane(table);
		
		//스타일 적용 
		area.setPreferredSize(new Dimension(600, 350));
		area.setBackground(Color.YELLOW);
		
		table.setPreferredSize(new Dimension(600, 200));
		
		//조립 
		setLayout(new FlowLayout());
		add(bt);
		add(scroll1);
		add(scroll2);
		
		setSize(650,700);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		//버튼과 리스너 연결 
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thread = new Thread() {
					public void run() {
						try {
							loadData();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
				thread.start();//쓰레드 동작 시작!!
			}
		});
		
	}
	
	/*
	 * try~catch로 예외처리를 하고 싶지 않은 경우, 
	 * 즉 예외에 대한 처리를 내가 부담하고 싶지 않은 경우엔 남한테 전가시킬수
	 * 있다..(무책임) , 그럼 누가 처리하나? 아래의 경우처럼  throws 명시하면
	 * 아래의 메서드를 호출한 者자가  	 예외처리할 것을 부담하게 됨...
	 */
	public void loadData() throws Exception{
        StringBuilder urlBuilder = new StringBuilder(serviceURL); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "="+key); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("mntnNm","UTF-8") + "=" + URLEncoder.encode("지리산", "UTF-8")); /**/
        urlBuilder.append("&" + URLEncoder.encode("mntnHght","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /**/
        urlBuilder.append("&" + URLEncoder.encode("mntnAdd","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /**/
        urlBuilder.append("&" + URLEncoder.encode("mntnInfoAraCd","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /**/
        urlBuilder.append("&" + URLEncoder.encode("mntnInfoSsnCd","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /**/
        urlBuilder.append("&" + URLEncoder.encode("mntnInfoThmCd","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /**/
        URL url = new URL(urlBuilder.toString());
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(is=conn.getInputStream(), "UTF-8"));
        } else {
            rd = new BufferedReader(new InputStreamReader(is=conn.getErrorStream(),"UTF-8"));
        }
        parseData();
	}
	
	public void parseData() {
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		
		try {
			SAXParser saxParser = parserFactory.newSAXParser();
			saxParser.parse(is, mountainHandler = new MountainHandler());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			/*
			if(rd!=null) {
				try {
					rd.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				conn.disconnect();
			}	
			*/		
		}
	}
	
	//아래의 코드처럼 작성하면 최종적으로 jvm이 예외를 감당해야 한다...
	public static void main(String[] args) throws Exception{
		new MountainApp();
	}
}




