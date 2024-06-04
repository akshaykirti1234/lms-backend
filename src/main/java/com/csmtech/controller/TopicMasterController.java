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
            TopicMaster savedTopic = topicMasterService.saveTopic(topicDto);
            return new ResponseEntity<>(savedTopic, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while saving the topic: {}", e.getMessage());
            return new ResponseEntity<>("An error occurred while saving the topic: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTopic")
    public ResponseEntity<?> viewTopicData() {
        logger.info("viewTopicData method of TopicMasterController is executed");
        try {
            List<TopicMaster> topicData = topicMasterService.viewTopicData();
            return ResponseEntity.ok().body(topicData);
        } catch (Exception e) {
            logger.error("An error occurred while retrieving topics: {}", e.getMessage());
            return new ResponseEntity<>("An error occurred while retrieving topics: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTopic/{topicId}")
    public ResponseEntity<?> getTopicById(@PathVariable("topicId") Integer topicId) {
        logger.info("getTopicById method of TopicMasterController is executed");
        try {
            TopicMaster topicData = topicMasterService.getTopicById(topicId);
            if (topicData == null) {
                return new ResponseEntity<>("Topic not found with id: " + topicId, HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok().body(topicData);
        } catch (Exception e) {
            logger.error("An error occurred while retrieving the topic: {}", e.getMessage());
            return new ResponseEntity<>("An error occurred while retrieving the topic: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteTopic/{topicId}")
    public ResponseEntity<?> deleteTopic(@PathVariable("topicId") Integer topicId) {
        logger.info("deleteTopic method of TopicMasterController is executed");
        try {
            topicMasterService.deleteTopic(topicId);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "deleted");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            logger.error("An error occurred while deleting the topic: {}", e.getMessage());
            return new ResponseEntity<>("An error occurred while deleting the topic: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTopicByUserIdAndScheduleId/{userId}/{scheduleForId}")
    public ResponseEntity<?> getTopicByUserIdAndScheduleId(@PathVariable Integer userId,
            @PathVariable Integer scheduleForId) {
        logger.info("getTopicByUserIdAndScheduleId method of TopicMasterController is executed");
        try {
            TopicMaster topicMaster = topicMasterService.getTopicByUserIdAndScheduleId(userId, scheduleForId);
            if (topicMaster != null) {
                return ResponseEntity.ok(topicMaster);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            logger.error("An error occurred while retrieving the topic: {}", e.getMessage());
            return new ResponseEntity<>("An error occurred while retrieving the topic: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/saveRecordedTopic")
    public ResponseEntity<?> saveRecordedTopic(@RequestParam("file") MultipartFile file,
            @RequestParam("topicId") Integer topicId) {
        logger.info("saveRecordedTopic method of TopicMasterController is executed");
        try {
            TopicMaster savedTopicMaster = topicMasterService.saveRecordedTopic(file, topicId);
            return new ResponseEntity<>(savedTopicMaster, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while saving the recorded topic: {}", e.getMessage());
            return new ResponseEntity<>("An error occurred while saving the recorded topic: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

