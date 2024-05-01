package com.csmtech.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name = "SESSIONMASTER")
public class SessionMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SESSIONID")
	private Integer sessionId;

	@ManyToOne
	@JoinColumn(name = "SUBMODULEID")
	private SubModule subModule;

	@Column(name = "SESSIONNAME")
	private String sessionName;

	@ManyToOne
	@JoinColumn(name = "SCHEDULEFORID")
	private ScheduleForMaster scheduleFor;

	@Column(name = "VEDIO")
	private String video;

	@Column(name = "DOCUMENT")
	private String document;

	@Column(name = "SESSIONDESCRIPTION", columnDefinition = "TEXT")
	private String sessionDescription;

	@Column(name = "ISLASTSESSION")
	private Boolean isLastSession;

	@Column(name = "CREATEDBY")
	private Integer createdBy;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "CREATEDON")
//	private Date createdOn;

	@Column(name = "UPDATEDBY")
	private Integer updatedBy;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "UPDATEDON")
//	private Date updatedOn;
//
//	@Column(name = "DELETEDFLAG")
//	private Boolean deletedFlag;

	@Transient
	private boolean resultStatus;

	@Transient
	private boolean accessStatus;

}
