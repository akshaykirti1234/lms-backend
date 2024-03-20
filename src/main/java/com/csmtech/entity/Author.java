
package com.csmtech.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "author")
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AUTHID")
	private Integer authId;

	@Column(name = "AUTHNAME")
	private String authName;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "CREATEDBY")
	private Integer createdBy;

//	@Column(name = "CREATEDON")
//	private Date createdOn;

	@Column(name = "UPDATEDBY")
	private Integer updatedBy;

//	@Column(name = "UPDATEDON")
//	private Date updatedOn;
//
//	@Column(name = "DELETEDFLAG")
//	private boolean deletedFlag;

}