package network.image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
 * json 형식의 텍스트기반의 데이터 집합으로 부터 데이터 추출하여 겔러리로 
 * 응용해본다!!
 * 주의) 자바 자체는 JSON 표기법을 이해할 수 없다!!!
 *       자바는 {브레이스} 지역화라고 이해하므로, 에러발생!!
 * 해결책) 자바가  JSON 표기법을 이해할 수있도록 별도의 라이브러리를 이용하면
 *          된다..  자바의 API 에서 기본으로 제공되는게 아니므로, 외부라이브러리
 *          를 이용해야 한다!!
 *          
 * 라이브러리 다운로드 사이트 ) 아파치에서 운영하는 저장소 사이트 
 *                                maven repository 들어가자!!                
 * */
public class JSONGallery {
	public static void main(String[] args) {
		String path="D:/web_app/java_workspace/Project0511/src/json/pet.json";
		
		//문자열에 불과한 json 형식의 데이터를 JSON 객체로 해석해주는 파서
		//파싱한 이후에는 일반문자열로 간주되는게아니라, 객체로 간주됨..
		//파싱이란? 분석,해석을 통해 의미있는 데이터를 추출하는 과정
		JSONParser jsonParser=new JSONParser();
		
		try {
			FileReader reader = new FileReader(path);
			
			//파싱이 완료되면 분석이 끝난 것이기 때문에, 문자열에 불과한 
			//json문자열이, 이 시점부터는 객체의 접근방식으로 사용가능!!
			JSONObject jsonObject=(JSONObject)jsonParser.parse(reader);//문자기반 스트림으로 json 파일을 파싱한다
			
			//따라서 get으로 접근하면 된다!!
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





















