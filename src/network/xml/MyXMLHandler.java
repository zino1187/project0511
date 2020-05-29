package network.xml;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//SAX파서 방식에서, 실행부가 각 태그를 만났을때마다 
//이벤트를 발생시키고, 해당 메서드를 호출해주는 객체!!
public class MyXMLHandler extends DefaultHandler{
	ArrayList<Pet> list;//완성된 후, TableModel의 list에 대입!!
	Pet pet;
	
	//실행부의 위치가 어디인지를 체크해주는 논리값!!
	boolean isName; 
	boolean isAge;
	boolean isGender;
	DataApp dataApp;
	
	public MyXMLHandler(DataApp dataApp){
		this.dataApp=dataApp;
	}
	
	@Override
	public void startDocument() throws SAXException {
		System.out.println("xml문서를 시작합니다.");
	}
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		System.out.print("<"+qName+">");
		//시작태그 중 pets 를 만나면 반려견들을 담을 수 있는 
		//List 를 준비할시점이다
		if(qName.equals("pets")) { //<pets>만나면..
			list = new ArrayList<Pet>();
		}else if(qName.equals("pet")) { //<pet>만나면, VO 생성
			pet=new Pet();//Empty 인스턴스
		}else if(qName.equals("name")) {
			isName=true;
		}else if(qName.equals("age")) {
			isAge=true;
		}else if(qName.equals("gender")) {
			isGender=true;
		}
	}
	
	//태그 사이의 문자열 발견 메서드!!
	public void characters(char[] ch, int start, int length) throws SAXException {
		String data = new String(ch, start, length);
		System.out.print(data);
		//현재 실행부가 어디에 와 있는지를 체크하지 않으면 아래의 메서드가
		//모두 호출되버린다...
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
		System.out.print("채워진 리스트의 수는 "+list.size());
		//XMLModel 의  리스트에 채워진 리스트를 대입 
		dataApp.xmlModel.list=list;
	}
}
















