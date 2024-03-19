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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
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

	@Value("${actuallogofile.path}")
	private String actualFilePath;

	@Value("${templogofile.path}")
	private String tempPath;

	@Autowired
	private ModuleMasterService moduleMasterService;

	@PostMapping("/module")
	public ResponseEntity<ModuleMaster> saveModuleMaster(@RequestBody ModuleMasterDto moduleMasterDto)
			throws Exception {
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
					System.out.println(fileFormat);
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
						e.printStackTrace();
					}
				}
			}
		}

		return ResponseEntity.ok().body(moduleMaster);

	}

	@GetMapping("/module")
	public ResponseEntity<List<ModuleMaster>> getModule() {
		List<ModuleMaster> moduleMasterList = moduleMasterService.getModuleMaster();

		return ResponseEntity.ok().body(moduleMasterList);

	}

	@GetMapping("/module/{moduleId}")
	public ModuleMaster getModuleById(@PathVariable("moduleId") Integer moduleId) {
		ModuleMaster moduleMaster = moduleMasterService.getModuleById(moduleId);
		return moduleMaster;

	}

	@DeleteMapping("/module/{moduleId}")
	public ResponseEntity<Map<String, Object>> deleteModule(@PathVariable("moduleId") Integer moduleId) {
		moduleMasterService.deleteModuleById(moduleId);
		Map<String, Object> response = new HashMap<>();
		response.put("status", 200);
		response.put("deleted", "module is deleted successfuly");
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/setlogo")
	public ResponseEntity<Map<String, Object>> setTempFile(@RequestParam("file") MultipartFile file)
			throws IOException {
		Map<String, Object> response = new HashMap<>();
		File f1 = null;
		String fileNameType = file.getOriginalFilename();
		if (fileNameType != null) {
			String[] fileArray = fileNameType.split("[.]");
			if (fileArray.length > 1) {
				f1 = new File(tempPath + "/" + fileNameType);

				System.out.println(f1.getAbsolutePath());
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

	@GetMapping(path = "/viewLogo/{fileName}")
	public ResponseEntity<?> downloadDocument(HttpServletResponse response, @PathVariable("fileName") String fileName)
			throws Exception {

		String filePath = "";
		String fileFormat = "";
		filePath = actualFilePath;
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-disposition", "inline;filename=" + fileName);
		int lastDotIndex = fileName.lastIndexOf('.');
		if (lastDotIndex != -1) {
			fileFormat = fileName.substring(lastDotIndex + 1);
			System.out.println(fileFormat);
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
	}

}
