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

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tbl_jobseeker")
public class Jobseeker {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "DN_ID", nullable = false, unique = true)
	private String jobseekerId;

	private String firstName;

	private String lastName;

	private String occupation;

	private String profileImage;

	@OneToOne(targetEntity = Country.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "nationality")
	private Country nationality;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "DN_JOBSEEKER_EXPERIENCE")
	private List<JobsekerExperience> experience;

	private String bio;

	@Column(name = "training", columnDefinition = "tinyint(1) default 0")
	private boolean training;

	public List<JobseekerPathway> getPathway() {
		return pathway;
	}

	public void setPathway(List<JobseekerPathway> pathway) {
		this.pathway = pathway;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "DN_JOBSEEKER_PATHWAY")
	private List<JobseekerPathway> pathway;

	public List<JobsekerExperience> getExperience() {
		return experience;
	}

	public void setExperience(List<JobsekerExperience> experience) {
		this.experience = experience;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
	private Date createdOn = new Date();

	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "user")
	private User userInfo;

	private String resume;

	private String skill;

	public String getFirstName() {
		return firstName;
	}

	public Jobseeker() {
		super();
	}

	public Jobseeker(String firstName, String lastName, String occupation, User userInfo) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.occupation = occupation;
		this.userInfo = userInfo;
	}

	public String getJobseekerId() {
		return jobseekerId;
	}

	public void setJobseekerId(String jobseekerId) {
		this.jobseekerId = jobseekerId;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public Country getNationality() {
		return nationality;
	}

	public void setNationality(Country nationality) {
		this.nationality = nationality;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public User getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(User userInfo) {
		this.userInfo = userInfo;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public boolean isTraining() {
		return training;
	}

	public void setTraining(boolean training) {
		this.training = training;
	}

}
