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

import lombok.Data;

@Data
@Entity
@Table(name = "usertraninfo")
public class UserTranInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USERTRANID")
	private Integer userTransId;

	@ManyToOne
	@JoinColumn(name = "USERID")
	private UserMaster userMaster;

	@ManyToOne
	@JoinColumn(name = "SESSIONID")
	private SessionMaster sessionMaster;

	@Column(name = "STARTTIME")
	private Date startTime;

	@Column(name = "ENDTIME")
	private Date endTime;

	@Column(name = "RATING")
	private Integer rating;

}
