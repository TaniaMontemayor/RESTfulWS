package org.paquete.base.utm.repository;

import java.util.List;

import org.paquete.base.utm.model.Notification;

public interface NotificationRepository {

	List<Notification> getNotifications();

	Notification getNotification(String key);

	Notification createNotification(String key, String message, String subject, List<String> to, List<String> cc);

	Notification updateNotification(String key, Notification notification);

	void addNotification(Notification notification);

}
