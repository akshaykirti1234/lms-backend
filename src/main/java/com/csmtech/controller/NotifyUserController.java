package com.csmtech.controller;

import java.util.HashMap;
import java.util.Map;

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

	@Autowired
	private UserMasterService userMasterService;

	@Autowired
	private EmailServiceUtil emailService;

	@GetMapping("/getAllUsers")
	public ResponseEntity<?> getMethodName() {
		return userMasterService.getAllUsers();
	}

	@PostMapping("/send")
	public ResponseEntity<Map<String, Object>> sendEmail(@RequestBody EmailDto emailFormDTO) {
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
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
}
