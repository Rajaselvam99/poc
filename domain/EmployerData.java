package com.obs.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tbl_employer_data")
public class EmployerData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DN_ID", nullable = false)
	private Long id;

	@Column(name = "DN_EMPLOYER")
	private String employer;

	@Column(name = "active", columnDefinition = "tinyint(1) default 1")
	private boolean active;

	@Column(name = "deleted", columnDefinition = "tinyint(1) default 0")
	private boolean deleted;

	@Column(name = "newlyAdded", columnDefinition = "tinyint(1) default 0")
	private boolean newlyAdded;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
	private Date createdon = new Date();

	private String overview;

	private String website;

	private String phone;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isNewlyAdded() {
		return newlyAdded;
	}

	public void setNewlyAdded(boolean newlyAdded) {
		this.newlyAdded = newlyAdded;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
