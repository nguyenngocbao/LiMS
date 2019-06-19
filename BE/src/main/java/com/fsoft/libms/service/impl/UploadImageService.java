package com.fsoft.libms.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fsoft.libms.exception.LibMsException;
import com.fsoft.libms.security.token.TokenProvider;
import com.fsoft.libms.service.IUploadImageService;

@Service
@Profile("dev")
public class UploadImageService implements IUploadImageService {
	private final Path fileStorageLocation;
	private final Path fileStorageLocationAvatar;
	@Autowired
	TokenProvider tokenProvider;
	private static final String NOT_CREATE_DIRECTORY = "Could not create the directory where the uploaded files will be stored";
	private static final String NAME_CONTAIN_INVALID_PATH = "Sorry! Filename contains invalid path sequence";
	private static final String NOT_STORE_FILE = "Could not store file";
	private static final String TRY = ". Please try again!";
	private static final String FILE_NOT_FOUND = "File not found";
	private static final String folder = "uploads";
	private static final String folderAvatar = "avatars";

	public UploadImageService() throws LibMsException {
		this.fileStorageLocation = Paths.get(folder).toAbsolutePath().normalize();
		this.fileStorageLocationAvatar = Paths.get(folderAvatar).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
			Files.createDirectories(this.fileStorageLocationAvatar);
		} catch (Exception ex) {
			throw new LibMsException(NOT_CREATE_DIRECTORY, ex);
		}
	}

	public String storeFile(MultipartFile file, String fileName) throws LibMsException {
		// Normalize file name upload.png; tttthuy.png
		try {

			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new LibMsException(NAME_CONTAIN_INVALID_PATH + fileName);
			}
			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			return fileName;
		} catch (IOException ex) {
			throw new LibMsException(NOT_STORE_FILE + fileName + TRY, ex);
		}
	}

	public String storeFileAvatar(MultipartFile file, String fileName) throws LibMsException {
		// Normalize file name upload.png; tttthuy.png
		try {

			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new LibMsException(NAME_CONTAIN_INVALID_PATH + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocationAvatar.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			return fileName;
		} catch (IOException ex) {
			throw new LibMsException(NOT_STORE_FILE + fileName + TRY, ex);
		}
	}

	public void deleteFile(String fileName) throws LibMsException {
		try {
			Path path = Paths.get(fileStorageLocation + File.separator + fileName);
			Files.deleteIfExists(path);
		} catch (IOException e) {
			throw new LibMsException("Could not delete file");
		}
	}

	public void deleteFileAvatar(String fileName) throws LibMsException {
		try {
			Path path = Paths.get(fileStorageLocationAvatar + File.separator + fileName);
			Files.deleteIfExists(path);
		} catch (IOException e) {
			throw new LibMsException("Could not delete file");
		}
	}

	public Resource loadFileAsResource(String fileName) throws LibMsException {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new LibMsException(FILE_NOT_FOUND);
			}
		} catch (MalformedURLException ex) {
			throw new LibMsException(FILE_NOT_FOUND, ex);
		}
	}
	
	public Resource loadFileAsResourceAvatar(String fileName) throws LibMsException {
		try {
			Path filePath = this.fileStorageLocationAvatar.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new LibMsException(FILE_NOT_FOUND);
			}
		} catch (MalformedURLException ex) {
			throw new LibMsException(FILE_NOT_FOUND, ex);
		}
	}

}
