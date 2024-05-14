package com.csmtech.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csmtech.dto.TopicMasterDto;
import com.csmtech.entity.ScheduleForMaster;
import com.csmtech.entity.TopicMaster;
import com.csmtech.entity.UserMaster;
import com.csmtech.repository.TopicMasterRepository;

@Service
public class TopicMasterServiceImpl implements TopicMasterService {

	private static final Logger logger = LoggerFactory.getLogger(TopicMasterServiceImpl.class);

	@Autowired
	private TopicMasterRepository topicMasterRepository;

	@Override
	public TopicMaster saveTopic(TopicMasterDto topicDto) {
		logger.info("saveTopic method of TopicMasterServiceImpl is executed");
		TopicMaster topic = new TopicMaster();
		topic.setTopicId(topicDto.getTopicId());
		topic.setTopicName(topicDto.getTopicName());
		topic.setReferTo(topicDto.getReferTo());

		UserMaster userMaster = new UserMaster();
		userMaster.setUserId(topicDto.getUserId());

		ScheduleForMaster scheduleForMaster = new ScheduleForMaster();
		scheduleForMaster.setScheduleForId(1);

		topic.setUserMaster(userMaster);
		topic.setScheduleForMaster(scheduleForMaster);
		// topic.setCreatedBy(1);
		// topic.setUpdatedBy(1);

		return topicMasterRepository.save(topic);
	}

	@Override
	public List<TopicMaster> viewTopicData() {
		logger.info("viewTopicData method of TopicMasterServiceImpl is executed");
		return topicMasterRepository.viewTopicData();
	}

	@Override
	public void deleteTopic(Integer topicId) {
		logger.info("deleteTopic method of TopicMasterServiceImpl is executed");
		topicMasterRepository.deleteTopic(topicId);
	}

	@Override
	public TopicMaster getTopicById(Integer topicId) {
		logger.info("getTopicById method of TopicMasterServiceImpl is executed");
		return topicMasterRepository.getTopicById(topicId);
	}

}