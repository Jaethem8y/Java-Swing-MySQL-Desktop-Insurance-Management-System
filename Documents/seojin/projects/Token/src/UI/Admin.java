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

public class Admin extends JFrame{
	JPasswordField password = new JPasswordField(20);
	JButton btn[] = new JButton[10];
	
	
	public Admin() {
		int result = JOptionPane.showConfirmDialog(null, new initial(),"Message",JOptionPane.YES_NO_OPTION);
		if(result == JOptionPane.NO_OPTION) {
			dispose();
		}else {
			String comp = new String(password.getPassword());
			if(comp.equals("0000")) {
				dispose();
			}
			Connection conn = DB.Connect.makeConnection("foodcourt");
			String pw = new String(password.getPassword());
			try {
				Statement st= conn.createStatement();
				ResultSet re = st.executeQuery("select passwd from member");
				int y = -1;
				while(re.next()) {
					if(re.getString("passwd").equals(pw)) {
						y = 1;
					}
				}
				if(y == 1) {
					System.out.println("yes");
					new GeneralForm();
					dispose();
				}else {
					JOptionPane.showMessageDialog(null, "Check Admin PW","Message",JOptionPane.ERROR_MESSAGE);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	class initial extends JPanel{
		
		public initial() {
			setTitle("Admin password");
			setLayout(new BorderLayout());
			password.setDocument(new JPasswordFieldLimit(4));
			for(int i=0;i<10;i++) {
				btn[i] = new JButton(Integer.toString(i));
				btn[i].addActionListener(new MyActionListener());
			}
			JPanel a = new JPanel();
			a.setLayout(new GridLayout(4,3));
			for(int i=1;i<10;i++) {
				a.add(btn[i]);
			}
			a.add(btn[0]);
			JPanel b = new JPanel();
		
			JPanel c = new JPanel();
			c.add(password);
			add(c,BorderLayout.NORTH);
			add(a,BorderLayout.CENTER);
			add(b,BorderLayout.SOUTH);
			setVisible(true);
			setSize(300,300);
			
		}
	}

	class MyActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			String pass = new String(password.getPassword());
			password.setText(pass+e.getActionCommand());
			
		}
		
	}
	public class JPasswordFieldLimit extends PlainDocument{
		private int limit;
		public JPasswordFieldLimit(int limit) {
			super();
			this.limit = limit;
		}
		public void insertString(int offset,String str, AttributeSet attr) {
			if(str == null) return;
			if((getLength()+str.length())<=limit) {
				try {
					super.insertString(offset, str, attr);
				}catch(Exception e) {}
			}
		}
	}
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		new Admin();
//	}

}





























