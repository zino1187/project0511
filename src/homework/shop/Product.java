package homework.shop;

/*
 * �����ͺ��̽��� ���̺��� 1:1 �����ϴ� VO�� ����� ������ ����غ���!
 * �����ͺ��̽� ���迡���� ������ �ϳ��� �繰�� ���̺�� �������ϰ�, 
 * ���̺� �� ������ ���ڵ尡 �ٷ� ���ǿ����� ��ü�̴�. 
 * ���� ���̺��� ���� �繰�� �ƴ϶�, ��Ǫ���̴�!!
 * ���� �ڹټ��󿡼��� ������ �����ϴ� �ϳ��� �繰�� Ŭ������� ������ ǥ���ϵ�
 * �� Ŭ������ ���� �繰�� �ƴ϶� ��Ǫ���̰�, �� ��Ǫ������ �¾��� �ϳ��� �ν�
 * �Ͻ��� ���� �繰�̴�. 
 * 
 *                         �����ͺ��̽�               �ڹٺо� 
 *   ������ �繰           Table�� ǥ��            Class �� ǥ�� 
 *   �ν��Ͻ�               Table���� ���ڵ�     ���� �ö�� ��ü
 * */
public class Product {
	private int product_id;
	private String name;
	private int price;
	private String brand;
	private String img;
	
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	
}















