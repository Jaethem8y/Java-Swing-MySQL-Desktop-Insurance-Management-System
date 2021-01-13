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
public class Pay extends JFrame{
	String memberID;
	Vector<Vector<String>>row;
	Vector<JPanel>tickets = new Vector<JPanel>();
	String orderID;
	int menu;
	public Pay() {}
	public Pay(String member,Vector<Vector<String>>ro,int m) {
		setTitle("Ticket");
		memberID = member;
		row = ro;
		menu = m;
		setLayout(new GridLayout(tickets.size(),1,20,20));
		setBackground(Color.WHITE);
		new Tickets();
		for(int i=0;i<tickets.size();i++) {
			add(tickets.elementAt(i));
		}
		System.out.println(tickets.size());
		System.out.println(row);
		setSize(320,tickets.size()*200);
		setVisible(true);
		save();
	}public void save() {
		BufferedImage image = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();
		paint(g2);
		try {
			ImageIO.write(image, "jpg", new File("/Users/jaehyeokchoi/Desktop/tickets/"+orderID+memberID+menu+".jpg"));
		}catch(Exception e) {
			e.getStackTrace();
		}
	}
	class Tickets extends JPanel{
		public Tickets() {
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date data = new Date();
			orderID = data+"-"+memberID+"-"+menu;
			String date =dateFormat.format(data);
			setDB();
			for(int i=0;i<row.size();i++) {
				String pname = row.elementAt(i).elementAt(1);
				int amount = Integer.parseInt(row.elementAt(i).elementAt(2));
				int totprice = Integer.parseInt(row.elementAt(i).elementAt(3));
				int price = totprice/amount;
				
				if(i%2==0) {
					if(amount>1) {
						for(int j=1;j<=amount;j++) {
							JLabel serial = new JLabel(date+"-"+memberID+"-"+menu);
							JLabel cent = new JLabel("<html><p style =\"text-align:\"center\"\">Ticket <br>"+formata(price)+"won</p></html>");
							JLabel menu = new JLabel("menu:"+pname);
							JPanel a = new JPanel();
							JPanel b = new JPanel();
							a.setLayout(new BorderLayout());
							a.add(serial,BorderLayout.NORTH);
							cent.setFont(new Font("Ariel",Font.BOLD,40));
							JPanel centa = new JPanel();
							centa.add(cent);
							a.add(centa,BorderLayout.CENTER);
							centa.setBackground(Color.PINK);
							b.setLayout(new BorderLayout());
							JLabel bot = new JLabel(j+"/"+amount);
							b.add(menu,BorderLayout.WEST);
							b.add(bot,BorderLayout.EAST);
							a.add(b,BorderLayout.SOUTH);
							b.setBackground(Color.PINK);
							a.setBackground(Color.PINK);
							serial.setBackground(Color.PINK);
							cent.setBackground(Color.PINK);
							bot.setBackground(Color.PINK);
							menu.setBackground(Color.PINK);
							tickets.add(a);
							a.setSize(300,200);
						}
					}else {
						JLabel serial = new JLabel(date+"-"+memberID+"-"+menu);
						JLabel cent = new JLabel("<html><p style =\"text-align:\"center\"\">Ticket <br>"+formata(price)+"won</p></html>");
						JLabel menu = new JLabel("menu:"+pname);
						JPanel a = new JPanel();
						JPanel b = new JPanel();
						cent.setFont(new Font("Ariel",Font.BOLD,40));

						a.setLayout(new BorderLayout());
						a.add(serial,BorderLayout.NORTH);
						JPanel centa = new JPanel();
						centa.add(cent);
						a.add(centa,BorderLayout.CENTER);
						centa.setBackground(Color.PINK);
						b.setLayout(new BorderLayout());
						JLabel bot = new JLabel(1+"/"+1);
						b.add(menu,BorderLayout.WEST);
						b.add(bot,BorderLayout.EAST);
						a.add(b,BorderLayout.SOUTH);
						b.setBackground(Color.PINK);
						a.setBackground(Color.PINK);
						serial.setBackground(Color.PINK);
						cent.setBackground(Color.PINK);
						bot.setBackground(Color.PINK);
						menu.setBackground(Color.PINK);
						tickets.add(a);
						a.setSize(300,200);

					}
				}else {
					if(amount>1) {
						
						for(int j=1;j<=amount;j++) {
							JLabel serial = new JLabel(date+"-"+memberID+"-"+menu);
							JLabel cent = new JLabel("<html><p style =\"text-align:\"center\"\">Ticket <br>"+formata(price)+"won</p></html>");
							JLabel menu = new JLabel("menu:"+pname);
							JPanel a = new JPanel();
							JPanel b = new JPanel();
							a.setLayout(new BorderLayout());
							cent.setFont(new Font("Ariel",Font.BOLD,40));

							a.add(serial,BorderLayout.NORTH);
							JPanel centa = new JPanel();
							centa.add(cent);
							a.add(centa,BorderLayout.CENTER);
							centa.setBackground(Color.BLUE);
							b.setLayout(new BorderLayout());
							JLabel bot = new JLabel(j+"/"+amount);
							b.add(menu,BorderLayout.WEST);
							b.add(bot,BorderLayout.EAST);
							a.add(b,BorderLayout.SOUTH);
							b.setBackground(Color.BLUE);
							a.setBackground(Color.BLUE);
							serial.setBackground(Color.BLUE);
							cent.setBackground(Color.BLUE);
							bot.setBackground(Color.BLUE);
							menu.setBackground(Color.BLUE);
							tickets.add(a);
							a.setSize(300,200);

						}
					}else {
						JLabel serial = new JLabel(date+"-"+memberID+"-"+menu);
						JLabel cent = new JLabel("<html><p style =\"text-align:\"center\"\">Ticket <br>"+formata(price)+"won</p></html>");
						JLabel menu = new JLabel("menu:"+pname);
						JPanel a = new JPanel();
						JPanel b = new JPanel();
						a.setLayout(new BorderLayout());
						a.add(serial,BorderLayout.NORTH);
						cent.setFont(new Font("Ariel",Font.BOLD,40));

						JPanel centa = new JPanel();
						centa.add(cent);
						a.add(centa,BorderLayout.CENTER);
						centa.setBackground(Color.BLUE);
						b.setLayout(new BorderLayout());
						JLabel bot = new JLabel(1+"/"+1);
						b.add(menu,BorderLayout.WEST);
						b.add(bot,BorderLayout.EAST);
						a.add(b,BorderLayout.SOUTH);
						b.setBackground(Color.BLUE);
						a.setBackground(Color.BLUE);
						serial.setBackground(Color.BLUE);
						cent.setBackground(Color.BLUE);
						bot.setBackground(Color.BLUE);
						menu.setBackground(Color.BLUE);
						tickets.add(a);
						a.setSize(300,200);

					}
				}
			}
			
		}
		public void setDB() {
			Connection conn = DB.Connect.makeConnection("foodcourt");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date dataaa = new Date();
			String here = formatter.format(dataaa);
			try {
				PreparedStatement psmt = conn.prepareStatement("insert into orderlist(cuisineNo,mealNo,memberNo,orderCount,amount,orderDate)values(?,?,?,?,?,?)");
				for(int i=0;i<row.size();i++) {
					psmt.setString(1, Integer.toString(menu));
					psmt.setString(2, row.elementAt(i).elementAt(0));
					psmt.setString(3,memberID);
					psmt.setString(4, row.elementAt(i).elementAt(2));
					psmt.setString(5, row.elementAt(i).elementAt(3));
					psmt.setString(6, here);
					psmt.executeUpdate();
				}
				for(int i=0;i<row.size();i++) {
					String po = row.elementAt(i).elementAt(1);
					int num = Integer.parseInt(row.elementAt(i).elementAt(2));
					Statement st = conn.createStatement();
					ResultSet re = st.executeQuery("select maxCount from meal where mealName = '"+po+"'");
					re.next();
					int maxc = (int) re.getLong("maxCount");
					int fin = maxc-num;
					st.executeUpdate("update meal set maxCount = "+fin+" where mealName = '"+po+"'");
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
		public String formata(int value) {
			DecimalFormat df = new DecimalFormat("###,###,###");
			return df.format(value);
		}
	}
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}

}
