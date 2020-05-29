package network.xml;

/*
 * 현실에 존재하는 사물을 표현하되, 로직은 없고 오직 데이터만을 
 * 담고 있는 역할을 수행하는 객체를 가리켜 VO(Value Object)
 * 어플리케이션 설계 분야(아키텍처)
 * */
public class Pet {
	private int pet_id; //dbms 를 염두해 두고...
	private String name;
	private int age;
	private String gender;
	
	public int getPet_id() {
		return pet_id;
	}
	public void setPet_id(int pet_id) {
		this.pet_id = pet_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
}









