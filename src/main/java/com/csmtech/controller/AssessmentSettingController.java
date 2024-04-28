package com.csmtech.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	@Autowired
	private AssessmentSettingService assessmentSettingService;
	
	@GetMapping("/getApi")
    public ResponseEntity<String> getApi(){
		return ResponseEntity.ok().body("Hii Assessment Setting is working");
    	
    }
	
	@PostMapping("/assessmentSetting")
	public ResponseEntity<AssessmentSetting> saveAssessmentSetting(@RequestBody AssessmentSettingDto assessmentSettingDto){
		AssessmentSetting assessmentSetting  =assessmentSettingService.saveAssessmentSetting(assessmentSettingDto);
		return ResponseEntity.ok().body(assessmentSetting);
		
	}
	
	@GetMapping("/assessmentSetting")
	public ResponseEntity<List<Map<String,Object>>> getAssessmentSetting(){
		List<Map<String,Object>> assessmentSetting  =assessmentSettingService.getAssessmentSetting();
		return ResponseEntity.ok().body(assessmentSetting);
	
	
	
	}
	
	@GetMapping("/assessmentSetting/{assessmentSettingId}")
	public ResponseEntity<Map<String,Object>> getAssessmentSettingById(@PathVariable("assessmentSettingId") Integer assessmentSettingId){
		Map<String,Object> assessmentSetting  =assessmentSettingService.getAssessmentSettingById(assessmentSettingId);
		return ResponseEntity.ok().body(assessmentSetting);
	
	}
	
	@PutMapping("/assessmentSetting/{assessmentSettingId}")
	public ResponseEntity<Map<String,Object>> updateAssessmentSetting(@PathVariable("assessmentSettingId") Integer assessmentSettingId, @RequestBody AssessmentSettingScheduleDto scheduleDto ){
	    Integer noOfQuestions=scheduleDto.getNumberOfQuestions();
	    assessmentSettingService.updateAssessmentSetting(assessmentSettingId, noOfQuestions);
	    
	        Map<String,Object> response = new HashMap<>();
	        response.put("status", 200);
	        response.put("Updated", "AssessmentSetting updated successfully");
	        return ResponseEntity.ok().body(response);
	   
	}

	
	@DeleteMapping("/assessmentSetting/{assessmentSettingId}")
	public ResponseEntity<Map<String,Object>> deleteAssessmentSetting(@PathVariable("assessmentSettingId") Integer assessmentSettingId){
		assessmentSettingService.deleteAssessmentSetting(assessmentSettingId);
		
		Map<String,Object> response =new HashMap<>();
		response.put("status", 200);
		response.put("Deleted", "AssessmentSetting Successfily deleted");
		
		return ResponseEntity.ok().body(response);
	}

}
