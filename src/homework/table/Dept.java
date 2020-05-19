package homework.table;

/*
 * 아래의 클래스는 로직을 수행하기 위함이 아니라, 
 * 단지 현실에 존재하는 부서라는 개념을 표현하기 위한 객체이다!!
 * 이러한 목적의 객체를 가리켜 어플리케이션 설계분야에서는 
 * VO(Value Object): 값을 담는 객체 
 * DTO(=Data Transfer Object) : 값이 담겨진 상태로
 * 												 여기저기 전달목적의 객체
 * */
public class Dept {
	private int deptno;
	private String dname;
	private String loc;
	
	public int getDeptno() {
		return deptno;
	}
	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	
	
}





