package UI;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class Lookup extends JFrame{
	JLabel lab = new JLabel("Menu:");
	JTextField menut = new JTextField(10);
	JButton btn[] = new JButton[4];
	Vector<Vector<String>>orderlist = new Vector<Vector<String>>();
	Vector<Vector<String>>member = new Vector<Vector<String>>();
	Vector<Vector<String>>meal = new Vector<Vector<String>>();
	Vector<Vector<String>>cuisine = new Vector<Vector<String>>();
	Vector<String>col = new Vector<String>();
	Vector<Vector<String>>data = new Vector<Vector<String>>();
	DefaultTableModel model;
	JTable jt;
	
	public Lookup() {
		setTitle("Payment Lookup");
		setComponent();
		refresh();
		JPanel a= new JPanel();
		JScrollPane jps = new JScrollPane(jt);
		JPanel b = new JPanel();
		b.add(lab);b.add(menut);
		for(int i=0;i<btn.length;i++) {
			b.add(btn[i]);
		}
		add(b,BorderLayout.NORTH);
		add(jps,BorderLayout.CENTER);
		setSize(800,800);
		setVisible(true);


		
	}
	public void setComponent() {
		String name[] = {"lookup","refresh","print","exit"}; 
		String names[] = {"종류","메뉴명","사원명","결제수량","총결제금액","결제일"};
		for(int i=0;i<names.length;i++) {
			col.add(names[i]);
		}
		model = new DefaultTableModel(data,col) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jt = new JTable(model);
		for(int i=0;i<btn.length;i++) {
			btn[i] = new JButton(name[i]);
			btn[i].addActionListener(new MyActionListener());
		}
	}
	public void refresh() {
		data.clear();
		menut.setText("");
		Connection conn = DB.Connect.makeConnection("foodcourt");
		try {
			Statement st = conn.createStatement();
			ResultSet re = st.executeQuery("select * from cuisine");
			while(re.next()) {
				Vector<String>a = new Vector<String>();
				a.add(re.getString("cuisineNo"));a.add(re.getString("cuisineName"));
				cuisine.add(a);
			}
			re = st.executeQuery("select * from meal");
			while(re.next()) {
				Vector<String>a = new Vector<String>();
				a.add(re.getString("mealNo"));a.add(re.getString("cuisineNo"));a.add(re.getString("mealName"));a.add(re.getString("price"));a.add(re.getString("maxCount"));a.add(re.getString("todayMeal"));
				meal.add(a);
			}
			re = st.executeQuery("select * from member");
			while(re.next()) {
				Vector<String>a = new Vector<String>();
				a.add(re.getString("memberNo"));a.add(re.getString("memberName"));a.add(re.getString("passWd"));
				member.add(a);
			}
			re = st.executeQuery("select * from orderlist");
			while(re.next()) {
				Vector<String>a = new Vector<String>();
				a.add(re.getString("orderNo"));a.add(re.getString("cuisineNo"));a.add(re.getString("mealNo"));a.add(re.getString("memberNo"));a.add(re.getString("orderCount"));a.add(re.getString("amount"));a.add(re.getString("orderDate"));
				orderlist.add(a);
			}
			for(int i=0;i<orderlist.size();i++) {
				String kind =orderlist.elementAt(i).elementAt(1);
				String menu = orderlist.elementAt(i).elementAt(2);
				String name = orderlist.elementAt(i).elementAt(3);
				String amount = orderlist.elementAt(i).elementAt(4);
				String price = orderlist.elementAt(i).elementAt(5);
				String date = orderlist.elementAt(i).elementAt(6);
				for(int j=0;j<cuisine.size();j++) {
					if(kind.equals(cuisine.elementAt(j).elementAt(0))) {
						kind = cuisine.elementAt(j).elementAt(1);
					}
				}
				for(int j=0;j<meal.size();j++) {
					if(menu.equals(meal.elementAt(j).elementAt(0))) {
						menu = meal.elementAt(j).elementAt(2);
					}
				}
				for(int j=0;j<member.size();j++) {
					if(name.equals(member.elementAt(j).elementAt(0))) {
						name = member.elementAt(j).elementAt(1);
					}
				}
				Vector<String>b = new Vector<String>();
				b.add(kind);b.add(menu);b.add(name);b.add(amount);b.add(price);b.add(date);
				data.add(b);
				
			}
			System.out.println(data);
			jt.updateUI();
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void lookup() {
//		data.clear();
		String search = menut.getText();
		Vector<Vector<String>>b = new Vector<Vector<String>>();
		Vector<String>a = new Vector<String>();
		Connection conn = DB.Connect.makeConnection("foodcourt");
		try {
			Statement st = conn.createStatement();
			ResultSet re = st.executeQuery("select mealName from meal where mealName like '%"+search+"%'");
			while(re.next()) {
				a.add(re.getString("mealName"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		
		for(int j=0;j<a.size();j++) {
			for(int i=0;i<data.size();i++) {
				if(a.elementAt(j).equals(data.elementAt(i).elementAt(1))) {
//					System.out.println(data.elementAt(i).elementAt(1));
					b.add(data.elementAt(i));
				}
			}
		}
	
		data.clear();
//		System.out.println(data);
		System.out.println("-------------------");
		for(int i=0;i<b.size();i++) {
			Vector<String>k = new Vector<String>();
			for(int j=0;j<6;j++) {
				k.add(b.elementAt(i).elementAt(j));
			}
			data.add(k);
		}
		System.out.println(data);
		
		jt.updateUI();
		
		
	}
	class MyActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			switch(command) {
			case "lookup":
				lookup();
				break;
			case "refresh":
				refresh();
				jt.updateUI();
				break;
			case "print":
				try {
					jt.print(JTable.PrintMode.NORMAL);
				} catch (PrinterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case "exit":
				dispose();
				new GeneralForm();
				break;
			}
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Lookup();
	}

}
