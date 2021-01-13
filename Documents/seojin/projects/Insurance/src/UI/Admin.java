package UI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Admin extends JFrame{

	ImageIcon im = new ImageIcon("/Users/Jaehyeokchoi/Documents/seojin/projects/insurance/img/img.jpg");
	JLabel img = new JLabel(im);
	JButton[] btn = new JButton[4];
	
	public Admin() {
		setTitle("Insurance Admin Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new Bts(),BorderLayout.NORTH);
		JPanel imga = new JPanel();
		imga.add(img);
		add(imga,BorderLayout.CENTER);
		setSize(500,500);
		setVisible(true);
	}
	class Bts extends JPanel{
		public Bts() {
			String names[] = {
					"Register","Lookup","Manage","Exit",
			};
			for(int i=0;i<4;i++) {
				btn[i] = new JButton(names[i]);
				btn[i].addActionListener(new MyActionListener());
				add(btn[i]);
			}
		
		}
	}
	class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			switch(command) {
			case "Register":
				new Register();
				break;
			case "Lookup":
				new Lookup();
				break;
			case "Manage":
				new Contract();
				break;
			case "Exit":
				dispose();
				break;
			}
			
		}
		
	}
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		new Admin();
//	}

}
