package homework.shop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

//하나의 화면은 앞으로 패널로 처리하자!~!
public class ProductManage extends Page{
	//--------------------------서쪽 관련
	JPanel p_west;//서쪽에 붙여질 컨테이너
	JTextField t_name;
	JTextField t_price;
	JTextField t_brand;
	JButton bt_find;//이미지 찾기 버튼
	JPanel p_thumb;//등록시 보여질 미리보기 썸네일 
	JButton bt_regist;// 등록버튼
	
	//-------------------------센터영역
	JPanel p_content;//가운테 들어갈 모든 컴포넌트의 어버이!!
	JTable table;
	JScrollPane scroll;
	JPanel p_detailBox;//상세보기 컨테이너
	JPanel p_detail;//등록후 보여질 상세보기 
	JPanel p_info;//이름,가격,브렌드 나올 영역 
	JLabel la_name;
	JLabel la_price;
	JLabel la_brand;
	
	
	public ProductManage(ShopApp shopApp, String title,Color color, int width,int height,boolean showFlag) {
		super(shopApp,title,color,width,height,showFlag);
		
		p_west=new JPanel();
		t_name = new JTextField(10);
		t_price = new JTextField(10);
		t_brand = new JTextField(10);
		
		bt_find = new JButton("이미지");
		p_thumb = new JPanel();
		bt_regist = new JButton("등록");
		
		p_content=new JPanel();
		table = new JTable();
		scroll = new JScrollPane(table);
		
		p_detailBox= new JPanel();
		p_detail = new JPanel();
		p_info = new JPanel();
		la_name = new JLabel("제품명:티셔츠");
		la_price = new JLabel("가격:80,000원");
		la_brand = new JLabel("브랜드:POLHAM");
		
		//스타일 적용 
		p_content.setBackground(Color.PINK);
		p_west.setPreferredSize(new Dimension(120, height));
		
		p_thumb.setBackground(Color.WHITE);
		p_thumb.setPreferredSize(new Dimension(120, 120));
		p_detail.setBackground(Color.BLACK);
		p_detail.setPreferredSize(new Dimension(180, 180));
		p_info.setBackground(Color.RED);
		
		la_name.setPreferredSize(new Dimension(350, 50));
		la_price.setPreferredSize(new Dimension(350, 50));
		la_brand.setPreferredSize(new Dimension(350, 50));
		
		la_name.setFont(new Font("돋움",Font.BOLD,14));
		la_price.setFont(new Font("돋움",Font.BOLD,14));
		la_brand.setFont(new Font("돋움",Font.BOLD,14));
		
		//보더 레이아웃 적용 
		this.setLayout(new BorderLayout());
		p_content.setLayout(new BorderLayout());
		p_detailBox.setLayout(new BorderLayout());
		
		//조립 
		add(p_content, BorderLayout.CENTER);
		p_west.add(t_name);
		p_west.add(t_price);
		p_west.add(t_brand);
		p_west.add(bt_find);
		p_west.add(p_thumb);
		p_west.add(bt_regist);
		
		add(p_west, BorderLayout.WEST);
		p_content.add(scroll);
		
		//상세보기 영역 조립
		p_info.add(la_name);
		p_info.add(la_price);
		p_info.add(la_brand);
		
		p_detailBox.add(p_detail, BorderLayout.WEST);
		p_detailBox.add(p_info);
		
		p_content.add(p_detailBox, BorderLayout.SOUTH);
	}

}













