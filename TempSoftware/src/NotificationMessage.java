import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class NotificationMessage {
	private String content;
	private String contact;
	private NMHandler nm;
	private boolean sent;
	
	public NotificationMessage() {
		// TODO Auto-generated constructor stub
	}
	public NotificationMessage(String contact, int tid, String placeholder) {
		this.contact=contact;
		DatabaseAccess s=new DatabaseAccess();
		NotificationTemplate t=s.Read(tid);
		parseBody(placeholder,t);
		if(t.getChannel().equalsIgnoreCase("SMS"))
		{
			nm=new SMS();
		}
		else
		{
			nm=new Email();
		}
		sent=false;
	}
	
	
	public boolean isSent() {
		return sent;
	}
	public void setSent(boolean sent) {
		this.sent = sent;
	}
	public NMHandler getNm() {
		return nm;
	}
	public void setNm(NMHandler nm) {
		this.nm = nm;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	
	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	
	public void parseBody(String placeHolders,NotificationTemplate t)
	{
		String[] holders=placeHolders.split(",");
		content=t.getBody();
		
		if((content.contains("{")|| content.contains("}")))
		{
			int j=0;
			Pattern pattern=Pattern.compile("\\{(.*?)\\}");
			Matcher m=pattern.matcher(content);
			while(m.find() && j<holders.length)
			{
				String test=m.group(1);
				content=content.replaceAll(test,holders[j]);
				j++;
			}
			content=content.replace('{',' ').replace('}', ' ');
		}
		else 
		{
			return;
		}
		content=t.getSubject()+"\n"+content;
	}
	
}
