package DB;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.Scanner;
import java.util.StringTokenizer;
public class Connect {
	public static Connection makeConnection(String DB) {
		String url = "jdbc:mysql://localhost/"+DB;
		String id = "root";
		String pw = "1234";
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver on");
			con = DriverManager.getConnection(url,id,pw);
			System.out.println("Database on");
		}catch(ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}catch(SQLException ea) {
			System.out.println(ea.getMessage());
		}
		return con;	
	}
	public static Connection createDB(String DB) {
		String createdb = "create database if not exists "+DB;
		Connection conn = makeConnection("");
		Connection connect = null;
		try {
			Statement st = conn.createStatement();
			st.executeUpdate(createdb);
			System.out.println("DB created");
			connect = makeConnection("DB");
			System.out.println("Connected to DB");
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		return connect;
	}
	public static void createTables() {
		String[] command = {
				"create table if not exists admin(name varchar(20) not null, passwd varchar(20) not null,position varchar(20),jumin char(14),inputDate date, primary key(name,passwd))",
				"create table if not exists customer(code char(7) not null,name varchar(20) not null, birth date, tel varchar(20),address varchar(100),company varchar(20), primary key(code,name))",
				"create table if not exists contract(customerCode varchar(7) not null, contractName varchar(20) not null, regPrice int, regDate date not null, monthPrice int, adminName varchar(20) not null)",
		};
		createDB("insurance");
		Connection conn = makeConnection("insurance");
		try {
			Statement st =  conn.createStatement();
			for(int i=0;i<3;i++) {
				st.executeUpdate(command[i]);
			}
			System.out.println("tables created");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
	}
	public static void insert() throws FileNotFoundException, SQLException {
		createTables();
		try {
			String command[] = {
				"insert into admin values(?,?,?,?,?)",
				"insert into contract values(?,?,?,?,?,?)",
				"insert into customer values(?,?,?,?,?,?)",
			};
			String files[] = {
				"file/admin.txt",
				"file/contract.txt",
				"file/customer.txt",
			};
			Connection conn = makeConnection("insurance");
			PreparedStatement psmt = null;
			for(int i=0;i<3;i++) {
				psmt = conn.prepareStatement(command[i]);
				Scanner s = new Scanner(new FileReader("/Users/Jaehyeokchoi/Documents/seojin/projects/insurance/"+files[i]));
				s.nextLine();
				while(s.hasNext()) {
					String line = s.nextLine();
					StringTokenizer st = new StringTokenizer(line,"\t");
					int repeat = st.countTokens();
					for(int j=0;j<repeat;j++) {
						psmt.setString(j+1,st.nextToken());
					}
					int re = psmt.executeUpdate();
					if(re == 1) {
						System.out.println("data has been stored");
					}else {
						System.out.println("data storing has failed");
					}
				}
			}
		}catch(java.sql.SQLIntegrityConstraintViolationException e) {
			System.out.println(e.getMessage());
		}
		
	}	

}
