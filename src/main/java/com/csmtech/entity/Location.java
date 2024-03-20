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
@Table(name = "locationmaster")
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LOCATIONID")
	private Integer locationId;

	@Column(name = "LOCATIONNAME")
	private String locationName;

	@Column(name = "CREATEDBY")
	private Integer createdBy;

	@Column(name = "UPDATEDBY")
	private Integer updatedBy;

}
