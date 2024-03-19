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
@Table(name = "submodulemaster")
public class SubModule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SUBMODULEID")
	private Integer submoduleId;

	@ManyToOne
	@JoinColumn(name = "MODULEID")
	private ModuleMaster moduleMaster;

	@Column(name = "SUBMODULENAME")
	private String submoduleName;

	@Column(name = "CREATEDBY")
	private Integer createdBy;

	@Column(name = "CREATEDON")
	private Date createdOn;

	@Column(name = "UPDATEDBY")
	private Integer updatedBy;

	@Column(name = "UPDATEDON")
	private Date updatedOn;

	@Column(name = "DELETEDFLAG")
	private boolean deletedFlag;
}
