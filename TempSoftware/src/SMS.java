import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMS implements NMHandler{
	
	java.sql.Connection con=null;
	public SMS()  {
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
		Pattern p = Pattern.compile("^\\+(?:[0-9] ?){6,14}[0-9]$"); 
		 
		Matcher m = p.matcher(a.getContact()); 
		if(m.find() && m.group().equals(a.getContact())) {

		String sql="insert into SMS values(?,?)";
		 try
		 {
			 PreparedStatement st=con.prepareStatement(sql);
			 st.setString(1, a.getContent());
			 st.setString(2, a.getContact());
			 st.executeUpdate();
			 a.setSent(true);
			 
		 }catch(Exception e)
		 {
			 System.out.println(e);
		 }
		}
	}
	@Override
	public void send() {
			
			 String sql="select * from SMS";
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
