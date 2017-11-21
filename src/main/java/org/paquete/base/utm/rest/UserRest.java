package org.paquete.base.utm.rest;

import org.paquete.base.utm.exception.ResourceNotFoundException;
import org.paquete.base.utm.form.UserForm;
import org.paquete.base.utm.model.User;
import org.paquete.base.utm.model.UserList;
import org.paquete.base.utm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/")
public class UserRest {
	
	@Autowired
	private UserService userService;
	
	/**
	 * Recurso para obtener colección de entidades
	 * */
	@RequestMapping(value = "user", 
					method = RequestMethod.GET)
    @ResponseBody @ResponseStatus(HttpStatus.OK)
    public UserList getUsers() {
        UserList list = new UserList();
        list.setValue(this.userService.getUsers());
        return list;
    }
	
	/**
	 * Recurso para obtener entidad
	 * */
	@RequestMapping(value = "user/{username}", 
					method = RequestMethod.GET)
    @ResponseBody @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable("username") String username) {
        User user = this.userService.getUser(username);
        if(user == null)
            throw new ResourceNotFoundException("User was not found");
        return user;
    }
	
	/**
	 * Recurso para eliminar entidad
	 * */
	@RequestMapping(value = "user/{username}", 
					method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("username") String username) {
        if(this.userService.getUser(username) == null)
            throw new ResourceNotFoundException("User was not found");
        this.userService.deleteUser(username);
    }
	
	/**
	 * Recurso para crear entidad
	 * */
	@RequestMapping(value = "user", 
					method = RequestMethod.POST)
	public ResponseEntity<User> create(@RequestBody UserForm form){
		User newUser = this.userService.createUser(form.getUsername(), 
				form.getPassword(), form.getFullName());
		
		String uri = ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path("/user/{username}").buildAndExpand(newUser.getUsername()).toString();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", uri);

        return new ResponseEntity<>(newUser, headers, HttpStatus.CREATED);
	}
	
	/**
	 * Recurso para actualizar entidad
	 * */
    @RequestMapping(value = "user/{username}", 
    				method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("username") String username,
    		@RequestBody UserForm form){
        User user = this.userService.getUser(username);
        if(user == null)
            throw new ResourceNotFoundException("User was not found");
        user.setFullName(form.getFullName());
        user.setPassword(form.getPassword());
        user.setUsername(form.getUsername());
        this.userService.updateUser(user);
    }
    
	@RequestMapping(value = "user", 
			method = RequestMethod.OPTIONS)
	public ResponseEntity<Void> userIndex() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Allow", "OPTIONS,HEAD,GET,POST");
		return new ResponseEntity<>(null, headers, HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "user/{username}", 
			method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> userOptions(@PathVariable("username") String username) {
        if(this.userService.getUser(username) == null)
            throw new ResourceNotFoundException("User was not found");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "OPTIONS,HEAD,GET,PUT,DELETE");
        return new ResponseEntity<>(null, headers, HttpStatus.NO_CONTENT);
    }

}
