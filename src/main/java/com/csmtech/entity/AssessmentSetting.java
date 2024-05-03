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

@Entity
@Data
@Table(name = "assessmentsetting")
public class AssessmentSetting {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ASSESSMENTSETTINGID")
	private Integer assessmentSettingId;

	@ManyToOne
	@JoinColumn(name = "MODULEID")
	private ModuleMaster moduleMaster;

	@ManyToOne
	@JoinColumn(name = "SUBMODULEID")
	private SubModule subModule;

	@ManyToOne
	@JoinColumn(name = "SCHEDULEFORID")
	private ScheduleForMaster scheduleForMaster;

	@Column(name = "NOOFQUESTION")
	private Integer numberOfQuestion;
	
	@Column(name = "PASSINGPERCENTAGE")
	private Double passingPercentage;
}
