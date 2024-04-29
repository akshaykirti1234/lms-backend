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
@Table(name = "sessionassessmentmaster")
public class SessionAssessmentMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SESSIONASSESSMENTMASTERID")
	private Integer sessionAssesmentMasterId;
	
	
	@Column(name = "MODULEID")
	private Integer moduleId;

	@Column(name = "SUBMODULEID")
	private Integer submoduleId;
	
	@Column(name = "SESSIONID")
	private Integer sessionId;

	@Column(name = "SCHEDULEFORID")
	private Integer scheduleForId;

	@Column(name = "QUESTION")
	private String question;

	@Column(name = "OPTION1")
	private String option1;

	@Column(name = "OPTION2")
	private String option2;

	@Column(name = "OPTION3")
	private String option3;

	@Column(name = "OPTION4")
	private String option4;

	@Column(name = "ANSWER")
	private String answer;

	@Column(name = "CREATEDBY")
	private Integer createdBy;

	@Column(name = "UPDATEDBY")
	private Integer updatedBy;

}
