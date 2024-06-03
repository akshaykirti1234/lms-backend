package com.csmtech.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csmtech.service.FileService;

@RestController
@CrossOrigin("*")
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private FileService fileService;

	@PostMapping("/setTempFile")
	public ResponseEntity<Map<String, Object>> setTempFile(@RequestParam("file") MultipartFile file,
			@RequestParam("name") String name) throws IOException {
		logger.info("setTempFile method of FileController is executed");
		return fileService.setTempFileService(file, name);

	}

	@GetMapping(path = "/viewFile/{fileName}")
	public ResponseEntity<?> downloadDocument(HttpServletResponse response, @PathVariable("fileName") String fileName)
			throws Exception {
		logger.info("downloadDocument method of FileController is executed");
		return fileService.downloadDocument(fileName);
	}

}
