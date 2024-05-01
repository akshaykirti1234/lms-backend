package com.csmtech.controller;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csmtech.service.SessionAssessmentMasterService;
import com.csmtech.util.SessionExcelDownloader;

@RestController
@CrossOrigin("*")
public class AssessmentSessionExcelController {

	@Autowired
	private SessionAssessmentMasterService sessionAssessmentMasterService;
	
	/*
	 * @Autowired private AssessmentMasterService assessmentMasterService;
	 */

	@GetMapping("/downloadExcelSessionQuestions")
	public ResponseEntity<byte[]> downloadSessionExcel(HttpServletResponse response) throws Exception {
		Map<String, Object> excelData = new HashMap<>();
		Map<String, String[]> map = new HashMap<>();
		Object[] list = null;
		list = sessionAssessmentMasterService.retriveModuleTypeList().stream().map(e -> e.get("MODULENAME")).toArray();
		map.put("moduleTypeList", Arrays.copyOf(list, list.length, String[].class));

		list = sessionAssessmentMasterService.retriveSubModuleList().stream().map(e -> e.get("SUBMODULENAME")).toArray();
		map.put("subModuleList", Arrays.copyOf(list, list.length, String[].class));

		list = sessionAssessmentMasterService.retriveScheduleForList().stream().map(e -> e.get("SCHEDULEFOR")).toArray();
		map.put("scheduleForList", Arrays.copyOf(list, list.length, String[].class));

		list = sessionAssessmentMasterService.retriveSessionList().stream().map(e -> e.get("SESSIONNAME")).toArray();
		map.put("sessionList", Arrays.copyOf(list, list.length, String[].class));

		excelData.put("sampleData", map);
		Workbook workbook = SessionExcelDownloader.buildExcelDocument(excelData);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);
		HttpHeaders headers1 = new HttpHeaders();
		headers1.setContentType(
				MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		headers1.setContentDispositionFormData("attachment", "SessionList.xlsx");

		return new ResponseEntity<>(outputStream.toByteArray(), headers1, HttpStatus.OK);
	}

	@PostMapping("/uploadSessionExcel")
	public ResponseEntity<?> uploadSessionExcel(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body("Please select a file to upload.");
		}
		ResponseEntity<Map<String, Object>> response = sessionAssessmentMasterService.uploadSessionExcelData(file);

		return ResponseEntity.ok("File uploaded successfully.");
	}

}