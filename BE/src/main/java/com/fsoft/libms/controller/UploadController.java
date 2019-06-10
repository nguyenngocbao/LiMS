package com.fsoft.libms.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.service.IUploadImageService;

@RestController
@RequestMapping("/api/upload")
public class UploadController extends AbstractController {

	@Autowired
	private IUploadImageService uploadService;

	@CrossOrigin
	@RequestMapping(value = "/{filename:.+}", method = RequestMethod.GET)
	public ResponseEntity<Resource> getFile(@PathVariable String filename, HttpServletRequest request)
			throws LibMsException {
		// Load file as Resource
		Resource resource = uploadService.loadFileAsResource(filename);
		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			LOGGER.info("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);

	}
	
	@CrossOrigin
	@RequestMapping(value = "/user/{filename:.+}", method = RequestMethod.GET)
	public ResponseEntity<Resource> getAvatarFile(@PathVariable String filename, HttpServletRequest request)
			throws LibMsException {
		// Load file as Resource
		Resource resource = uploadService.loadFileAsResourceAvatar(filename);
		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			LOGGER.info("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);

	}

}
