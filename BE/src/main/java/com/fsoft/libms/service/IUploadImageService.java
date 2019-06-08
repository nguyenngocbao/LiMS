package com.fsoft.libms.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.fsoft.libms.exception.LibMsException;

public interface IUploadImageService {
	
	public String storeFile(MultipartFile file, String fileName) throws LibMsException;

	public Resource loadFileAsResource(String fileName) throws LibMsException;
	
	public void deleteFile( String fileName ) throws LibMsException;
	
	public String storeFileAvatar(MultipartFile file, String fileName) throws LibMsException;
	
	public Resource loadFileAsResourceAvatar(String fileName) throws LibMsException;
	
	public void deleteFileAvatar( String fileName ) throws LibMsException;
}
