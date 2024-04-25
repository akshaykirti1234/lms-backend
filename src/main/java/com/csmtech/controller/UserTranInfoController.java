package com.csmtech.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private UserTranInfoService userTranInfoService;

	@PostMapping("/saveUserInfoForm")
	public ResponseEntity<?> saveUserInfo(@RequestBody UserTranInfoDto userTranInfoDto) {
		return userTranInfoService.saveUserInfo(userTranInfoDto);
	}

}
