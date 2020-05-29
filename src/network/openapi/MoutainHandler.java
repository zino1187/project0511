package network.openapi;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//xml 형식을 대상으로 이벤트를 일으키고, 해당 이벤트마다 적절한 메서드 호출
//시작 태그 만나면 호출되는 메서드 : startElement() 호출.. 
public class MoutainHandler extends DefaultHandler{
	MountainApp mountainApp;
	StringBuilder sb;
	
	ArrayList<Mountain> list;
	//실행부가 지나가는 표시!!
	boolean isMntnid;
	boolean isMntnnm;
	boolean isMntninfopoflc;
	boolean isMntninfohght;
	
	Mountain mt;//VO
	
	public MoutainHandler(MountainApp mountainApp) {
		this.mountainApp=mountainApp;
	}
	
	public void startDocument() throws SAXException {
		System.out.println("문서가 시작되었네요");
		sb= new StringBuilder();
		list = new ArrayList<Mountain>();
	}
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//System.out.print("<"+qName+">");
		sb.append("<"+qName+">");
		
		if(qName.equals("item")) {
			mt = new Mountain();  
		}else if(qName.equals("mntnid")) {
			isMntnid=true;
		}else if(qName.equals("mntnnm")) {
			isMntnnm=true;
		}else if(qName.equals("mntninfopoflc")) {
			isMntninfopoflc=true;
		}else if(qName.equals("mntninfohght")) {
			isMntninfohght=true;
		}
		
	}
	//태그와 태그 사이의 문자열 처리
	public void characters(char[] ch, int start, int length) throws SAXException {
		String data = new String(ch,start,length);
		//System.out.print(data);
		sb.append(data);
		
		//태그 사이의 값을 채우자!!
		if(isMntnid) {
			mt.setMntnid(Integer.parseInt(data));//산고유번호
		}else if(isMntnnm) {
			mt.setMntnnm(data);//산 이름
		}else if(isMntninfopoflc) {
			mt.setMntninfopoflc(data);//소재지
		}else if(isMntninfohght) {
			mt.setMntninfohght(Integer.parseInt(data));//산높이
		}	
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		System.out.println("</"+qName+">");
		sb.append("</"+qName+">\n");
		
		if(qName.equals("mntnid")) {
			isMntnid=false;
		}else if(qName.equals("mntnnm")) {
			isMntnnm=false;
		}else if(qName.equals("mntninfopoflc")) {
			isMntninfopoflc=false;
		}else if(qName.equals("mntninfohght")) {
			isMntninfohght=false;
		}else if(qName.equals("item")) {
			//하나의 산 정보의 끝인  item 태그를 만나면 , 리스트에 추가!!
			list.add(mt);
		}
	}
	public void endDocument() throws SAXException {
		System.out.println("문서가 종료되었네요");
		mountainApp.area.append(sb.toString());
		
		System.out.println("검색된 산의 수는 "+list.size());
		
		for(int i=0;i<list.size();i++) {
			Mountain mt = list.get(i);
			System.out.println(mt.getMntnid()+","+mt.getMntninfopoflc());
		}
		
		//모델객체가 보유한 리스트를 지금 채워진 리스트로 교체
		//System.out.println("모델객체 :"+mountainApp.model);
		//System.out.println("모델의 리스트 "+mountainApp.model.list);
		mountainApp.model.list = list;
		mountainApp.table.updateUI();
		
		//System.out.println(sb.toString());
	}
}









