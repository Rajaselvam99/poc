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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="tbl_referred_job")
public class JobRefer {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "DN_ID", nullable = false, unique = true)
	private String id;
	
	@OneToOne (targetEntity = PostJob.class, fetch = FetchType.EAGER)
	@JoinColumn (name = "DN_POSTJOB")
	private PostJob job;
	
	@OneToOne (targetEntity = Jobseeker.class, fetch = FetchType.EAGER)
	@JoinColumn (name = "DN_JOBSEEKER")
	private Jobseeker jobseeker;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
	private Date createdOn = new Date();
	
	@OneToOne (targetEntity = Jobseeker.class, fetch = FetchType.EAGER)
	@JoinColumn (name = "DN_REFER_JOBSEEKER")
	private Jobseeker referedjobseeker;

	private String referEmail;
	
	@Transient
	private long totalApplication;
	
	@Transient
	private long referApplication;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public Jobseeker getReferedjobseeker() {
		return referedjobseeker;
	}

	public void setReferedjobseeker(Jobseeker referedjobseeker) {
		this.referedjobseeker = referedjobseeker;
	}

	public String getReferEmail() {
		return referEmail;
	}

	public void setReferEmail(String referEmail) {
		this.referEmail = referEmail;
	}

	public long getTotalApplication() {
		return totalApplication;
	}

	public void setTotalApplication(long totalApplication) {
		this.totalApplication = totalApplication;
	}

	public long getReferApplication() {
		return referApplication;
	}

	public void setReferApplication(long referApplication) {
		this.referApplication = referApplication;
	}
}
