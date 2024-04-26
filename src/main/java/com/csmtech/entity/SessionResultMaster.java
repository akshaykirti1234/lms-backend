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
@Table(name = "sessionresultmaster")
public class SessionResultMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SESSIONRESULTMASTERID")
	private Integer sessionResultMasterId;

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
	@JoinColumn(name = "SESSIONID")
	private SessionMaster sessionMaster;

	@ManyToOne
	@JoinColumn(name = "USERID")
	private UserMaster userMaster;

	@ManyToOne
	@JoinColumn(name = "SESSIONASSESSMENTMASTERID")
	private SessionAssessmentMaster sessionAssessmentMaster;

	@Column(name = "ACTUALANSWER")
	private String actualAnswer;

	@Column(name = "GIVENANSWER")
	private String givenAnswer;

	@Column(name = "RESULTSTATUS")
	private Boolean resultStatus;
}
