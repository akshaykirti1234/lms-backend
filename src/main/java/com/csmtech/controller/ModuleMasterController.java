package com.csmtech.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csmtech.dto.ModuleMasterDto;
import com.csmtech.entity.ModuleMaster;
import com.csmtech.service.ModuleMasterService;

@RestController
@CrossOrigin("*")
public class ModuleMasterController {
	private static final Logger logger = LoggerFactory.getLogger(ModuleMasterController.class);

	@Value("${actuallogofile.path}")
	private String actualFilePath;

	@Value("${templogofile.path}")
	private String tempPath;

	@Autowired
	private ModuleMasterService moduleMasterService;

	@PostMapping("/module")
	public ResponseEntity<ModuleMaster> saveModuleMaster(@RequestBody ModuleMasterDto moduleMasterDto)
			throws Exception {
		logger.info("saveModuleMaster method of ModuleMasterController is executed");
		ModuleMaster moduleMaster = moduleMasterService.saveModule(moduleMasterDto);

// for re;[move the file temppath to actualpath and delete the file from
// temppath

		List<String> fileUploadList = new ArrayList<>();
		String fileFormat = null;

		fileUploadList.add(moduleMasterDto.getLogo());
		for (String fileUpload : fileUploadList) {
			if (fileUpload != null && (!fileUpload.equals(""))) {
				int lastDotIndex = fileUpload.lastIndexOf('.');
				if (lastDotIndex != -1) {
					fileFormat = fileUpload.substring(lastDotIndex + 1);
				} else {
					throw new Exception("No file format found");
				}

				File file = new File(tempPath + "/" + fileUpload);
				if (file.exists()) {
					File srcFile = new File(tempPath + "/" + fileUpload);
					File destFile = new File(actualFilePath + "/" + fileUpload);
					try {
						Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
						Files.delete(srcFile.toPath());
					} catch (IOException e) {
						logger.info(
								"IOException occured while delete file inside saveModuleMaster method of ModuleMasterController:"
										+ e.getMessage());
						e.printStackTrace();
					}
				}
			}
		}

		return ResponseEntity.ok().body(moduleMaster);

	}

	@GetMapping("/module")
	public ResponseEntity<List<ModuleMaster>> getModule() {
		logger.info("getModule method of ModuleMasterController is executed");
		try {
			List<ModuleMaster> moduleMasterList = moduleMasterService.getModuleMaster();

			return ResponseEntity.ok().body(moduleMasterList);
		} catch (Exception e) {
			logger.error("Exception caught in getModule method: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
		}

	}

	@GetMapping("/module/{moduleId}")
	public ModuleMaster getModuleById(@PathVariable("moduleId") Integer moduleId) {
		logger.info("getModuleById method of ModuleMasterController is executed");
		ModuleMaster moduleMaster = moduleMasterService.getModuleById(moduleId);
		return moduleMaster;

	}

	@DeleteMapping("/module/{moduleId}")
	public ResponseEntity<Map<String, Object>> deleteModule(@PathVariable("moduleId") Integer moduleId) {
		logger.info("deleteModule method of ModuleMasterController is executed");
		Map<String, Object> errorResponse = new HashMap<>();

		try {
			moduleMasterService.deleteModuleById(moduleId);
			Map<String, Object> response = new HashMap<>();
			response.put("status", 200);
			response.put("deleted", "module is deleted successfuly");
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			logger.error("Exception caught in deleteModule method: ", e);
			errorResponse.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	@PostMapping("/setlogo")
	public ResponseEntity<Map<String, Object>> setTempFile(@RequestParam("file") MultipartFile file)
			throws IOException {
		logger.info("setTempFile method of ModuleMasterController is executed");
		Map<String, Object> response = new HashMap<>();
		File f1 = null;
		try {
			if (file.isEmpty()) {
				throw new Exception("File is empty");
			}
			String fileNameType = file.getOriginalFilename();
			if (fileNameType != null) {
				String[] fileArray = fileNameType.split("[.]");
				if (fileArray.length > 1) {
					File folder = new File(tempPath);
					if (!folder.exists()) {
						folder.mkdirs();
					}
					f1 = new File(folder.getPath() + "/" + fileNameType);
					try (FileOutputStream fos = new FileOutputStream(f1);
							BufferedOutputStream bos = new BufferedOutputStream(fos)) {

						byte[] bytes = file.getBytes();
						bos.write(bytes);

						response.put("status", 200);
						response.put("fileName", f1.getName());
						

					} catch (Exception e) {
						logger.info("IOException occured inside setTempFile method of ModuleMasterController:"
								+ e.getMessage());
						response.put("status", 500);
						response.put("message", e.getMessage());
						return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
					}
				}
			}

			
		} catch (Exception e) {
			logger.error("Exception caught in setTempFile method: ", e);
			response.put("status", 500);
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		return ResponseEntity.ok().body(response);

	}

	@GetMapping(path = "/viewLogo/{fileName}")
	public ResponseEntity<?> downloadDocument(HttpServletResponse response, @PathVariable("fileName") String fileName)
			throws Exception {
		logger.info("downloadDocument method of ModuleMasterController is executed");
		Map<String, Object> errorResponse = new HashMap<>();
		try {
			String filePath = "";
			String fileFormat = "";
			filePath = actualFilePath;
			HttpHeaders headers = new HttpHeaders();
			headers.add("content-disposition", "inline;filename=" + fileName);
			int lastDotIndex = fileName.lastIndexOf('.');
			if (lastDotIndex != -1) {
				fileFormat = fileName.substring(lastDotIndex + 1);
			} else {
				throw new Exception("No file format found");
			}

			File file = new File(filePath + "/" + fileName);
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			String contentType = "";
			if (null != fileName && fileName.contains(".")) {
				String fileExtension = fileName.split("\\.")[1];

				if (fileExtension.equalsIgnoreCase("png"))
					contentType = "image/png";
				else if (fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("jpeg")
						|| fileExtension.equalsIgnoreCase("jfif"))
					contentType = "image/jpeg";
				else if (fileExtension.equalsIgnoreCase("gif"))
					contentType = "image/gif";
				else if (fileExtension.equalsIgnoreCase("bmp"))
					contentType = "image/bmp";
				else if (fileExtension.equalsIgnoreCase("svg"))
					contentType = "image/svg+xml";
				else if (fileExtension.equalsIgnoreCase("tiff") || fileExtension.equalsIgnoreCase("tif"))
					contentType = "image/tiff";

			}

			return ResponseEntity.ok().headers(headers).contentLength(file.length())
					.contentType(MediaType.parseMediaType(contentType)).body(resource);
		} catch (Exception e) {
			logger.error("Exception caught in downloadDocument method: ", e);

			errorResponse.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

}
