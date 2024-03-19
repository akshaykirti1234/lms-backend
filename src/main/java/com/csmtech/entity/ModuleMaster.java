package com.csmtech.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "modulemaster")
public class ModuleMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MODULEID")
	private Integer moduleId;

	@Column(name = "MODULENAME")
	private String moduleName;

	@Column(name = "MODULEDESCRIPTION")
	private String moduleDescription;

	@Column(name = "LOGO")
	private String logo;

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
