package com.csmtech.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.csmtech.dto.TopicMasterDto;
import com.csmtech.entity.TopicMaster;

public interface TopicMasterService {

	TopicMaster saveTopic(TopicMasterDto topicDto);

	List<TopicMaster> viewTopicData();

	void deleteTopic(Integer topicId);

	TopicMaster getTopicById(Integer topicId);

	/***
	 * @author akshaykirti.muduli
	 */
	TopicMaster getTopicByUserIdAndScheduleId(Integer userId, Integer scheduleForId);

	/***
	 * @author akshaykirti.muduli
	 */
	TopicMaster saveRecordedTopic(MultipartFile file, Integer topicId);
}
