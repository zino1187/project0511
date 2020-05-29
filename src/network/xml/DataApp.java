package network.xml;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;
/*
 * json vs xml  
 * 
 * 공통점 : 텍스트기반의 데이터 교환 형식 
 * 차이점 : 구조화 방법이 틀리다. 
 *           json : 자바스크립트의 객체표기법 
 *           xml : 태그를 이용
 * 장점: 이 기종간의 데이터 교환이 가능함          
 * */
public class DataApp extends JFrame implements ActionListener{
	JPanel p_west;
	JButton bt_json;
	JButton bt_xml;
	JButton bt_dbms;
	JTable table;
	JScrollPane scroll;
	
	JSONModel jsonModel;
	XMLModel xmlModel;
	DBMSModel dbmsModel;
	String path="D:/web_app/java_workspace/Project0511/src/network/xml/";
	public DataApp() {
		p_west = new JPanel();
		bt_json = new JButton("json");
		bt_xml = new JButton("xml");
		bt_dbms = new JButton("dbms");
		table  =new JTable();
		scroll = new JScrollPane(table);
		
		//스타일 적용
		p_west.setPreferredSize(new Dimension(120, 150));
		p_west.setBackground(Color.WHITE);
		
		//조립 
		p_west.add(bt_json);
		p_west.add(bt_xml);
		p_west.add(bt_dbms);
		
		add(p_west, BorderLayout.WEST);
		add(scroll);
		
		setSize(600, 450);
		setVisible(true);
		setLocationRelativeTo(null);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//데이터베이스나 스트림 등 열린 자원 해제
				System.exit(0);
			}
		});
		
		//버튼과 리스너 연결 
		bt_json.addActionListener(this);
		bt_xml.addActionListener(this);
		bt_dbms.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj==bt_json) {
			loadJSON();
		}else if(obj==bt_xml) {
			loadXML();
		}else if(obj==bt_dbms) {
			loadDBMS();
		}
	}
	public void loadJSON() {
		table.setModel(jsonModel=new JSONModel());
		//json 파싱!!!
		JSONParser jsonParser=new JSONParser();
		FileReader reader=null;
			
		try {
			reader = new FileReader(new File(path+"pets.json"));
			
			//파싱한 이후부터는 json을 객체화 시켰기 때문에 객체처럼 사용할수
			//있다!!
			JSONObject jsonObject=(JSONObject)jsonParser.parse(reader);
			JSONArray jsonArray=(JSONArray)jsonObject.get("pets");
			
			ArrayList<Pet> list=new ArrayList<Pet>();
			
			for(int i=0;i<jsonArray.size();i++) {
				JSONObject obj=(JSONObject)jsonArray.get(i);
				Pet pet = new Pet();//empty  상태의 빈 인스턴스 생성!!
				pet.setName((String)obj.get("name"));
				long age=(Long)obj.get("age");
				pet.setAge((int)age);
				pet.setGender((String)obj.get("gender"));
				
				list.add(pet);//반려동물 인스턴스를 list 에 추가하기
			}
			System.out.println("담겨진 반려동물의 수는 "+list.size());
			
			//tableModel의 list에 대입 
			jsonModel.list = list;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}finally {
			if(reader!=null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		table.updateUI();
	}
	public void loadXML() {
		table.setModel(xmlModel=new XMLModel());
		//xml 파싱 
		//XML 파싱의 2가지 방식 : DOM 방식, SAX 방식
		//DOM : 장점 - 기존의 html돔처럼 제어할 수 있으므로,개발자에게익숙
		//          단점-  pc는 메모리가 충분하지만, 스마트기기의 경우 메모리가
		//부족하므로 DOM방식을 이용하면 비효율적임..
		//SAX : 이벤트 기반의 제어방식..
		//JSON parser는 자바기본 api없기 때문에 외부라이브러리를 이용했지
		//만 xml 은 전통적으로 자바언어에서 주로 사용해왔던 형식이므로 
		//자체적인 파서가 지원된다!!
		
		SAXParserFactory parserFactory=SAXParserFactory.newInstance();
		
		try {
			SAXParser saxParser=parserFactory.newSAXParser();
			
			//원하는 xml 파일에 대해 파싱을 시작한다!!
			MyXMLHandler myXMLHandler=new MyXMLHandler(this);
			
			saxParser.parse(new File(path+"pets.xml"), myXMLHandler);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table.updateUI();	
		
	}
	public void loadDBMS() {
		table.setModel(dbmsModel=new DBMSModel());
		table.updateUI();	
	}
	
	public static void main(String[] args) {
		new DataApp();
	}

}





