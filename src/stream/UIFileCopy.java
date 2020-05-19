/*과제 풀이
 * 
 * -----------------------------------------
 * IO 란? 입력과 출력
 * -----------------------------------------
 * 입력과 출력은 실행중인 프로그램을 기준으로 한다
 * 입력: 실행중인 프로그램으로 데이터가 들어오는 것
 * 출력: 실행중인 프로그램에서 데이터가 나가는 것
 * 
 * -----------------------------------------
 * Exception: 정상적이지 않은 예외 상황
 * -----------------------------------------
 * 예외상황의 유형 : 
 * 에러 : error 개발자가 감당할 수 없음
 * 예외 : 프로그래밍적으로 처리가 가능한 경우
 * 목적 : 비정상 종료를 방지 	
 * */
package stream;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class UIFileCopy extends JFrame{
	JTextField t_ori, t_dest;
	JButton bt_open, bt_save, bt_copy;
	FileInputStream fis;
	FileOutputStream fos;
	JFileChooser chooser; //탐색기
	
	public UIFileCopy() {
		t_ori = new JTextField(30);
		t_dest = new JTextField(30);
		
		bt_open = new JButton("열기");
		bt_save = new JButton("저장");
		bt_copy = new JButton("복사실행");
		
		chooser = new JFileChooser("D:/web_app/js_workspace/images");
		
		setLayout(new FlowLayout());
		
		add(t_ori);
		add(bt_open);
		add(t_dest);
		add(bt_save);
		add(bt_copy);
		
		setSize(430,150);
		setVisible(true);
		
		bt_open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				open();
			}
		});
		bt_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		bt_copy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copy();
			}
		});
		
	}
	
	public void open(){
		//parent 라는 단어는 부모클래스가 아니라, 부모 컨테이너를 말함
		if(chooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION) {
			//선택한 파일의 정보 가져오기!!
			File file=chooser.getSelectedFile();
			
			
			//경로 구해서 텍스트박스에 넣기
			String ori=file.getAbsolutePath();
			t_ori.setText(ori);
			
			
		}
	};
	
	public void save(){
		int result = chooser.showSaveDialog(this);
		
		if(result == JFileChooser.APPROVE_OPTION) {
			
			File file=chooser.getSelectedFile();
			
			System.out.println(file.getAbsolutePath());
			
			String dest =file.getAbsolutePath(); 
			t_dest.setText(dest);
		}
	};
	
	public void copy(){
		// 복사란 : 입력스트림으로 읽어들인 데이터를 다시 출력스트림으로
		//           출력하는 행위!!
		try {
			fis = new FileInputStream(t_ori.getText());
			fos= new FileOutputStream(t_dest.getText());
			
			//읽기 
			int data=-1;//읽은게 없음
			while(true) {
				data=fis.read(); //1byte 읽기
				if(data==-1)break;
				//쓰기 
				fos.write(data);//1byte 쓰기
			}
			
			JOptionPane.showMessageDialog(this,"복사완료");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			//일단 try나  catch 에 들어선 실행부는  finally를 피해갈수
			//없다!! 톨게이트와 같다!!
			//열려있는 스트림, 데이터베이스와 접속은 무조건 닫아주자!!
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
		
	};
	
	public static void main(String[] args) {
		new UIFileCopy();
	}

}




