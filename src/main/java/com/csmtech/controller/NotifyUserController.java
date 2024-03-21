package com.csmtech.controller;

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
		System.out.println("lhbgvyh");
		return userMasterService.getAllUsers();
	}
	
	@PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDto emailFormDTO) {
        try {
            if ("information".equalsIgnoreCase(emailFormDTO.getNotifyStatus())) {
                emailService.sendInformationEmail(emailFormDTO.getSelectedEmail(), emailFormDTO.getDescription());
            } else if ("password".equalsIgnoreCase(emailFormDTO.getNotifyStatus())) {
                emailService.sendPasswordEmail(emailFormDTO.getSelectedEmail());
            }
            return ResponseEntity.ok("Email(s) sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email(s)");
        }
    }
	

}
