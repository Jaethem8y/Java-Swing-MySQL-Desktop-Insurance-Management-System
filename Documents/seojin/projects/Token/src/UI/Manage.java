package UI;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
public class Manage extends JFrame{
	Vector<String>col = new Vector<String>();
	Vector<Vector<Object>>row = new Vector<Vector<Object>>();
	Vector<Vector<String>>meal = new Vector<Vector<String>>();
	DefaultTableModel model;
	JTable jt;
	JLabel kind = new JLabel("Cuisine");
	String name [] = {"Korean","Chinese","Japanes","Western"};
	JComboBox<String> cuisine = new JComboBox<String>(name);
	JButton btn[] = new JButton[5];
	String pick;
	int cuisineNo=1;
	public Manage() {
		setTitle("Manage");
		JPanel a = new JPanel();
		makeComponent();
		a.add(kind);
		a.add(cuisine);
		for(int i=0;i<btn.length;i++) {
			a.add(btn[i]);
		}
		add(a,BorderLayout.NORTH);
		getVector();
		makeComponent();
		jt = new JTable(model);
		JScrollPane jps = new JScrollPane(jt);
		add(jps,BorderLayout.CENTER);
		setSize(800,500);
		setVisible(true);
	}
	public void makeComponent() {
		String names[] = {"search","edit","delete","todaysMeal","exit"};
		for(int i=0;i<names.length;i++) {
			btn[i] = new JButton(names[i]);
			btn[i].addActionListener(new MyActionListener());
		}
	}
	class MyActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			switch(command) {
			case "search":
				search();
				break;
			case "edit":
				edit();
				break;
			case "delete":
				delete();
				break;
			case "todaysMeal":
				todayMeal();
				break;
			case "exit":
				new GeneralForm();
				break;
			}
		}
	}
	public void todayMeal() {
		int count = 0;
		for(int i=0;i<row.size();i++) {
			if(row.elementAt(i).elementAt(4).equals("Y")) {
				count++;
			}
		}
		if(count>=25) {
			JOptionPane.showMessageDialog(null, "Can't exceed 25","Message",JOptionPane.ERROR_MESSAGE);
		}else {
			int re = jt.getSelectedRow();
			String name = (String)row.elementAt(re).elementAt(1);
			Connection conn = DB.Connect.makeConnection("foodcourt");
			try {
				Statement st = conn.createStatement();
				st.executeUpdate("update meal set todayMeal = 1 where mealName = '"+name+"'");
				row.clear();
				getVector();
				jt.updateUI();
				JOptionPane.showMessageDialog(null, "Changed","message",JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	public void edit() {
		int re =jt.getSelectedRow();
		int na = (int) cuisine.getSelectedIndex();
		int count =0;
		String name = (String) row.elementAt(re).elementAt(1);
		for(int i=0;i<row.size();i++) {
			if((boolean)row.elementAt(i).elementAt(0)==true) {
				count++;
			}
		}
		if(count>1) {
			JOptionPane.showMessageDialog(null, "One at a time please","Message",JOptionPane.ERROR_MESSAGE);
			
		}else if(re == -1) {
			JOptionPane.showMessageDialog(null, "Pick a menu","Message",JOptionPane.ERROR_MESSAGE);
		}else {
			new Edit(name,na);
			row.clear();
			getVector();
			jt.updateUI();
		}
	}
	class Edit extends JFrame{
		JLabel label[] = new JLabel[4];
		JButton editor = new JButton("edit");
		JButton ex = new JButton("exit");
		JComboBox<String>ki;
		JComboBox<Integer>pri;
		JComboBox<Integer>am;
		Vector<String>kindv = new Vector<String>();
		Vector<Integer>pricev = new Vector<Integer>();
		Vector<Integer>amountv = new Vector<Integer>();
		String name;
		int cuisine;
		JTextField menu = new JTextField(10);
		public Edit(String a,int b) {
			setTitle("edit menu");
			name = a;
			cuisine = b;
			makeEditComp();
			setLayout(new GridLayout(5,2));
			add(label[0]);
			add(ki);
			add(label[1]);
			add(menu);
			add(label[2]);
			add(pri);
			add(label[3]);
			add(am);
			add(editor);
			add(ex);
			setSize(300,300);
			setVisible(true);
			
		}
		public void makeEditComp() {
			String namei[] = {"kind","*menu","price","maxAmount",};
			for(int i=0;i<namei.length;i++) {
				label[i] = new JLabel(namei[i]);
			}
			for(int i=1000;i<=12000;i+=500) {
				pricev.add(i);
			}
			for(int i=0;i<=50;i++) {
				amountv.add(i);
			}
			kindv.add("Korean");
			kindv.add("Chinese");
			kindv.add("Japanese");
			kindv.add("Western");
			menu.setText(name);
			menu.setEnabled(false);
			ki = new JComboBox<String>(kindv);
			pri = new JComboBox<Integer>(pricev);
			am = new JComboBox<Integer>(amountv);
			ki.setSelectedIndex(cuisine);
			ki.setEnabled(false);
			editor.addActionListener(new Listener());
			ex.addActionListener(new Listener());
		}
		class Listener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getActionCommand().equals("exit")) {
					dispose();
				}else {
				
					int price = (int) pri.getSelectedItem();
					int max = (int) am.getSelectedItem();
					Connection conn = DB.Connect.makeConnection("foodcourt");
					try {
						Statement st = conn.createStatement();
						st.executeUpdate("update meal set price = "+price+", maxCount = "+max+" where mealName = '"+name+"'");
						System.out.println("done");
						dispose();
						row.clear();
						getVector();
						jt.updateUI();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
			
		}
		
	}
	public void search() {
		cuisineNo = cuisine.getSelectedIndex()+1;
		row.clear();
		getVector();
		jt.updateUI();
	}
	public void delete() {
		int re = jt.getSelectedRow();
		String del = (String) row.elementAt(re).elementAt(1);
		Connection conn = DB.Connect.makeConnection("foodcourt");
		Statement st;
		try {
			st = conn.createStatement();
			st.executeUpdate("delete from meal where mealName = '"+del+"'");
			row.clear();
			getVector();
			jt.updateUI();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void getVector() {
		Connection conn = DB.Connect.makeConnection("foodcourt");
		col.add("");col.add("menuName");col.add("price");col.add("maxCount");col.add("todayMeal");
		try {
			Statement st = conn.createStatement();
			ResultSet re = st.executeQuery("select * from meal where cuisineNo="+cuisineNo);
			while(re.next()) {
				Vector<Object>a = new Vector<Object>();
				a.add(false);a.add(re.getString("mealName"));a.add(re.getString("price"));a.add(re.getString("maxCount"));
				try {
					if(re.getString("todayMeal").equals("1")) {
						a.add("Y");
					}else {
						a.add("N");
					}
					row.add(a);
				}catch(Exception e) {
					Connection con = DB.Connect.makeConnection("foodcourt");
					Statement sto = con.createStatement();
					sto.executeUpdate("update meal set todayMeal = 0 where mealName ='"+re.getString("mealName")+"'");
					a.add("N");
					row.add(a);
				}
				
			}
			model = new DefaultTableModel(row,col) {
				public Class<?> getColumnClass(int columnIndex){
					if(columnIndex == 0) {
						return Boolean.class;
					}else {
						return String.class;
					}
				}
			};
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public static void main(String[] args) {
//		new Manage();
//	}
	
}














