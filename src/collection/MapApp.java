package collection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*
 * Collection Framework �÷��� �����ӿ�?
 * - �ڹ��� api �� ��ü�� ��Ƽ� ó���ϴµ� ������ ����� �����ϴ� ��Ű�� 
 * - java.util ���� ���� 
 * - ��ü�� ��Ƽ� ó���ϴ� ����� ũ�� 3���� �������� �з��� 
 * 1) List �迭 (���� ����) : �迭�� ���� ����
 * 2) Set �迭 (���� ����) : ex) ���ں������� ����...
 * 3) Map�迭 (��������) : key-value�� ������ ��ü�� ������ ���ִ� ��ü
 * 
 * */
public class MapApp {
	public static void main(String[] args) {
		HashSet set=new HashSet();
		
		set.add("���");
		set.add("����");
		set.add("�ٳ���");
		set.add("����");
		
		//������ ���ٴ� ���� ������ ��������, ��� ��ü�� �ݺ������� �����Ҽ�����
		//�̷��� ������ �ذ��� �� �ִ� ���?
		System.out.println("����ִ� ����� ���� "+set.size());
		
		
		//������ ���� �÷����� �����ְ� ���� �� ��������!!!
		Iterator<String> it=set.iterator();
		
		//String obj=(String)it.next();//��ҿ� �����Ѵ�!! rsó�� ���� ��ҷ�
		//System.out.println(obj);
		
		//String obj2=(String)it.next();//��ҿ� �����Ѵ�!! rsó�� ���� ��ҷ�
		//System.out.println(obj2);
		
		//�����Ͱ� ����������...
		while(it.hasNext()) {
			String ele = it.next();
			System.out.println(ele);
		}
		
		//Map��  key-value�� ������ �̷���� ������ ���� 
		//��ǥ���� ���� JSON�̴�~~
		//Key�� ���ڿ��� �ְ�, �� key�����Ǵ� �����͵� ���ڿ����� 
		//���� �ϰڴٴ� ���ʸ� ��
		Map<String,String> map=null;
		map = new HashMap<String, String>();
		
		map.put("k1", "Ȩ����");
		map.put("k2", "��Ĩ");
		map.put("k3", "�����ܸſ��");
		map.put("k4", "��������Ĩ");
		
		//String data=map.get("k3");
		//System.out.println(data);
		
		//�����κ��� key���� �����ؼ�  set ���� ��ȯ����!!
		//����) ������ ��ü ��ü�� �ƴ� �̸����ո� ��ȯ����!!
		Set keySet = map.keySet();
		
		Iterator iter=keySet.iterator();
		
		while(iter.hasNext()) {
			//System.out.println(iter.next());
			String key=(String)iter.next();//��� ����
			
			String data=map.get(key);
			System.out.println(data);
		}
	}
}









