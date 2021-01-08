import java.util.ArrayList;
import java.util.Scanner;


public class MemoryNotificationTemplateAccess implements INotificationTemplateDataAccessLayer {
	 static ArrayList<NotificationTemplate> templates;
	
	public MemoryNotificationTemplateAccess()
	{
		templates=new ArrayList<NotificationTemplate>();
	}
	public static ArrayList<NotificationTemplate> getTemplates() {
		return templates;
	}
	
	public NotificationTemplate Search(int id)
	{
		for(int i=0;i<templates.size();i++)
		{	
			if(templates.get(i).getId()==id)
				return templates.get(i);
		}
		return null;
	}
	@Override
	public void Create(NotificationTemplate t) {
		templates.add(t);
		
	}
	@Override
	public void Update(NotificationTemplate t) {
		NotificationTemplate check=Search(t.getId()-1);
		if(check==null)
			System.out.println("This template does not exist");
		else
		{
			templates.set(t.getId(), t);
		}
		
	}
	@Override
	public  NotificationTemplate Read(int id) {
		NotificationTemplate check=Search(id);
		if(check==null){
			System.out.println("This template does not exist");
			return null;
		}
		else
		{
			return check;
		}
		
	}
	
	@Override
	public void Delete(int templateID) {
		NotificationTemplate T=Read(templateID);
		if(T!=null) {
			System.out.println("The template is deleted successfully");
			templates.remove(T);
		}
		else
			System.out.println("This template does not exist");
	}
	
	
}
