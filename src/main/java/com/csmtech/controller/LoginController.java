package com.csmtech.controller;

import java.util.HashMap;
import java.util.Map;

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

import com.csmtech.dto.UserMasterDTO;
import com.csmtech.service.UserMasterService;

@CrossOrigin
@RestController
@RequestMapping("/api/login")
public class LoginController {

	private static final  Logger logger=LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private UserMasterService userMasterService;

	@PostMapping("/loginValidate")
	public ResponseEntity<?> loginValidate(@RequestBody UserMasterDTO userMasterDTO) {
		logger.info("loginValidate method of LoginController is executed");
		   Map<String, Object> errorResponse = new HashMap<>();
		try {
		ResponseEntity<?> response = userMasterService.loginValidate(userMasterDTO);
		return response;
		}catch (Exception e) {
			logger.error("Exception caught while validating login: ", e);
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
		
	}

}
