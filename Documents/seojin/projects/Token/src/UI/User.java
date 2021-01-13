package UI;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class User extends JFrame implements Runnable{
	
	ImageIcon img[] = new ImageIcon[4];
	JButton[] btn= new JButton[4];
	JLabel title = new JLabel("Order Here");
	Thread th;
	JLabel time = new JLabel("");
	public User() {
		setTitle("Order Service");
		add(new North(),BorderLayout.NORTH);
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Menu",new Center());
		add(tabbedPane,BorderLayout.CENTER);
		add(new South(),BorderLayout.SOUTH);
		setSize(500,850);
		setVisible(true);
		th = new Thread(this);
		th.start();
		
		
	}
	class North extends JPanel{
		public North() {
			add(title);
		}
	}
	class Center extends JPanel{
		public Center() {
			String name[] = {"menu_1","menu_2","menu_3","menu_4",};
			String menu[] = {"Korean Food","Chinese Food","Japanese Food","Western Food"};
			setLayout(new GridLayout(2,2));
			for(int i=0;i<4;i++) {
				img[i] = new ImageIcon("/Users/Jaehyeokchoi/Desktop/문제들/2017 식권발매/DataFiles/"+name[i]+".png");
				btn[i] = new JButton(img[i]);
				btn[i].setActionCommand(Integer.toString(i+1));
				btn[i].addActionListener(new MyActionListener());
				btn[i].setToolTipText(menu[i]);
				add(btn[i]);
			}
		}
	}
	class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int command = Integer.parseInt(e.getActionCommand());
			new Process(command);
			dispose();
		}
		
	}
	public void run() {
		Calendar c = Calendar.getInstance();
		while(true) {
			try {
				Thread.sleep(1000);
//			
				Date date = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String okay = (String)formatter.format(date);
				String a = "Current time "+okay;
				time.setText(a);
				revalidate();
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
				
			}
			
		}
	}
	class South extends JPanel{
		
		Thread th;
		public South() {
			setBackground(Color.BLACK);
			time.setBackground(Color.BLACK);
			time.setForeground(Color.BLUE);
			time.setOpaque(true);
			add(time);
			
		}
			
	}
	
// 	public static void main(String[] args) {
//		// TODO Auto-generated method stub
// 		new User();
//
//	}

}
