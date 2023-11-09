package com.obs.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tbl_applied_job")
public class JobApply {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "DN_ID", nullable = false, unique = true)
	private String id;

	@OneToOne(targetEntity = PostJob.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "DN_POSTJOB")
	private PostJob job;

	@OneToOne(targetEntity = Jobseeker.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "DN_JOBSEEKER")
	private Jobseeker jobseeker;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
	private Date createdOn = new Date();

	@Column(name = "DB_ACTIVE", columnDefinition = "tinyint(1) default 1")
	private boolean active;

	private String applyStatus;

	private String referby;

	public PostJob getJob() {
		return job;
	}

	public void setJob(PostJob job) {
		this.job = job;
	}

	public Jobseeker getJobseeker() {
		return jobseeker;
	}

	public void setJobseeker(Jobseeker jobseeker) {
		this.jobseeker = jobseeker;
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

	public String getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReferby() {
		return referby;
	}

	public void setReferby(String referby) {
		this.referby = referby;
	}

}
