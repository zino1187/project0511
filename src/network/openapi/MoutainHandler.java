package network.openapi;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//xml ������ ������� �̺�Ʈ�� ����Ű��, �ش� �̺�Ʈ���� ������ �޼��� ȣ��
//���� �±� ������ ȣ��Ǵ� �޼��� : startElement() ȣ��.. 
public class MoutainHandler extends DefaultHandler{
	MountainApp mountainApp;
	StringBuilder sb;
	
	ArrayList<Mountain> list;
	//����ΰ� �������� ǥ��!!
	boolean isMntnid;
	boolean isMntnnm;
	boolean isMntninfopoflc;
	boolean isMntninfohght;
	
	Mountain mt;//VO
	
	public MoutainHandler(MountainApp mountainApp) {
		this.mountainApp=mountainApp;
	}
	
	public void startDocument() throws SAXException {
		System.out.println("������ ���۵Ǿ��׿�");
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
	//�±׿� �±� ������ ���ڿ� ó��
	public void characters(char[] ch, int start, int length) throws SAXException {
		String data = new String(ch,start,length);
		//System.out.print(data);
		sb.append(data);
		
		//�±� ������ ���� ä����!!
		if(isMntnid) {
			mt.setMntnid(Integer.parseInt(data));//�������ȣ
		}else if(isMntnnm) {
			mt.setMntnnm(data);//�� �̸�
		}else if(isMntninfopoflc) {
			mt.setMntninfopoflc(data);//������
		}else if(isMntninfohght) {
			mt.setMntninfohght(Integer.parseInt(data));//�����
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
			//�ϳ��� �� ������ ����  item �±׸� ������ , ����Ʈ�� �߰�!!
			list.add(mt);
		}
	}
	public void endDocument() throws SAXException {
		System.out.println("������ ����Ǿ��׿�");
		mountainApp.area.append(sb.toString());
		
		System.out.println("�˻��� ���� ���� "+list.size());
		
		for(int i=0;i<list.size();i++) {
			Mountain mt = list.get(i);
			System.out.println(mt.getMntnid()+","+mt.getMntninfopoflc());
		}
		
		//�𵨰�ü�� ������ ����Ʈ�� ���� ä���� ����Ʈ�� ��ü
		//System.out.println("�𵨰�ü :"+mountainApp.model);
		//System.out.println("���� ����Ʈ "+mountainApp.model.list);
		mountainApp.model.list = list;
		mountainApp.table.updateUI();
		
		//System.out.println(sb.toString());
	}
}









