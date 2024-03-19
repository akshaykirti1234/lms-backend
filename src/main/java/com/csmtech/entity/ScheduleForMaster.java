package com.csmtech.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "scheduleformaster")
public class ScheduleForMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SCHEDULEFORID")
	private Integer scheduleForId;

	@Column(name = "SCHEDULEFOR")
	private String scheduleForName;

	@ManyToOne
	@JoinColumn(name = "SUBMODULEID")
	private SubModule subModule;

	@ManyToOne
	@JoinColumn(name = "AUTHID")
	private Author author;

	@ManyToOne
	@JoinColumn(name = "TECHID")
	private Technology technology;

	@Column(name = "NOOFSESSION")
	private Integer noOfSession;

	@Column(name = "NOOFHOURS")
	private Integer noOfHours;

	@Column(name = "ISASSESMENTPREPARED")
	private Boolean isAssessmentPrepared;

	@Column(name = "CREATEDBY")
	private Integer createdBy;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDON", updatable = false)
	private Date createdOn;

	@Column(name = "UPDATEDBY")
	private Integer updatedBy;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDON", insertable = false)
	private Date updatedOn;

	@Column(name = "DELETEDFLAG")
	private Boolean deletedFlag;

}
