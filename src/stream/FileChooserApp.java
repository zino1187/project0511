package stream;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

//복사 프로그램의 경로를 일일이 수작업으로 입력하기 힘들어서
//개선해 줄수 잇는 방법 및 원리를 배운다!!
public class FileChooserApp extends JFrame{
	JButton bt,bt2;
	JFileChooser chooser;
	
	public FileChooserApp() {
		setLayout(new FlowLayout());
		
		add(bt = new JButton("열기"));
		add(bt2 = new JButton("저장"));
		
		chooser = new JFileChooser();//인스턴스를 미리 만들어놓음
		
		setSize(300,120);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//파일탐색기를 띄워본다!!
				openFile();
			}
		});
		
		//저장버튼 누르면..
		bt2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//파일탐색기를 띄워본다!!
				saveFile();
			}
		});
		
	}
	public void openFile() {
		int choose = chooser.showOpenDialog(this);
		
		//파일 탐색기에 대한 유저의 선택결과를 주사해본다!!
		//열기는 0, 취소는 1: 직관적이지 못하므로, 숫자를 암기하기 보다는 
		//차라리 이미 정해진 상수를 이용하면 직관적이다!!
		System.out.println(choose); //확인, 취소 버튼의 결과 출력!
		if(choose==JFileChooser.APPROVE_OPTION){
			System.out.println("파일 선택했군요");
			
			//유저가 선택한 파일정보 얻기!! 
			//파일 선택 정보는 당연히 파일추저가 알고있다.따라서 메서드를 조사
			//해보자!!
			File file = chooser.getSelectedFile();
			
			//사용자가 선택한 파일의 풀 경로!!
			String selectedPath = file.getAbsolutePath();
			System.out.println("당신이 선택한 파일은 "+selectedPath);
			
		}else if(choose==JFileChooser.CANCEL_OPTION) {
			System.out.println("취소했군요");
		}
	}
	
	//저장용 탐색기를 띄우자!!
	public void saveFile() {
		chooser.showSaveDialog(this);
	}
	public static void main(String[] args) {
		new FileChooserApp();
	}
}








