package com.csmtech.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Value("${tempfile.path}")
	private String tempPath;

	@Value("${actualfile.path}")
	private String finalUploadPath;

	@PostMapping("/setTempFile")
	public ResponseEntity<Map<String, Object>> setTempFile(@RequestParam("file") MultipartFile file,
			@RequestParam("name") String name) throws IOException {
		logger.info("setTempFile method of FileController is executed");
		Map<String, Object> response = new HashMap<>();
		File f1 = null;
		String fileNameType = file.getOriginalFilename();
		if (fileNameType != null) {
			String[] fileArray = fileNameType.split("[.]");
			if (fileArray.length > 1) {
				String actualType = fileArray[fileArray.length - 1];
				String folderName = actualType.equals("mp4") || actualType.equals("avi") || actualType.equals("wmv")
						|| actualType.equals("mov") || actualType.equals("flv") || actualType.equals("mpeg")
						|| actualType.equals("mkv") || actualType.equals("webm") || actualType.equals("3gp") ? "VIDEO"
								: "DOCUMENT";

				File folder = new File(tempPath + folderName);
				if (!folder.exists()) {
					folder.mkdirs(); // Create the folder if it doesn't exist
				}

				f1 = new File(folder.getPath() + "/" + name + "." + actualType);
				try (FileOutputStream fos = new FileOutputStream(f1);
						BufferedOutputStream bos = new BufferedOutputStream(fos)) {

					byte[] bytes = file.getBytes();
					bos.write(bytes);

					response.put("status", 200);
					response.put("fileName", f1.getName());

				} catch (Exception e) {
					response.put("status", 500);
					response.put("message", e.getMessage());
				}
			}
		}

		return ResponseEntity.ok().body(response);

	}

	@GetMapping(path = "/viewFile/{fileName}")
	public ResponseEntity<?> downloadDocument(HttpServletResponse response, @PathVariable("fileName") String fileName)
			throws Exception {
		logger.info("downloadDocument method of FileController is executed");
		String filePath = "";
		String fileFormat = "";
		String folderName = "";
		filePath = finalUploadPath;
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-disposition", "inline;filename=" + fileName);
		int lastDotIndex = fileName.lastIndexOf('.');
		if (lastDotIndex != -1) {
			fileFormat = fileName.substring(lastDotIndex + 1);
		} else {
			throw new Exception("No file format found");
		}
		if (fileFormat.equals("mp4") || fileFormat.equals("avi") || fileFormat.equals("wmv") || fileFormat.equals("mov")
				|| fileFormat.equals("flv") || fileFormat.equals("mpeg") || fileFormat.equals("mkv")
				|| fileFormat.equals("webm") || fileFormat.equals("3gp")) {
			folderName = "VIDEO";
		} else if (fileFormat.equals("mp3") || fileFormat.equals("wav")) {
			folderName = "AUDIO";
		} else {
			folderName = "DOCUMENT";
		}
		File file = new File(filePath + folderName + "/" + fileName);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		String contentType = "";
		if (null != fileName && fileName.contains(".")) {
			String fileExtension = fileName.split("\\.")[1];
			if (fileExtension.equalsIgnoreCase("pdf")) {
				contentType = "application/pdf";
			}
			if (fileExtension.equalsIgnoreCase("xls")) {
				contentType = "application/vnd.ms-excel";
			}
			if (fileExtension.equalsIgnoreCase("xlsx")) {
				contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
			} else if (fileExtension.equalsIgnoreCase("png")) {
				contentType = "image/png";
			} else if (fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("jpeg")) {
				contentType = "image/jpeg";
			} else if (fileExtension.equalsIgnoreCase("ppt") || fileExtension.equalsIgnoreCase("pptx")) {
				contentType = "application/vnd.ms-powerpoint";
			} else if (fileExtension.equalsIgnoreCase("mp4") || fileExtension.equalsIgnoreCase("avi")) {
				contentType = "video/mp4"; // Adjust as needed based on the video format
			} else if (fileExtension.equalsIgnoreCase("mp3")) {
				contentType = "audio/mpeg";
			} else if (fileExtension.equalsIgnoreCase("wav")) {
				contentType = "audio/wav";
			} else if (fileFormat.equalsIgnoreCase("doc")) {
				contentType = "application/msword";
			} else if (fileFormat.equalsIgnoreCase("docx")) {
				contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
			} else if (fileFormat.equalsIgnoreCase("txt")) {
				contentType = "text/plain";
			} else {
				contentType = "application/octet-stream";
			}
		}

		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType(contentType)).body(resource);
	}
}
