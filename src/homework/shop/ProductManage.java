package homework.shop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import homework.lib.FileManager;

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
	JButton bt_list;// 목록버튼
	
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
	
	JFileChooser chooser;
	Toolkit kit=Toolkit.getDefaultToolkit();
	Image thumbImg;//썸네일 이미지
	Image detailImg;//상세 이미지
	
	FileInputStream fis;//파일을 대상으로 한 입력스트림 
	FileOutputStream fos;//파일을 대상으로 한 출력스트림 
	File thumbFile;//썸네일 처리를  위한 파일객체
	String copyName;//새롭게 부여된 파일명!!
	ProductModel productModel;
	
	public ProductManage(ShopApp shopApp, String title,Color color, int width,int height,boolean showFlag) {
		super(shopApp,title,color,width,height,showFlag);
		
		p_west=new JPanel();
		t_name = new JTextField(10);
		t_price = new JTextField(10);
		t_brand = new JTextField(10);
		
		bt_find = new JButton("이미지");
		
		p_thumb = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(thumbImg, 0, 0, 120,120, ProductManage.this);
			}
		};
		
		bt_regist = new JButton("등록");
		bt_list = new JButton("목록");
		
		p_content=new JPanel();
		table = new JTable();
		scroll = new JScrollPane(table);
		
		p_detailBox= new JPanel();
		p_detail = new JPanel();
		p_info = new JPanel();
		la_name = new JLabel("제품명:티셔츠");
		la_price = new JLabel("가격:80,000원");
		la_brand = new JLabel("브랜드:POLHAM");
		
		chooser = new JFileChooser("D:/web_app/js_workspace/images");
		table.setModel(productModel=new ProductModel());
		
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
		p_west.add(bt_list);
		
		add(p_west, BorderLayout.WEST);
		p_content.add(scroll);
		
		//상세보기 영역 조립
		p_info.add(la_name);
		p_info.add(la_price);
		p_info.add(la_brand);
		
		p_detailBox.add(p_detail, BorderLayout.WEST);
		p_detailBox.add(p_info);
		
		p_content.add(p_detailBox, BorderLayout.SOUTH);
		
		//버튼과 리스너 연결 
		bt_find.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findImg();
			}
		});
		
		bt_regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				regist();
			}
		});
		
		bt_list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getList();
			}
		});
		
	}
	
	//이미지 탐색기 열고, 선택한 이미지 그리기!!
	public void findImg() {
		int result=chooser.showOpenDialog(shopApp);
		
		// 유저가 파일선택 후 ok를 누르면..
		if(result==JFileChooser.APPROVE_OPTION) {
			thumbFile=chooser.getSelectedFile();
			//System.out.println(file.getAbsolutePath());
			
			//선택한 이미지 경로를 이용한 그림 그리기!!
			thumbImg=kit.getImage(thumbFile.getAbsolutePath());
			p_thumb.repaint();
		}
	}
	
	//데이터베이스에 넣기 및 이미지 복사!!
	public void regist() {
		//복사먼저 시도하고, 성공하면  db에 넣자!!!
		if(copy()) {//복사가 성공하면  db에 넣자!!
			
			String name=t_name.getText();
			int price = Integer.parseInt(t_price.getText());
			String brand = t_brand.getText();
			
			
			String sql="insert into product(product_id, name, price,brand,img)";
			sql+=" values(seq_product.nextval,'"+name+"', "+price+" ,'"+brand+"','"+copyName+"')";
			//쿼리문 수행객체인  PreparedStatement  생성!!
			//인터페이스라서 new 하여 생성 불가!! 해결책? 
			//접속이 되어야 쿼리도 날리므로, Connection으로 부터 간접적으로
			//인스턴스를 얻어오면 된다!!
			PreparedStatement pstmt=null;
			try {
				pstmt=shopApp.con.prepareStatement(sql);
				//쿼리 수행메서드 호출!! DML executeUpdate() 호출 
				int result=pstmt.executeUpdate();//실행결과 이 쿼리문에 의해 영향을
				//받은 레코드 갯수를 반환해줌!!
				if(result==0) {//에러가 아니라 단지 insert가 안된거임
					JOptionPane.showMessageDialog(this, "등록실패");
				}else {
					JOptionPane.showMessageDialog(this, "등록성공");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				shopApp.connectionManager.closeDB(pstmt);
			}
			
			
			
		}
	}
	
	//복사 성공여부를 반환하는 메서드!!
	public boolean copy() {
		boolean result=false;
		
		try {
			fis = new FileInputStream(thumbFile);
			//복사될 이미지는 사용자가 올린 이미지명을 사용하지 않고 개발자의
			//규칙을 적용하여 새롭게 생성해야 함!!
			long time=System.currentTimeMillis();
			//System.out.println(time);
			
			//확장자 구하기 
			String ext=FileManager.getExt(thumbFile.getAbsolutePath());
			System.out.println(time+"."+ext);
			
			copyName=time+"."+ext;
			
			fos = new FileOutputStream("D:/web_app/java_workspace/Project0511/data/"+copyName);
			//복사시작!!
			byte[] buff=new byte[1024];//퍼마실 그릇용량 1kbyte
			int data=-1;//데이터의 끝을 알려주는 변수 
			
			while(true) {
				try {
					data=fis.read(buff);//데이터입력!! read()할때마다 1024개
					if(data==-1)break;//파일의 끝을 만나면 반복문 탈출!!
					fos.write(buff);//출력, write()할때마다 1024개씩!!
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			JOptionPane.showMessageDialog(this, "복사완료");
			result=true;//복사성공
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			result=true;//복사실패
		}finally {
			if(fos!=null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	//목록 가져오기 
	public void getList() {
		String sql="select * from product order by product_id desc";
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=shopApp.con.prepareStatement(sql);//준비
			rs=pstmt.executeQuery();
			
			//커서를 내려가면서  VO 에 레코드를 담자!
			//List를 생성하여 아래의 VO들을 차곡차곡 넣어두자!!
			//그래야 TableModel 에서 배열처럼 사용하니깐!!
			ArrayList productList=new ArrayList();
			
			while(rs.next()){//커서 한칸 이동!!
				Product product = new Product();
				//배열의 인덱스가 아니라, 변수명을 이용하므로 훨씬 직관적!!
				product.setProduct_id(rs.getInt("product_id"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getInt("price"));
				product.setBrand(rs.getString("brand"));
				product.setImg(rs.getString("img"));
				
				productList.add(product);//완성된 VO를 리스트에 누적!
			}
			System.out.println("담겨진 총 상품의 수는 "+productList.size());
			
			//생성된 리스트를 TableModel 에게 전달하자!!
			productModel.list=productList;
			table.updateUI();//JTable 새로고침
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			shopApp.connectionManager.closeDB(pstmt, rs);
		}
	}
}





















