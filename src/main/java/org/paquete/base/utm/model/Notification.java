package org.paquete.base.utm.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Notification implements Serializable {

	public static final long serialVersionUID = 1L;
	private String subject;
	private String message;
	private List<String> toAdress;
	private List<String> ccAdress;
	private String messageId;
	private String status;
	
	public Notification(){
		messageId = UUID.randomUUID().toString();
	}
	
	public Notification(String message, List<String> toAdress){
		this();
		this.message = message;
		this.toAdress = toAdress;
	}
	
	public Notification(String message, List<String> toAdress, List<String> ccAddress){
		this();
		this.message = message;
		this.toAdress = toAdress;
		this.ccAdress = ccAddress;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getToAdress() {
		return toAdress;
	}

	public void setToAdress(List<String> toAdress) {
		this.toAdress = toAdress;
	}

	public List<String> getCcAdress() {
		return ccAdress;
	}

	public void setCcAdress(List<String> ccAdress) {
		this.ccAdress = ccAdress;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	

}
