package com.obs.customdomain;

import java.util.List;

import com.obs.domain.PostJobQuestion;

public class PostJobInput {

	private String postId;
	
	private String name;
	
	private String description;
	
	private String pathway;
	
	private String skillsRequired;
	
	private int jobType;
	
	private String department;
	
	private double salary;
	
	private double salaryMin;
	
	private String salaryMax;
	
	private double fee;
	
	private String employerId;
	
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

	public String getEmployerId() {
		return employerId;
	}

	public void setEmployerId(String employerId) {
		this.employerId = employerId;
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
