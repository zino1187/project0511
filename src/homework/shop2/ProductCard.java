package homework.shop2;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

//��ǰī�� �ϳ��� ǥ���ϴ� Ŭ����!!
public class ProductCard extends JPanel{
	JPanel p_canvas;
	JLabel la_name;
	JLabel la_price;
	JLabel la_brand;
	
	Product product;//�Ѱ��� ���ڵ带 �������ִ� �������� ��ü��
							//������ VO , js������ JSON��  VO����..
	
	public ProductCard(Product product) {
		this.product=product;
		
		//������ ����!!
		p_canvas = new JPanel() {
			
		};
		la_name = new JLabel("��ǰ�� : "+product.getName());
		la_price = new JLabel("�� �� : "+product.getPrice());
		la_brand = new JLabel("�귣�� : "+product.getBrand());
		
		//��Ÿ�� ����
		
		//���� Ŭ������ ī���� ũ��!!
		this.setPreferredSize(new Dimension(130,175));
		this.setBackground(Color.YELLOW);
		
		p_canvas.setPreferredSize(new Dimension(110, 70));
		p_canvas.setBackground(Color.CYAN);
		
		la_name.setPreferredSize(new Dimension(120, 30));
		la_price.setPreferredSize(new Dimension(120, 30));
		la_brand.setPreferredSize(new Dimension(120, 30));
		
		//����
		this.add(p_canvas);
		this.add(la_name);
		this.add(la_price);
		this.add(la_brand);
	}
	
}














