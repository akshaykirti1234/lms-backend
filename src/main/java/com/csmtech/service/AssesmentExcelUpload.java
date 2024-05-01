package com.csmtech.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface AssesmentExcelUpload {

	ResponseEntity<Map<String, Object>> uploadExcelSessionExcelData(MultipartFile file, Integer moduleId,
			Integer submoduleId, Integer scheduleForId, Integer sessionId);

	ResponseEntity<Map<String, Object>> uploadExcelSessionExcelData(MultipartFile file, Integer moduleId,
			Integer submoduleId, Integer scheduleForId);

}
