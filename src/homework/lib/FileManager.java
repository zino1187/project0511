package homework.lib;

/*
	파일과 관련된 중복된 작업을 대신해주는 클래스 정의!!
*/
public class FileManager {
	
	//파일의 확장자 구하기
	//확장자를 구하기 위한경로를 호출시 넘기면 됨!!
	public static String getExt(String path) {
		//1단계: 파일명만 추출하기
		//			마지막 슬래시 다음 부터 , 끝까지가 파일명
		int lastIndex= path.lastIndexOf("\\");//escape 시키면 특수문자가
						//기능을 탈출하여 그냥  일반문자가 되버린다..
		String filename=path.substring(lastIndex+1, path.length());
		
		//2단계: 파일명을 대상으로 확장자만 가져오기 
		//         제일 마지막 점부터 끝까지 추출!!
		lastIndex=filename.lastIndexOf(".");
		String ext=filename.substring(lastIndex+1, filename.length());
		
		return ext;//호출자에게 결과 전달!!
	}
}









