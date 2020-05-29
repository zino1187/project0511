package collection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*
 * Collection Framework 컬렉션 프레임웍?
 * - 자바의 api 중 객체를 모아서 처리하는데 유용한 기능을 지원하는 패키지 
 * - java.util 에서 지원 
 * - 객체를 모아서 처리하는 방법은 크게 3가지 유형으로 분류됨 
 * 1) List 계열 (순서 있음) : 배열과 거의 동일
 * 2) Set 계열 (순서 없음) : ex) 과자봉투안의 과자...
 * 3) Map계열 (순서없음) : key-value의 쌍으로 객체를 관리할 수있는 객체
 * 
 * */
public class MapApp {
	public static void main(String[] args) {
		HashSet set=new HashSet();
		
		set.add("사과");
		set.add("딸기");
		set.add("바나나");
		set.add("포도");
		
		//순서가 없다는 것은 넣을때 편하지만, 모든 객체를 반복문으로 추출할수없다
		//이러한 문제를 해결할 수 있는 방법?
		System.out.println("들어있는 요소의 수는 "+set.size());
		
		
		//순서가 없는 컬렉션은 순서있게 만든 후 추출하자!!!
		Iterator<String> it=set.iterator();
		
		//String obj=(String)it.next();//요소에 접근한다!! rs처럼 다음 요소로
		//System.out.println(obj);
		
		//String obj2=(String)it.next();//요소에 접근한다!! rs처럼 다음 요소로
		//System.out.println(obj2);
		
		//데이터가 있을때까지...
		while(it.hasNext()) {
			String ele = it.next();
			System.out.println(ele);
		}
		
		//Map은  key-value의 쌍으로 이루어진 데이터 형식 
		//대표적인 맵은 JSON이다~~
		//Key를 문자열로 주고, 이 key대응되는 데이터도 문자열형식 
		//으로 하겠다는 제너릭 형
		Map<String,String> map=null;
		map = new HashMap<String, String>();
		
		map.put("k1", "홈런볼");
		map.put("k2", "썬칩");
		map.put("k3", "꼬깔콘매운맛");
		map.put("k4", "포테이토칩");
		
		//String data=map.get("k3");
		//System.out.println(data);
		
		//맵으로부터 key값만 추출해서  set 으로 반환해줌!!
		//주의) 데이터 본체 자체가 아닌 이름집합만 반환해줌!!
		Set keySet = map.keySet();
		
		Iterator iter=keySet.iterator();
		
		while(iter.hasNext()) {
			//System.out.println(iter.next());
			String key=(String)iter.next();//명단 추출
			
			String data=map.get(key);
			System.out.println(data);
		}
	}
}









