package homework.table;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

public class TableSelectionApp extends JFrame{
	JPanel p_west;
	JTextField t_ip;//접속하고자 하는 db서버의 아이피주소
	Choice choice;
	JButton bt_load,bt_edit, bt_del;
	JTable table;
	JScrollPane scroll;
	int deptno; //유저가 선택한 행의 부서번호!!
	TableModel model=null;
	
	public TableSelectionApp() {
		p_west = new JPanel();
		t_ip = new JTextField("localhost");
		choice = new Choice();
		bt_load = new JButton("Load");
		bt_del = new JButton("삭제");
		bt_edit = new JButton("수정");
		
		table = new JTable();
		scroll = new JScrollPane(table);
		
		//스트일 적용 
		p_west.setBackground(Color.WHITE);
		p_west.setPreferredSize(new Dimension(120, 400));
		p_west.add(t_ip);
		p_west.add(choice);
		p_west.add(bt_load);
		p_west.add(bt_del);
		p_west.add(bt_edit);
		
		//테이블명 
		choice.add("EMP");
		choice.add("DEPT");
		choice.add("STUDENT");
		
		add(p_west, BorderLayout.WEST);
		
		add(scroll);
		
		setSize(500,400);
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});		
		
		bt_load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				load();
			}
		});
		
		bt_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				del();
			}
		});
		
		//테이블과 마우스리스너 연결 
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row=table.getSelectedRow();
				int col=table.getSelectedColumn();
				String value=(String)table.getValueAt(row, 0);
				
				deptno=Integer.parseInt(value);//클릭시마다 선택한 deptno를 보관함
				
				System.out.println("당신 선택한 row 는 "+row+",col "+col);
				
				String sql="delete from dept where deptno="+value;
				System.out.println(sql);
			}
		});
	}
	
	public void load() {
		//선택한 테이블이 출력될 수 있도록 적절한 TableModel을 선택하자!!
		//EmpTableModel, DeptTableModel, StudentTalbeModel
		
		String item = choice.getSelectedItem();
		System.out.println(t_ip.getText()+" 서버의 "+item+" 테이블 원해?");
		
		
		
		if(item.equals("EMP")) { //EmpTableModel 사용
			model=new EmpTableModel(t_ip.getText());
		}else if(item.equals("DEPT")) {
			model=new DeptTableModel(t_ip.getText());
		}else if(item.equals("STUDENT")) {
			model=new StudentTableModel(t_ip.getText());
		}
		table.setModel(model);
		table.updateUI();
	}
	
	public void del() {
		//유저가 선택한 레코드를 삭제한다!!
		//체크사항 1.선택한 레코드가 있는지 여부 2.삭제할 의지확인
		if(deptno==0) {
			JOptionPane.showMessageDialog(this, "삭제하실 레코드를 선택하세요");
			return;
		}
		
		//삭제할 자격이 생기면...
		int result=JOptionPane.showConfirmDialog(this, "삭제하시겠습니까?");
		if(result==JOptionPane.OK_OPTION) {
			System.out.println("삭제 수행~!!");
			//TableModel 의 이용하여 삭제처리!!
			//이 클래스는 디자인 영역이므로, 삭제를 처리해서는 안된다!!
			DeptTableModel deptModel=(DeptTableModel)model;
			int row=deptModel.deleteDeptById(deptno);
			if(row==0) {
				JOptionPane.showMessageDialog(this,"삭제실패");
			}else {
				JOptionPane.showMessageDialog(this,"삭제성공");
				deptModel.select();
				table.updateUI();
				
			}
			
		}
	}
	
	public static void main(String[] args) {
		new TableSelectionApp();
	}
}














