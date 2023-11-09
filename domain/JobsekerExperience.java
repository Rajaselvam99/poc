package com.obs.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="tbl_experience")
public class JobsekerExperience {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "DN_ID", nullable = false, unique = true)
	private String expId;
	
	private String jobTitle;
	
	private String employer;
	
	private String department;
	
	private String location;
	
	private String country;
	
	private String pathway;
	
	private String pathwayStep;

	@Column(name="DB_PRESENT",columnDefinition="tinyint(1) default 0")
	private boolean present;
	
	@JsonFormat(pattern = "MM-yyyy", timezone = "UTC")
	private Date startFrom;
	
	@JsonFormat(pattern = "MM-yyyy", timezone = "UTC")
	private Date endTo;
	
	private String responsibilities;
	
	public String getExpId() {
		return expId;
	}

	public void setExpId(String expId) {
		this.expId = expId;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	
	public Date getStartFrom() {
		return startFrom;
	}

	public void setStartFrom(Date startFrom) {
		this.startFrom = startFrom;
	}

	public Date getEndTo() {
		return endTo;
	}

	public void setEndTo(Date endTo) {
		this.endTo = endTo;
	}

	public String getPathway() {
		return pathway;
	}

	public void setPathway(String pathway) {
		this.pathway = pathway;
	}

	public String getPathwayStep() {
		return pathwayStep;
	}

	public void setPathwayStep(String pathwayStep) {
		this.pathwayStep = pathwayStep;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public boolean isPresent() {
		return present;
	}

	public void setPresent(boolean present) {
		this.present = present;
	}

	public String getResponsibilities() {
		return responsibilities;
	}

	public void setResponsibilities(String responsibilities) {
		this.responsibilities = responsibilities;
	}


}
