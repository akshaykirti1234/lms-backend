package com.csmtech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.csmtech.entity.TopicMaster;
import com.csmtech.repository.TopicMasterRepository;
import com.csmtech.util.EmailServiceUtil;
import com.csmtech.util.FileUpload;

@Service
public class TopicMasterServiceImpl implements TopicMasterService {

	@Autowired
	private TopicMasterRepository topicMasterRepository;
	@Autowired
	private EmailServiceUtil emailServiceUtil;

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
	public TopicMaster saveRecordedTopic(MultipartFile file, Integer topicId) {

		String uploadFile = FileUpload.uploadFile(file);

		TopicMaster topicMaster = topicMasterRepository.findById(topicId).get();
		topicMaster.setVideoPath(uploadFile);

		TopicMaster saveTopicMaster = topicMasterRepository.save(topicMaster);
		emailServiceUtil.sendRecording(saveTopicMaster.getReferTo(), saveTopicMaster.getVideoPath());

		return saveTopicMaster;
	}

}
