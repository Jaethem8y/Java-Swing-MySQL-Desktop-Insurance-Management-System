package UI;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
public class Process extends JFrame {
	Vector<Vector<String>>meal = new Vector<Vector<String>>();
	Vector<Vector<String>>row = new Vector<Vector<String>>();
	Vector<Vector<String>>admin = new Vector<Vector<String>>();
	Vector<String>adminID = new Vector<String>();
	Vector<String>col = new Vector<String>();
	Vector<JButton>btn = new Vector<JButton>();
	JComboBox<String>memberID;
	DefaultTableModel model;
	JTable jt;
	JLabel title;
	JLabel total = new JLabel("won");
	JTextField selected = new JTextField(15);
	JTextField amount = new JTextField(5);
	JPasswordField pass = new JPasswordField(10);

	int menu;
	int count=0;
	public Process(int i) {
		setTitle("Select");
		menu = i;
		makeComponent();
		JPanel tit = new JPanel();
		title.setFont(new Font("Ariel",Font.BOLD,30));
		tit.add(title);
		add(tit,BorderLayout.NORTH);
		add(new Left(),BorderLayout.CENTER);
		add(new Right(),BorderLayout.EAST);
		jt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					int re = jt.getSelectedRow();
					DefaultTableModel model = (DefaultTableModel)jt.getModel();
					Vector<String>ans = row.elementAt(re);
					model.removeRow(re);
					int index=0;
					for(int i=0;i<meal.size();i++) {
						if(ans.elementAt(0).equals(meal.elementAt(i).elementAt(0))) {
							index = i;
						}
					}
					btn.elementAt(index).setEnabled(true);
				}
			}
		});
		setSize(1200,800);
		setVisible(true);
	}
	
	public void makeComponent() {
		selected.setEnabled(false);
		amount.setEnabled(false);
		String name[] = {"Product Number","Product Name","Amount","Price"};
		if(menu == 1) {
			title = new JLabel("Korean");
		}else if(menu ==2) {
			title = new JLabel("Chinese");
		}else if(menu ==3) {
			title = new JLabel("Japanese");
		}else if(menu ==4) {
			title = new JLabel("Western");
		}
		for(int i=0;i<name.length;i++) {
			col.add(name[i]);
		}
		model = new DefaultTableModel(row,col) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jt = new JTable(model);
		Connection conn = DB.Connect.makeConnection("foodcourt");
		try {
			Statement st = conn.createStatement();
			ResultSet re = st.executeQuery("select * from meal where cuisineNo="+menu);
			while(re.next()) {
				Vector<String>a = new Vector<String>();
				a.add(re.getString("mealNo"));a.add(re.getString("cuisineNo"));a.add(re.getString("mealName"));a.add(re.getString("price"));a.add(re.getString("maxCount"));a.add(re.getString("todayMeal"));
				meal.add(a);
			}
			re = st.executeQuery("select * from member");
			while(re.next()) {
				Vector<String>a = new Vector<String>();
				a.add(re.getString("memberNo"));a.add(re.getString("passwd"));
				adminID.add(re.getString("memberNo"));
				admin.add(a);
			}
			for(int i=0;i<meal.size();i++) {
				System.out.println(meal.size());
				JButton but = new JButton("<html><p style=\"text-alignment:\"center\"\">"+meal.elementAt(i).elementAt(2)+"<br>"+meal.elementAt(i).elementAt(3)+"</p></html>");
				but.setActionCommand(Integer.toString(i));
				but.addActionListener(new MenuListener());
				if(meal.elementAt(i).elementAt(4).equals("0")||meal.elementAt(i).elementAt(5).equals("0")) {
					but.setEnabled(false);
				}else {
					count++;
				}
				btn.add(but);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	class MenuListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int index = Integer.parseInt(e.getActionCommand());
			btn.elementAt(index).setEnabled(false);
			Vector<String>a = new Vector<String>();
			a.add(meal.elementAt(index).elementAt(0));
			a.add(meal.elementAt(index).elementAt(2));
			a.add("1");
			a.add(meal.elementAt(index).elementAt(3));
			row.add(a);
			selected.setText(meal.elementAt(index).elementAt(2));
			jt.updateUI();
			calc();
			
		}
		
	}
	public void calc() {
		int alltot = 0;
		for(int i=0;i<row.size();i++) {
			alltot += Integer.parseInt(row.elementAt(i).elementAt(3));
		}
		total.setText((formata(alltot))+"won");
		revalidate();
	}
	public String formata(int value) {
		DecimalFormat df = new DecimalFormat("###,###,###");
		return df.format(value);
	}
	
	
	class Left extends JPanel{
		public Left() {
			int limit = (int)Math.ceil(Math.sqrt(count));
			if(limit<5) {
				limit = 5;
			}
			setLayout(new GridLayout(limit,limit));
			for(int i=0;i<meal.size();i++) {
				if(btn.elementAt(i).isEnabled()) {
					add(btn.elementAt(i));
				}else {
					if(i>=count) {
						add(btn.elementAt(i));
					}
				}
			}
			setVisible(true);
			setSize(500,500);
		}
	}
	class Right extends JPanel{
		public Right() {
			setLayout(new GridLayout(2,1));
			add(new Top());
			add(new Bot());
			setSize(500,800);
			setVisible(true);
			
		}
		class Top extends JPanel{
			JLabel t = new JLabel("Total Price : ");
			public Top() {
				setLayout(new BorderLayout());
				JPanel a = new JPanel();
				a.setLayout(new BorderLayout());
				a.add(t, BorderLayout.WEST);
				a.add(total,BorderLayout.EAST);
				add(a,BorderLayout.NORTH);
				JScrollPane jps = new JScrollPane(jt);
				add(jps,BorderLayout.CENTER);
			}
		}
		class Bot extends JPanel{
			JLabel la = new JLabel("Selected");
			JLabel am = new JLabel("Amount");
			JButton btn[] = new JButton[10];
			JButton insert = new JButton("insert");
			JButton reset = new JButton("reset");
			JButton pay = new JButton("Pay");
			JButton cancel = new JButton("cancel");
			public Bot() {
				insert.addActionListener(new MyActionListener());
				reset.addActionListener(new MyActionListener());
				pay.addActionListener(new MyActionListener());
				cancel.addActionListener(new MyActionListener());
				
				JPanel a = new JPanel();
				a.add(la);
				a.add(selected);
				a.add(am);
				a.add(amount);
				setLayout(new BorderLayout());
				add(a,BorderLayout.NORTH);
				JPanel b = new JPanel();
				b.setLayout(new GridLayout(3,3));
				for(int i=1;i<=9;i++) {
					btn[i] = new JButton(Integer.toString(i));
					btn[i].addActionListener(new NumListener());
					b.add(btn[i]);
				}
				btn[0] = new JButton("0");
				btn[0].addActionListener(new NumListener());
				JPanel c = new JPanel();
				c.setLayout(new BorderLayout());
				c.add(b,BorderLayout.CENTER);
				c.add(btn[0],BorderLayout.SOUTH);
				JPanel d = new JPanel();
				d.setLayout(new BorderLayout());
				d.add(insert,BorderLayout.CENTER);
				d.add(reset,BorderLayout.SOUTH);
				JPanel e = new JPanel();
				e.setLayout(new BorderLayout());
				e.add(c,BorderLayout.CENTER);
				e.add(d,BorderLayout.EAST);
				JPanel f = new JPanel();
				f.setLayout(new GridLayout(1,2));
				f.add(pay);
				f.add(cancel);
				JPanel g = new JPanel();
				g.setLayout(new BorderLayout());
				
				add(e,BorderLayout.CENTER);
				add(f,BorderLayout.SOUTH);
			}
		}
		
		class NumListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				String com = amount.getText()+e.getActionCommand();
				if(selected.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "choose a menu","message",JOptionPane.ERROR_MESSAGE);
				}else {
					if(Integer.parseInt(com)>100) {
						
					}else {
						amount.setText(com);
					}
				}
			}
			
		}
		class Message extends JPanel{
			JLabel m = new JLabel("memberID");
			JLabel p = new JLabel("password");
			public Message() {
				memberID = new JComboBox<String>(adminID);
				setLayout(new GridLayout(2,2));
				add(m);
				add(memberID);
				add(p);
				add(pass);
				
			}
		}
		class MyActionListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("reset")) {
					selected.setText("");
					amount.setText("");
				}else if(e.getActionCommand().equals("cancel")) {
					dispose();
				}else if(e.getActionCommand().equals("Pay")){
					int result = JOptionPane.showConfirmDialog(null, new Message(),"Confirm?",JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.NO_OPTION) {
						dispose();
						new Main();
					}else {
						String a = (String) memberID.getSelectedItem();
						String p = new String(pass.getPassword());
						for(int i=0;i<admin.size();i++) {
							if(a.equals(admin.elementAt(i).elementAt(0))) {
								if(p.equals(admin.elementAt(i).elementAt(1))) {
									JOptionPane.showMessageDialog(null, "Tickets are printing","Message",JOptionPane.INFORMATION_MESSAGE);
									dispose();
									new Pay(a,row,menu);
								}else {
									JOptionPane.showMessageDialog(null, "Wrong Password","Message",JOptionPane.ERROR_MESSAGE);
								}
							}
						}
						
					}
					
					
				}else {
					String name = selected.getText();
					int po = Integer.parseInt(amount.getText());
					System.out.println(po);
					if(po>10) {
						JOptionPane.showMessageDialog(null, "Exceed possible amount","Message",JOptionPane.ERROR_MESSAGE);
					}else {
						for(int i=0;i<meal.size();i++) {
							if(meal.elementAt(i).elementAt(2).equals(name)) {
								if(po>Integer.parseInt(meal.elementAt(i).elementAt(4))) {
									JOptionPane.showMessageDialog(null, "Exceed possible amount","Message",JOptionPane.ERROR_MESSAGE);
								}
							}
						}
						for(int i=0;i<row.size();i++) {
							if(row.elementAt(i).elementAt(1).equals(name)) {
								row.elementAt(i).set(2, amount.getText());
								int in= Integer.parseInt(row.elementAt(i).elementAt(2));
								int pr = Integer.parseInt(row.elementAt(i).elementAt(3));
								String tot = Integer.toString(in*pr);
								row.elementAt(i).set(3,tot);
								jt.updateUI();
								calc();
								
							}
						}
					}
					amount.setText("");
					selected.setText("");
				}
			}
			
		}
	}
	
	
	
//	public static void main(String[] args) {
//		new Process(4);
//	}

}
