package com.csmtech.controller;

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

import com.csmtech.dto.UserTranInfoDto;
import com.csmtech.service.UserTranInfoService;

@CrossOrigin
@RestController
@RequestMapping("/api/userInfo")
public class UserTranInfoController {
    
    private static final Logger logger = LoggerFactory.getLogger(UserTranInfoController.class);

    @Autowired
    private UserTranInfoService userTranInfoService;

    @PostMapping("/saveUserInfoForm")
    public ResponseEntity<?> saveUserInfo(@RequestBody UserTranInfoDto userTranInfoDto) {
        logger.info("saveUserInfo method of UserTranInfoController is executed");
        try {
            return userTranInfoService.saveUserInfo(userTranInfoDto);
        } catch (Exception e) {
            logger.error("Error occurred while saving user information", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }
}



