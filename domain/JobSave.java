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
@Table(name = "tbl_saved_job")
public class JobSave {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "DN_ID", nullable = false, unique = true)
	private String id;

	@Column(name = "DC_POST_JOB_ID")
	private String postJobId;

	@Column(name = "DC_JOBSEKER_ID")
	private String jobseekerId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
	private Date createdOn = new Date();

	@Column(name = "DD_UPDATED_ON")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	private Date updatedon;

	@Column(name = "DB_ACTIVE", columnDefinition = "tinyint(1) default 1")
	private boolean active;

	@Column(name = "DB_DELETED", columnDefinition = "tinyint(1) default 0")
	private boolean deleted;

	@Column(name = "DB_SAVED", columnDefinition = "tinyint(1) default 1")
	private boolean saved;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPostJobId() {
		return postJobId;
	}

	public void setPostJobId(String postJobId) {
		this.postJobId = postJobId;
	}

	public String getJobseekerId() {
		return jobseekerId;
	}

	public void setJobseekerId(String jobseekerId) {
		this.jobseekerId = jobseekerId;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
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

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public Date getUpdatedon() {
		return updatedon;
	}

	public void setUpdatedon(Date updatedon) {
		this.updatedon = updatedon;
	}

}
