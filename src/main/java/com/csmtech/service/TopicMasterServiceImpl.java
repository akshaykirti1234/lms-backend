package com.csmtech.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.csmtech.dto.TopicMasterDto;
import com.csmtech.entity.ScheduleForMaster;
import com.csmtech.entity.TopicMaster;
import com.csmtech.entity.UserMaster;
import com.csmtech.repository.TopicMasterRepository;
import com.csmtech.util.EmailServiceUtil;
import com.csmtech.util.FileUpload;

@Service
public class TopicMasterServiceImpl implements TopicMasterService {

	private static final Logger logger = LoggerFactory.getLogger(TopicMasterServiceImpl.class);

	@Autowired
	private TopicMasterRepository topicMasterRepository;

	@Autowired
	private EmailServiceUtil emailServiceUtil;

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

	/***
	 * @author akshaykirti.muduli
	 */
	@Override
	public TopicMaster getTopicByUserIdAndScheduleId(Integer userId, Integer scheduleForId) {
		return topicMasterRepository.getTopicByUserIdAndScheduleId(userId, scheduleForId);
	}

	/***
	 * @author akshaykirti.muduli
	 */
	@Override
	@Transactional
	public TopicMaster saveRecordedTopic(MultipartFile file, Integer topicId) {
		try {
			String uploadFile = FileUpload.uploadFile(file);

			TopicMaster topicMaster = topicMasterRepository.findById(topicId)
					.orElseThrow(() -> new RuntimeException("Topic not found"));
			topicMaster.setVideoPath(uploadFile);

			TopicMaster saveTopicMaster = topicMasterRepository.save(topicMaster);
			emailServiceUtil.sendRecording(saveTopicMaster.getReferTo(), saveTopicMaster.getVideoPath());

			return saveTopicMaster;
		} catch (Exception e) {
			// Log the exception
			// You can use any logging framework or System.out.println
			System.out.println("Exception occurred: " + e.getMessage());
			throw new RuntimeException("Failed to save recorded topic and send email", e);
		}
	}

}