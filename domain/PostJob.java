package com.obs.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tbl_post")
public class PostJob {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "DN_ID", nullable = false, unique = true)
	private String postId;

	private String name;

	private String description;

	private String pathway;

	private String skillsRequired;

	private int jobType;

	private String inviteLink;

	private String inviteUrl;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
	private Date createdOn = new Date();

	public PostJob(String name, String description, String skillsRequired, String department) {
		super();
		this.name = name;
		this.description = description;
		this.skillsRequired = skillsRequired;
		this.department = department;
	}

	public PostJob() {
		super();
	}

	private String department;

	private double salary;
	
	private double salaryMin;
	
	private String salaryMax;
	
	private double fee;

	@Transient
	private String applyStatus;

	@Transient
	private String isApplied;

	@Transient
	private boolean saved;

	@OneToOne(targetEntity = Employer.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "user")
	private Employer employer;

	@Column(name = "active", columnDefinition = "tinyint(1) default 1")
	private boolean active;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "DN_POST_JOB_ID")
	private List<PostJobQuestion> questions;

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPathway() {
		return pathway;
	}

	public void setPathway(String pathway) {
		this.pathway = pathway;
	}

	public String getSkillsRequired() {
		return skillsRequired;
	}

	public void setSkillsRequired(String skillsRequired) {
		this.skillsRequired = skillsRequired;
	}

	public int getJobType() {
		return jobType;
	}

	public void setJobType(int jobType) {
		this.jobType = jobType;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getInviteLink() {
		return inviteLink;
	}

	public void setInviteLink(String inviteLink) {
		this.inviteLink = inviteLink;
	}

	public String getInviteUrl() {
		return inviteUrl;
	}

	public void setInviteUrl(String inviteUrl) {
		this.inviteUrl = inviteUrl;
	}

	public String getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getIsApplied() {
		return isApplied;
	}

	public void setIsApplied(String isApplied) {
		this.isApplied = isApplied;
	}

	public double getSalaryMin() {
		return salaryMin;
	}

	public void setSalaryMin(double salaryMin) {
		this.salaryMin = salaryMin;
	}

	public String getSalaryMax() {
		return salaryMax;
	}

	public void setSalaryMax(String salaryMax) {
		this.salaryMax = salaryMax;
	}

	public List<PostJobQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<PostJobQuestion> questions) {
		this.questions = questions;
	}

}
