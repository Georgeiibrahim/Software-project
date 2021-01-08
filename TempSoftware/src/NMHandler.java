import java.sql.SQLException;

public interface NMHandler {
	
	public void create (NotificationMessage a);
	public void send();
}
