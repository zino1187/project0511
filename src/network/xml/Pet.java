package network.xml;

/*
 * ���ǿ� �����ϴ� �繰�� ǥ���ϵ�, ������ ���� ���� �����͸��� 
 * ��� �ִ� ������ �����ϴ� ��ü�� ������ VO(Value Object)
 * ���ø����̼� ���� �о�(��Ű��ó)
 * */
public class Pet {
	private int pet_id; //dbms �� ������ �ΰ�...
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









