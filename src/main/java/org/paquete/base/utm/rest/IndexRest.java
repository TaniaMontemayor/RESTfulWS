package org.paquete.base.utm.rest;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.paquete.base.utm.model.Link;
import org.paquete.base.utm.model.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1")
public class IndexRest {

	
	@RequestMapping(method = RequestMethod.OPTIONS)
	public ResponseEntity<Void> showOptions() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Allow", "OPTIONS,HEAD,GET");
		return new ResponseEntity<>(null, headers, HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = { "", "/" }, 
			method = RequestMethod.GET, 
			produces = { "application/json", "text/json" })
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> indexJson() {
		ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentServletMapping();
		List<Link> links = new ArrayList<Link>();
		links.add(new Link(builder.path("/api/v1/").build().toString(), "self"));
		builder = ServletUriComponentsBuilder.fromCurrentServletMapping();
		links.add(new Link(builder.path("/api/v1/user").build().toString(), "user"));
		builder = ServletUriComponentsBuilder.fromCurrentServletMapping();
		links.add(new Link(builder.path("/api/v1/file").build().toString(), "file"));
		builder = ServletUriComponentsBuilder.fromCurrentServletMapping();
		links.add(new Link(builder.path("/api/v1/directory").build().toString(), "directory"));
		builder = ServletUriComponentsBuilder.fromCurrentServletMapping();
		links.add(new Link(builder.path("/api/v1/notify").build().toString(), "notify"));
		
		Map<String, Object> response = new Hashtable<>(2);
		response.put("_links", links);
		response.put("version", "1");
		return response;
	}
	
	/**
	 * Genera indice con Hypermedia para XML
	 * */
	@RequestMapping(value = { "", "/" },
			method = RequestMethod.GET, 
			produces = { "application/xml", "text/xml" })
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Resource indexXml() {
		ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentServletMapping();
		Resource resource = new Resource();
		resource.addLink(new Link(builder.path("/api/v1/").build().toString(), "self"));
		builder = ServletUriComponentsBuilder.fromCurrentServletMapping();
		resource.addLink(new Link(builder.path("/api/v1/user").build().toString(), "user"));
		builder = ServletUriComponentsBuilder.fromCurrentServletMapping();
		resource.addLink(new Link(builder.path("/api/v1/file").build().toString(), "file"));
		builder = ServletUriComponentsBuilder.fromCurrentServletMapping();
		resource.addLink(new Link(builder.path("/api/v1/directory").build().toString(), "directory"));
		builder = ServletUriComponentsBuilder.fromCurrentServletMapping();
		resource.addLink(new Link(builder.path("/api/v1/notify").build().toString(), "notify"));
		return resource;
	}
	
	
}
