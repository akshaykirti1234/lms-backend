package com.csmtech.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csmtech.dto.TopicMasterDto;
import com.csmtech.entity.TopicMaster;
import com.csmtech.service.TopicMasterService;

@CrossOrigin
@RequestMapping("/api/topic")
@RestController
public class TopicMasterController {

	private static final Logger logger = LoggerFactory.getLogger(TopicMasterController.class);

	@Autowired
	private TopicMasterService topicMasterService;

	@PostMapping("/saveTopic")
	public ResponseEntity<?> saveTopic(@RequestBody TopicMasterDto topicDto) {
		logger.info("saveTopic method of TopicMasterController is executed");
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
		logger.info("viewTopicData method of TopicMasterController is executed");
		List<TopicMaster> TopicData = topicMasterService.viewTopicData();
		return ResponseEntity.ok().body(TopicData);

	}

	@GetMapping("/getTopic/{topicId}")
	public ResponseEntity<TopicMaster> getTopicById(@PathVariable("topicId") Integer topicId) {
		logger.info("getTopicById method of TopicMasterController is executed");
		TopicMaster TopicData = topicMasterService.getTopicById(topicId);
		return ResponseEntity.ok().body(TopicData);

	}

	@DeleteMapping("deleteTopic/{topicId}")
	public ResponseEntity<Map<String, Object>> deleteTopic(@PathVariable("topicId") Integer topicId) {
		logger.info("deleteTopic method of TopicMasterController is executed");
		Map<String, Object> response = new HashMap<>();
		topicMasterService.deleteTopic(topicId);
		response.put("status", "deleted");
		return ResponseEntity.ok().body(response);
	}

	/**
	 * @author akshaykirti.muduli
	 */
	@GetMapping("/getTopicByUserIdAndScheduleId/{userId}/{scheduleForId}")
	public ResponseEntity<?> getTopicByUserIdAndScheduleId(@PathVariable Integer userId,
			@PathVariable Integer scheduleForId) {
		try {
			TopicMaster topicMaster = topicMasterService.getTopicByUserIdAndScheduleId(userId, scheduleForId);
			if (topicMaster != null && topicMaster.getTopicId() != null) {
				return new ResponseEntity<>(topicMaster, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/***
	 * @author akshaykirti.muduli
	 */
	@PostMapping("/saveRecordedTopic")
	public ResponseEntity<?> saveRecordedTopic(@RequestParam("file") MultipartFile file,
			@RequestParam("topicId") Integer topicId) {
		try {
			TopicMaster savedTopicMaster = topicMasterService.saveRecordedTopic(file, topicId);
			return new ResponseEntity<>(savedTopicMaster, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
