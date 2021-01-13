package DB;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Connect {
	public static Connection makeConnection(String db){
		String url = "jdbc:mysql://localhost/"+db;
		String id = "root";
		String pw = "1234";
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver on");
			conn = DriverManager.getConnection(url,id,pw);
			System.out.println("DB on");
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}
	public static void createDB() {
		Connection conn = makeConnection("");
		String sql = "create database if not exists foodcourt";
		try {
			Statement st = conn.createStatement();
			st.executeUpdate(sql);
			System.out.println("DB created");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void createTable() {
		createDB();
		Connection conn = makeConnection("foodcourt");
		String sql[] = {
				"create table if not exists member(memberNo int not null primary key auto_increment,memberName varchar(20), passwd varchar(4))",
				"create table if not exists cuisine(cuisineNo int primary key not null auto_increment, cuisineName varchar(10))",
				"create table if not exists meal(mealNo int primary key not null auto_increment,cuisineNo int, mealName varchar(20), price int, maxCount int, todayMeal tinyint(1))",
				"create table if not exists orderlist(orderNo int primary key not null auto_increment, cuisineNo int, mealNo int, memberNo int, orderCount int, amount int, orderDate datetime)",
				
		};
		try {
			Statement st = conn.createStatement();
			for(int i=0;i<4;i++) {
				st.executeUpdate(sql[i]);
			}
			System.out.println("tables created");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void insertTable() {
		try {
			createTable();
			Connection conn = makeConnection("foodcourt");
			String[] filename = {
					"cuisine.txt",
					"meal.txt",
					"member.txt",
					"orderlist.txt",
			};
			String table[] = {
					"cuisine","meal","member","orderlist",
			};
			for(int i=0;i<table.length;i++) {
				String sql = "insert into "+table[i]+" values(";
				Scanner s = new Scanner(new FileReader("/Users/Jaehyeokchoi/Desktop/문제들/2017 식권발매/DataFiles/"+filename[i]));
				String count = s.nextLine();
//				System.out.println(count);
				StringTokenizer ct = new StringTokenizer(count);
				int turn = ct.countTokens();
//				System.out.println(turn);
				if(i==3) {
					turn+=1;
				}
				for(int j=0;j<turn;j++) {
					if(j==0) {
						sql =sql+"?";
					}else {
						sql = sql +",?";
					}
				}
				sql += ")";
				
				PreparedStatement psmt = conn.prepareStatement(sql);
				
				while(s.hasNext()) {
					String a = s.nextLine();
					StringTokenizer term = new StringTokenizer(a,"\t");
					for(int j=0;j<turn;j++) {
						psmt.setString(j+1, term.nextToken());
					}
//					System.out.println(psmt);
					psmt.executeUpdate();
				}
			}
		}catch(java.sql.SQLIntegrityConstraintViolationException e) {
			
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public static void main(String[] args) {
//		insertTable();
//
//	}

}
