package graphic;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MoveImg extends JFrame{
	/*
  켄버스는 다른 컴포넌트와는 달리, 어떤 그림을 그릴지에 대한 결정을
  개발자가 주도하에 할수있다..텅빈 도화지...즉 원하는 그림 그리라고 준거다
  
  자바분야에서는 그림의 주체가 컴포넌스 스스로 이기 때문에, 자기 자신을 그린다
  이때 그리는 행동은  paint() 메서드로 동작한다!! 따라서 우리의 경우 
  켄버스가 스스로 그렸을때 아무것도 없기 때문에, 이번엔 개발자가 주체가  되어
  그림을 뺏어야 한다..따라서 Canvas의  paint 메서드를 오버라이딩!
	*/
	Canvas can;
	JPanel p_north;
	JButton bt_prev, bt_next;
	int x=0;
	int velX=0;
	
	Toolkit kit;//로컬상의 이미지를 불러오기 위한 객체
	Image img;
	
	public MoveImg() {
		p_north = new JPanel();
		bt_prev = new JButton("좌측");
		bt_next = new JButton("우측");
		
		//추상클래스는 new를 사용할수 없기 때문에, 
		//1) 자식을 정의하여 자식을 new 방법
		//2) 자체적으로 인스턴스를 얻게끔 해주는 static 메서드를 지원
		kit = Toolkit.getDefaultToolkit();//인스턴스 얻기
		img=kit.getImage("D:/web_app/java_workspace/project0506/res/pica.png");
		
		p_north.add(bt_prev);
		p_north.add(bt_next);
		add(p_north, BorderLayout.NORTH);
		
		//별도의 클래스로  Canvas 를 존재시키면, 개발의 편의성, 효율성이
		//저하되므로 이 클래스안에 두겠다!! 무조건 그러면 안되고, 상황에 맞게
		//현재의 프로젝트의 경우 Canvas 의 재활용성이 없기 때문에 이렇게 함
		
		//내부익명클래스(Annoymous Inner Class)는 바깥쪽 외부 클래스의
		//멤버를 내것처럼 접근할 수 있다
		can = new Canvas(){
			//개발자가 호출해도 안되고, 호출할수도 없다!!
			//개발자는 오직 아래의  paint메서드가 다시 호출되기를 요청만할수있다
			//repaint(다시그려달라)  --> update() --> paint()
			public void paint(Graphics g) {
				//g.drawOval(x, 200, 50, 50);
				
				//피카추 이미지를 그리자!!
				g.drawImage(img, x, 150, 50, 50, this);
				
			}
		};
		
		can.setBackground(Color.WHITE);
		add(can);
		setSize(600,550);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//버튼에 리스너 연결 
		bt_prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				velX=-5;
				tick();
				render();
			}
		});
		bt_next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				velX=5;
				tick();
				render();
			}
		});
		
	}
	public void tick() {
		x+=velX;
	}
	public void render() {
		//변화된 물리량을 이용하여 그래픽에 반영하자!!
		//화면을 싹~지우고 다시 그리게 만들자!!
		can.repaint();
		
	}
	public static void main(String[] args) {
		new MoveImg();
	}
}








