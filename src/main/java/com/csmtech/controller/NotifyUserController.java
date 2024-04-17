package com.csmtech.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.dto.EmailDto;
import com.csmtech.service.UserMasterService;
import com.csmtech.util.EmailServiceUtil;

@CrossOrigin
@RestController
@RequestMapping("/api/notify")
public class NotifyUserController {
	
	Logger logger=LoggerFactory.getLogger(NotifyUserController.class);

	@Autowired
	private UserMasterService userMasterService;

	@Autowired
	private EmailServiceUtil emailService;

	@GetMapping("/getAllUsers")
	public ResponseEntity<?> getMethodName() {
		logger.info("getMethodName method of NotifyUserController is executed");
		return userMasterService.getAllUsers();
	}

	@PostMapping("/send")
	public ResponseEntity<Map<String, Object>> sendEmail(@RequestBody EmailDto emailFormDTO) {
		logger.info("sendEmail method of NotifyUserController is executed");
		Map<String, Object> response = new HashMap<>();
		try {
			if ("information".equalsIgnoreCase(emailFormDTO.getNotifyStatus())) {
				emailService.sendInformationEmail(emailFormDTO.getSelectedEmails(), emailFormDTO.getDescription());
			} else if ("password".equalsIgnoreCase(emailFormDTO.getNotifyStatus())) {
				emailService.sendPasswordEmail(emailFormDTO.getSelectedEmails());
			}
			response.put("message", "Email(s) sent successfully");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("message", "Failed to send email(s)");
			logger.error("error occured in sendEmail method of NotifyUserController");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			
			
		}
	}
}
