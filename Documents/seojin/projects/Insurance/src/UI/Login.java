package UI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends JFrame{
	JLabel t = new JLabel("Admin Login");
	JLabel n = new JLabel("name");
	JLabel p = new JLabel("password");
	JTextField name = new JTextField(10);
	JPasswordField password = new JPasswordField(10);
	JButton confirm = new JButton("Confirm");
	JButton exit = new JButton("Exit");
	public Login() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel a = new JPanel();
		a.setLayout(new GridLayout(1,2));
		a.add(n);
		a.add(name);
		JPanel b = new JPanel();
		b.setLayout(new GridLayout(1,2));
		b.add(p);
		b.add(password);
		JPanel c = new JPanel();
		c.add(a);
		c.add(b);
		
		confirm.addActionListener(new MyActionListener());
		exit.addActionListener(new MyActionListener());
		
		
		JPanel d = new JPanel();
		d.add(confirm);
		d.add(exit);
		
		JPanel e = new JPanel();
		e.add(t);
		
		add(e, BorderLayout.NORTH);
		add(c, BorderLayout.CENTER);
		add(d,BorderLayout.SOUTH);
		setSize(300,150);
		setVisible(true);
		
		
	}
	
	class MyActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String a = e.getActionCommand();
			if(a.equals("Confirm")) {
				Connection conn = DB.Connect.makeConnection("insurance");
				String id = name.getText();
				String pass = new String(password.getPassword());
				String sql = "select * from admin where name = '"+id+"' and passwd = '"+pass+"'";
				Statement st;
				try {
					st = conn.createStatement();
					ResultSet re = st.executeQuery(sql);
					if(re.next()) {
						System.out.println("success");
						new Admin();
						dispose();
					}else {
						System.out.println("failed");
						dispose();
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
			}else {
				dispose();
			}
		}
		
	}
	
	
	public static void main(String[] args) throws FileNotFoundException, SQLException {
		// TODO Auto-generated method stub
		DB.Connect.insert();
		
		new Login();
		
	}

}
