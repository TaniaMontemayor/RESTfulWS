package org.paquete.base.utm.service;

import java.util.List;

import org.paquete.base.utm.model.Notification;

public interface NotificationService {
	List<Notification> getNotifications();
	void notify(String subject, String message, List<String> toAddress, List<String> ccAddress);

}
