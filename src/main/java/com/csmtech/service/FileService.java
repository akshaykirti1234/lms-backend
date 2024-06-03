package com.csmtech.service;

import java.io.FileNotFoundException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	ResponseEntity<Map<String, Object>> setTempFileService(MultipartFile file, String name);

	ResponseEntity<?> downloadDocument(String fileName) throws FileNotFoundException, Exception;

}
