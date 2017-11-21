package org.paquete.base.utm.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "users")
public class UserList {
	private List<User> value;

    @XmlElement(name = "user")
    public List<User> getValue() {
        return value;
    }

    public void setValue(List<User> users) {
        this.value = users;
    }
}

