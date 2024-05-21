package com.csmtech.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.dto.AssessmentSettingDto;
import com.csmtech.dto.AssessmentSettingScheduleDto;
import com.csmtech.entity.AssessmentSetting;
import com.csmtech.service.AssessmentSettingService;

@RestController
@CrossOrigin("*")
public class AssessmentSettingController {
	
	private static final Logger logger = LoggerFactory.getLogger(AssessmentSettingController.class);
	
	@Autowired
	private AssessmentSettingService assessmentSettingService;
	
	@GetMapping("/getApi")
    public ResponseEntity<String> getApi(){
		return ResponseEntity.ok().body("Hii Assessment Setting is working");
    	
    }
	
// for schedulewise data
    
    @GetMapping("/scheduleForAssessmentSetting/{submoduleId}")
    public ResponseEntity<List<Map<String,Object>>> getScheduleforAssessmentSetting(@PathVariable Integer submoduleId){
        logger.info("getScheduleforAssessmentSetting method of AssessmentSettingController is executed");
        try {
            List<Map<String,Object>> scheduleList = assessmentSettingService.getScheduleforAssessmentSetting(submoduleId);
            return ResponseEntity.ok().body(scheduleList);
        } catch (Exception e) {
            logger.error("Error occurred while fetching schedule for assessment setting", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @PostMapping("/assessmentSetting")
    public ResponseEntity<AssessmentSetting> saveAssessmentSetting(@RequestBody AssessmentSettingDto assessmentSettingDto){
        logger.info("saveAssessmentSetting method of AssessmentSettingController is executed");
        try {
            AssessmentSetting assessmentSetting = assessmentSettingService.saveAssessmentSetting(assessmentSettingDto);
            return ResponseEntity.ok().body(assessmentSetting);
        } catch (Exception e) {
            logger.error("Error occurred while saving assessment setting", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping("/assessmentSetting")
    public ResponseEntity<List<Map<String,Object>>> getAssessmentSetting(){
        logger.info("getAssessmentSetting method of AssessmentSettingController is executed");
        try {
            List<Map<String,Object>> assessmentSettingList = assessmentSettingService.getAssessmentSetting();
            return ResponseEntity.ok().body(assessmentSettingList);
        } catch (Exception e) {
            logger.error("Error occurred while fetching assessment settings", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping("/assessmentSetting/{assessmentSettingId}")
    public ResponseEntity<Map<String,Object>> getAssessmentSettingById(@PathVariable("assessmentSettingId") Integer assessmentSettingId){
        logger.info("getAssessmentSettingById method of AssessmentSettingController is executed");
        try {
            Map<String,Object> assessmentSetting = assessmentSettingService.getAssessmentSettingById(assessmentSettingId);
            return ResponseEntity.ok().body(assessmentSetting);
        } catch (Exception e) {
            logger.error("Error occurred while fetching assessment setting by ID: " + assessmentSettingId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @PutMapping("/assessmentSetting/{assessmentSettingId}")
    public ResponseEntity<Map<String,Object>> updateAssessmentSetting(@PathVariable("assessmentSettingId") Integer assessmentSettingId, @RequestBody AssessmentSettingScheduleDto scheduleDto ){
        logger.info("updateAssessmentSetting method of AssessmentSettingController is executed");
        try {
            Integer noOfQuestions = scheduleDto.getNumberOfQuestions();
            Double passingPercentage = scheduleDto.getPassingPercentage();
            assessmentSettingService.updateAssessmentSetting(assessmentSettingId, noOfQuestions , passingPercentage);
            
            Map<String,Object> response = new HashMap<>();
            response.put("status", 200);
            response.put("Updated", "AssessmentSetting updated successfully");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            logger.error("Error occurred while updating assessment setting with ID: " + assessmentSettingId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/assessmentSetting/{assessmentSettingId}")
    public ResponseEntity<Map<String,Object>> deleteAssessmentSetting(@PathVariable("assessmentSettingId") Integer assessmentSettingId){
        logger.info("deleteAssessmentSetting method of AssessmentSettingController is executed");
        try {
            assessmentSettingService.deleteAssessmentSetting(assessmentSettingId);
            
            Map<String,Object> response =new HashMap<>();
            response.put("status", 200);
            response.put("Deleted", "AssessmentSetting successfully deleted");
            
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            logger.error("Error occurred while deleting assessment setting with ID: " + assessmentSettingId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
