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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.dto.UserMasterDTO;
import com.csmtech.entity.UserMaster;
import com.csmtech.service.UserMasterService;

@RestController
@CrossOrigin("*")
public class UserMasterController {
    
    private static final Logger logger = LoggerFactory.getLogger(UserMasterController.class);
    
    @Autowired
    private UserMasterService userMasterService;

    @GetMapping("/get")
    public ResponseEntity<String> checkedApi() {
        logger.info("checkedApi method of UserMasterController is executed");
        return ResponseEntity.ok().body("Api is working");
    }

    @PostMapping("/userMaster")
    public ResponseEntity<?> saveUserMaster(@RequestBody UserMasterDTO userMasterDto) {
        logger.info("saveUserMaster method of UserMasterController is executed");
        try {
            UserMaster userMaster = userMasterService.saveUserMaster(userMasterDto);
            return ResponseEntity.ok().body(userMaster);
        } catch (Exception e) {
            logger.error("Error occurred while saving user master", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }

    @GetMapping("/userMaster")
    public ResponseEntity<?> getUseMasterList() {
        logger.info("getUseMasterList method of UserMasterController is executed");
        try {
            List<Map<String, Object>> UserMasterList = userMasterService.getUseMasterList();
            return ResponseEntity.ok().body(UserMasterList);
        } catch (Exception e) {
            logger.error("Error occurred while fetching user master list", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }

    @DeleteMapping("/userMaster/{userId}")
    public ResponseEntity<?> deleteUserMaster(@PathVariable("userId") Integer userId) {
        logger.info("deleteUserMaster method of UserMasterController is executed");
        try {
            userMasterService.deleteUserMaster(userId);
            Map<String, Object> response = new HashMap<>();
            response.put("status", 200);
            response.put("deleted", "UserMaster is deleted successfully");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            logger.error("Error occurred while deleting user master with ID {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }

    @GetMapping("/userMaster/{userId}")
    public ResponseEntity<?> getUserMasterById(@PathVariable("userId") Integer userId) {
        logger.info("getUserMasterById method of UserMasterController is executed");
        try {
            Map<String, Object> userMaster = userMasterService.getUserMasterById(userId);
            return ResponseEntity.ok().body(userMaster);
        } catch (Exception e) {
            logger.error("Error occurred while fetching user master by ID {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }

    @GetMapping("/emailId")
    public ResponseEntity<?> getEmaiLList() {
        logger.info("getEmaiLList method of UserMasterController is executed");
        try {
            List<Map<String, Object>> emailIdList = userMasterService.gettEmailList();
            return ResponseEntity.ok().body(emailIdList);
        } catch (Exception e) {
            logger.error("Error occurred while fetching email list", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody String passwordPayload) {
        logger.info("updatePassword method of UserMasterController is executed");
        try {
            ResponseEntity<?> response = userMasterService.updatePassword(passwordPayload);
            return response;
        } catch (Exception e) {
            logger.error("Error occurred while updating password", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }
}
