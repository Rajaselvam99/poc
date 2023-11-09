package com.obs.view;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "view_jobseeker_pathway")
@Immutable
public class JobseekerPathwayView {

	@Id
	private String pathwayId;

	private String userId;

	private String email;

	private String jobseekerName;

	private String country;

	private String currentPath;

	private String targetPath;

	private String jobseekerId;

	public String getPathwayId() {
		return pathwayId;
	}

	public void setPathwayId(String pathwayId) {
		this.pathwayId = pathwayId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getJobseekerName() {
		return jobseekerName;
	}

	public void setJobseekerName(String jobseekerName) {
		this.jobseekerName = jobseekerName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCurrentPath() {
		return currentPath;
	}

	public void setCurrentPath(String currentPath) {
		this.currentPath = currentPath;
	}

	public String getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}

	public String getJobseekerId() {
		return jobseekerId;
	}

	public void setJobseekerId(String jobseekerId) {
		this.jobseekerId = jobseekerId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
