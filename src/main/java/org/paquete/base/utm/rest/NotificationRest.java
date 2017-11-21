package org.paquete.base.utm.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.paquete.base.utm.model.Link;
import org.paquete.base.utm.model.NotificationLinkListResource;
import org.paquete.base.utm.model.OptionDocs;
import org.paquete.base.utm.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/notify")
public class NotificationRest {

	@Autowired
	private NotificationService notificationService;
	
	@RequestMapping(method = RequestMethod.OPTIONS)
	@ResponseBody
	public ResponseEntity<?> showOptions() {
		OptionDocs optionDocs = new OptionDocs();
		optionDocs.getAllow().put(HttpMethod.GET, "List specified directory content...");
		optionDocs.getAllow().put(HttpMethod.OPTIONS, "Resource documentation");
		ResponseEntity<OptionDocs> result = new ResponseEntity<>(optionDocs, HttpStatus.OK);
		result.getHeaders().add("Allow", "OPTIONS,GET,DELETE,POST");
		return result;
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = { "application/json", "text/json" })
	@ResponseBody
	@ResponseStatus(code = HttpStatus.OK)
	public Map<String, Object> getNotificationsJSON() {
		List<Link> links = new ArrayList<Link>();
		links.add(
				new Link(ServletUriComponentsBuilder.fromCurrentServletMapping().path("/api/v1/").build().toString(), "api"));
		links.add(new Link(ServletUriComponentsBuilder.fromCurrentServletMapping().path("/api/v1/notify/").build().toString(),
				"self"));
		Map<String, Object> response = new Hashtable<>(2);
		response.put("_links", links);
		response.put("data", notificationService.getNotifications());
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = { "application/xml", "text/xml" })
	@ResponseBody
	@ResponseStatus(code = HttpStatus.OK)
	public NotificationLinkListResource getNotificationsXML() {
		NotificationLinkListResource notificationLinksResource = new NotificationLinkListResource();
		List<Link> links = new ArrayList<Link>();
		links.add(
				new Link(ServletUriComponentsBuilder.fromCurrentServletMapping().path("/api/v1/").build().toString(), "api"));
		links.add(new Link(ServletUriComponentsBuilder.fromCurrentServletMapping().path("/api/v1/notify/").build().toString(),
				"self"));
		notificationLinksResource.setLinks(links);
		notificationLinksResource.setNotifications(notificationService.getNotifications());
		return notificationLinksResource;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> notify(@RequestParam String subject, @RequestParam String message,
			@RequestParam String to, @RequestParam String cc) {
		List<String> toAddress = Arrays.asList(to.split(";"));
		List<String> ccAddress = Arrays.asList(cc.split(";"));
		notificationService.notify(subject, message, toAddress, ccAddress);
		ResponseEntity<?> result = new ResponseEntity<>(HttpStatus.ACCEPTED);
		return result;
	}
	
	
}
