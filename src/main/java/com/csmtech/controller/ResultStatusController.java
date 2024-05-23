package com.csmtech.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.entity.ResultStatus;
import com.csmtech.service.ResultStatusService;

@CrossOrigin
@RestController
public class ResultStatusController {
	
	@Autowired
	ResultStatusService resultStatusService;

	private static final Logger logger = LoggerFactory.getLogger(ResultStatusController.class);
	
	@GetMapping("/getFinalResult/{scheduleForId}/{userId}")
    public ResponseEntity<?> getFinalResultByScheduleIdUserId(@PathVariable("scheduleForId") Integer scheduleForId, @PathVariable("userId") Integer userId) {
        logger.info("getFinalResultByScheduleIdUserId method of ResultStatusController is executed");
        try {
            List<ResultStatus> response = resultStatusService.getFinalResultByScheduleIdUserId(scheduleForId, userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error occurred while fetching session result for scheduleForId {} and userId {}", scheduleForId, userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }
	
	@GetMapping("getResultStatus/{scheduleForId}/{userId}")
	public ResponseEntity<?> getResultStatusByScheduleIdUserId(@PathVariable("scheduleForId") Integer scheduleForId, @PathVariable("userId") Integer userId){
		logger.info("getResultStatusByScheduleIdUserId method of ResultStatusController is executed");
		try {
			ResultStatus resultStatus=resultStatusService.getResultStatusByScheduleIdUserId(scheduleForId,userId);
			return ResponseEntity.ok(resultStatus);
		} catch (Exception e) {
            logger.error("Error occurred while fetching session result for scheduleForId {} and userId {}", scheduleForId, userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
		
	}
	
}
