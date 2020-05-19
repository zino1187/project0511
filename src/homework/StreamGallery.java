package homework;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class StreamGallery extends JFrame implements ActionListener{
	JPanel p_west;
	JPanel p_copy;
	JPanel p_canvas;
	JPanel p_container;
	
	JTextField t_ori;
	JTextField t_dest;
	JButton bt_ori;
	JButton bt_dest;
	JButton bt_copy;
	
	/*스트림 보유
	 *스트림의 유형을 기준에 따라 분류해 본다 
	 1) 방향을 기준
	   	입력(Input) : 실행중인 프로그램으로 데이터가 들어올때
	   	출력(Output): 실행중인 프로그램에서 데이터가 나갈때
	  	IO
	  	
	 2)데이터 처리 방식 
	    -바이트기반 스트림: 1byte씩 처리하고 이해함
	    -문자기반 스트림 : 2byte 를 묶어서 이해할 수 있는 능력
	    -버퍼기반 스트림 : 한줄씩 입,출력을 처리
	 */
	JFileChooser chooser;
	//문자기반의 파일이 없기 때문에 바이트기반 스트림을 이용하자
	FileInputStream fis;
	FileOutputStream fos;
	Border border;
	Toolkit kit=Toolkit.getDefaultToolkit();//static 메서드!!
	Image detailImg;//큰 이미지
	String destName;//방금 생성된 복사본의 경로
	
	public StreamGallery() {
		p_west = new JPanel();
		p_copy = new JPanel();
		
		p_canvas = new JPanel(){
			public void paint(Graphics g) {
				g.drawImage(detailImg, 0, 0, 400, 300, this);
			}
		};
		//재사용성이 떨어지거나, 피곤할때..
		p_container = new JPanel(); 
		t_ori = new JTextField(25);
		t_dest = new JTextField(25);
		
		bt_ori = new JButton("열기");
		bt_dest = new JButton("저장");
		bt_copy = new JButton("복사");
		
		chooser = new JFileChooser("D:/web_app/js_workspace/images");
		
		//보더생성 
		border = BorderFactory.createLineBorder(Color.RED, 5, false);
		
		//스타일 적용 
		p_west.setPreferredSize(new Dimension(100, 500));
		p_west.setBackground(Color.YELLOW);
		
		p_copy.setPreferredSize(new Dimension(400, 150));
		p_copy.setBackground(Color.GREEN);
		
		p_canvas.setPreferredSize(new Dimension(400, 300));
		p_canvas.setBackground(Color.WHITE);
		
		p_container.setBackground(Color.WHITE);

		
		//카피영역에 조립 
		p_copy.add(t_ori);
		p_copy.add(bt_ori);
		
		p_copy.add(t_dest);
		p_copy.add(bt_dest);		
		p_copy.add(bt_copy);
		
		//프레임 서쪽에 패널 부착 
		add(p_west, BorderLayout.WEST);
		
		//컨테이너 패널에 카피패널, 상세이미지패널 부착 
		p_container.add(p_copy);
		p_container.add(p_canvas);
		
		//프레임의 센터에  p_container부착 
		add(p_container);
		
		setSize(600,550);
		setVisible(true);
		setLocationRelativeTo(null);//스크린의 가운데로 오게함
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeStream();
			}
		});
		
		//버튼들과 리스너 연결!!
		bt_ori.addActionListener(this);
		bt_dest.addActionListener(this);
		bt_copy.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		/*자바에서는 클릭을 Action이라 하고, Action과 모든 이벤트 
		 * 정보는 JVM에 의해 ActionEvent 객체로 전달된다*/
		Object obj=e.getSource();//이벤트 일으킨 컴포넌트를 반환!!
		JButton btn = (JButton)obj;
		
		if(btn.equals(bt_ori)) {
			System.out.println("열기 눌렀어?");
			open();
		}else if(btn.equals(bt_dest)) {
			System.out.println("저장 눌렀어?");
			save();
		}else if(btn.equals(bt_copy)) {
			System.out.println("복사 눌렀어?");
			copy();
		}
	}
	
	public void open() {
		int result=chooser.showOpenDialog(this);
		if(result == JFileChooser.APPROVE_OPTION) {
			//사용자가 선택한 파일을 얻어오자!!
			File file = chooser.getSelectedFile();
			//스트림 열고, 경로도 텍스트필드에 출력!!
			String ori_path=file.getAbsolutePath();
			t_ori.setText(ori_path);//경로 출력!!
			//스트림 생성 
			try {
				fis = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void copy() {
		
		byte[] buff=new byte[1024];//1kbyte
		
		//파일의 데이터를 들이마시자!!
		int data=-1;
		
		try {
			while(true){
				//data=fis.read(); 읽어들인 데이터가 1알갱이기 
				//때문에  data변수에 들어간다!
				
				//하지만 아래와 같이 배열로 퍼먹을땐 읽어들인 데이터가
				//data가 변수가 아닌  buff배열에 채워진다!!
				//그럼  data변수의 용도는? 파일의 끝에 도달햇는지체크
				data=fis.read(buff);//한번의 read()시 1024개의 바이트를
				//퍼먹는다!!
				if(data==-1)break;
				//퍼먹은걸 다시 내뱉자
				fos.write(buff);
			}
			JOptionPane.showMessageDialog(this, "복사완료");
			
			//라벨을 동적으로 생성하여 서쪽에 추가!!
			String destName=t_dest.getText();//방금 생성된 파일경로
			createThumb(destName);
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			closeStream();
		}
	}
	
	public void save() {
		int result = chooser.showSaveDialog(this);
		
		if(result ==JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			//출력스트림 생성!!
			try {
				fos = new FileOutputStream(file);
				//경로 출력!!
				t_dest.setText(file.getAbsolutePath());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}		
	}
	
	//열려있는 스트림을 닫는 메서드 
	public void closeStream() {
		//열려있는 모든 스트림을 해제!!, 단 스트림이 존재할때만..
		if(fis!=null) {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(fos!=null) {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void createThumb(String destName) {
		this.destName=destName;
		
		//아이콘 생성하여 라벨에 넣기
		ImageIcon icon = new ImageIcon(destName);
		JLabel thumb = new JLabel(resizeIcon(icon,75,65));
		thumb.setPreferredSize(new Dimension(80, 70));
		thumb.setBorder(border);
		
		//서쪽에 부착!!
		p_west.add(thumb);//패널에 라벨을 부착!! updateUI();
		p_west.updateUI();
		
		//repaint() 메서드는 그래픽처리로 그림을 그렸을때 다시 그려달라는요청
		//paint메서드로 그래픽작업을때만 사용!!
		//updateUI() 메서드는 컴포넌트 갱신요청
		thumb.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				 showDetail();
			}
		});
	}
	
	//기존의 아이콘을 내가 원하는 크기로 변경 후 반환해주는 메서드
	//변환시키기를 원하는 아이콘을 매개변수로 넘겨주면 된다!!
	public ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
		Image scaledImage = icon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(scaledImage);
	}
	
	//상세이미지 나오게 처리하기 
	public void showDetail() {
		//선택한 썸네일과 같은 경로에 잇는 이미지 출력!!
		//p_canvas가 그릴 이미지를 생성하여, 다시 그리게 한다!~!
		detailImg = kit.getImage(destName);
		p_canvas.repaint();		
	}
	
	public static void main(String[] args) {
		new StreamGallery();
	}
}





