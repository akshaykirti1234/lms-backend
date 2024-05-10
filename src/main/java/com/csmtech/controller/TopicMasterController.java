package com.csmtech.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.dto.TopicMasterDto;
import com.csmtech.entity.TopicMaster;
import com.csmtech.service.TopicMasterService;
import com.csmtech.service.UserMasterService;

@CrossOrigin
@RequestMapping("/api/topic")
@RestController
public class TopicMasterController {

	@Autowired
	private TopicMasterService topicMasterService;

	@Autowired
	private UserMasterService userMasterService;

	@GetMapping("/usersByUserType")
	public ResponseEntity<?> getUsersByUserTypeNotEqualToOne() {
		try {
			List<Map<String, Object>> users = userMasterService.getUseMasterList();
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/saveTopic")
	public ResponseEntity<?> saveTopic(@RequestBody TopicMasterDto topicDto) {
		try {
			// Call the service method to save the topic
			TopicMaster savedTopic = topicMasterService.saveTopic(topicDto);
			return new ResponseEntity<>(savedTopic, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getTopic")
	public ResponseEntity<List<TopicMaster>> viewTopicData() {
		List<TopicMaster> TopicData = topicMasterService.viewTopicData();
		return ResponseEntity.ok().body(TopicData);

	}
	
	
	
	@DeleteMapping("deleteTopic/{topicId}")
	public ResponseEntity<Map<String, Object>> deleteTopic(@PathVariable("topicId") Integer topicId) {
		Map<String, Object> response = new HashMap<>();
		topicMasterService.deleteTopic(topicId);
		response.put("status", "deleted");
		return ResponseEntity.ok().body(response);
	}

}
