package org.paquete.base.utm.model;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.http.HttpMethod;

@XmlRootElement	
public class OptionDocs {

	@XmlAttribute
	private Map<HttpMethod, String> allow = new HashMap<>();

	public Map<HttpMethod, String> getAllow() {
		return allow;
	}

	public void setAllow(Map<HttpMethod, String> allow) {
		this.allow = allow;
	}
	
	
}
