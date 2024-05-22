
package com.csmtech.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getSessionResult(@PathVariable("userId") Integer userId) {
        logger.info("getSessionResult method of SessionResultStatusController is executed");
        try {
            List<SessionResultStatus> response = sessionResultStatusService.getSessionResultStatus(userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error occurred while fetching session result for userId {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }

    @PostMapping("/getSessionResultBySessionIdUserId/{sessionId}/{userId}")
    public ResponseEntity<?> getSessionResultBySessionIdUserId(@PathVariable("sessionId") Integer sessionId, @PathVariable("userId") Integer userId) {
        logger.info("getSessionResultBySessionIdUserId method of SessionResultStatusController is executed");
        try {
            List<SessionResultStatus> response = sessionResultStatusService.getSessionResultBySessionIdUserId(sessionId, userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error occurred while fetching session result for sessionId {} and userId {}", sessionId, userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
        }
    }
}