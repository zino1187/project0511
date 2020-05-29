package network.image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
 * json ������ �ؽ�Ʈ����� ������ �������� ���� ������ �����Ͽ� �ַ����� 
 * �����غ���!!
 * ����) �ڹ� ��ü�� JSON ǥ����� ������ �� ����!!!
 *       �ڹٴ� {�극�̽�} ����ȭ��� �����ϹǷ�, �����߻�!!
 * �ذ�å) �ڹٰ�  JSON ǥ����� ������ ���ֵ��� ������ ���̺귯���� �̿��ϸ�
 *          �ȴ�..  �ڹ��� API ���� �⺻���� �����Ǵ°� �ƴϹǷ�, �ܺζ��̺귯��
 *          �� �̿��ؾ� �Ѵ�!!
 *          
 * ���̺귯�� �ٿ�ε� ����Ʈ ) ����ġ���� ��ϴ� ����� ����Ʈ 
 *                                maven repository ����!!                
 * */
public class JSONGallery {
	public static void main(String[] args) {
		String path="D:/web_app/java_workspace/Project0511/src/json/pet.json";
		
		//���ڿ��� �Ұ��� json ������ �����͸� JSON ��ü�� �ؼ����ִ� �ļ�
		//�Ľ��� ���Ŀ��� �Ϲݹ��ڿ��� ���ֵǴ°Ծƴ϶�, ��ü�� ���ֵ�..
		//�Ľ��̶�? �м�,�ؼ��� ���� �ǹ��ִ� �����͸� �����ϴ� ����
		JSONParser jsonParser=new JSONParser();
		
		try {
			FileReader reader = new FileReader(path);
			
			//�Ľ��� �Ϸ�Ǹ� �м��� ���� ���̱� ������, ���ڿ��� �Ұ��� 
			//json���ڿ���, �� �������ʹ� ��ü�� ���ٹ������ ��밡��!!
			JSONObject jsonObject=(JSONObject)jsonParser.parse(reader);//���ڱ�� ��Ʈ������ json ������ �Ľ��Ѵ�
			
			//���� get���� �����ϸ� �ȴ�!!
			long age = (Long)jsonObject.get("age");
			System.out.println(age);
			
			String profile=(String)jsonObject.get("profile");
			System.out.println(profile);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
}





















