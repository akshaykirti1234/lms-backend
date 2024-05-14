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
	
   //for shedulewise data
	
	@GetMapping("/scheduleForAssessmentSetting/{submoduleId}")
		public ResponseEntity<List<Map<String,Object>>> getScheduleforAssessmentSetting(@PathVariable Integer submoduleId){
		logger.info("getScheduleforAssessmentSetting method of AssessmentSettingController is executed");
			List<Map<String,Object>> sheduleList=assessmentSettingService.getScheduleforAssessmentSetting(submoduleId);
			return ResponseEntity.ok().body(sheduleList);
		}
	
	@PostMapping("/assessmentSetting")
	public ResponseEntity<AssessmentSetting> saveAssessmentSetting(@RequestBody AssessmentSettingDto assessmentSettingDto){
		logger.info("saveAssessmentSetting method of AssessmentSettingController is executed");
		AssessmentSetting assessmentSetting  =assessmentSettingService.saveAssessmentSetting(assessmentSettingDto);
		return ResponseEntity.ok().body(assessmentSetting);
		
	}
	
	@GetMapping("/assessmentSetting")
	public ResponseEntity<List<Map<String,Object>>> getAssessmentSetting(){
		logger.info("getAssessmentSetting method of AssessmentSettingController is executed");
		List<Map<String,Object>> assessmentSetting  =assessmentSettingService.getAssessmentSetting();
		return ResponseEntity.ok().body(assessmentSetting);
	
	
	
	}
	
	@GetMapping("/assessmentSetting/{assessmentSettingId}")
	public ResponseEntity<Map<String,Object>> getAssessmentSettingById(@PathVariable("assessmentSettingId") Integer assessmentSettingId){
		logger.info("getAssessmentSettingById method of AssessmentSettingController is executed");
		Map<String,Object> assessmentSetting  =assessmentSettingService.getAssessmentSettingById(assessmentSettingId);
		return ResponseEntity.ok().body(assessmentSetting);
	
	}
	
	@PutMapping("/assessmentSetting/{assessmentSettingId}")
	public ResponseEntity<Map<String,Object>> updateAssessmentSetting(@PathVariable("assessmentSettingId") Integer assessmentSettingId, @RequestBody AssessmentSettingScheduleDto scheduleDto ){
		logger.info("updateAssessmentSetting method of AssessmentSettingController is executed");
	    Integer noOfQuestions=scheduleDto.getNumberOfQuestions();
	    Double passingPercentage = scheduleDto.getPassingPercentage();
	    assessmentSettingService.updateAssessmentSetting(assessmentSettingId, noOfQuestions , passingPercentage);
	    
	        Map<String,Object> response = new HashMap<>();
	        response.put("status", 200);
	        response.put("Updated", "AssessmentSetting updated successfully");
	        return ResponseEntity.ok().body(response);
	   
	}

	
	@DeleteMapping("/assessmentSetting/{assessmentSettingId}")
	public ResponseEntity<Map<String,Object>> deleteAssessmentSetting(@PathVariable("assessmentSettingId") Integer assessmentSettingId){
		logger.info("deleteAssessmentSetting method of AssessmentSettingController is executed");
		assessmentSettingService.deleteAssessmentSetting(assessmentSettingId);
		
		Map<String,Object> response =new HashMap<>();
		response.put("status", 200);
		response.put("Deleted", "AssessmentSetting Successfily deleted");
		
		return ResponseEntity.ok().body(response);
	}

}
