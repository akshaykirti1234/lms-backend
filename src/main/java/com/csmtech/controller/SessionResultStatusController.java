
package com.csmtech.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.entity.SessionResultStatus;
import com.csmtech.service.SessionResultStatusService;

@CrossOrigin
@RestController
@RequestMapping("/api/sessionResultStatus")
public class SessionResultStatusController {
	
	private static final Logger logger = LoggerFactory.getLogger(SessionResultStatusController.class);
	
	@Autowired
	private SessionResultStatusService sessionResultStatusService;

	@PostMapping("/getSessionResult/{userId}")
	public List<SessionResultStatus> getSessionResult(@PathVariable("userId") Integer userId) {
		logger.info("getSessionResult method of SessionResultStatusController is executed");
		System.out.println(userId);
		List<SessionResultStatus> response = sessionResultStatusService.getSessionResultStatus(userId);
		System.out.println(response);
		return response;
	}
	
	@PostMapping("/getSessionResultBySessionIdUserId/{sessionId}/{userId}")
	public List<SessionResultStatus> getSessionResultBySessionIdUserId(@PathVariable("sessionId") Integer sessionId,@PathVariable("userId") Integer userId) {
		logger.info("getSessionResultBySessionIdUserId method of SessionResultStatusController is executed");
		System.out.println(userId);
		List<SessionResultStatus> response = sessionResultStatusService.getSessionResultBySessionIdUserId(sessionId,userId);
		System.out.println(response);
		return response;
	}
}