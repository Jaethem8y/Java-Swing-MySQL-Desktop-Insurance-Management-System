package UI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
public class Register extends JFrame{
	
	String names[] = {
			"Customer Code","*Customer Name","*Birthday(YYYY-MM-DD)","*Contact","Address","Work",
	};
	JLabel lab[] = new JLabel[6];
	JTextField[] jt = new JTextField[6];
	JButton add = new JButton("add");
	JButton exit = new JButton("exit");
	
	public Register() {
		setTitle("Register");
		JPanel a = new JPanel();
		a.setLayout(new GridLayout(6,2));
		for(int i=0;i<6;i++) {
			lab[i] = new JLabel(names[i]);
			jt[i] = new JTextField(10);
			a.add(lab[i]);
			a.add(jt[i]);
		}
		jt[0].setEnabled(false);
		jt[2].addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyChar()=='\n') {
					Calendar cal = Calendar.getInstance();
					int year = cal.get(Calendar.YEAR)-2000;
					String str[] = jt[2].getText().split("-");
					int hap = Integer.valueOf(str[0])+Integer.valueOf(str[1])+Integer.valueOf(str[2]);
					jt[0].setText("S"+year+hap);
				}
			}
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}
			
		});
		jt[2].requestFocus();
		jt[2].setFocusable(true);
		add(a,BorderLayout.CENTER);
		
		JPanel b = new JPanel();
		add.addActionListener(new MyActionListener());
		exit.addActionListener(new MyActionListener());
		
		b.add(add);
		b.add(exit);
		add(b,BorderLayout.SOUTH);
		
		
		setSize(500,500);
		setVisible(true);
	}
	
	class MyActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("add")) {
				if(jt[0].getText().equals("")||jt[1].getText().equals("") || jt[2].getText().equals("") || jt[3].getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Fill out all the required field","Register Error",JOptionPane.ERROR_MESSAGE);
					dispose();
				}else {
					Connection conn = DB.Connect.makeConnection("insurance");
					String sql = "insert into customer values(?,?,?,?,?,?)";
					try {
						PreparedStatement psmt = conn.prepareStatement(sql);
						for(int i=0;i<6;i++) {
							psmt.setString(i+1, jt[i].getText());
						}
						int re = psmt.executeUpdate();
						if(re == 1) {
							JOptionPane.showMessageDialog(null, "Registration Successful","Message",JOptionPane.INFORMATION_MESSAGE);
						}else {
							System.out.println("failed");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					dispose();
				}
			}else {
				dispose();
			}
			
		}
		
	}
	
//	public static void main(String[] args) {
//		new Register();
//	}

}
