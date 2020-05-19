/*
 메모장을 구현한다 : 파일읽기,저장...
*/
package stream;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MemoApp extends JFrame{
	JMenuBar bar;
	JMenu m_file;
	JMenuItem item_open, item_save,item_exit;
	JTextArea area;
	JScrollPane scroll;
	JFileChooser chooser;
	
	FileInputStream fis;//1byte 단위로 이해함(영문만 가능)
	InputStreamReader reader;//문자기반 스트림 2byte씩 묶은
											//문자를 이해하는 스트림(모든 문자가능)
	BufferedReader buffr;//버퍼처리된 문자기반 스트림
	
	FileOutputStream fos;
	
	public MemoApp() {
		setJMenuBar(bar=new JMenuBar());
		bar.setBackground(Color.BLACK);
		m_file = new JMenu("파일");
		item_open = new JMenuItem("열기");
		item_save 	= new JMenuItem("저장");
		item_exit 	= new JMenuItem("종료");
		chooser = new JFileChooser("D:/web_app/java_workspace");
		
		m_file.add(item_open);
		m_file.add(item_save);
		m_file.addSeparator();//구분선!!
		m_file.add(item_exit);
		
		m_file.setForeground(Color.WHITE);
		m_file.setPreferredSize(new Dimension(100, 40));
		area = new JTextArea();
		scroll = new JScrollPane(area);
		
		area.setBackground(Color.YELLOW);
		
		bar.add(m_file);
		add(scroll);
		
		setSize(600,500);
		setVisible(true);
		
		//아이템들과 리스너와의 연결!!
		//재활용성이 떨어지는 1회성코드는 내부익명을 많이 사용
		item_open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				open();
			}
		});
		item_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		item_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit();	
			}
		});
	}
	
	//파일 열기!!
	public void open() {
		int result = chooser.showOpenDialog(this);
		
		if(result == JFileChooser.APPROVE_OPTION) {
			
			//사용자가 방금 선택한 파일에 대한 정보를 갖는 File 인스턴스 얻기!
			File file =chooser.getSelectedFile();
			
			//제목 출력 
			this.setTitle(file.getName());
			
			try {
				fis = new FileInputStream(file.getAbsolutePath());
				reader = new InputStreamReader(fis);//빨대 덧씌움!!
				buffr = new BufferedReader(reader);
				
				//읽어들인 데이터를  area에 출력하자!!
				String data=null;//데이터 없음으로 간주
				
				while(true){
					data = buffr.readLine();
					
					if(data==null)break; //파일의 끝에 도달하면 데이터대신 -1반환
												//하고 이때 반복문 탈출!!
					area.append(data+"\n");	
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				if(buffr!=null) {
					try {
						buffr.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public void exit() {
		System.exit(0);
	}
	public static void main(String[] args) {
		new MemoApp();
	}
}











