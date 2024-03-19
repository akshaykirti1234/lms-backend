
package com.csmtech.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "ASSESSMENTMASTER")
public class AssessmentMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ASSESSMENTID")
	private Integer assessmentId;

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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDON")
	private Date createdOn;

	@Column(name = "UPDATEDBY")
	private Integer updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDON")
	private Date updatedOn;

	@Column(name = "DELETEDFLAG")
	private Boolean deletedFlag;

}
