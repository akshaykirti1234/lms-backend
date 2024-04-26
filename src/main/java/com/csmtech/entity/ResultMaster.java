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
@Table(name = "resultmaster")
public class ResultMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RESULTMASTERID")
	private Integer resultMasterId;

	@ManyToOne
	@JoinColumn(name = "MODULEID")
	private ModuleMaster moduleMaster;

	@ManyToOne
	@JoinColumn(name = "SUBMODULEID")
	private SubModule subModule;

	@ManyToOne
	@JoinColumn(name = "SCHEDULEFORID")
	private ScheduleForMaster scheduleForMaster;

	@ManyToOne
	@JoinColumn(name = "USERID")
	private UserMaster userMaster;

	@ManyToOne
	@JoinColumn(name = "ASSESSMENTMASTERID")
	private AssessmentMaster assessmentMaster;

	@Column(name = "ACTUALANSWER")
	private String actualAnswer;

	@Column(name = "GIVENANSWER")
	private String givenAnswer;

	@Column(name = "RESULTSTATUS")
	private Boolean resultStatus;

}
