package util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONTest {

	public static void main(String[] args) {
		//아래의 코드를 자바는 제이슨으로 보지 않는다!!
		//그냥 지역화된 브레이스 영역으로 본다!!
		//따라서 아래의 코드를 일단 문자열로 표현해보자
		/*
		String json="{";
		json=json+"\"name\":\"batman\"";
		json=json+"}";
		*/
		
		//위의 경우엔 스트링 상수가 3개나 생성된다!!
		//따라서 수정가능한 스트링으로 해결한다!!
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"name\":\"batman\"");
		sb.append("}");
		
		System.out.println(sb.toString());//String으로 변환!
		
		//자바프로그랭 언어에서는 기본적으로 JSON 을 이해할 수 없다!!
		//sun에서 기본적으로 지원하지 않기 때문에 외부에서 라이브러리를
		//가져와서 처리하자!!
		//jar를 에디트 플러스를 이용했다면, 클래스패스에 등록해야 한다
		//하지만 이클립스의 경우엔 툴 자체에 등록하면 된다!!
		
		//아래의 객체는 제이슨 형태의 문자열을 분석하여, 제이슨으로 처리
		JSONParser parser = new JSONParser();
		try {
			JSONObject obj = (JSONObject)parser.parse(sb.toString());//문자열에 불과했던  sb가
			// 제이슨으로 해석이 됨!!
			//따라서 이 시점부터는 위의 문자열에 불과햇던  sb를 객체취급할수있다
			System.out.println(obj.get("name"));
			//결론!!
			//비록 자바가 제이슨 자체를 이해할 순 없지만, json표기법으로
			//작성된 문자열을 마치 제이슨 처럼 쓰기 위해 외부 라이브러리를
			//이용할 수 있다!!
		} catch (ParseException e) {
			System.out.println("제이슨 구문에 문제가 있네요..");
			e.printStackTrace();
		}
	}
}











