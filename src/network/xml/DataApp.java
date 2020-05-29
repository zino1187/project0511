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
 * ������ : �ؽ�Ʈ����� ������ ��ȯ ���� 
 * ������ : ����ȭ ����� Ʋ����. 
 *           json : �ڹٽ�ũ��Ʈ�� ��üǥ��� 
 *           xml : �±׸� �̿�
 * ����: �� �������� ������ ��ȯ�� ������          
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
		
		//��Ÿ�� ����
		p_west.setPreferredSize(new Dimension(120, 150));
		p_west.setBackground(Color.WHITE);
		
		//���� 
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
				//�����ͺ��̽��� ��Ʈ�� �� ���� �ڿ� ����
				System.exit(0);
			}
		});
		
		//��ư�� ������ ���� 
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
		//json �Ľ�!!!
		JSONParser jsonParser=new JSONParser();
		FileReader reader=null;
			
		try {
			reader = new FileReader(new File(path+"pets.json"));
			
			//�Ľ��� ���ĺ��ʹ� json�� ��üȭ ���ױ� ������ ��üó�� ����Ҽ�
			//�ִ�!!
			JSONObject jsonObject=(JSONObject)jsonParser.parse(reader);
			JSONArray jsonArray=(JSONArray)jsonObject.get("pets");
			
			ArrayList<Pet> list=new ArrayList<Pet>();
			
			for(int i=0;i<jsonArray.size();i++) {
				JSONObject obj=(JSONObject)jsonArray.get(i);
				Pet pet = new Pet();//empty  ������ �� �ν��Ͻ� ����!!
				pet.setName((String)obj.get("name"));
				long age=(Long)obj.get("age");
				pet.setAge((int)age);
				pet.setGender((String)obj.get("gender"));
				
				list.add(pet);//�ݷ����� �ν��Ͻ��� list �� �߰��ϱ�
			}
			System.out.println("����� �ݷ������� ���� "+list.size());
			
			//tableModel�� list�� ���� 
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
		//xml �Ľ� 
		//XML �Ľ��� 2���� ��� : DOM ���, SAX ���
		//DOM : ���� - ������ html��ó�� ������ �� �����Ƿ�,�����ڿ����ͼ�
		//          ����-  pc�� �޸𸮰� ���������, ����Ʈ����� ��� �޸𸮰�
		//�����ϹǷ� DOM����� �̿��ϸ� ��ȿ������..
		//SAX : �̺�Ʈ ����� ������..
		//JSON parser�� �ڹٱ⺻ api���� ������ �ܺζ��̺귯���� �̿�����
		//�� xml �� ���������� �ڹپ��� �ַ� ����ؿԴ� �����̹Ƿ� 
		//��ü���� �ļ��� �����ȴ�!!
		
		SAXParserFactory parserFactory=SAXParserFactory.newInstance();
		
		try {
			SAXParser saxParser=parserFactory.newSAXParser();
			
			//���ϴ� xml ���Ͽ� ���� �Ľ��� �����Ѵ�!!
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





