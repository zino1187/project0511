package network.xml;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//SAX�ļ� ��Ŀ���, ����ΰ� �� �±׸� ������������ 
//�̺�Ʈ�� �߻���Ű��, �ش� �޼��带 ȣ�����ִ� ��ü!!
public class MyXMLHandler extends DefaultHandler{
	ArrayList<Pet> list;//�ϼ��� ��, TableModel�� list�� ����!!
	Pet pet;
	
	//������� ��ġ�� ��������� üũ���ִ� ����!!
	boolean isName; 
	boolean isAge;
	boolean isGender;
	DataApp dataApp;
	
	public MyXMLHandler(DataApp dataApp){
		this.dataApp=dataApp;
	}
	
	@Override
	public void startDocument() throws SAXException {
		System.out.println("xml������ �����մϴ�.");
	}
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		System.out.print("<"+qName+">");
		//�����±� �� pets �� ������ �ݷ��ߵ��� ���� �� �ִ� 
		//List �� �غ��ҽ����̴�
		if(qName.equals("pets")) { //<pets>������..
			list = new ArrayList<Pet>();
		}else if(qName.equals("pet")) { //<pet>������, VO ����
			pet=new Pet();//Empty �ν��Ͻ�
		}else if(qName.equals("name")) {
			isName=true;
		}else if(qName.equals("age")) {
			isAge=true;
		}else if(qName.equals("gender")) {
			isGender=true;
		}
	}
	
	//�±� ������ ���ڿ� �߰� �޼���!!
	public void characters(char[] ch, int start, int length) throws SAXException {
		String data = new String(ch, start, length);
		System.out.print(data);
		//���� ����ΰ� ��� �� �ִ����� üũ���� ������ �Ʒ��� �޼��尡
		//��� ȣ��ǹ�����...
		if(isName) {
			pet.setName(data);
		}else if(isAge) {
			pet.setAge(Integer.parseInt(data));	
		}else if(isGender) {
			pet.setGender(data);
		}
	}
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		System.out.println("</"+qName+">");
		
		if(qName.equals("name")) { 
			isName=false;
		}else if(qName.equals("age")) { 
			isAge=false;
		}else if(qName.equals("gender")) { 
			isGender=false;
		}else if(qName.equals("pet")) {
			list.add(pet);
		}
	}
	@Override
	public void endDocument() throws SAXException {
		System.out.print("ä���� ����Ʈ�� ���� "+list.size());
		//XMLModel ��  ����Ʈ�� ä���� ����Ʈ�� ���� 
		dataApp.xmlModel.list=list;
	}
}
















