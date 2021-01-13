package UI;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
public class Chart extends JFrame{
	Vector<Integer> per = new Vector<Integer>();
	JButton save = new JButton("Save as image");
	JButton exit = new JButton("exit");
	int total = 0;
	Circle circle = new Circle();
	public Chart() {
		setTitle("Menu Chart");
		getVector();
		JPanel a = new JPanel();
		a.setLayout(new BorderLayout());
		JPanel b = new JPanel();
		save.addActionListener(new MyActionListener());
		exit.addActionListener(new MyActionListener());
		b.add(save);
		b.add(exit);
		a.add(b,BorderLayout.EAST);
		add(a,BorderLayout.NORTH);
		add(circle,BorderLayout.CENTER);
		setSize(500,500);
		setVisible(true);
	}
	public void save() {
		BufferedImage image = new BufferedImage(circle.getWidth(),circle.getHeight(),BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String filename = dateFormat.format(date);
		paint(g2);
		try {
			ImageIO.write(image, "jpg",new File(filename+"-chart.jpg"));
			System.out.println("File has been created");
		}catch(Exception e) {
			
		}
	}
	
	class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("exit")) {
				dispose();
				new GeneralForm();
			}else {
				save();
				dispose();
			}
			
		}
		
	}
	
	public void getVector() {
		
		Connection conn = DB.Connect.makeConnection("foodcourt");
		try {
			Statement st = conn.createStatement();
			for(int i=1;i<5;i++) {
				ResultSet re = st.executeQuery("select count(*) as count from orderlist where cuisineNo="+i);
				while(re.next()) {
					per.add((int) re.getLong("count"));
					total += (int) re.getLong("count");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class Circle extends JPanel{
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawString("Circle Chart", 160,50);
			int angle =(int)Math.round(((((float)per.elementAt(0))/total)*360));
			System.out.println(angle);
			g.setColor(Color.MAGENTA);
			g.fillArc(100, 100, 200, 200, 0, angle);
			g.fillRect(350, 160, 10, 10);
			g.setColor(Color.GREEN);
			int end = (int) Math.round(((((float)per.elementAt(1))/total)*360));
			System.out.println(angle);
			g.fillArc(100, 100, 200, 200, angle, end);
			g.fillRect(350, 180, 10, 10);
			int start = angle + end;
			end = (int) Math.round(((((float)per.elementAt(2))/total)*360));
			g.setColor(Color.BLUE);
			g.fillArc(100, 100, 200, 200, start, end);
			g.fillRect(350, 200, 10, 10);
			start = start + end;
			end =  (int) Math.round(((((float)per.elementAt(3))/total)*360));
			g.setColor(Color.yellow);
			g.fillArc(100, 100, 200, 200, start, end);
			g.fillRect(350, 220, 10, 10);
			g.setColor(Color.BLACK);
			g.drawString("Korean("+per.elementAt(0)+")", 365, 170);
			g.drawString("Chinese("+per.elementAt(1)+")", 365, 190);
			g.drawString("Japanese("+per.elementAt(2)+")", 365, 210);
			g.drawString("Western("+per.elementAt(3)+")", 365, 230);
		
		}
		
	}
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		new Chart();
//	}

}
