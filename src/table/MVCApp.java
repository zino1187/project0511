package table;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/*
 JTable에서 만일 현재 보고 있는 테이블을 교체할때는 가장 효과적으로
 코드를 수정하려면 어떻게 하는가? 
 즉 보여질 데이터가 다를때, 과연 기존 소스를 버려야 하는가?
 결론) 데이터가 바뀐다고 하여 기존 코드를 버린다면 엄청난 비용의 손해이자
 시간적 손실도 크다!!
 해결책) 데이터와 디자인을 철저히 분리시켜서 개발하라!!
           이 원칙을 따르는  개발방법을 가리켜 MVC패턴이라 한다!!
 참고) JTable은 MVC패턴을 어느정도 반영한 컴포넌트이다!!
 실습) emp, dept, student 등 데이터를 교체시
          현재의 클래스가 큰 영향을 받지 않는 코드 기법을 배워보도록
          한다                     
*/
public class MVCApp extends JFrame{
	JTable table;
	JScrollPane scroll;
	TableModel model;
	
	public MVCApp() {
		//TableModel 이란 객체는 JTable 이 보여줘야 할 데이터와, 
		//Table 디자인을 분리시켜주는 역할을 수행!!
		//주의) 명칭은 모델이 들어가지만, 역할로 본다면 Controller이다.
		//(by zino)
		table = new JTable(model = new DeptModel());
		scroll = new JScrollPane(table);
		
		//setLayout(new FlowLayout());
		add(scroll);
		setSize(600,400);
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeDB();
				System.exit(0);
			}
		});
	}
	
	public void closeDB() {
		
	}
	
	public static void main(String[] args) {
		new MVCApp();
	}
}




