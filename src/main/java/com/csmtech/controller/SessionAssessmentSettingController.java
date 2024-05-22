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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.csmtech.dto.SessionAssessmentSettingDto;
import com.csmtech.dto.SessionAssessmentSettingSessionDto;
import com.csmtech.entity.SessionAssessmentSetting;
import com.csmtech.service.SessionAssessmentSettingService;

@RestController
@CrossOrigin("*")
public class SessionAssessmentSettingController {
    
    private static final Logger logger = LoggerFactory.getLogger(SessionAssessmentSettingController.class);
    
    @Autowired
    private SessionAssessmentSettingService sessionAssessmentSettingService;
    
    @GetMapping("/getApiChecked")
    public ResponseEntity<String> getApi(){
        return ResponseEntity.ok().body("Hii Session Assessment Setting is working");
    }
    
    @GetMapping("/sessionForAssessmentSetting/{scheduleForId}")
    public ResponseEntity<List<Map<String,Object>>> getSessionforAssessmentSetting(@PathVariable Integer scheduleForId){
        logger.info("getSessionforAssessmentSetting method of SessionAssessmentSettingController is executed");
        try {
            List<Map<String,Object>> sessionList = sessionAssessmentSettingService.getSessionforAssessmentSetting(scheduleForId);
            return ResponseEntity.ok().body(sessionList);
        } catch (Exception e) {
            logger.error("Error fetching session for assessment setting", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch session data", e);
        }
    }
    
    @PostMapping("/sessionAssessmentSetting")
    public ResponseEntity<SessionAssessmentSetting> saveSessionAssessmentSetting(@RequestBody SessionAssessmentSettingDto sessionAssessmentSettingDto){
        logger.info("saveSessionAssessmentSetting method of SessionAssessmentSettingController is executed");
        try {
            SessionAssessmentSetting sessionAssessmentSetting = sessionAssessmentSettingService.saveSessionAssessmentSetting(sessionAssessmentSettingDto);
            return ResponseEntity.ok().body(sessionAssessmentSetting);
        } catch (Exception e) {
            logger.error("Error saving session assessment setting", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save session assessment setting", e);
        }
    }
    
    @GetMapping("/sessionAssessmentSetting")
    public ResponseEntity<List<Map<String,Object>>> getSessionAssessmentSetting(){
        logger.info("getSessionAssessmentSetting method of SessionAssessmentSettingController is executed");
        try {
            List<Map<String,Object>> sessionAssessmentSetting = sessionAssessmentSettingService.getSessionAssessmentSetting();
            return ResponseEntity.ok().body(sessionAssessmentSetting);
        } catch (Exception e) {
            logger.error("Error fetching session assessment setting", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch session assessment setting", e);
        }
    }
    
    @GetMapping("/sessionAssessmentSetting/{sessionAssessmentSettingId}")
    public ResponseEntity<Map<String,Object>> getSessionAssessmentSettingById(@PathVariable("sessionAssessmentSettingId") Integer sessionAssessmentSettingId){
        logger.info("getSessionAssessmentSettingById method of SessionAssessmentSettingController is executed");
        try {
            Map<String,Object> sessionAssessmentSetting = sessionAssessmentSettingService.getSessionAssessmentSettingById(sessionAssessmentSettingId);
            return ResponseEntity.ok().body(sessionAssessmentSetting);
        } catch (Exception e) {
            logger.error("Error fetching session assessment setting by id", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch session assessment setting by id", e);
        }
    }
    
    @PutMapping("/sessionAssessmentSetting/{sessionAssessmentSettingId}")
    public ResponseEntity<Map<String,Object>> updateSessionAssessmentSetting(@PathVariable("sessionAssessmentSettingId") Integer assessmentSettingId, @RequestBody SessionAssessmentSettingSessionDto sessionDto){
        logger.info("updateSessionAssessmentSetting method of SessionAssessmentSettingController is executed");
        try {
            Integer noOfQuestions = sessionDto.getNumberOfQuestions();
            Double passingPercentage = sessionDto.getPassingPercentage();
            sessionAssessmentSettingService.updateSessionAssessmentSetting(assessmentSettingId, noOfQuestions, passingPercentage);
            
            Map<String,Object> response = new HashMap<>();
            response.put("status", 200);
            response.put("Updated", "SessionAssessmentSetting updated successfully");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            logger.error("Error updating session assessment setting", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update session assessment setting", e);
        }
    }
    
    @DeleteMapping("/sessionAssessmentSetting/{sessionAssessmentSettingId}")
    public ResponseEntity<Map<String,Object>> deleteSessionAssessmentSetting(@PathVariable("sessionAssessmentSettingId") Integer sessionAssessmentSettingId){
        logger.info("deleteSessionAssessmentSetting method of SessionAssessmentSettingController is executed");
        try {
            sessionAssessmentSettingService.deleteSessionAssessmentSetting(sessionAssessmentSettingId);
            
            Map<String,Object> response = new HashMap<>();
            response.put("status", 200);
            response.put("Deleted", "SessionAssessmentSetting successfully deleted");
            
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            logger.error("Error deleting session assessment setting", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete session assessment setting", e);
        }
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", ex.getStatus().value());
        errorDetails.put("message", ex.getReason());
        errorDetails.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(errorDetails, ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        logger.error("Unhandled exception", ex);
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDetails.put("message", "An unexpected error occurred");
        errorDetails.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
