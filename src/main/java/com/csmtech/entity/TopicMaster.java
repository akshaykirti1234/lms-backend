package com.csmtech.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "topic")
public class TopicMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TOPICID")
	private Integer topicId;

	@Column(name = "TOPICNAME")
	private String topicName;

	@Column(name = "REFFERTO")
	private String referTo;

	@Column(name = "VIDEOPATH")
	private String videoPath;

	@ManyToOne
	@JoinColumn(name = "USERID")
	private UserMaster userMaster;

	@ManyToOne
	@JoinColumn(name = "SCHEDULEFORID")
	private ScheduleForMaster scheduleForMaster;

}
