package com.csmtech.service;
import java.util.List;
import com.csmtech.dto.TopicMasterDto;
import com.csmtech.entity.TopicMaster;


public interface TopicMasterService {

	 TopicMaster saveTopic(TopicMasterDto topicDto);

	List<TopicMaster> viewTopicData();

	void deleteTopic(Integer topicId);

	TopicMaster getTopicById(Integer topicId);
}
