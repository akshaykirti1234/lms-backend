package com.csmtech.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

	@Value("${tempfile.path}")
	private String tempPath;

	@Value("${actualfile.path}")
	private String finalUploadPath;

	@Override
	public ResponseEntity<Map<String, Object>> setTempFileService(MultipartFile file, String name) {
		Map<String, Object> response = new HashMap<>();
		File f1 = null;
		String fileNameType = file.getOriginalFilename();
		if (fileNameType != null) {
			String[] fileArray = fileNameType.split("[.]");
			if (fileArray.length > 1) {
				String actualType = fileArray[fileArray.length - 1];
				String folderName = setFolderName(actualType);

				File folder = new File(tempPath + folderName);
				if (!folder.exists()) {
					folder.mkdirs(); // Create the folder if it doesn't exist
				}

				f1 = new File(folder.getPath() + "/" + name + "." + actualType);
				setTempFile(file, response, f1);
			}
		}

		return ResponseEntity.ok().body(response);
	}

	private String setFolderName(String actualType) {
		String folderName = actualType.equals("mp4") || actualType.equals("avi") || actualType.equals("wmv")
				|| actualType.equals("mov") || actualType.equals("flv") || actualType.equals("mpeg")
				|| actualType.equals("mkv") || actualType.equals("webm") || actualType.equals("3gp") ? "VIDEO"
						: "DOCUMENT";
		return folderName;
	}

	private void setTempFile(MultipartFile file, Map<String, Object> response, File f1) {
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

//	@Override
//	public ResponseEntity<?> downloadDocument(String fileName) throws Exception {
//		String filePath = "";
//		String fileFormat = "";
//		String folderName = "";
//		filePath = finalUploadPath;
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("content-disposition", "inline;filename=" + fileName);
//		int lastDotIndex = fileName.lastIndexOf('.');
//		if (lastDotIndex != -1) {
//			fileFormat = fileName.substring(lastDotIndex + 1);
//		} else {
//			throw new Exception("No file format found");
//		}
//		if (fileFormat.equals("mp4") || fileFormat.equals("avi") || fileFormat.equals("wmv") || fileFormat.equals("mov")
//				|| fileFormat.equals("flv") || fileFormat.equals("mpeg") || fileFormat.equals("mkv")
//				|| fileFormat.equals("webm") || fileFormat.equals("3gp")) {
//			folderName = "VIDEO";
//		} else if (fileFormat.equals("mp3") || fileFormat.equals("wav")) {
//			folderName = "AUDIO";
//		} else {
//			folderName = "DOCUMENT";
//		}
//		File file = new File(filePath + folderName + "/" + fileName);
//		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//		String contentType = "";
//		if (null != fileName && fileName.contains(".")) {
//			String fileExtension = fileName.split("\\.")[1];
//			if (fileExtension.equalsIgnoreCase("pdf")) {
//				contentType = "application/pdf";
//			}
//			if (fileExtension.equalsIgnoreCase("xls")) {
//				contentType = "application/vnd.ms-excel";
//			}
//			if (fileExtension.equalsIgnoreCase("xlsx")) {
//				contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
//			} else if (fileExtension.equalsIgnoreCase("png")) {
//				contentType = "image/png";
//			} else if (fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("jpeg")) {
//				contentType = "image/jpeg";
//			} else if (fileExtension.equalsIgnoreCase("ppt") || fileExtension.equalsIgnoreCase("pptx")) {
//				contentType = "application/vnd.ms-powerpoint";
//			} else if (fileExtension.equalsIgnoreCase("mp4") || fileExtension.equalsIgnoreCase("avi")) {
//				contentType = "video/mp4"; // Adjust as needed based on the video format
//			} else if (fileExtension.equalsIgnoreCase("mp3")) {
//				contentType = "audio/mpeg";
//			} else if (fileExtension.equalsIgnoreCase("wav")) {
//				contentType = "audio/wav";
//			} else if (fileFormat.equalsIgnoreCase("doc")) {
//				contentType = "application/msword";
//			} else if (fileFormat.equalsIgnoreCase("docx")) {
//				contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
//			} else if (fileFormat.equalsIgnoreCase("txt")) {
//				contentType = "text/plain";
//			} else {
//				contentType = "application/octet-stream";
//			}
//		}
//
//		return ResponseEntity.ok().headers(headers).contentLength(file.length())
//				.contentType(MediaType.parseMediaType(contentType)).body(resource);
//	}

	@Override
	public ResponseEntity<?> downloadDocument(String fileName) throws Exception {
		String filePath = "";
		String fileFormat = "";
		String folderName = "";
		filePath = finalUploadPath;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline;filename=" + fileName);
		headers.add(HttpHeaders.ACCEPT_RANGES, "bytes"); // Indicate support for range requests

		int lastDotIndex = fileName.lastIndexOf('.');
		if (lastDotIndex != -1) {
			fileFormat = fileName.substring(lastDotIndex + 1).toLowerCase();
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
		Path path = file.toPath();
		if (!Files.exists(path)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
		}

		String range = headers.getFirst(HttpHeaders.RANGE);
		long fileLength = Files.size(path);

		if (range == null) {
			InputStreamResource resource = new InputStreamResource(Files.newInputStream(path));
			return ResponseEntity.ok().headers(headers).contentLength(fileLength)
					.contentType(MediaType.parseMediaType(getContentType(fileFormat))).body(resource);
		}

		String[] ranges = range.replace("bytes=", "").split("-");
		long rangeStart = Long.parseLong(ranges[0]);
		long rangeEnd = ranges.length > 1 ? Long.parseLong(ranges[1]) : fileLength - 1;

		if (rangeEnd > fileLength - 1) {
			rangeEnd = fileLength - 1;
		}

		long contentLength = rangeEnd - rangeStart + 1;
		headers.add("Content-Range", "bytes " + rangeStart + "-" + rangeEnd + "/" + fileLength);

		InputStream inputStream = Files.newInputStream(path);
		inputStream.skip(rangeStart);
		InputStreamResource resource = new InputStreamResource(inputStream);

		return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).headers(headers).contentLength(contentLength)
				.contentType(MediaType.parseMediaType(getContentType(fileFormat))).body(resource);
	}

	private String getContentType(String fileFormat) {
		Map<String, String> contentTypeMap = new HashMap<>();
		contentTypeMap.put("pdf", "application/pdf");
		contentTypeMap.put("xls", "application/vnd.ms-excel");
		contentTypeMap.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		contentTypeMap.put("png", "image/png");
		contentTypeMap.put("jpg", "image/jpeg");
		contentTypeMap.put("jpeg", "image/jpeg");
		contentTypeMap.put("ppt", "application/vnd.ms-powerpoint");
		contentTypeMap.put("pptx", "application/vnd.ms-powerpoint");
		contentTypeMap.put("mp4", "video/mp4");
		contentTypeMap.put("avi", "video/x-msvideo");
		contentTypeMap.put("mp3", "audio/mpeg");
		contentTypeMap.put("wav", "audio/wav");
		contentTypeMap.put("doc", "application/msword");
		contentTypeMap.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		contentTypeMap.put("txt", "text/plain");

		return contentTypeMap.get(fileFormat);
	}

}
