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
@Table(name = "resultstatus")
public class ResultStatus {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RESULTSTATUSID")
	private Integer resultStatusId;

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

	@Column(name = "PERCENTAGE")
	private Double percentage;

	@Column(name = "STATUSOFRESULT")
	private Boolean statusOfResult;
	
	@Column(name = "CREATEDBY")
	private Integer createdBy;

    @Column(name = "UPDATEDBY")
	private Integer updatedBy;

}
