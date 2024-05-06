
package com.csmtech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.dto.SessionResultDto;
import com.csmtech.entity.SessionResultStatus;
import com.csmtech.service.SessionResultMasterService;
import com.csmtech.service.SessionResultStatusService;

@CrossOrigin
@RestController
@RequestMapping("/api/sessionResultStatus")
public class SessionResultStatusController {
	@Autowired
	private SessionResultStatusService sessionResultStatusService;

	@PostMapping("/getSessionResult/{userId}")
	public List<SessionResultStatus> getSessionResult(@PathVariable("userId") Integer userId) {
		System.out.println(userId);
		List<SessionResultStatus> response = sessionResultStatusService.getSessionResultStatus(userId);
		System.out.println(response);
		return response;
	}
	
	@PostMapping("/getSessionResultBySessionIdUserId/{sessionId}/{userId}")
	public List<SessionResultStatus> getSessionResultBySessionIdUserId(@PathVariable("sessionId") Integer sessionId,@PathVariable("userId") Integer userId) {
		System.out.println(userId);
		List<SessionResultStatus> response = sessionResultStatusService.getSessionResultBySessionIdUserId(sessionId,userId);
		System.out.println(response);
		return response;
	}
}