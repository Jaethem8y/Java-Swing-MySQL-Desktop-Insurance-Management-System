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

public class Register extends JFrame{
	JLabel lab[] = new JLabel[5];
	JTextField tf[] = new JTextField[2];
	JPasswordField pf[] = new JPasswordField[2];
	JButton register = new JButton("Register");
	JButton exit = new JButton("Exit");
	String id;
	boolean match = false;
	public Register() {
		setTitle("Register");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getID();
		setLayout(new GridLayout(5,2));
		setComponent();
		for(int i=0;i<tf.length;i++) {
			add(lab[i]);
			add(tf[i]);	
		}
		add(lab[2]);
		add(pf[0]);
		
		tf[0].setText(id);
		tf[0].setEnabled(false);
		
		JPanel a = new JPanel();
		a.setLayout(new GridLayout(1,2));
		a.add(lab[3]);
		a.add(lab[4]);
		
		add(a);
		add(pf[1]);
		
		pf[0].setDocument(new JPasswordFieldLimit(4));
		
		pf[0].setFocusable(true);
		pf[0].requestFocus();
		pf[1].setFocusTraversalKeysEnabled(false);
		pf[1].addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == KeyEvent.VK_TAB) {
					System.out.println("ok");
					String p1 = new String(pf[0].getPassword());
					String p2 = new String(pf[1].getPassword());
					if(p1.equals(p2)) {
						lab[4].setText("Match");
						lab[4].setForeground(Color.BLUE);
						revalidate();
						System.out.println("switch");
						pf[1].setFocusTraversalKeysEnabled(true);
						match = true;
					}
					
				}
			}
		});
		pf[1].setDocument(new JPasswordFieldLimit(4));

		pf[1].requestFocus();
		pf[1].setFocusable(true);
		
		register.addActionListener(new MyActionListener());
		exit.addActionListener(new MyActionListener());
		add(register);
		add(exit);
		
		
		setVisible(true);
		setSize(380,300);
		
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
	
	class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Register")) {				
				if(tf[1].getText().equals("") || pf[0].getPassword().length==0|| pf[1].getPassword().length==0) {
					JOptionPane.showMessageDialog(null,"Please fill all the *","Message",JOptionPane.ERROR_MESSAGE);
				}else if(match == false) {
					JOptionPane.showMessageDialog(null,"Please check the password","Message",JOptionPane.ERROR_MESSAGE);
				}else {
					Connection conn = DB.Connect.makeConnection("foodcourt");
					try {
						PreparedStatement psmt = conn.prepareStatement("insert into member value(?,?,?)");
						psmt.setString(1, tf[0].getText());
						psmt.setString(2, tf[1].getText());
						psmt.setString(3, new String(pf[0].getPassword()));
						psmt.executeUpdate();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					dispose();
					JOptionPane.showMessageDialog(null, "Registration Complete","Message",JOptionPane.INFORMATION_MESSAGE);
					new Main();
				}
			}else {
				dispose();
				new Main();
			}
			
		}
		
	}
	
	public void setComponent() {
		String name[] = {"ID : ","*Name : ","*Password : ","*re-Password:","no-match"};
		for(int i=0;i<name.length;i++) {
			lab[i] = new JLabel(name[i]);
		}
		for(int i=0;i<tf.length;i++) {
			tf[i] = new JTextField(10);
			pf[i] = new JPasswordField(10);
		}
		lab[4].setForeground(Color.RED);
	}
	
	public void getID() {
		Connection conn = DB.Connect.makeConnection("foodcourt");
		try {
			Statement st = conn.createStatement();
			ResultSet re = st.executeQuery("select memberNo from member order by memberNo desc");
			re.next();
			id = Integer.toString(Integer.parseInt(re.getString("memberNo"))+1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
//	public static void main(String[] args) {
//		new Register();
//	}
	
}
