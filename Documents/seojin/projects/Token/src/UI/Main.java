package UI;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class Main extends JFrame{
	JButton btn[] = new JButton[4];
	String name[] = {"User","Admin","Register","Exit"};
	
	public Main() {
		setTitle("Main");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(4,1));
		for(int i=0;i<4;i++) {
			btn[i] = new JButton(name[i]);
			btn[i].addActionListener(new MyActionListener());
			add(btn[i]);
		}
		setSize(300,300);
		setVisible(true);
	}
		
	
	class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			switch(command) {
			case "User":
				new User();
				break;
			case "Admin":
				new Admin();
				dispose();
				break;
			case "Register":
				new Register();
				dispose();
				break;
			case "Exit":
				System.exit(DISPOSE_ON_CLOSE);
				break;
			}
			
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new DB.Connect().insertTable();
		new Main();
		
	}

}
