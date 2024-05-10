package com.csmtech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.repository.TopicMasterRepository;

@Service
public class TopicMasterServiceImpl implements TopicMasterService {

	@Autowired
	private TopicMasterRepository topicMasterRepository;

}
