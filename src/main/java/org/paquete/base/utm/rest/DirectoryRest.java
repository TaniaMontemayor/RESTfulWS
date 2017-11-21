package org.paquete.base.utm.rest;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.paquete.base.utm.exception.ResourceNotFoundException;
import org.paquete.base.utm.model.File;
import org.paquete.base.utm.model.FileLinkListResource;
import org.paquete.base.utm.model.Link;
import org.paquete.base.utm.model.OptionDocs;
import org.paquete.base.utm.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/directory")
public class DirectoryRest {
	
	@Autowired
	private FileService fileService;
	
	@RequestMapping(method = RequestMethod.OPTIONS)
	@ResponseBody
	public ResponseEntity<?> showOptions() {
		OptionDocs optionDocs = new OptionDocs();
		optionDocs.getAllow().put(HttpMethod.GET, "List specified directory content...");
		optionDocs.getAllow().put(HttpMethod.OPTIONS, "Resource documentation");
		ResponseEntity<OptionDocs> result = new ResponseEntity<>(optionDocs, HttpStatus.OK);
		result.getHeaders().add("Allow", "OPTIONS,GET");
		return result;
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public FileLinkListResource getFilesJSON(@RequestParam String dir) throws IOException {
		Path path = FileSystems.getDefault().getPath(dir);
		if (Files.notExists(path)){ 
			throw new ResourceNotFoundException();
		}
		List<Path> paths = new ArrayList<Path>();
		paths = fileService.walkDir(path, paths);

		List<File> files = new ArrayList<File>();
		for (Path myPath : paths) {
			File file = new File();
			file.setPath(myPath.getParent().toString());
			file.setName(myPath.getFileName().toString());
			file.setFullPath(myPath.toString());
			file.setSizeBytes(Files.size(myPath));
			Link link = new Link(ServletUriComponentsBuilder.fromCurrentServletMapping()
					.path("/api/v1/file?path=" + URLEncoder.encode(file.getFullPath())).build().toString(), "download");
			file.set_link(link);
			files.add(file);
		}

		List<Link> links = new ArrayList<Link>();
		links.add(
				new Link(ServletUriComponentsBuilder.fromCurrentServletMapping().path("/").build().toString(), "api"));
		links.add(new Link(
				ServletUriComponentsBuilder.fromCurrentServletMapping().path("/directory").build().toString(), "self"));
		FileLinkListResource result = new FileLinkListResource();
		result.setLinks(links);
		result.setFiles(files);
		return result;
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public FileLinkListResource getFilesXML(@RequestParam String dir) throws IOException {
		Path path = FileSystems.getDefault().getPath(dir);
		
		if (Files.notExists(path)){ 
			throw new ResourceNotFoundException();
		}

		List<Path> paths = new ArrayList<Path>();
		paths = fileService.walkDir(path, paths);

		List<File> files = new ArrayList<File>();
		for (Path myPath : paths) {
			File file = new File();
			file.setPath(myPath.getParent().toString());
			file.setName(myPath.getFileName().toString());
			file.setFullPath(myPath.toString());
			file.setSizeBytes(Files.size(myPath));
			Link link = new Link(ServletUriComponentsBuilder.fromCurrentServletMapping()
					.path("/api/v1/file?path=" + URLEncoder.encode(file.getFullPath())).build().toString(), "download");
			file.set_link(link);
			files.add(file);
		}

		List<Link> links = new ArrayList<Link>();
		links.add(
				new Link(ServletUriComponentsBuilder.fromCurrentServletMapping().path("/").build().toString(), "api"));
		links.add(new Link(
				ServletUriComponentsBuilder.fromCurrentServletMapping().path("/directory").build().toString(), "self"));
		FileLinkListResource result = new FileLinkListResource();
		result.setLinks(links);
		result.setFiles(files);
		return result;
	}
}
