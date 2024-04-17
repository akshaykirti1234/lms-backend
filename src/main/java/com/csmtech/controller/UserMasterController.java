package com.csmtech.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	Logger logger=LoggerFactory.getLogger(UserMasterController.class);
	
	@Autowired
	private UserMasterService userMasterService;

	@GetMapping("/get")
	public ResponseEntity<String> checkedApi() {
		logger.info("checkedApi method of UserMasterController is executed");
		return ResponseEntity.ok().body("Api is working");
	}

	@PostMapping("/userMaster")
	public ResponseEntity<UserMaster> saveUserMaster(@RequestBody UserMasterDTO userMasterDto) {
		logger.info("saveUserMaster method of UserMasterController is executed");
		System.err.println(userMasterDto);
		UserMaster userMaster = userMasterService.saveUserMaster(userMasterDto);
		return ResponseEntity.ok().body(userMaster);
	}

	@GetMapping("/userMaster")
	public ResponseEntity<List<Map<String, Object>>> getUseMasterList() {
		logger.info("getUseMasterList method of UserMasterController is executed");
		List<Map<String, Object>> UserMasterList = userMasterService.getUseMasterList();
		return ResponseEntity.ok().body(UserMasterList);
	}

	@DeleteMapping("/userMaster/{userId}")
	public ResponseEntity<Map<String, Object>> deleteUserMaster(@PathVariable("userId") Integer userId) {
		logger.info("deleteUserMaster method of UserMasterController is executed");
		userMasterService.deleteUserMaster(userId);
		Map<String, Object> response = new HashMap<>();
		response.put("status", 200);
		response.put("deleted", "UserMaster is deleted successfuly");
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/userMaster/{userId}")
	public ResponseEntity<Map<String, Object>> getUserMasterById(@PathVariable("userId") Integer userId) {
		logger.info("getUserMasterById method of UserMasterController is executed");
		Map<String, Object> userMaster = userMasterService.getUserMasterById(userId);
		return ResponseEntity.ok().body(userMaster);

	}

	@GetMapping("/emailId")
	public ResponseEntity<List<Map<String, Object>>> getEmaiLList() {
		logger.info("getEmaiLList method of UserMasterController is executed");
		List<Map<String, Object>> emailIdList = userMasterService.gettEmailList();
		return ResponseEntity.ok().body(emailIdList);
	}

	@PostMapping("/updatePassword")
	public ResponseEntity<?> updatePassword(@RequestBody String passwordPayload) {
		logger.info("updatePassword method of UserMasterController is executed");
		ResponseEntity<?> response = userMasterService.updatePassword(passwordPayload);
		return response;
	}

}