package org.paquete.base.utm.rest;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import org.paquete.base.utm.exception.ResourceNotFoundException;
import org.paquete.base.utm.model.OptionDocs;
import org.paquete.base.utm.service.FileService;
import org.paquete.base.utm.view.DownloadView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/file")
public class FileRest {
	
	@Autowired
	private FileService fileService;

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

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public DownloadView downloadFile(@RequestParam("path") String dir) throws IOException {
		Path path = FileSystems.getDefault().getPath(dir);

		if (Files.notExists(path)) {
			throw new ResourceNotFoundException();
		}
		Path attachment = fileService.getFile(dir);		
		return new DownloadView(attachment.getFileName().toString(),
				Files.probeContentType(attachment), 
				Files.readAllBytes(attachment));
	}

	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public String deleteFile(@RequestParam("path") String dir) throws IOException {
		Path path = FileSystems.getDefault().getPath(dir);

		if (Files.notExists(path)) {
			throw new ResourceNotFoundException();
		}
		return fileService.delete(path);
	
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> uploadFile(@RequestParam("name") String name, @RequestParam("dir") String path,
			@RequestParam("file") MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			String message = String.format("Failed to upload file %s to %s. File was empty.", name, path);
			ResponseEntity<String> result = new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
			return result;
		} else {
			boolean status = fileService.uploadFile(file, name, path);
			if (status) {
				HttpHeaders headers = new HttpHeaders();
				headers.add(HttpHeaders.LOCATION, ServletUriComponentsBuilder.fromCurrentServletMapping()
						.path("/api/v1/file?path=" + URLEncoder.encode(path + "/" + name)).build().toString());
				ResponseEntity<String> result = new ResponseEntity<>(null, headers, HttpStatus.CREATED);
				return result;
			} else {
				ResponseEntity<String> result = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
				return result;
			}

		}

	}

}
