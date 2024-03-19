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
@Table(name = "usermaster")
public class UserMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USERID")
	private Integer userId;

	@Column(name = "NORMALPASSWORD")
	private String normalPassword;

	@Column(name = "UPASSWORD")
	private String password;

	@Column(name = "FULLNAME")
	private String fullName;

	@Column(name = "CONTACTNO")
	private String contactNo;

	@Column(name = "EMAILID")
	private String emailId;

	@Column(name = "DESIGNATION")
	private String designation;

	@Column(name = "DEPARTMENT")
	private String department;

	@ManyToOne
	@JoinColumn(name = "LOCATIONID")
	private Location location;

	@ManyToOne
	@JoinColumn(name = "USERTYPEID")
	private UserType userType;

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
