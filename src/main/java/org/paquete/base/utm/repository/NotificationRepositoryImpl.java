package org.paquete.base.utm.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.paquete.base.utm.model.Notification;
import org.springframework.stereotype.Repository;

@Repository("notificationRepository")
public class NotificationRepositoryImpl implements NotificationRepository {

	private static Map<String, Notification> notificationDB = new HashMap<>();

	@Override
	public List<Notification> getNotifications() {
		return new ArrayList<Notification>(notificationDB.values());
	}

	@Override
	public Notification getNotification(String key) {
		return notificationDB.get(key);
	}

	@Override
	public Notification createNotification(String key, String message, String subject, List<String> to, List<String> cc) {
		Notification notification = new Notification(message, to, cc);
		notification.setSubject(subject);
		notification.setMessageId(key);
		notificationDB.put(notification.getMessageId(), notification);
		return notification;
	}
	
	@Override
	public Notification updateNotification(String key, Notification notification) {
		notificationDB.put(key, notification);
		return notification;
	}
	
	@Override
	public void addNotification(Notification notification) {
		notificationDB.put(notification.getMessageId(), notification);
	}
}
