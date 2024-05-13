package com.csmtech.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.dto.AssessmentSettingScheduleDto;
import com.csmtech.dto.SessionAssessmentSettingDto;
import com.csmtech.dto.SessionAssessmentSettingSessionDto;
import com.csmtech.entity.SessionAssessmentSetting;
import com.csmtech.service.SessionAssessmentSettingService;

@RestController
@CrossOrigin("*")
public class SessionAssessmentSettingController {
	
	private static final  Logger logger = LoggerFactory.getLogger(SessionAssessmentSettingController.class);
	
	@Autowired
	private SessionAssessmentSettingService sessionAssessmentSettingService;
	
	@GetMapping("/getApiChecked")
    public ResponseEntity<String> getApi(){
		return ResponseEntity.ok().body("Hii Session Assessment Setting is working");
    	
    }
	
	//for sessionewise data
	  @GetMapping("/sessionForAssessmentSetting/{scheduleForId}")
			public ResponseEntity<List<Map<String,Object>>> getSessionforAssessmentSetting(@PathVariable Integer scheduleForId){
		  logger.info("getSessionforAssessmentSetting method of SessionAssessmentSettingController is executed");
				List<Map<String,Object>> sessionList=sessionAssessmentSettingService.getSessionforAssessmentSetting(scheduleForId);
				return ResponseEntity.ok().body(sessionList);
			}
		
	
	@PostMapping("/sessionAssessmentSetting")
	public ResponseEntity<SessionAssessmentSetting> saveSessionAssessmentSetting(@RequestBody SessionAssessmentSettingDto sessionAssessmentSettingDto){
		logger.info("saveSessionAssessmentSetting method of SessionAssessmentSettingController is executed");
		SessionAssessmentSetting sessionAssessmentSetting=sessionAssessmentSettingService.saveSessionAssessmentSetting(sessionAssessmentSettingDto);
		return ResponseEntity.ok().body(sessionAssessmentSetting);
		
	}
	
	@GetMapping("/sessionAssessmentSetting")
	public ResponseEntity<List<Map<String,Object>>> getSessionAssessmentSetting(){
		logger.info("getSessionAssessmentSetting method of SessionAssessmentSettingController is executed");
		List<Map<String,Object>> sessionAssessmentSetting=sessionAssessmentSettingService.getSessionAssessmentSetting();
		return ResponseEntity.ok().body(sessionAssessmentSetting);
		
	}
	
	@GetMapping("/sessionAssessmentSetting/{sessionAssessmentSettingId}")
	public ResponseEntity<Map<String,Object>> getSessionAssessmentSettingById(@PathVariable("sessionAssessmentSettingId") Integer sessionAssessmentSettingId){
		logger.info("getSessionAssessmentSettingById method of SessionAssessmentSettingController is executed");
		Map<String,Object> sessionAssessmentSetting=sessionAssessmentSettingService.getSessionAssessmentSettingById(sessionAssessmentSettingId);
		return ResponseEntity.ok().body(sessionAssessmentSetting);
		
	}
	
	@PutMapping("/sessionAssessmentSetting/{sessionAssessmentSettingId}")
	public ResponseEntity<Map<String,Object>> updateSessionAssessmentSetting(@PathVariable("sessionAssessmentSettingId") Integer assessmentSettingId, @RequestBody SessionAssessmentSettingSessionDto sessionDto ){
		logger.info("updateSessionAssessmentSetting method of SessionAssessmentSettingController is executed");
	    Integer noOfQuestions=sessionDto.getNumberOfQuestions();
	    Double passingPercentage = sessionDto.getPassingPercentage();
	    sessionAssessmentSettingService.updateSessionAssessmentSetting(assessmentSettingId, noOfQuestions, passingPercentage);
	    
	        Map<String,Object> response = new HashMap<>();
	        response.put("status", 200);
	        response.put("Updated", "SessionAssessmentSetting updated successfully");
	        return ResponseEntity.ok().body(response);
	   
	}
	
	@DeleteMapping("/sessionAssessmentSetting/{sessionAssessmentSettingId}")
	public ResponseEntity<Map<String,Object>> deleteSessionAssessmentSetting(@PathVariable("sessionAssessmentSettingId") Integer sessionAssessmentSettingId){
		logger.info("deleteSessionAssessmentSetting method of SessionAssessmentSettingController is executed");
		sessionAssessmentSettingService.deleteSessionAssessmentSetting(sessionAssessmentSettingId);
		
		Map<String,Object> response =new HashMap<>();
		response.put("status", 200);
		response.put("Deleted", "SessionAssessmentSetting Successfily deleted");
		
		return ResponseEntity.ok().body(response);
	}
	

}
