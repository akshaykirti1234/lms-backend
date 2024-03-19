package com.csmtech.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "notifymaster")
public class NotifyMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NOTIFYID")
	private Integer notifyId;

	@Column(name = "NOTIFYNAME")
	private String notifyName;

	@Column(name = "NOTIFYSTATUS")
	private String notifyStatus;

	@Column(name = "CREATEDBY")
	private String createdBy;

	@Column(name = "CREATEDON")
	private Date createdOn;

	@Column(name = "UPDATEDBY")
	private String updatedBy;

	@Column(name = "UPDATEDON")
	private Date updatedOn;

	@Column(name = "DELETEDFLAG")
	private boolean deletedFlag;
}
