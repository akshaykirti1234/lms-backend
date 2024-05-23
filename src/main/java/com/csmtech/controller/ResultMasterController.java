package com.csmtech.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.dto.SessionResultDto;
import com.csmtech.service.ResultMasterService;

@CrossOrigin
@RestController
@RequestMapping("/api/result")
public class ResultMasterController {
	
private static final Logger logger = LoggerFactory.getLogger(ResultMasterController.class);
	
	@Autowired
	private ResultMasterService resultMasterService;

	@PostMapping("/saveScheduleResult")
    public ResponseEntity<?> saveScheduleResult(@RequestBody List<SessionResultDto> responsePayload) {
        logger.info("saveScheduleResult method of ResultMasterController is executed");
        try {
            ResponseEntity<?> response = resultMasterService.saveScheduleResult(responsePayload);
            return response;
        } catch (Exception e) {
            logger.error("Error occurred while saving session result", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }

 }
}
	
	


