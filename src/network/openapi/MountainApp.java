package network.openapi;

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
import javax.swing.JTextField;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;



public class MountainApp extends JFrame{
	String serviceURL="http://openapi.forest.go.kr/openapi/service/trailInfoService/getforeststoryservice";
	String key="TPK6sq5VdCOFrijK99CmJHQCEVer9GwK4sxLvP6ED6dBExrBc6FO298QjQadJsw7C4sDZ8yBXJfsYZ%2FVT6LG0A%3D%3D";
	
	JButton bt;
	JTextField t_input;
	JTextArea area;
	JScrollPane scroll1;
	
	JTable table;
	JScrollPane scroll2;
	Thread thread; //Open API ��û�� ������ �߻��ϹǷ� ������� ó��
						//���ξ������ ����� ����, ����, ��� � ������ �ؼ���
						//�ȵ�
	
	SAXParserFactory parserFactory;
	SAXParser saxParser;
	
	//xml�� ���Ϸ� �����ϴ� ���� �ƴ϶�, �޸𸮻��� �����ͷ� �����ϱ� ������
	//��Ʈ���� ������� �Ľ��ؾ� �Ѵ�!! �� ������ ������ ������� �Ľ��Ҽ�
	//�մ� �� �ƴ��� ��������!!
	HttpURLConnection conn;
	BufferedReader rd;
	
	InputStream is;
	MoutainHandler moutainHandler;
	
	MountainModel model;
	
	public MountainApp() throws Exception{
		bt = new JButton("Load");
		t_input = new JTextField(10);
		area = new JTextArea();
		scroll1 = new JScrollPane(area);
		
		table = new JTable(model=new MountainModel());
		scroll2 = new JScrollPane(table);
		
		//�Ľ̰��� ��ü ���� 
		parserFactory = SAXParserFactory.newInstance();
		saxParser  = parserFactory.newSAXParser();//�ļ� ����
		
		//��Ÿ�� ���� 
		area.setPreferredSize(new Dimension(600, 350));
		area.setBackground(Color.YELLOW);
		
		table.setPreferredSize(new Dimension(600, 200));
		
		//���� 
		setLayout(new FlowLayout());
		add(bt);
		add(t_input);
		add(scroll1);
		add(scroll2);
		
		setSize(650,700);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		//��ư�� ������ ���� 
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
				thread.start();//������ ���� ����!!
			}
		});
		
	}
	
	/*
	 * try~catch�� ����ó���� �ϰ� ���� ���� ���, 
	 * �� ���ܿ� ���� ó���� ���� �δ��ϰ� ���� ���� ��쿣 ������ ������ų��
	 * �ִ�..(��å��) , �׷� ���� ó���ϳ�? �Ʒ��� ���ó��  throws �����ϸ�
	 * �Ʒ��� �޼��带 ȣ���� ���ڰ�  	 ����ó���� ���� �δ��ϰ� ��...
	 */
	public void loadData() throws Exception{
        StringBuilder urlBuilder = new StringBuilder(serviceURL); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "="+key); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("mntnNm","UTF-8") + "=" + URLEncoder.encode(t_input.getText(), "UTF-8")); /**/
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
        
        //�ε�� xml�� ������� �Ľ��� ��������!!
        parseXML();
	}
	
	public void parseXML() {
		try {
			//�Ľ� ����!!!
			saxParser.parse(is, moutainHandler=new MoutainHandler(this));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
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
		}
	}
	
	//�Ʒ��� �ڵ�ó�� �ۼ��ϸ� ���������� jvm�� ���ܸ� �����ؾ� �Ѵ�...
	public static void main(String[] args) throws Exception{
		new MountainApp();
	}
}



