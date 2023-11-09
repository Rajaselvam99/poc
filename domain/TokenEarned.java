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
@Table(name = "tbl_token_earned")
public class TokenEarned {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "DN_ID", nullable = false, unique = true)
	private String id;

	private double tokenByJob;

	private double tokenByNetwork;

	private double tokenFromUser;
	
	private double tokenByAdmin;

	private double totalToken;

	private String jobseekerId;

	private String referrerJobseekerId;

	private String tokenStatus;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
	private Date createdOn = new Date();

	@Column(name = "active", columnDefinition = "tinyint(1) default 1")
	private boolean active;

	@Column(name = "deleted", columnDefinition = "tinyint(1) default 0")
	private boolean deleted;

	@Column(name = "approve", columnDefinition = "tinyint(1) default 0")
	private boolean approve;

	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "DN_USER")
	private User user;

	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "DN_REFERRER_USER")
	private User referrerUser;

	private String jobSeekerEmail;

	private int type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getTokenByJob() {
		return tokenByJob;
	}

	public void setTokenByJob(double tokenByJob) {
		this.tokenByJob = tokenByJob;
	}

	public double getTokenByNetwork() {
		return tokenByNetwork;
	}

	public void setTokenByNetwork(double tokenByNetwork) {
		this.tokenByNetwork = tokenByNetwork;
	}

	public double getTokenFromUser() {
		return tokenFromUser;
	}

	public void setTokenFromUser(double tokenFromUser) {
		this.tokenFromUser = tokenFromUser;
	}

	public double getTotalToken() {
		return totalToken;
	}

	public void setTotalToken(double totalToken) {
		this.totalToken = totalToken;
	}

	public String getJobseekerId() {
		return jobseekerId;
	}

	public void setJobseekerId(String jobseekerId) {
		this.jobseekerId = jobseekerId;
	}

	public String getReferrerJobseekerId() {
		return referrerJobseekerId;
	}

	public void setReferrerJobseekerId(String referrerJobseekerId) {
		this.referrerJobseekerId = referrerJobseekerId;
	}

	public String getTokenStatus() {
		return tokenStatus;
	}

	public void setTokenStatus(String tokenStatus) {
		this.tokenStatus = tokenStatus;
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

	public boolean isApprove() {
		return approve;
	}

	public void setApprove(boolean approve) {
		this.approve = approve;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getReferrerUser() {
		return referrerUser;
	}

	public void setReferrerUser(User referrerUser) {
		this.referrerUser = referrerUser;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getJobSeekerEmail() {
		return jobSeekerEmail;
	}

	public void setJobSeekerEmail(String jobSeekerEmail) {
		this.jobSeekerEmail = jobSeekerEmail;
	}

	public double getTokenByAdmin() {
		return tokenByAdmin;
	}

	public void setTokenByAdmin(double tokenByAdmin) {
		this.tokenByAdmin = tokenByAdmin;
	}

}
