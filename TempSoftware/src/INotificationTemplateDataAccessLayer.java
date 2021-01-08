
public interface INotificationTemplateDataAccessLayer {
	void Create(NotificationTemplate t);
	void Update(NotificationTemplate t);
	NotificationTemplate Read(int id);
	
	void Delete(int templateID);
}
