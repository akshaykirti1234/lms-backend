package com.csmtech.service;

import org.springframework.web.multipart.MultipartFile;

import com.csmtech.entity.TopicMaster;

public interface TopicMasterService {

	/***
	 * @author akshaykirti.muduli
	 */
	TopicMaster getTopicByUserIdAndScheduleId(Integer userId, Integer scheduleForId);

	/***
	 * @author akshaykirti.muduli
	 */
	TopicMaster saveRecordedTopic(MultipartFile file, Integer topicId);

}
