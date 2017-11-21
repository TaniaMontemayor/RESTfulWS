package org.paquete.base.utm.model;

import java.io.Serializable;

public class File implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String path;
	private String fullPath;
	private Long sizeBytes;
	private Link _link;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFullPath() {
		return fullPath;
	}
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	public Long getSizeBytes() {
		return sizeBytes;
	}
	public void setSizeBytes(Long sizeBytes) {
		this.sizeBytes = sizeBytes;
	}
	public Link get_link() {
		return _link;
	}
	public void set_link(Link _link) {
		this._link = _link;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
