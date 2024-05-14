package com.csmtech.dto;

import lombok.Data;

@Data
public class TopicMasterDto {
	private Integer topicId;

	private String topicName;

	private String referTo;

	private String videoPath;

	private Integer userId;

	private Integer scheduleForMaster;

}
