package homework.shop2;

import java.awt.Color;
import java.awt.Component;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//�ϳ��� ȭ���� ������ �гη� ó������!~!
public class ShoppingMain extends Page{
	public ShoppingMain(ShopApp shopApp, String title,Color color, int width,int height,boolean showFlag) {
		super(shopApp,title,color,width,height,showFlag);
		
		//selectAll();
	}
	
	//�����ͺ��̽��� �ִ� ��� ��ǰ ���� ��������!!
	public void selectAll(){
		String sql="select * from product order by product_id desc";
		
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			pstmt=shopApp.con.prepareStatement(sql);
			rs=pstmt.executeQuery();//��� ���!!!
			
			while(rs.next()) {
				Product product = new Product();
				
				//rs���� product �Űܽ���!!
				product.setProduct_id(rs.getInt("product_id"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getInt("price"));
				product.setBrand(rs.getString("brand"));
				product.setImg(rs.getString("img"));
				
				ProductCard card = new ProductCard(product);
				//���� ���� �г��̹Ƿ�, ī���г��� �� �гο� ������!!
				
				this.add(card);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			shopApp.connectionManager.closeDB(pstmt, rs);
		}
		
	}
	
	//���� �гο� �ڽ�������Ʈ���� ��� �����!!
	//js ������ removeChild() 
	public void removeChild() {
		//�ڽ� ��� ���ϱ�!!
		Component[] childList=this.getComponents();
		
		for(int i=0;i<childList.length;i++) {
			this.remove(childList[i]);
		}
	}
	
	//������ �޼��� 
	public void createProduct() {
		//���Ƿ� 5���� �����غ���!!
		for(int i=0;i<15;i++) {
			Product product = new Product();
			product.setName("û����");
			product.setPrice(50000);
			product.setBrand("Guess");
			
			ProductCard card = new ProductCard(product);
			
			//���� ���� �г��̹Ƿ�, ī���г��� �� �гο� ������!!
			this.add(card);
		}
		
	}
}







