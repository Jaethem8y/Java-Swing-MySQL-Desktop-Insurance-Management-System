package UI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.*;
public class Lookup extends JFrame{
	
	JTable jt;
	Vector<String>col = new Vector<String>();
	Vector<Vector<String>>data = new Vector<Vector<String>>();
	String[] colName = {
			"code","name","birth","tel","address","company",
	};
	String[] btnName = {
			"Lookup","All","Update","Delete","Exit",
	};
	JButton btn[] = new JButton[5];
	JLabel na = new JLabel("name");
	JTextField name = new JTextField(10);
	Vector<String>picked = new Vector<String>();
	public Lookup() {
		setTitle("Customer Lookup");
		makeCol();
		DefaultTableModel model = new DefaultTableModel(data,col);
		jt = new JTable(model);
		JScrollPane jps = new JScrollPane(jt);
		add(new North(),BorderLayout.NORTH);
		add(jps,BorderLayout.CENTER);
		setSize(800,800);
		setVisible(true);
		
	}
	public void makeCol() {
		for(int i=0;i<6;i++) {
			col.add(colName[i]);
		}
	}
	class North extends JPanel{
		public North() {
			setLayout(new FlowLayout());
			add(na);
			add(name);
			for(int i=0;i<5;i++) {
				btn[i] = new JButton(btnName[i]);
				btn[i].addActionListener(new MyActionListener());
				add(btn[i]);
			}
		}
	}
	class MyActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String command = e.getActionCommand();
			switch(command) {
			case "Lookup":
				lookup();
				break;
			case "All":
				all();
				break;
			case "Update":
				int selection = jt.getSelectedRow();
				picked = data.get(selection);
				System.out.println("up");
				update();
				break;
			case "Delete":
				int selected = jt.getSelectedRow();
				picked = data.get(selected);
				System.out.println("up");
				delete();
				all();
				break;
			case "Exit":
				dispose();
				break;
			}
		}
	}
	public void all() {
		Connection conn = DB.Connect.makeConnection("insurance");
		String sql = "select * from customer";
		try {
			data.clear();
			jt.updateUI();
			Statement st = conn.createStatement();
			ResultSet re = st.executeQuery(sql);
			while(re.next()) {
				Vector<String>adic = new Vector<String>();
				for(int i=0;i<6;i++) {
					adic.add(re.getString(colName[i]));
					
				}
				data.add(adic);
			}
			jt.updateUI();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void lookup() {
		Connection conn = DB.Connect.makeConnection("insurance");
		String sql = "select * from customer where name like '"+name.getText()+"%'";
		try {
			data.clear();
			jt.updateUI();
			Statement st = conn.createStatement();
			ResultSet re = st.executeQuery(sql);
			while(re.next()) {
				Vector<String>adic = new Vector<String>();
				for(int i=0;i<6;i++) {
					adic.add(re.getString(colName[i]));
					
				}
				data.add(adic);
			}
			jt.updateUI();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void update() {
		new UpdateC();
	}
	public void delete() {
		int result = JOptionPane.showConfirmDialog(null, "Are you sure to delete "+picked.elementAt(1)+"?","Confirmation",JOptionPane.YES_NO_OPTION);
		if(result == JOptionPane.YES_OPTION) {
			String del = picked.elementAt(0);
			String sql = "delete from customer where code = '"+del+"'";
			Connection conn = DB.Connect.makeConnection("insurance");
			try {
				Statement st = conn.createStatement();
				st.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "Delete Complete","Message",JOptionPane.INFORMATION_MESSAGE);
		}else {
		
		}
		
	}
	
	class UpdateC extends JFrame{
		String [] labName = {
				"Customer Code","Customer Name","Birth Date","Contact","Address","Work",
		};
		JTextField field[] = new JTextField[6];
		JLabel lab[] = new JLabel[6];
		JButton update = new JButton("update");
		JButton exit = new JButton("exit");
		public UpdateC() {
			setTitle("Update");
			JPanel pan = new JPanel();
			pan.setLayout(new GridLayout(6,2));
			for(int i=0;i<6;i++) {
				field[i] = new JTextField(10);
				field[i].setText(picked.elementAt(i));
				lab[i] = new JLabel(labName[i]);
				pan.add(lab[i]);
				pan.add(field[i]);
			}
			field[0].setEnabled(false);
			field[1].setEnabled(false);
			JPanel bot = new JPanel();
			update.addActionListener(new MeActionListener());
			exit.addActionListener(new MeActionListener());
			bot.add(update);
			bot.add(exit);
			add(pan,BorderLayout.CENTER);
			add(bot,BorderLayout.SOUTH);
			setSize(500,300);
			setVisible(true);
		}
		class MeActionListener implements ActionListener{
			
			public void actionPerformed(ActionEvent e) {
				String com = e.getActionCommand();
				if(com.equals("update")) {
					if(field[2].getText().equals("")||field[3].getText().equals("")||field[3].getText().equals("")||field[4].getText().equals("")||field[5].getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Please fill out all the boxes","Error",JOptionPane.ERROR_MESSAGE);
						dispose();
					}else{
						Connection conn = DB.Connect.makeConnection("insurance");
						String sql = "update customer set birth = ?, tel =?, address = ?, company = ? where code = '"+field[0].getText()+"'";
						try {
							PreparedStatement st = conn.prepareStatement(sql);
							for(int i=0;i<4;i++) {
								st.setString(i+1, field[i+2].getText());
							}
							st.executeUpdate();
							JOptionPane.showMessageDialog(null, "Update Complete","Message",JOptionPane.INFORMATION_MESSAGE);
							dispose();
							all();

						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}else {
					dispose();
				}
			}
			
		}
	}

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		new Lookup();
//	}

}
