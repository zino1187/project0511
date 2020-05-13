package graphic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ThumbnailGallery extends JFrame{
	JPanel p_west;
	JPanel p_center;//컨텐츠 영역
	JPanel p_canvas;//큰 이미지 그려질 패널
	Toolkit kit=Toolkit.getDefaultToolkit();//이미지를 얻는 방법은 여러가지가 있지만, 이번에는 툴킷이용
	Image bigImage;//큰 이미지 객체
	ImageIcon prev_icon, next_icon;
	JButton bt_prev, bt_next;
	String dir="D:/web_app/js_workspace/images/";
	String[] path= {"t1.jpg","t2.jpg","t3.jpg","t4.jpg","t5.jpg","t6.jpg","t7.jpg","t8.jpg","t9.jpg","t10.jpg"};
	ThumbLabel[] la_thumb;//좌측 썸네일 라벨들!!
	ImageIcon[] icon;//각 라벨에 들어갈 아이콘
	JScrollPane scroll;
	JTextField t_id;//현재 보고있는 이미지의  id 출력정보
	int cursor=0; //현재 보고있는 이미지의 인덱스!!
	
	public ThumbnailGallery() {
		p_west = new JPanel();
		p_center = new JPanel();
		
		//.java 소스로 분리시키면 서로 레퍼런스를 주고받아야하는
		//불편함이 있기 때문에, 패널이 현재 클래스의 멤버변수를 쉽게 접근
		//하려면 내부익명으로 처리하자!!
		p_canvas = new JPanel(){
			public void paint(Graphics g) {
				g.drawImage(bigImage, 0, 0, 450, 400,this);
			}
		};
		
		prev_icon = new ImageIcon("D:/web_app/js_workspace/images/prev.webp");
		next_icon = new ImageIcon("D:/web_app/js_workspace/images/next.webp");
		bt_prev = new JButton("prev");
		bt_next = new JButton("next");
		scroll = new JScrollPane(p_west);
		t_id = new JTextField("0", 5);
		
		//스타일 부여
		p_west.setBackground(Color.YELLOW);
		p_center.setBackground(Color.CYAN);
		p_center.setBackground(Color.RED);
		p_west.setPreferredSize(new Dimension(100, 400));
		p_canvas.setPreferredSize(new Dimension(450, 400));
		t_id.setFont(new Font("돋움", Font.BOLD, 45));
		
		//컨텐츠 영역 조립 
		p_center.add(bt_prev);
		p_center.add(bt_next);
		p_center.add(p_canvas);
		p_center.add(t_id);
		
		
		add(scroll, BorderLayout.WEST);//서쪽에 썸네일 패널 부착!!
		add(p_center);//센터에 컨텐츠 컨테이너 부착!!
		
		
		//서쪽 패널에 라벨들 부착!!
		createImage();//라벨 및 아이콘 생성 
		
		for(int i=0;i<path.length;i++) {
			p_west.add(la_thumb[i]);
			
			la_thumb[i].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					//이벤트를 일으킨 원본 컴포넌트를 가리켜 이벤트 소스
					ThumbLabel obj=(ThumbLabel)e.getSource();
					//System.out.println(obj.id);
					
					//원칙상 기본자료형과 객체자료형은 서로 변환이 불가능하므로
					//Wrapper 클래스를 이용하면 된다 
					//int(기본자료형) --> String(객체)
					cursor=obj.id;
					t_id.setText(Integer.toString(obj.id));
					
					showImage(obj.id);//선택한 썸네일과 매칭되는 큰이미지 보여주기
				}
			});
		}
		
		//버튼과 리스너 연결 
		bt_prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cursor--;//이전 사진으로 커서를 이동
				//cursor의 결과가 0보다 작으면 욕!!
				if(cursor<0) {
					JOptionPane.showMessageDialog(ThumbnailGallery.this ,"이전 사진이 없네요");
					cursor=0;
				}else {
					showImage(cursor);
					t_id.setText(Integer.toString(cursor));
				}
			}
		});
		
		bt_next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cursor++;//다음 사진으로 커서를 이동
				if(cursor > path.length-1) {
					JOptionPane.showMessageDialog(ThumbnailGallery.this ,"다음 사진이 없네요");
					cursor=path.length-1;
				}else {
					showImage(cursor);
					t_id.setText(Integer.toString(cursor));
				}
			}
		});
		
		setSize(600,700);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	//좌측 썸네일 및 컨텐츠 영역의 상세 이미지 처리
	public void createImage() {
		//이미지 아이콘을 가진, 라벨 10개 생성!!
		la_thumb = new ThumbLabel[path.length];//라벨 배열생성
		icon = new ImageIcon[path.length];//아이콘 배열생성
		
		//텅빈 배열을 반복문으로 채워넣자!!
		for(int i=0;i<path.length;i++) {
			la_thumb[i] = new ThumbLabel(i, new ImageIcon(dir+path[i]));
		}
		
		//큰 이미지 처리 
		bigImage = kit.getImage(dir+path[0]);
	}
	
	//큰이미지 출력하는 메서드 
	//매개변수로 썸네일의 고유번호를 넘겨받는다 
	public void showImage(int id) {
		//System.out.println(id+"번째 큰 이미지 원해?");
		
		bigImage = kit.getImage(dir+path[id]);
		//패널을 다시 그리게 유도하자!!
		p_canvas.repaint();
	}
	
	public static void main(String[] args) {
		new ThumbnailGallery();

	}

}





