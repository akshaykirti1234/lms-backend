package com.csmtech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csmtech.service.TopicMasterService;

@CrossOrigin
@RequestMapping("/api/topic/")
@RestController
public class TopicMasterController {

	@Autowired
	private TopicMasterService topicMasterService;
}
