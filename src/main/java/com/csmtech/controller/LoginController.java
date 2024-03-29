package com.csmtech.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private UserMasterService userMasterService;

	@PostMapping("/loginValidate")
	public ResponseEntity<?> loginValidate(@RequestBody UserMasterDTO userMasterDTO) {
		System.err.println(userMasterDTO);
		ResponseEntity<?> response = userMasterService.loginValidate(userMasterDTO);
		return response;
	}

}
