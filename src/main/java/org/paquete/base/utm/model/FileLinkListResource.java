package org.paquete.base.utm.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement	
public class FileLinkListResource {
	
	private List<Link> links;
	private List<File> files;
	
	
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	public List<File> getFiles() {
		return files;
	}
	public void setFiles(List<File> files) {
		this.files = files;
	}
	

}
