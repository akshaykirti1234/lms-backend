package com.csmtech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csmtech.entity.TopicMaster;
import com.csmtech.service.TopicMasterService;

@CrossOrigin
@RequestMapping("/api/topic")
@RestController
public class TopicMasterController {

	@Autowired
	private TopicMasterService topicMasterService;

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
