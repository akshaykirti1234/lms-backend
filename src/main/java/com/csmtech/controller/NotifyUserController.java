package com.csmtech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.service.UserMasterService;

@CrossOrigin
@RestController
@RequestMapping("/api/notify")
public class NotifyUserController {

	@Autowired
	private UserMasterService userMasterService;

	@GetMapping("/getAllUsers")
	public ResponseEntity<?> getMethodName() {
		System.out.println("lhbgvyh");
		return userMasterService.getAllUsers();
	}

}
