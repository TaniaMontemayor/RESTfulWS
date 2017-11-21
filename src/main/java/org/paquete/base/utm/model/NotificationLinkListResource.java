package org.paquete.base.utm.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement	
public class NotificationLinkListResource {
	
	private List<Link> links;
	private List<Notification> notifications;
	
	
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	public List<Notification> getNotifications() {
		return notifications;
	}
	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}
	
}
