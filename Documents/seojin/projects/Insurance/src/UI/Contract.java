package UI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.text.*;
public class Contract extends JFrame{
	JLabel lab[] = new JLabel[8];
	JTextField[] fields = new JTextField[5];
	JButton btn[] = new JButton[4];
	Vector<String>customer = new Vector<String>();
	Vector<String>manager = new Vector<String>();
	Vector<String>product = new Vector<String>();
	JComboBox<String> custom;
	JComboBox<String> manage;
	JComboBox<String> prod;
	JPanel pa = new JPanel();
	Vector<String>col = new Vector<String>();
	Vector<Vector<String>>row = new Vector<Vector<String>>();
	JTable jt;
	
	
	public Contract() {
		setTitle("Insurance Contract");
		makeLabel();
		makeButton();
		makeVectors();
		makeFields();
		custom.addActionListener(new MyActionListener());
		JPanel p = new JPanel();
		p.add(new Left());
		p.add(new Right());
		JPanel pa = new JPanel();
		pa.add(lab[7]);
		pa.add(manage);
		for(int i=0;i<4;i++) {
			pa.add(btn[i]);
		}
		createCol();
		JScrollPane jps = new JScrollPane(jt);
		JPanel a = new JPanel();
		a.setLayout(new BorderLayout());
		a.add(p,BorderLayout.NORTH);
		a.add(pa,BorderLayout.CENTER);
		JPanel power = new JPanel();
		JLabel oof = new JLabel("<Customer Contract Status>");
		power.add(oof);
		setVisible(true);
		setSize(400,800);
		add(a,BorderLayout.NORTH);
		add(power,BorderLayout.CENTER);
		add(jps,BorderLayout.SOUTH);
		setSize(800,650);
		setVisible(true);	
		openSet();
	}
	


	public void createCol() {
		String[] names = {
				"customerCode","contractName","regPrice","regDate","monthPrice","adminName",
		};
		for(int i=0;i<6;i++) {
			col.add(names[i]);
		}
		DefaultTableModel model = new DefaultTableModel(row,col);
		jt = new JTable(model);
	}
	
	class MyActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String s = (String)custom.getSelectedItem();
			getInfo(s);
			display();
		}
		
	}
	
	public void display() {
		row.clear();
		Connection conn = DB.Connect.makeConnection("insurance");
		try {
			Statement st = conn.createStatement();
			String sql = "select * from contract where customerCode = '"+fields[0].getText()+"' order by regDate desc";
			System.out.println(fields[0].getText());
			ResultSet re = st.executeQuery(sql);
			String[] names = {
					"customerCode","contractName","regPrice","regDate","monthPrice","adminName",
			};
			while(re.next()) {
				Vector<String>a = new Vector<String>();
				for(int i=0;i<6;i++) {
					a.add(re.getString(names[i]));
				}
				row.add(a);
			}
			jt.updateUI();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void openSet() {
		String s = (String)custom.getSelectedItem();
		getInfo(s);
		display();
	}
	
	public void getInfo(String s) {
		Connection conn = DB.Connect.makeConnection("Insurance");
		try {
			Statement st = conn.createStatement();
			String sql = "select * from customer where name = '"+s+"'";
			ResultSet re = st.executeQuery(sql);
			while(re.next()) {
				System.out.println(re.getString("code"));
				fields[0].setText(re.getString("code"));
				fields[1].setText(re.getString("birth"));
				fields[2].setText(re.getString("tel"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void makeFields() {
		for(int i=0;i<5;i++) {
			fields[i] = new JTextField(15);
		}
		for(int i=0;i<3;i++) {
			fields[i].setEnabled(false);
		}
	}
	public void makeVectors() {
		Connection conn = DB.Connect.makeConnection("insurance");
		try {
			Statement st = conn.createStatement();
			String command = "select name from customer";
			ResultSet re = st.executeQuery(command);
			while(re.next()) {
				customer.add(re.getString("name"));
			}
			custom = new JComboBox<String>(customer);
			command = "select name from admin";
			re = st.executeQuery(command);
			while(re.next()) {
				manager.add(re.getString("name"));
			}
			manage = new JComboBox<String>(manager);
			command = "select distinct contractName from contract;";
			re = st.executeQuery(command);
			while(re.next()) {
				product.add(re.getString("contractName"));
				System.out.println(re.getString("contractName"));
			}
			prod = new JComboBox<String>(product);
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void makeLabel() {
		String[] names = {
				"Customer Code","Customer Name","Birth Date","Contact","Insurance Product","Cost Amount","Monthly Subscription","Manager : ",
		};
		for(int i=0;i<8;i++) {
			lab[i] = new JLabel(names[i]);
		}
	}
	public void makeButton() {
		String[] names = {
				"Register","Delete","Save in file","close",
		};
		for(int i=0;i<4;i++) {
			btn[i] = new JButton(names[i]);
			btn[i].addActionListener(new BtnListener());
		}
	}
	class BtnListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			switch(command){
				case "Register":
					register();
					display();
					break;
				case "Delete":
					delete();
					display();
					break;
				case "Save in file":
					file();
					JOptionPane.showMessageDialog(null, "File has been made at File folder!","Message",JOptionPane.INFORMATION_MESSAGE);
					break;
				case "close":
					dispose();
					break;
			}
		}
		public void file() {
			try {
				FileWriter fout = new FileWriter("/Users/Jaehyeokchoi/Desktop/file/Contract_Status.txt");
				String top = "Customer Name: "+(String)custom.getSelectedItem()+"\n \n";
				String center = "Manager Name: "+(String)manage.getSelectedItem()+"\n \n";
				String target = "Product\tCost Amount\tregDate\tMonthly Subscription\n";
				fout.write(top);fout.write(center);fout.write(target);
				for(int i=0;i<row.size();i++) {
					Vector<String>a = row.elementAt(i);
					String writer = "";
					for(int j=1;j<5;j++) {
						writer = writer + a.elementAt(j)+"\t";
					}
					fout.write(writer+"\n");
				}
				fout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void delete() {
			int re = jt.getSelectedRow();
			Vector<String>a = row.elementAt(re);
			int result = JOptionPane.showConfirmDialog(null, "Sure to delete "+a.elementAt(0) + "("+a.elementAt(1)+")","Confirm",JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION) {
				Connection conn = DB.Connect.makeConnection("insurance");
				String sql = "delete from contract where customerCode = ? and contractName = ? and regPrice = ? and regDate = ? and monthPrice =? and adminName =?";
				try {
					PreparedStatement psmt = conn.prepareStatement(sql);
					for(int i=0;i<6;i++) {
						psmt.setString(i+1, a.elementAt(i));
					}
					psmt.executeUpdate();
					JOptionPane.showMessageDialog(null, "Deleted","Message",JOptionPane.INFORMATION_MESSAGE);
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
			
		}
		public void register() {
			String query[] = new String[6];
			query[0] = fields[0].getText(); query[1] = (String)prod.getSelectedItem(); query[2] = fields[3].getText();
			Date date = Calendar.getInstance().getTime();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			query[3] = dateFormat.format(date);			
			query[4] = fields[4].getText();
			query[5] = (String)manage.getSelectedItem();
			
			Connection conn = DB.Connect.makeConnection("insurance");
			String sql = "insert into contract values(?,?,?,?,?,?)";
			try {
				PreparedStatement psmt = conn.prepareStatement(sql);
				for(int i=0;i<6;i++) {
					psmt.setString(i+1, query[i]);
				}
				psmt.executeUpdate();
				JOptionPane.showMessageDialog(null,"Registered","Message",JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	class Left extends JPanel{
		public Left() {
			setLayout(new GridLayout(4,2));
			add(lab[0]);
			add(fields[0]);
			add(lab[1]);
			add(custom);
			add(lab[2]);
			add(fields[1]);
			add(lab[3]);
			add(fields[2]);
			setVisible(true);
		}
	}
	
	class Right extends JPanel{
		public Right() {
			setLayout(new GridLayout(3,2));
			add(lab[4]);
			add(prod);
			add(lab[5]);
			add(fields[3]);
			add(lab[6]);
			add(fields[4]);
			setVisible(true);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Contract();
	}

}
