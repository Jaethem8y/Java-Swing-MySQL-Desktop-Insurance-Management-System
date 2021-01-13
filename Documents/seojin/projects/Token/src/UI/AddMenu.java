package UI;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class AddMenu extends JFrame {
	JLabel lab[] = new JLabel[4];
	JComboBox<String> kind;
	JComboBox<Integer> price;
	JTextField name = new JTextField(10);
	JComboBox<Integer> amount;
	JButton register = new JButton("register");
	JButton exit = new JButton("exit");
	public AddMenu() {
		setTitle("Adding new menu");
		makeComponent();
		setLayout(new GridLayout(5,2));
		add(lab[0]);
		add(kind);
		add(lab[1]);
		add(name);
		add(lab[2]);
		add(price);
		add(lab[3]);
		add(amount);
		add(register);
		add(exit);
		setVisible(true);
		setSize(400,400);
		
	}
	public void makeComponent() {
		String name [] = {"kind","*menu name","price","max-amount"};
		for(int i=0;i<name.length;i++) {
			lab[i] = new JLabel(name[i]);
		}
		Connection conn = DB.Connect.makeConnection("foodcourt");
		Vector<String>kindv = new Vector<String>();
		Vector<Integer>pricev = new Vector<Integer>();
		Vector<Integer>amountv = new Vector<Integer>();
		for(int i=1000;i<=12000;i+=500) {
			pricev.add(i);
		}
		for(int i=0;i<=50;i++) {
			amountv.add(i);
		}
		try {
			Statement st = conn.createStatement();
			ResultSet re = st.executeQuery("select cuisineName from cuisine");
			
			while(re.next()) {
				kindv.add(re.getString("cuisineName"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		kind = new JComboBox<String>(kindv);
		price = new JComboBox<Integer>(pricev);
		amount = new JComboBox<Integer>(amountv);
		register.addActionListener(new MyActionListener());
		exit.addActionListener(new MyActionListener());
		
	}
	class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("exit")) {			
				dispose();
				new GeneralForm();
			}else {
				if(name.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Check the name of the menu","Message",JOptionPane.ERROR_MESSAGE);
				}else {
					Connection conn = DB.Connect.makeConnection("foodcourt");
					int menuKind = kind.getSelectedIndex()+1;
					String menuName = name.getText();
					int dbPrice = (int)price.getSelectedItem();
					int max = (int)amount.getSelectedItem();
					try {
						PreparedStatement psmt = conn.prepareStatement("insert into meal(cuisineNo,mealName,price,maxCount) values(?,?,?,?)");
						psmt.setLong(1,menuKind);
						psmt.setString(2, menuName);
						psmt.setLong(3, dbPrice);
						psmt.setLong(4, max);
						psmt.executeUpdate();
						JOptionPane.showMessageDialog(null, "Registration Complete","Message",JOptionPane.INFORMATION_MESSAGE);
						dispose();
						new GeneralForm();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
		
	}
	
//	public static void main(String[] args) {
//		new AddMenu();
//	}
}
