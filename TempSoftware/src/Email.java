import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Email implements NMHandler {
	
	
	java.sql.Connection con=null;
	public Email(){
		 try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         try {
			con=(java.sql.Connection) DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Component;user=Admin;password=Admin");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@Override
	public void create(NotificationMessage a) {
		 String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	      if(a.getContact().matches(regex)) {
		String sql="insert into EMAIL values(?,?)";
		 try
		 {
			 PreparedStatement st=con.prepareStatement(sql);
			 st.setString(1, a.getContent());
			 st.setString(2, a.getContact());
			 a.setSent(true);
			st.executeUpdate();
		 }catch(Exception e)
		 {
			 System.out.println(e);
		 }
	}
	}

	

	@Override
	public void send() {
		 String sql="select * from Email";
		 String sql1="";
		 try
		 {
			 Statement st=con.createStatement();
			 Statement st1=con.createStatement();
			 ResultSet rs=st.executeQuery(sql);
			 NotificationMessage n=new NotificationMessage();
			 while(rs.next())
			 {
				
				 n.setContent(rs.getString(1));
				 n.setContact(rs.getString(2));
				 System.out.println("Notification Message is sent to: "+n.getContact());
				 sql1="delete top(1) from SMS";
				 st1.executeUpdate(sql1);
			 }
			 
		 }catch(Exception e)
		 {
			 System.out.println(e);
		 }
	 }
	}

	


