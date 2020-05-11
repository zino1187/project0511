package stream;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class FileCopyUI extends JFrame implements ActionListener{
	JTextField t_ori,t_dest;
	JButton bt;
	/*
	 * 스트림과 데이터베이스는 프로그래밍 언어에서 접근 후, 반드시 
	 * 접근을 해제시켜야 한다!!
	 * 안하면?? 메모리에 계속 남아 있게되어, 결국엔 메모리가 모자라게 된다
	 * 				메모리 누수(Memory leak)
	 * */
	FileInputStream fis;
	FileOutputStream fos;
	
	public FileCopyUI() {
		t_ori = new JTextField(25);
		t_dest = new JTextField(25);
		
		bt = new JButton("copy실행");
		setLayout(new FlowLayout());
		
		add(t_ori);
		add(t_dest);
		add(bt);
		
		//버튼과 리스너 연결!!
		bt.addActionListener(this); //내가 리스너 구현자이기 때문에..is a관계
		
		setSize(370,150);
		setVisible(true);
	}
	
	//리스너의 재정의할 추상메서드!!
	public void actionPerformed(ActionEvent e) {
		//JOptionPane.showMessageDialog(this, "나 눌렀어?");	
		copy();	
	}
	
	public void copy() {
		//t_ori  에서 원본 경로를 이용하여입력 스트림 생성 후 , 
		//데이터 읽어서 t_dest 경로에 대한 출력 스트림을 이용한 데이터 출력!!
		String ori_path = t_ori.getText();//원본경로
		String dest_path = t_dest.getText();//복사될 경로
		
		System.out.println("원본경로"+ori_path);
		System.out.println("복사될 경로"+dest_path);
		
		try {
			fis = new FileInputStream(ori_path);
			fos=new FileOutputStream(dest_path);
			
			//try문이 무사히 여기까지 수행됬다면, 에러가 안난것이므로, 
			//생성된 스트림을 이용하여 데이터를 읽어들이고, 출력해보자!!(IO)
			int data=-1; //처음엔 읽어들인 데이터가 없다고 가정!!
			
			while(true) {
				data=fis.read(); //1byte 읽어들임!!
				if(data==-1)break; //while 문 빠져나감!!
				fos.write(data);//읽어들인 데이터를 다시 출력!!
			}
			
	
		} catch (FileNotFoundException e) {
			e.printStackTrace();//개발자가 콘솔을 보고 원인을 분석...
			JOptionPane.showMessageDialog(this,"파일경로를 확인해 주세요");
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			/*try문이나  catch 문을 수행한 실행부가 반드시 거쳐야할 영역
			 * 따라서 무언가를 마무리 짓거나 자원을 해제시켜야 할 경우 
			 * finally가 사용된다!!
			 * */
			if(fis !=null) {//올바르게 수행되어 객체가 생성되었을 경우만...
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fos !=null){//즉 스트림이 생성되었을때만 닫아라!!
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		
	}
	public static void main(String[] args) {
		new FileCopyUI();

	}

}





